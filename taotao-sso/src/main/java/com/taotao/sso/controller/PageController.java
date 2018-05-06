package com.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 实现登录功能
 * @author 11981
 *
 */
@Controller
@RequestMapping("/page")
public class PageController {
	
	@RequestMapping("/register")
	public String showRegister() {
		return "register";
	}
	
	@RequestMapping("/login")
	public String showLogin(String redirect, Model model) {
		/**
		 * 回调url应该是通过一个参数传递给显示登录页面的Controller。
		 * 参数名为：redirect需要把回调的url传递给jsp页面。
		 */
		model.addAttribute("redirect", redirect);
		return "login";
	}
}
