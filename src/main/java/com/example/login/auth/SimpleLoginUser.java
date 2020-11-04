package com.example.login.auth;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.example.login.entity.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleLoginUser extends org.springframework.security.core.userdetails.User {
	private User user;

	public SimpleLoginUser(User user) {
		super(user.getEmail(), user.getPassword(), user.getEnable(), true, true, true,
				convertGrantedAuthorities(user.getRoles()));
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	/**
	 * カンマ区切りのロールをSimpleGrantedAuthorityのコレクションへ変換する
	 *
	 * @param roles カンマ区切りのロール
	 * @return SimpleGrantedAuthorityのコレクション
	 */
	static Set<GrantedAuthority> convertGrantedAuthorities(String roles) {
		if (roles == null || roles.isEmpty()) {
			return Collections.emptySet();
		}
		Set<GrantedAuthority> authorities = Stream.of(roles.split(",")).map(SimpleGrantedAuthority::new)
				.collect(Collectors.toSet());
		return authorities;
	}
}
