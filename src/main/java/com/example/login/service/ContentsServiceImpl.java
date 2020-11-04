package com.example.login.service;

import com.example.login.auth.SimpleLoginUser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class ContentsServiceImpl  implements ContentsService{
	@Override
	public void doService() {
		SecurityContext context = SecurityContextHolder.getContext();
	    Authentication authentication = context.getAuthentication();
	    SimpleLoginUser loginUser = (SimpleLoginUser) authentication.getPrincipal();
	    log.info("#doService id:{}, name:{}", loginUser.getUser().getId(), loginUser.getUser().getName());

	}
}
