package com.epam.esm.task5.web.controller;

import com.epam.esm.task5.repository.entity.Tag;
import com.epam.esm.task5.repository.searchcriteria.ReservationSearchCriteria;
import com.epam.esm.task5.repository.util.TokenUtil;
import com.epam.esm.task5.service.ReservationService;
import com.epam.esm.task5.service.dto.Pagination;
import com.epam.esm.task5.service.dto.reservation.ReservationDTO;
import com.epam.esm.task5.service.dto.user.UserInfoDTO;
import com.epam.esm.task5.web.exception.ReservationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/reservations")
@CrossOrigin
public class ReservationController {

  private ReservationService reservationService;

  @Autowired
  public ReservationController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  /**
   * Adds a valid reservation to a user with id specified in authentication object. Returns saved
   * reservation, its uri and http status CREATED.
   *
   * @param reservationDTO valid reservationDTO containing information about quest id for
   *     reservation and time for reservation
   * @param ucb the UriComponentsBuilder Spring object for building uri of the created resource
   * @param authentication the Authentication Spring object for getting user id
   * @return the ResponseEntity with saved reservationDTO, header with the uri of created resource
   *     and http status CREATED
   */
  @PostMapping(consumes = "application/json")
  @PreAuthorize("#oauth2.hasScope('write')")
  public ResponseEntity<ReservationDTO> addReservation(
      @RequestBody @Valid ReservationDTO reservationDTO,
      Authentication authentication,
      UriComponentsBuilder ucb) {

    UserInfoDTO userInfoDTO = new UserInfoDTO();
    userInfoDTO.setId(TokenUtil.getUserIdFromToken(authentication));
    reservationDTO.setUser(userInfoDTO);
    ReservationDTO reservation = reservationService.addReservation(reservationDTO);

    HttpHeaders headers = new HttpHeaders();
    URI locationUri =
        ucb.path("/reservations/")
            .path(String.valueOf(reservation.getReservationId()))
            .build()
            .toUri();
    headers.setLocation(locationUri);

    return new ResponseEntity<>(reservation, headers, HttpStatus.CREATED);
  }

  @GetMapping(value = "/{id}", produces = "application/json")
  public ReservationDTO getReservationById(@PathVariable Long id) {
    ReservationDTO reservation = reservationService.getReservation(id);
    if (reservation == null) {
      throw new ReservationNotFoundException(id);
    }
    return reservation;
  }

  /**
   * Returns portion of reservations for current page and total count of reservations found by the
   * specified search criteria. The returning reservations depend on role of user specified in
   * authentication. If it's QUEST OWNER the search will be between all reservations of this owner.
   * If not, only between reservations of current user.
   *
   * @param criteria the search criteria that consists parameters for pagination
   * @return portion of reservations for current page and total count of found reservations
   */
  @GetMapping(produces = "application/json")
  @PreAuthorize("#oauth2.hasScope('read')")
  public Pagination<ReservationDTO> getReservations(
      @Valid ReservationSearchCriteria criteria, Authentication authentication) {

    if (TokenUtil.isPrincipalHasAdminRole(authentication)) {
      return reservationService.getAllReservations(criteria);
    }

    Long id = TokenUtil.getUserIdFromToken(authentication);
    if (TokenUtil.isPrincipalHasOwnerRole(authentication)) {
      return reservationService.getOwnerReservations(criteria, id);
    }
    return reservationService.getUserReservations(criteria, id);
  }

  @DeleteMapping(value = "/{id}", produces = "application/json")
  @PreAuthorize("#oauth2.hasScope('write')")
  public ResponseEntity<Tag> deleteReservation(
      @PathVariable Long id, Authentication authentication) {
    Long userId = TokenUtil.getUserIdFromToken(authentication);
    reservationService.deleteReservationById(id, userId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
