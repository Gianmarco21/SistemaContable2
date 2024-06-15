package com.upc.rpamypes.business.entities.repository.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.upc.rpamypes.business.entities.PasswordResetToken;
import com.upc.rpamypes.business.entities.Planilla;
import com.upc.rpamypes.business.entities.Usuario;
import com.upc.rpamypes.business.entities.repository.RpamypesRepository;

@Repository
public class RpamypesRepositoryImpl implements RpamypesRepository {
	private DataSource ds;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public RpamypesRepositoryImpl(DataSource ds, JdbcTemplate jdbcTemplate) {
		this.ds = ds;
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(this.ds);
	}

	public List<Planilla> GetPlanillas(String tipo) {
		List<Planilla> planillas = namedParameterJdbcTemplate.query(
				"select  PlanillaId, Ruta, Ruta2, FechaProcesada, EjecucionId, Documento, DuracionSegundos from dbo.Planilla where Tipo = :tipo",
				new MapSqlParameterSource().addValue("tipo", tipo),
				new BeanPropertyRowMapper<Planilla>(Planilla.class));
		return planillas;
	}

	public List<Planilla> GetPlanillas(String fechaIn, String fechaFn, String tipo) {
		List<Planilla> planillas = namedParameterJdbcTemplate.query(
				"select  PlanillaId, Ruta, Ruta2, FechaProcesada, EjecucionId, Documento, DuracionSegundos from dbo.Planilla where FechaProcesada between :fechaIn and :fechaFn and Tipo = :tipo",
				new MapSqlParameterSource().addValue("fechaIn", fechaIn).addValue("fechaFn", fechaFn).addValue("tipo",
						tipo),
				new BeanPropertyRowMapper<Planilla>(Planilla.class));
		return planillas;
	}

	@Override
	public int PostPlanilla(Planilla planilla) {
		return namedParameterJdbcTemplate.update(
				"insert into Planilla(PlanillaId, Ruta, Ruta2, FechaProcesada, EjecucionId, Documento, DuracionSegundos, Tipo)"
						+ " values (:PlanillaId, :Ruta, :Ruta2, :FechaProcesada, :EjecucionId, :Documento, :DuracionSegundos, :Tipo)",
				new BeanPropertySqlParameterSource(planilla));
	}

	@Override
	public int PostUsuario(Usuario usuario) {
		namedParameterJdbcTemplate.update(
				"insert into users(username, password, enabled, firstname, lastname)"
						+ " values (:username, :password, :enabled, :firstname, :lastname)",
				new BeanPropertySqlParameterSource(usuario));
		return namedParameterJdbcTemplate.update(
				"insert into authorities(username, authority)" + " values (:username, :authority)",
				new BeanPropertySqlParameterSource(usuario));
	}

	@Override
	public int PutUsuario(Usuario usuario) {
		namedParameterJdbcTemplate.update(
				"update users set password=:password, enabled=:enabled, firstname=:firstname, lastname=:lastname where username=:username",
				new BeanPropertySqlParameterSource(usuario));

		return namedParameterJdbcTemplate.update("update authorities set authority=:authority where username=:username",
				new BeanPropertySqlParameterSource(usuario));
	}

	@Override
	public int DeleteUsuario(String usuario, String state) {
		Usuario u = new Usuario();
		if (state.equals("1"))
			u.setEnabled("0");
		else {
			u.setEnabled("1");
		}
		u.setUsername(usuario);
		return namedParameterJdbcTemplate.update("update users set enabled=:enabled where username=:username",
				new BeanPropertySqlParameterSource(u));
	}

	@Override
	public List<Usuario> GetUsuarios() {
		List<Usuario> users = namedParameterJdbcTemplate.query(
				"select users.username, users.password, users.firstname, users.lastname, authorities.authority, users.enabled from users "
						+ "left join authorities on users.username = authorities.username",
				new MapSqlParameterSource(), new BeanPropertyRowMapper<Usuario>(Usuario.class));
		return users;
	}

	@Override
	public Usuario GetUsuario(String usuario) {
		return DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(
				"select users.username, users.password, users.firstname, users.lastname, authorities.authority, users.enabled from users "
						+ "left join authorities on users.username = authorities.username where users.username=:t1",
				new MapSqlParameterSource().addValue("t1", usuario),
				new BeanPropertyRowMapper<Usuario>(Usuario.class)));
	}

	@Override
	public int createPasswordResetTokenForUser(PasswordResetToken passwordResetToken) {
		return namedParameterJdbcTemplate.update(
				"insert into PasswordResetToken(id, token, username, expirydate) values(:id, :token, :username, :expirydate)",
				new BeanPropertySqlParameterSource(passwordResetToken));

	}

	@Override
	public PasswordResetToken findByToken(String token) {
		return DataAccessUtils.singleResult(namedParameterJdbcTemplate.query(
				"select id, token, username, expirydate from PasswordResetToken where token=:token ",
				new MapSqlParameterSource().addValue("token", token),
				new BeanPropertyRowMapper<PasswordResetToken>(PasswordResetToken.class)));
	}

	@Override
	public int UpdatePassword(String usuario, String password) {
		// TODO Auto-generated method stub
		return namedParameterJdbcTemplate.update("update users set password=:p1 where username=:u1",
				new MapSqlParameterSource().addValue("u1", usuario).addValue("p1", password));
	}

	@Override
	public int deleteByToken(String token) {
		// TODO Auto-generated method stub
		return namedParameterJdbcTemplate.update("delete from PasswordResetToken where token = :t1", new MapSqlParameterSource().addValue("t1", token));
	}
}
