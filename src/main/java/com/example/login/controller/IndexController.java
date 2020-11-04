package com.example.login.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.login.entity.User;
import com.example.login.service.AccountService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class IndexController {
	
	@Autowired
	private AccountService accountService;
	
	 /**
	   * トップページ
	   *
	   * @param signupForm サインアップフォームデータ
	   * @param model モデル（ユーザーリスト）
	   * @return index
	   */
	@GetMapping(value = "/")
	public String index(@ModelAttribute("signup") SignupForm signupForm, Model model) {
		List<User> userList = accountService.findAll();
		model.addAttribute("users", userList);
		return "index";
	}	
	
	@GetMapping(value = "login")
	public String login() {
		return "login";
	}
	
	/**
	   * アカウント登録処理
	   *
	   * @param signupForm サインアップフォームデータ
	   * @param redirectAttributes リダイレクト先へメッセージを送るため
	   * @return index (redirect)
	   */
	  @PostMapping(value = "signup")
	  public String signup(@ModelAttribute("signup") SignupForm signupForm, RedirectAttributes redirectAttributes) {
	    // TODO 暫定的に2つのロールを付与する
	    String[] roles = {"ROLE_USER"};
	    accountService.register(signupForm.getName(), signupForm.getEmail(), signupForm.getPassword(), roles);
	    redirectAttributes.addFlashAttribute("successMessage", "アカウントの登録が完了しました");
	    return "redirect:/";
	  }
}
