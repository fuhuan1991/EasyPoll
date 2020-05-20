package com.polling.repository;

import com.polling.model.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class OptionRepository {

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public OptionRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public int insertOption (Option option) {

    String sql = "" +
            "INSERT INTO option (" +
            " id, " +
            " index, " +
            " question_id, " +
            " name, " +
            " count)" +
            "VALUES (?, ?, ?, ?, ?)";

    int update = jdbcTemplate.update(
            sql,
            option.getId(),
            option.getIndex(),
            option.getQuestion_id(),
            option.getName(),
            option.getCount()
    );
    return update;
  }

  public List<Option> findAllQuestionOptions(UUID question_id) {

    String sql = "" +
            "SELECT " +
            " id, " +
            " index, " +
            " question_id, " +
            " name, " +
            " count " +
            "FROM option " +
            "WHERE question_id = ?" +
            "ORDER BY index";

    return jdbcTemplate.query(
            sql,
            new Object[]{question_id},
            mapOptionFromDb()
    );
  }

  public Option findOptionById(UUID option_id) {

    String sql = "" +
            "SELECT " +
            " id, " +
            " index, " +
            " question_id, " +
            " name, " +
            " count " +
            "FROM option " +
            "WHERE id = ?";

    List<Option> list =  jdbcTemplate.query(sql, new Object[]{option_id}, mapOptionFromDb());
    if (list.isEmpty()) return null;
    return list.get(0);
  }

  public int updateOption(Option option) {
    String sql = "UPDATE option " +
            "SET id = ?, " +
            " index = ?, " +
            " question_id = ?, " +
            " name = ?, " +
            " count = ? " +
            "WHERE id = ?";
    int update = jdbcTemplate.update(
            sql,
            option.getId(),
            option.getIndex(),
            option.getQuestion_id(),
            option.getName(),
            option.getCount(),
            option.getId()
    );
    return update;
  }

  public int deleteOption(UUID option_id) {
    String sql = "" +
            "DELETE FROM option " +
            "WHERE id = ?";
    int r = jdbcTemplate.update(
            sql,
            option_id
    );
    return r;
  }

  private RowMapper<Option> mapOptionFromDb() {

    return (resultSet, i) -> {
      String optionIdStr = resultSet.getString("id");
      UUID optionId = UUID.fromString(optionIdStr);
      int index = resultSet.getInt("index");
      String questionIdStr = resultSet.getString("question_id");
      UUID questionId = UUID.fromString(questionIdStr);

      String name = resultSet.getString("name");
      int count = resultSet.getInt("count");

      return new Option(optionId, index, questionId, name, count);
    };
  }
}
