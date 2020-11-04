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
@EnableWebSecurity // Spring Securityを使うための設定
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SuccessHandler successHandler;
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	// Web全体の設定
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.debug(false).ignoring().antMatchers("/images/**", "/js/**", "/css/**");
	}

	// URLごとに異なるセキュリティ設定
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				// トップページとアカウント登録処理はだれでもアクセス可能
				.mvcMatchers("/", "/signup","/login").permitAll()
				// /members/user/**ページは、USERロールを持つ認証ユーザがアクセス可能
				.mvcMatchers("/members/user/**").hasRole("USER")
				// /members/admin/**ページはADMINロールを持つ認証ユーザがアクセス可能
				.mvcMatchers("/members/admin/**").hasRole("ADMIN")
				// 上記以外のページは、認証ユーザがアクセス可能
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
