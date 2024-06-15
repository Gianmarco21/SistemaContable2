package com.upc.rpamypes.business.entities.service.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;

import com.upc.rpamypes.business.entities.PasswordResetToken;
import com.upc.rpamypes.business.entities.Planilla;
import com.upc.rpamypes.business.entities.Usuario;
import com.upc.rpamypes.business.entities.repository.RpamypesRepository;
import com.upc.rpamypes.business.entities.service.RpamypesService;

@Service
public class RpamypesServiceImpl implements RpamypesService {

	private RpamypesRepository repository;

	public RpamypesServiceImpl(RpamypesRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<Planilla> GetPlanillas(String tipo) {
		return repository.GetPlanillas(tipo);
	}

	@Override
	public List<Planilla> GetPlanillas(String fechaIn, String fechaFn, String tipo) {
		return repository.GetPlanillas(fechaIn, fechaFn, tipo);
	}

	@Override
	public int PostPlanilla(Planilla planilla) {
		return repository.PostPlanilla(planilla);
	}

	@Override
	public int PostUsuario(Usuario usuario) {
		return repository.PostUsuario(usuario);
	}

	@Override
	public int PutUsuario(Usuario usuario) {
		return repository.PutUsuario(usuario);
	}

	@Override
	public int DeleteUsuario(String usuario, String state) {
		return repository.DeleteUsuario(usuario, state);
	}

	@Override
	public List<Usuario> GetUsuarios() {
		return repository.GetUsuarios();
	}

	@Override
	public Usuario GetUsuario(String usuario) {
		return repository.GetUsuario(usuario);
	}

	@Override
	public int createPasswordResetTokenForUser(PasswordResetToken passwordResetToken) {
		return repository.createPasswordResetTokenForUser(passwordResetToken);
	}

	@Override
	public PasswordResetToken findByToken(String token) {
		return repository.findByToken(token);
	}

	@Override
	public String validatePasswordResetToken(String token) {
		final PasswordResetToken passToken = repository.findByToken(token);

		return !isTokenFound(passToken) ? "invalidToken" : isTokenExpired(passToken) ? "expired" : null;
	}

	private boolean isTokenFound(PasswordResetToken passToken) {
		return passToken != null;
	}

	private boolean isTokenExpired(PasswordResetToken passToken) {
		final Calendar cal = Calendar.getInstance();
		return passToken.getExpirydate().before(cal.getTime());
	}

	@Override
	public int UpdatePassword(String usuario, String password) {
		// TODO Auto-generated method stub
		return repository.UpdatePassword(usuario, password);
	}

	@Override
	public int deleteByToken(String token) {
		// TODO Auto-generated method stub
		return repository.deleteByToken(token);
	}
}
