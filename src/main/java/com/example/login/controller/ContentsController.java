package com.example.login.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.login.auth.SimpleLoginUser;
import com.example.login.entity.User;
import com.example.login.service.ContentsService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "members")
@Slf4j
public class ContentsController {
	@Autowired
	private ContentsService contentsService;
	
	@GetMapping(value ="/")
	public String any(Principal principal) {
		Authentication authentication = (Authentication) principal;
		SimpleLoginUser loginUser = (SimpleLoginUser) authentication.getPrincipal();
		log.info("#any id:{}, name:{}",loginUser.getUser().getId(), loginUser.getUser().getName());
		contentsService.doService();
		return "members/index";
	}
	
	@GetMapping(value = "user")
	public String user(@AuthenticationPrincipal SimpleLoginUser loginUser) {
		log.info("#user id:{}, name:{}", loginUser.getUser().getId(), loginUser.getUser().getName());
		return "members/user/index";
	}
	
	@GetMapping(value = "admin")
	public String admin(@AuthenticationPrincipal(expression = "user")User user) {
		log.info("#admin id:{}, name:{}", user.getId(),user.getName());
		return "members/admin/index";
	}
}
