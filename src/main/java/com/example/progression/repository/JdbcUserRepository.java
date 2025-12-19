package com.example.progression.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.progression.dto.UserToSaveDTO;
import com.example.progression.model.User;

@Repository
public class JdbcUserRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public int save(UserToSaveDTO user) {
		return jdbcTemplate.update("INSERT INTO users (username, password, admin) VALUES (?, ?, ?)", 
				new Object[] {user.getUsername(), user.getPassword(), user.isAdmin()});
	}
	
	public int update(User user) {
		return jdbcTemplate.update("UPDATE users SET admin=?, username=? WHERE id=?", 
				new Object[] {user.isAdmin(), user.getUsername(), user.getId()});
	}

	public User findById(Long id) {
		try {
			User user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE id=?", 
					BeanPropertyRowMapper.newInstance(User.class), id);
			
			return user;
		} catch (IncorrectResultSizeDataAccessException e) {
			return null;
		}
	}

	public int deleteById(Long id) {
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

	public User findByUsername(String username) {
		try {
			User user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE username=?", 
					BeanPropertyRowMapper.newInstance(User.class), username);
			return user;
		} catch (IncorrectResultSizeDataAccessException e) {
			return null;
		}
	}
	
	public int deleteAll() {
		return jdbcTemplate.update("DELETE FROM users");
	}

}
