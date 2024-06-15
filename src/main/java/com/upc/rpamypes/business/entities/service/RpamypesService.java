package com.upc.rpamypes.business.entities.service;

import java.util.List;

import com.upc.rpamypes.business.entities.PasswordResetToken;
import com.upc.rpamypes.business.entities.Planilla;
import com.upc.rpamypes.business.entities.Usuario;

public interface RpamypesService {

	public int PostUsuario(Usuario usuario);

	public int PutUsuario(Usuario usuario);
    public int UpdatePassword( String usuario, String password);
	public int DeleteUsuario(String usuario, String state);

	public List<Usuario> GetUsuarios();
	public Usuario GetUsuario(String usuario);
	public int PostPlanilla(Planilla planilla);
	public List<Planilla> GetPlanillas(String tipo);
	public List<Planilla> GetPlanillas(String fechaIn, String fechaFn, String tipo);
	
	public int createPasswordResetTokenForUser(PasswordResetToken passwordResetToken);
	public PasswordResetToken findByToken(String token);
	public String validatePasswordResetToken(String token);
	public int deleteByToken(String token);
}
