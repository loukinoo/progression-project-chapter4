package com.example.progression.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.progression.dto.UserDTO;
import com.example.progression.model.User;

@Repository
public class JdbcUserRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	

	public int save(UserDTO user) {
		return jdbcTemplate.update("INSERT INTO users (admin, name) VALUES (?, ?)", 
				new Object[] {user.isAdmin(), user.getName()});
	}
	
	public int update(User user) {
		return jdbcTemplate.update("UPDATE users SET admin=?, name=? WHERE id=?", 
				new Object[] {user.isAdmin(), user.getName(), user.getId()});
	}

	public User findById(long id) {
		try {
			User user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE id=?", 
					BeanPropertyRowMapper.newInstance(User.class), id);
			
			return user;
		} catch (IncorrectResultSizeDataAccessException e) {
			return null;
		}
	}

	public int deleteById(long id) {
		return jdbcTemplate.update("DELETE FROM users WHERE id=?", id);
	}

	public List<User> findAll() {
		return jdbcTemplate.query("SELECT * FROM users", 
				BeanPropertyRowMapper.newInstance(User.class));
	}

	public List<User> findByRole(boolean isAdmin) {
		return jdbcTemplate.query("SELECT * FROM users WHERE admin=?", 
				BeanPropertyRowMapper.newInstance(User.class), isAdmin);
	}

	public int deleteAll() {
		return jdbcTemplate.update("DELETE FROM users");
	}

}
