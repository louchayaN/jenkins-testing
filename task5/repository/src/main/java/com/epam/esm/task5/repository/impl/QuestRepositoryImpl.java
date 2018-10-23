package com.epam.esm.task5.repository.impl;

import com.epam.esm.task5.repository.BasicRepositoryImpl;
import com.epam.esm.task5.repository.QuestRepository;
import com.epam.esm.task5.repository.entity.Quest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

@Repository
public class QuestRepositoryImpl extends BasicRepositoryImpl<Quest> implements QuestRepository {

  private static final String ADD_QUESTS =
      "INSERT INTO quest (q_name, id_owner, description, genre, price, duration, "
          + "date_of_creation, date_of_modification, max_number_of_people, date_of_reservation_start, date_of_reservation_end) "
          + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

  private Calendar tzUTC = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

  @Autowired private JdbcTemplate jdbcTemplate;

  @Override
  public void addQuests(List<Quest> quests) {
    jdbcTemplate.batchUpdate(
        ADD_QUESTS,
        new BatchPreparedStatementSetter() {

          public void setValues(PreparedStatement ps, int i) throws SQLException {
            ps.setString(1, quests.get(i).getName());
            ps.setLong(2, quests.get(i).getOwnerId());
            ps.setString(3, quests.get(i).getDescription());
            ps.setString(4, quests.get(i).getGenre());
            ps.setBigDecimal(5, quests.get(i).getPrice());
            ps.setInt(6, quests.get(i).getDuration());
            ps.setTimestamp(7, instantToTimestamp(quests.get(i).getDateOfCreation()), tzUTC);
            ps.setTimestamp(8, instantToTimestamp(quests.get(i).getDateOfModification()), tzUTC);
            ps.setInt(9, quests.get(i).getMaxNumberOfPeople());
            ps.setTimestamp(
                10, instantToTimestamp(quests.get(i).getDateOfReservationStart()), tzUTC);
            ps.setTimestamp(11, instantToTimestamp(quests.get(i).getDateOfReservationEnd()), tzUTC);
          }

          public int getBatchSize() {
            return quests.size();
          }
        });
  }

  private Timestamp instantToTimestamp(Instant date) {
    return date != null ? new Timestamp(date.toEpochMilli()) : null;
  }
}
