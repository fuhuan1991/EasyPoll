package com.polling.repository;

import com.polling.QuestionType;
import com.polling.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class QuestionRepository {

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public QuestionRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public int insertQuestion (Question question) {

    String sql = "" +
            "INSERT INTO question (" +
            " id, " +
            " index, " +
            " poll_id, " +
            " name, " +
            " type)" +
            "VALUES (?, ?, ?, ?, ?)";

    int update = jdbcTemplate.update(
            sql,
            question.getId(),
            question.getIndex(),
            question.getPoll_id(),
            question.getName(),
            question.getType().toString()
    );
    return update;
  }

  // get a list of Questions without option list
  public List<Question> findAllPollQuestions(String poll_id) {

    String sql = "" +
            "SELECT " +
            " id, " +
            " index, " +
            " poll_id, " +
            " name, " +
            " type " +
            "FROM question " +
            "WHERE poll_id = ?" +
            "ORDER BY index";

    return jdbcTemplate.query(
            sql,
            new Object[]{poll_id},
            mapOptionFromDb()
    );
  }

  // get one Question without option list by id
  public Question findQuestionById(UUID question_id) {

    String sql = "" +
            "SELECT " +
            " id, " +
            " index, " +
            " poll_id, " +
            " name, " +
            " type " +
            "FROM question " +
            "WHERE id = ?";

    List<Question> list =  jdbcTemplate.query(sql, new Object[]{question_id}, mapOptionFromDb());
    if (list.isEmpty()) return null;
    return list.get(0);
  }

//  public int updateQuestion(Question question) {
//    String sql = "UPDATE question " +
//            "SET id = ?, " +
//            " poll_id = ? " +
//            " name = ? " +
//            " type = ? " +
//            "WHERE id = ?";
//    int update = jdbcTemplate.update(
//            sql,
//            question.getId(),
//            question.getPoll_id(),
//            question.getName(),
//            question.getType().toString(),
//            question.getId()
//    );
//    return update;
//  }

  public int deleteQuestion(UUID question_id) {
    String sql = "" +
            "DELETE FROM question " +
            "WHERE id = ?";
    int r = jdbcTemplate.update(
            sql,
            question_id
    );
    return r;
  }

  private RowMapper<Question> mapOptionFromDb() {

    return (resultSet, i) -> {
      String questionIdStr = resultSet.getString("id");
      UUID questionId = UUID.fromString(questionIdStr);
      int index = resultSet.getInt("index");
      String poll_id = resultSet.getString("poll_id");

      String name = resultSet.getString("name");
      String type = resultSet.getString("type");

      return new Question(questionId, index, poll_id, name, type, null);
    };
  }
}
