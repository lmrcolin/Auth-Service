package com.unam.dwb.auth.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.unam.dwb.auth.domain.Usuario;

@Repository
public class UsuarioJdbcRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Optional<Usuario> findByUsername(String username) {
		String sql = "SELECT username, correo FROM usuario WHERE username = ?";

		List<Usuario> resultadoQuery = jdbcTemplate.query(sql,
				(rs, row) -> new Usuario(
						rs.getString("username"),
						rs.getString("correo")),
				username);

		return resultadoQuery.stream().findFirst();
	}

	public Optional<Usuario> findByUsernameCredential(String username, String hashPass) {
		String sql = "SELECT username, correo FROM usuario WHERE username = ? AND password = ?";

		List<Usuario> resultadoQuery = jdbcTemplate.query(sql,
				(rs, row) -> new Usuario(
						rs.getString("username"),
						rs.getString("correo")),
				username, hashPass);

		return resultadoQuery.stream().findFirst();
	}

	public Optional<Usuario> findByCorreoCredential(String correo, String hashPass) {
		String sql = "SELECT username, correo FROM usuario WHERE correo = ? AND password = ?";

		List<Usuario> resultadoQuery = jdbcTemplate.query(sql,
				(rs, row) -> new Usuario(
						rs.getString("username"),
						rs.getString("correo")),
				correo, hashPass);

		return resultadoQuery.stream().findFirst();
	}

}
