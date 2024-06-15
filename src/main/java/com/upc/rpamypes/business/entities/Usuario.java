package com.upc.rpamypes.business.entities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Usuario {
	@Email(message = "No tiene el formato adecuado el correo electronico.", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
	@NotEmpty(message = "El campo Correo está incompleto.")
	private String username;
	@NotEmpty(message = "El campo Contraseña está incompleto.")
	@Size(min = 6, message = "La contraseña debe tener un mínimo de 6 caracteres.")
	private String password;
	private String enabled;
	@NotEmpty(message = "El campo Nombre está incompleto.")
	@Size(min = 4, message = "El nombre debe tener un mínimo de 4 caracteres.")
	private String firstname;
	@NotEmpty(message = "El campo Apellido está incompleto.")
	@Size(min = 4, message = "El apellido debe tener un mínimo de 4 caracteres.")
	private String lastname;
	@NotEmpty(message = "El campo rol está incompleto.")
	private String authority;

	public Usuario(String username, String password, String enabled, String firstname, String lastname,
			String authority) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.firstname = firstname;
		this.lastname = lastname;
		this.authority = authority;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public Usuario() {
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

}
