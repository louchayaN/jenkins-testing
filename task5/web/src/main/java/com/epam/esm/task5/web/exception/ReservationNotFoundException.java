package com.epam.esm.task5.web.exception;

public class ReservationNotFoundException extends RuntimeException {

    private final Long reservationId;

    public ReservationNotFoundException(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Long getReservationId() {
        return reservationId;
    }
}
