package com.upc.rpamypes.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.upc.rpamypes.business.entities.PasswordResetToken;
import com.upc.rpamypes.business.entities.Planilla;
import com.upc.rpamypes.business.entities.PlanillaRequest;
import com.upc.rpamypes.business.entities.Usuario;
import com.upc.rpamypes.business.entities.service.RpamypesService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class RpamypesController {

	public RpamypesController(RpamypesService service, JavaMailSender mailSender) {
		this.service = service;
		this.mailSender = mailSender;
	}

	private JavaMailSender mailSender;

	@ModelAttribute
	PlanillaRequest planillaRequest() {
		return new PlanillaRequest();
	}

	@ModelAttribute
	Usuario usuario() {
		return new Usuario();
	}

	@ModelAttribute
	String nombre() {
		return "";
	}

	private RpamypesService service;

	@RequestMapping(value = "/planilla", params = { "search" })
	public String showPlanills(@Valid PlanillaRequest planillaRequest, BindingResult result, Model model)
			throws ParseException {
		model.addAttribute("nombre", "A");

		if (result.hasErrors()) {
			model.addAttribute("planillas", service.GetPlanillas("A"));
			return "user/A/index";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyyy");
		Date date1 = sdf.parse(planillaRequest.getFechaIn());
		Date date2 = sdf.parse(planillaRequest.getFechaFn());
		Date actual = new Date();
		if (date2.before(date1)) {
			ObjectError error = new ObjectError("globalError",
					"La fecha límite no puede ser menor que la fecha actual.");
			result.addError(error);
		}
		if (date2.after(actual)) {
			ObjectError error = new ObjectError("globalError",
					"La fecha límite no puede mayor al del día de hoy.");
			result.addError(error);
		}
		if (result.hasErrors()) {
			model.addAttribute("planillas", service.GetPlanillas("A"));
			return "user/A/index";
		}
		List<Planilla> lista = service.GetPlanillas(cambiarFecha(planillaRequest.getFechaIn() )+ " 00:00:00",
				cambiarFecha(planillaRequest.getFechaFn() )+ " 23:59:59", "A");
		model.addAttribute("planillas", lista);
		return "user/A/index";
	}

	@RequestMapping(value = "/planilla2", params = { "search" })
	public String showPlanills2(@Valid PlanillaRequest planillaRequest, BindingResult result, Model model)
			throws ParseException {
		model.addAttribute("nombre", "B");
		if (result.hasErrors()) {
			model.addAttribute("planillas", service.GetPlanillas("B"));
			return "user/B/index";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyyy");
		Date date1 = sdf.parse(planillaRequest.getFechaIn());
		Date date2 = sdf.parse(planillaRequest.getFechaFn());
		Date actual = new Date();
		if (date2.before(date1)) {
			ObjectError error = new ObjectError("globalError",
					"La fecha límite no puede ser menor que la fecha actual.");
			result.addError(error);
		}
		if (date2.after(actual)) {
			ObjectError error = new ObjectError("globalError",
					"La fecha límite no puede mayor al del día de hoy.");
			result.addError(error);
		}
		if (result.hasErrors()) {
			model.addAttribute("planillas", service.GetPlanillas("B"));
			return "user/B/index";
		}
		List<Planilla> lista = service.GetPlanillas(cambiarFecha(planillaRequest.getFechaIn()) + " 00:00:00",
				cambiarFecha(planillaRequest.getFechaFn())+ " 23:59:59", "B");
		model.addAttribute("planillas", lista);
		return "user/B/index";
	}

	private String cambiarFecha(String fecha) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return LocalDate.parse(fecha, formatter).format(formatter2);
	};

	@RequestMapping("/")
	public String root(Locale locale) {
		return "redirect:/index";
	}

	/** Home page. */
	@RequestMapping("/index")
	public String index() {
		return "index";
	}

	/** User zone index. */
	@RequestMapping(path = { "/user/index", "/user/A/index" })
	public String userIndex(Model model) {
		model.addAttribute("nombre", "A");
		model.addAttribute("planillas", service.GetPlanillas("A"));
		return "user/A/index";
	}

	@RequestMapping(path = { "/user/index", "/user/B/index" })
	public String userIndex2(Model model) {
		model.addAttribute("nombre", "B");
		model.addAttribute("planillas", service.GetPlanillas("B"));
		return "user/B/index";
	}

	@RequestMapping("/user/users")
	public String usuarios(Model model) {
		model.addAttribute("nombre", "C");
		model.addAttribute("usuarios", service.GetUsuarios());
		return "user/users";
	}

	@GetMapping("/user/add-user")
	public String usuarioAddR(Model model) {
		model.addAttribute("nombre", "C");
		return "user/add-user";
	}

	@PostMapping("/user/add-user")
	public String usuarioAdd(@Valid Usuario usuario, BindingResult result, Model model) {

		model.addAttribute("nombre", "C");
		usuario.setEnabled("1");
		if (result.hasErrors()) {
			return "user/add-user";
		}
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		usuario.setPassword(encoder.encode(usuario.getPassword()));
		service.PostUsuario(usuario);
		model.addAttribute("usuarios", service.GetUsuarios());
		return "redirect:/user/users";
	}

	@RequestMapping("/user/user-disable/{state}/{username}")
	public String usuarios(@PathVariable("state") String state, @PathVariable("username") String username,
			Model model) {
		service.DeleteUsuario(username, state);
		model.addAttribute("nombre", "C");
		model.addAttribute("usuarios", service.GetUsuarios());
		return "redirect:/user/users";
	}

	/** Login form. */
	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	/** Login form with error. */
	@RequestMapping("/login-error")
	public String loginError(Model model) {
		System.out.println("ERROR, MODE LOGIN");
		model.addAttribute("loginError", true);
		return "login";
	}

	@RequestMapping("/403")
	public String forbidden() {
		return "403";
	}

	@GetMapping("/user/resetPassword")
	public String resetPassUrl() {
		return "resetPassword/forgetPassword";
	}

	@PostMapping("/user/resetPassword")
	public String resetPassword(HttpServletRequest request, @RequestParam("username") String userEmail, Model model) {
		Usuario user = service.GetUsuario(userEmail);
		if (user == null) {
			model.addAttribute("error", "No existe cuenta con ese correo electrónico");
			return "resetPassword/forgetPassword";
		}
		String token = UUID.randomUUID().toString();
		PasswordResetToken prt = new PasswordResetToken();
		prt.setToken(token);
		prt.setUsername(user.getUsername());
		prt.setExpirydate(Date.from(LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.UTC)));
		service.createPasswordResetTokenForUser(prt);

		mailSender.send(constructResetTokenEmail(getAppUrl(request), token, user));
		model.addAttribute("error", "Se envió enlace web al correo para cambiar la contraseña");
		return "resetPassword/forgetPassword";
	}

	private SimpleMailMessage constructResetTokenEmail(String contextPath, String token, Usuario user) {
		String url = contextPath + "/user/changePassword?token=" + token;
		String message = "Cambio de Contraseña";
		return constructEmail("Cambio de Contraseña", message + " \r\n" + url, user);
	}

	private SimpleMailMessage constructEmail(String subject, String body, Usuario usuario) {
		final SimpleMailMessage email = new SimpleMailMessage();
		email.setSubject(subject);
		email.setText(body);
		email.setTo(usuario.getUsername());
		email.setFrom("Desarrollo_Contable@outlook.com");
		return email;
	}

	private String getAppUrl(HttpServletRequest request) {
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}

	@GetMapping("/user/changePassword")
	public String showChangePasswordPage(Model model, @RequestParam("token") String token) {
		String result = service.validatePasswordResetToken(token);
		if (result != null) {
			String message = result.equals("invalidToken") ? "Token Inválido, vuelve a generarlo"
					: "Token Expirado, vuelve a generarlo.";
			model.addAttribute("tokenmessage", message);
			return "resetPassword/forgetPassword";
		} else {
			model.addAttribute("token", token);
			return "resetPassword/updatePassword";
		}
	}

	@PostMapping("/user/changePassword")
	public String showChangePasswordP(Model model, @RequestParam("token") String token,
			@RequestParam("password") String password, @RequestParam("password1") String password1) {
		if (!password.equals(password1)) {
			model.addAttribute("tokenmessage", "Las contraseñas no coinciden");
			model.addAttribute("token", token);
			return "resetPassword/updatePassword";
		} else {
			PasswordResetToken prt = service.findByToken(token);
			PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
			service.UpdatePassword(prt.getUsername(), encoder.encode(password1));
			service.deleteByToken(token);
			model.addAttribute("token", null);
			model.addAttribute("tokenmessage", "Contraseña Actualizada");
			return "login";
		}
	}
}
