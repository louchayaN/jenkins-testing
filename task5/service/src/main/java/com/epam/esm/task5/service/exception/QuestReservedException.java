package com.epam.esm.task5.service.exception;

public class QuestReservedException extends RuntimeException {

    public QuestReservedException() {
        super();
    }

    public QuestReservedException(String message) {
        super(message);
    }
}
