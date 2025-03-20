package org.jsp.stocks.controller;

import org.jsp.stocks.dto.User;
import org.jsp.stocks.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class StockController {

	@Autowired
	StockService service;

	@GetMapping("/")
	public String loadMain() {
		return "main.html";
	}

	@GetMapping("/login")
	public String loadLogin() {
		return "login.html";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute User user, HttpSession session) {
		return service.login(user, session);
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		session.removeAttribute("admin");
		return "redirect:/";
	}
}
