package com.upc.rpamypes.configuration;



import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class RpamypesSecurityConfig {

	@Autowired
	private DataSource ds;

	@Bean
	public UserDetailsManager authenticateUsers() {
		JdbcUserDetailsManager users = new JdbcUserDetailsManager(ds);
		users.setAuthoritiesByUsernameQuery("select username, authority from authorities where username=?");
		users.setUsersByUsernameQuery("select username, password, enabled from users where username=?");
		return users;
	}

	@Bean
	public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
		http.formLogin(
				formLogin -> formLogin
				.loginPage("/login")
				.loginProcessingUrl("/login")
				.defaultSuccessUrl("/user/A/index")
				.failureUrl("/login?error"))
				.logout(logout -> logout.logoutSuccessUrl("/index"))
				.authorizeHttpRequests(authorize -> authorize
						.shouldFilterAllDispatcherTypes(false)
						.requestMatchers(new AntPathRequestMatcher("/"),
								new AntPathRequestMatcher("/index"),
								new AntPathRequestMatcher("/login"),
								new AntPathRequestMatcher("/planillas-rest"),
								new AntPathRequestMatcher("/css/**"),
								new AntPathRequestMatcher("/user/updatePassword"),
								new AntPathRequestMatcher("POST", "/user/updatePassword"),
								new AntPathRequestMatcher("/user/resetPassword"),
								new AntPathRequestMatcher("POST", "/user/resetPassword"),
								new AntPathRequestMatcher("/user/changePassword"),
								new AntPathRequestMatcher("POST", "/user/changePassword"),
								new AntPathRequestMatcher("/favicon.ico"))
						.permitAll()
						.requestMatchers(new AntPathRequestMatcher("/admin/**")).hasRole("ADMIN")
						.requestMatchers(new AntPathRequestMatcher("/user/**")).hasAnyRole("USER", "ADMIN")
						.anyRequest()
						.authenticated())
				.exceptionHandling(handling -> handling.accessDeniedPage("/403"));
			http.csrf().disable();
		return http.build();
	}
	
	@Bean
	public HttpFirewall getHttpFirewall() {
	    StrictHttpFirewall strictHttpFirewall = new StrictHttpFirewall();
	    strictHttpFirewall.setAllowSemicolon(true);
	    return strictHttpFirewall;
	}
}
