package com.alethe.opf.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.alethe.opf.jwtfilter.JwtFilter;
import com.alethe.opf.service.CustomUserDetailsServiceImpl;

/**
 * Created by Kunal Kumar
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailsServiceImpl cUserDetails;

	@Autowired
	private JwtFilter jwtFilterSecurty;

	
	//	@Autowired
//	private RoleHierarchy roleHierarchy;

	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}



//	@Bean
//	public RoleHierarchyVoter roleVoter() {
//		return new RoleHierarchyVoter(roleHierarchy);
//	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(cUserDetails);
				
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// http.headers().cacheControl().disable();
		http.cors().and().csrf().disable().authorizeRequests().antMatchers("/", "/get-token").permitAll().anyRequest()
				.authenticated().and().exceptionHandling().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtFilterSecurty, UsernamePasswordAuthenticationFilter.class);

	}

	@SuppressWarnings("deprecation")
	@Bean
	public PasswordEncoder getPasswordEncoder() {

		return NoOpPasswordEncoder.getInstance();
	}
	
	
	
//	@Bean
//	public BCryptPasswordEncoder getPasswordEncoder() {
//
//		return new BCryptPasswordEncoder();
//	}

}
