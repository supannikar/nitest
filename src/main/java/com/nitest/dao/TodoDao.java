package com.nitest.dao;

import com.nitest.model.TodoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Service
public class TodoDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public TodoModel save(TodoModel todo) {
        TodoModel result = null;

        if (todo.getId() == null) {
            result = add(todo);
        } else {
            result = update(todo);
        }
        return result;
    }

    public TodoModel findById(Integer id){
        return jdbcTemplate.queryForObject("SELECT id, subject, detail, status FROM todolist WHERE id = ?", new TodoModelRowMapper(), id);
    }

    public void delete(Integer id){
        jdbcTemplate.update("DELETE FROM todolist WHERE id = ?", id);
    }

    public List<TodoModel> listAll(){
        return jdbcTemplate.query("SELECT id, subject, detail, status FROM todolist", new TodoModelRowMapper());
    }

    private TodoModel add(TodoModel todo) {
        Long result = null;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new TodoStatementCreator(todo),keyHolder);
        result = keyHolder.getKey().longValue();
        todo.setId(result);
        return todo;

    }

    private TodoModel update(TodoModel todo) {

        jdbcTemplate.update("UPDATE todolist SET subject = ?, detail = ?, status = ?" + " WHERE id = ?", todo.getSubject(), todo.getDetail(), todo.getStatus(),
                todo.getId());
        return todo;
    }


    private class TodoModelRowMapper implements RowMapper<TodoModel> {
        public TodoModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            TodoModel todoModel = new TodoModel();
            todoModel.setId(rs.getLong("id"));
            todoModel.setSubject(rs.getString("subject"));
            todoModel.setDetail(rs.getString("detail"));
            todoModel.setStatus(rs.getString("status"));

            return todoModel;
        }
    }

    private static class TodoStatementCreator implements PreparedStatementCreator {
        private final TodoModel todo;

        public TodoStatementCreator(TodoModel todo) {
            this.todo = todo;
        }

        public PreparedStatement createPreparedStatement(Connection connection)
                throws SQLException {

            PreparedStatement ps = connection.prepareStatement("INSERT INTO todolist (subject, detail, status)" +
                    " VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, todo.getSubject());
            ps.setString(2, todo.getDetail());
            ps.setString(3, todo.getStatus());
            return ps;
        }
    }
}
