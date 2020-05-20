package com.polling.repository;

import com.polling.model.Poll;
import com.polling.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class PollRepository {

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public PollRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public int insertPoll (Poll poll) {

    String sql = "" +
            "INSERT INTO poll (" +
            " id, " +
            " title, " +
            " start_time, " +
            " end_time)" +
            "VALUES (?, ?, ?, ?)";

    Timestamp start_time = Timestamp.valueOf(poll.getStartTime());
    Timestamp end_time = Timestamp.valueOf(poll.getEndTime());

    int update = jdbcTemplate.update(
            sql,
            poll.getId(),
            poll.getTitle(),
            start_time,
            end_time
    );
    return update;
  }

  // get a list of polls without question list
  public List<Poll> findAllPolls() {

    String sql = "" +
            "SELECT " +
            " id, " +
            " title, " +
            " start_time, " +
            " end_time " +
            "FROM poll";

    return jdbcTemplate.query(
            sql,
            new Object[]{},
            mapOptionFromDb()
    );
  }

  // get one Question without option list by id
  public Poll findPollById(String id) {

    String sql = "" +
            "SELECT " +
            " id, " +
            " title, " +
            " start_time, " +
            " end_time " +
            "FROM poll " +
            "WHERE id = ?";

    List<Poll> list =  jdbcTemplate.query(sql, new Object[]{id}, mapOptionFromDb());
    if (list.isEmpty()) return null;
    return list.get(0);
  }

//  public int updatePoll(Poll poll) {
//    String sql = "UPDATE question " +
//            "SET id = ?, " +
//            " title = ?, " +
//            " start_time = ?, " +
//            " end_time = ? " +
//            "WHERE id = ?";
//    int update = jdbcTemplate.update(
//            sql,
//            poll.getId(),
//            poll.getTitle(),
//            poll.getStartTime(),
//            poll.getEndTime(),
//            poll.getId()
//    );
//    return update;
//  }

  public int deletePoll(String id) {
    String sql = "" +
            "DELETE FROM poll " +
            "WHERE id = ?";
    int r = jdbcTemplate.update(
            sql,
            id
    );
    return r;
  }

  private RowMapper<Poll> mapOptionFromDb() {

    return (resultSet, i) -> {
      String id = resultSet.getString("id");
      String title = resultSet.getString("title");
      String end_time = resultSet.getTimestamp("end_time").toString();
      String start_time = resultSet.getTimestamp("start_time").toString();
      return new Poll(id, title, null, start_time, end_time);
    };
  }
}
