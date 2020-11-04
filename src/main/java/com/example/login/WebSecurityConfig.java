package com.example.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.login.auth.SuccessHandler;

@Configuration
@EnableWebSecurity // Spring Security���g�����߂̐ݒ�
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SuccessHandler successHandler;
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	// Web�S�̂̐ݒ�
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.debug(false).ignoring().antMatchers("/images/**", "/js/**", "/css/**");
	}

	// URL���ƂɈقȂ�Z�L�����e�B�ݒ�
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				// �g�b�v�y�[�W�ƃA�J�E���g�o�^�����͂���ł��A�N�Z�X�\
				.mvcMatchers("/", "/signup","/login").permitAll()
				// /members/user/**�y�[�W�́AUSER���[�������F�؃��[�U���A�N�Z�X�\
				.mvcMatchers("/members/user/**").hasRole("USER")
				// /members/admin/**�y�[�W��ADMIN���[�������F�؃��[�U���A�N�Z�X�\
				.mvcMatchers("/members/admin/**").hasRole("ADMIN")
				// ��L�ȊO�̃y�[�W�́A�F�؃��[�U���A�N�Z�X�\
				.anyRequest().authenticated()
			.and()
			.formLogin()
				.loginPage("/login")
				.usernameParameter("username")
				.passwordParameter("password")
				.defaultSuccessUrl("/")
				.successHandler(successHandler)
			.and()
			.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.logoutSuccessUrl("/");
	}
}
