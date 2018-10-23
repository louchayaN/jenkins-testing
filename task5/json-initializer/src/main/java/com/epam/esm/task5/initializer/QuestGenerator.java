package com.epam.esm.task5.initializer;

import com.epam.esm.task5.service.dto.quest.QuestDTO;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class QuestGenerator {

  public List<QuestDTO> generateQuestsWithUniqueNames(int questQuantity, String uniqueString) {
    List<QuestDTO> quests = new ArrayList<>(questQuantity);
    for (int i = 0; i < questQuantity; i++) {
      long currentDate = Instant.now().getEpochSecond();

      QuestDTO quest =
          new QuestDTO.Builder()
              .ownerId(getRandomPositiveLong(1, 3))
              .name(getRandomString(20, 30) + uniqueString + "_" + i)
              .description(getRandomString(2, 20))
              .genre(getRandomString(2, 20))
              .price(getRandomPositiveBigDecimal(5, 2))
              .duration(getRandomPositiveInteger(1, 12 * 60 + 1))
              .maxNumberOfPeople(getRandomPositiveInteger(1, 30000))
              .dateOfReservationStart(getRandomPositiveLong(currentDate, currentDate + 100000))
              .dateOfReservationEnd(
                  getRandomPositiveLong(currentDate + 100001, currentDate + 200000))
              .build();

      quests.add(quest);
    }

    return quests;
  }

  private Long getRandomPositiveLong(long minLengthInclusive, long maxLengthExclusive) {
    return RandomUtils.nextLong(minLengthInclusive, maxLengthExclusive);
  }

  private String getRandomString(int minLengthInclusive, int maxLengthExclusive) {
    return RandomStringUtils.randomAlphabetic(minLengthInclusive, maxLengthExclusive);
  }

  private Integer getRandomPositiveInteger(int minLengthInclusive, int maxLengthExclusive) {
    return RandomUtils.nextInt(minLengthInclusive, maxLengthExclusive);
  }

  private BigDecimal getRandomPositiveBigDecimal(int integerDigits, int fractionDigits) {
    BigDecimal randomBigDecimal = BigDecimal.valueOf(RandomUtils.nextFloat(0, integerDigits));
    return randomBigDecimal.setScale(fractionDigits, BigDecimal.ROUND_HALF_UP);
  }
}
