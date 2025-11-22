package com.example.progression.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.progression.model.User;

@Repository
public class JdbcUserRepository implements IUserRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	

	@Override
	public int save(User user) {
		return jdbcTemplate.update("INSERT INTO users (is_admin, name) VALUES (?, ?)", 
				new Object[] {user.isAdmin(), user.getName()});
	}

	@Override
	public int update(User user) {
		return jdbcTemplate.update("UPDATE users SET is_admin=?, name=? WHERE id=?", 
				new Object[] {user.isAdmin(), user.getName(), user.getId()});
	}

	@Override
	public User findById(long id) {
		try {
			User user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE id=?", 
					BeanPropertyRowMapper.newInstance(User.class), id);
			
			return user;
		} catch (IncorrectResultSizeDataAccessException e) {
			return null;
		}
	}

	@Override
	public int deleteById(long id) {
		return jdbcTemplate.update("DELETE FROM users WHERE id=?", id);
	}

	@Override
	public List<User> findAll() {
		return jdbcTemplate.query("SELECT * FROM users", 
				BeanPropertyRowMapper.newInstance(User.class));
	}

	@Override
	public List<User> findByRole(boolean isAdmin) {
		return jdbcTemplate.query("SELECT * FROM users WHERE is_admin=?", 
				BeanPropertyRowMapper.newInstance(User.class), isAdmin);
	}

	@Override
	public int deleteAll() {
		return jdbcTemplate.update("DELETE FROM users");
	}

}
