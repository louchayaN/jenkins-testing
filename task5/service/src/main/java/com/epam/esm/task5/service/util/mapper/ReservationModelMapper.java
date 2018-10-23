package com.epam.esm.task5.service.util.mapper;

import com.epam.esm.task5.repository.entity.Quest;
import com.epam.esm.task5.repository.entity.Reservation;
import com.epam.esm.task5.repository.entity.User;
import com.epam.esm.task5.service.dto.quest.ReservedQuestDTO;
import com.epam.esm.task5.service.dto.reservation.ReservationDTO;
import com.epam.esm.task5.service.dto.user.UserInfoDTO;
import org.modelmapper.ModelMapper;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReservationModelMapper {

  private ReservationModelMapper() {
    throw new AssertionError("Class contains static methods only");
  }

  public static Reservation mapToReservation(ReservationDTO reservationDTO) {
    if (reservationDTO == null) {
      return null;
    }

    ModelMapper mapper = new ModelMapper();
    mapper.createTypeMap(Long.class, Instant.class);
    mapper.addConverter(new LongToInstantConverter());
    mapper.getTypeMap(Long.class, Instant.class).setProvider(new InstantProvider());

    return mapper.map(reservationDTO, Reservation.class);
  }

  public static ReservationDTO mapToReservationDTO(Reservation reservation) {
    if (reservation == null) {
      return null;
    }

    ModelMapper mapper = new ModelMapper();
    mapper.addConverter(new InstantToLongConverter());

    return mapper.map(reservation, ReservationDTO.class);
  }

  public static List<ReservationDTO> mapToReservationsDTO(List<Reservation> reservations) {
    if (reservations == null || reservations.isEmpty()) {
      return Collections.emptyList();
    }

    List<ReservationDTO> reservationsDTO = new ArrayList<>();
    for (Reservation reservation : reservations) {
      if(reservation == null){
        continue;
      }
      ReservationDTO reservationDTO = ReservationModelMapper.mapToReservationDTO(reservation);
      Quest quest = reservation.getQuest();
      if (quest != null) {
        ReservedQuestDTO reservedQuestDTO = QuestModelMapper.mapToReservedQuestDTO(quest);
        reservationDTO.setQuest(reservedQuestDTO);
      }
      User user = reservation.getUser();
      if (user != null) {
        UserInfoDTO userInfoDTO = UserModelMapper.mapUserToUserInfoDto(user);
        reservationDTO.setUser(userInfoDTO);
      }
      reservationsDTO.add(reservationDTO);
    }
    return reservationsDTO;
  }
}
