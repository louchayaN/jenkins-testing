package com.epam.esm.task5.service.util;

import com.epam.esm.task5.service.dto.reservation.TimeSlot;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TimeSlotUtil {

  private static final Integer MINUTES_PER_DAY_FROM_9_TO_9 = 12 * 60;

  private static final LocalTime START_RESERVATION_TIME = LocalTime.of(9, 0);

  private TimeSlotUtil() {
    throw new AssertionError("Class contains static methods only");
  }

  public static List<TimeSlot> getSlotsForQuest(Integer questDuration) {
    int timeSlotsQuantity = MINUTES_PER_DAY_FROM_9_TO_9 / questDuration;
    LocalTime time = START_RESERVATION_TIME;

    List<TimeSlot> timeSlots = new ArrayList<>(timeSlotsQuantity);
    for (int i = 1; i <= timeSlotsQuantity; i++) {
      TimeSlot slot = new TimeSlot();
      slot.setSlotId(i);
      slot.setIsReserved(false);
      slot.setTimeStart(time);
      time = time.plusMinutes(questDuration);
      slot.setTimeEnd(time);
      timeSlots.add(slot);
    }
    return timeSlots;
  }

  public static boolean isTimeSlotExistsForQuest(Integer questDuration, Integer slotId) {
    int timeSlotsQuantity = MINUTES_PER_DAY_FROM_9_TO_9 / questDuration;
    return slotId <= timeSlotsQuantity;
  }

  public static LocalTime getReservationTimeStart(Integer questDuration, Integer slotId) {
    return START_RESERVATION_TIME.plusMinutes(questDuration * (slotId - 1));
  }
}
