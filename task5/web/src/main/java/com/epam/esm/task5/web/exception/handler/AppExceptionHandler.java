package com.epam.esm.task5.web.exception.handler;

import com.epam.esm.task5.service.dto.ResponseMessage;
import com.epam.esm.task5.service.exception.*;
import com.epam.esm.task5.web.exception.ReservationNotFoundException;
import com.epam.esm.task5.web.exception.TagNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

  private MessageSource messageSource;

  @Autowired
  public void setMessages(ResourceBundleMessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @ExceptionHandler(QuestNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseMessage handleQuestNotFound(QuestNotFoundException e, Locale locale) {
    String message =
        messageSource.getMessage("quest.notfound", new Object[] {e.getQuestId()}, locale);
    return new ResponseMessage(404, message);
  }

  @ExceptionHandler(QuestReservationsDateException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseMessage handleInvalidQuestPossibleReservationDates(
          QuestReservationsDateException e, Locale locale) {
    String message =
            messageSource.getMessage("quest.reservationDates.invalid", new Object[] {}, locale);
    return new ResponseMessage(400, message);
  }

  @ExceptionHandler(QuestSearchPriceException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseMessage handleQuestSearchPriceException(
          QuestSearchPriceException e, Locale locale) {
    String message =
            messageSource.getMessage("questSearch.price.endLessStart", new Object[] {}, locale);
    return new ResponseMessage(400, message);
  }

  @ExceptionHandler(QuestOperationException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ResponseMessage handleQuestNotSupportedOperation(
          QuestOperationException e, Locale locale) {
    String message =
            messageSource.getMessage(
                    "quest.notSupportedOperation.unauthorized", new Object[] {}, locale);
    return new ResponseMessage(403, message);
  }

  @ExceptionHandler(QuestNotAvailableThisDateException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ResponseMessage handleQuestNotAvailableThisDate(
          QuestNotAvailableThisDateException e, Locale locale) {
    String message =
            messageSource.getMessage("reservation.time.notAvailable", new Object[] {}, locale);
    return new ResponseMessage(409, message);
  }

  @ExceptionHandler(QuestReservedThisTimeException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ResponseMessage handleQuestReservedThisTime(
          QuestReservedThisTimeException e, Locale locale) {
    String message = messageSource.getMessage("reservation.time.busy", new Object[] {}, locale);
    return new ResponseMessage(409, message);
  }

  @ExceptionHandler(QuestReservedException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ResponseMessage handleQuestReserved(QuestReservedException e, Locale locale) {
    String message = messageSource.getMessage("quest.deletion.reserved", new Object[] {}, locale);
    return new ResponseMessage(409, message);
  }

  @ExceptionHandler(TagNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseMessage handleTagNotFound(TagNotFoundException e, Locale locale) {
    String message = messageSource.getMessage("tag.notfound", new Object[] {e.getTagId()}, locale);
    return new ResponseMessage(404, message);
  }

  @ExceptionHandler(TagNameDuplicationException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ResponseMessage handleTagNameDuplication(TagNameDuplicationException e, Locale locale) {
    String message =
        messageSource.getMessage("tag.name.duplication", new Object[] {e.getTagName()}, locale);
    return new ResponseMessage(409, message);
  }

  @ExceptionHandler(UserNameDuplicationException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ResponseMessage handleUsernameDuplication(UserNameDuplicationException e, Locale locale) {
    String message =
        messageSource.getMessage("user.name.duplication", new Object[] {e.getUsername()}, locale);
    return new ResponseMessage(409, message);
  }

  @ExceptionHandler(ReservationNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseMessage handleTagNotFound(ReservationNotFoundException e, Locale locale) {
    String message = messageSource.getMessage("reservation.notfound", new Object[] {e.getReservationId()}, locale);
    return new ResponseMessage(404, message);
  }

  @ExceptionHandler(ReservationOperationException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ResponseMessage handleReservationNotSupportedOperation(
      ReservationOperationException e, Locale locale) {
    String message =
        messageSource.getMessage(
            "reservation.notSupportedOperation.unauthorized", new Object[] {}, locale);
    return new ResponseMessage(403, message);
  }

  @ExceptionHandler(ReservationSlotNotExistsException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseMessage handleSlotNotExists(ReservationSlotNotExistsException e, Locale locale) {
    String message =
            messageSource.getMessage(
                    "reservation.slotId.notExists", new Object[] {e.getSlotId()}, locale);
    return new ResponseMessage(404, message);
  }

  @ExceptionHandler(ReservationInThePastDeletionException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ResponseMessage handleReservationInThePastDeletion(
          ReservationInThePastDeletionException e, Locale locale) {
    String message =
            messageSource.getMessage(
                    "reservation.deletion.inThePast", new Object[] {}, locale);
    return new ResponseMessage(409, message);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    List<FieldError> errors = ex.getBindingResult().getFieldErrors();
    return handleArgumentsException(errors);
  }

  @Override
  protected ResponseEntity<Object> handleBindException(
      BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    List<FieldError> errors = ex.getBindingResult().getFieldErrors();
    return handleArgumentsException(errors);
  }

  private ResponseEntity<Object> handleArgumentsException(List<FieldError> errors) {
    ModelMap errorMap = new ModelMap();
    ArrayList<String> messages = new ArrayList<>();
    for (FieldError fieldError : errors) {
      messages.add(fieldError.getDefaultMessage());
    }
    errorMap.addAttribute("message", messages);
    return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    String message = messageSource.getMessage("message.notReadable", null, request.getLocale());
    ResponseMessage responseMessage = new ResponseMessage(400, message);
    return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
      HttpRequestMethodNotSupportedException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    String message = messageSource.getMessage("method.notAllowed", null, request.getLocale());
    ResponseMessage responseMessage = new ResponseMessage(405, message);
    return new ResponseEntity<>(responseMessage, HttpStatus.METHOD_NOT_ALLOWED);
  }

  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(
      NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    String message = messageSource.getMessage("notHandlerFound", null, request.getLocale());
    ResponseMessage responseMessage = new ResponseMessage(404, message);
    return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
  }
}
