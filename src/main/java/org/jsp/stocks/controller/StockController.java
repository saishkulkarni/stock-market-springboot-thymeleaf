package org.jsp.stocks.controller;

import org.jsp.stocks.dto.User;
import org.jsp.stocks.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

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

	@GetMapping("/otp/{id}")
	public String loadOtpPage(@PathVariable int id, Model model) {
		model.addAttribute("id", id);
		return "otp.html";
	}

	@GetMapping("/register")
	public String loadRegister(User user, Model model) {
		return service.register(user, model);
	}

	@PostMapping("/register")
	public String register(@ModelAttribute @Valid User user, BindingResult result) {
		return service.register(user, result);
	}

	@PostMapping("/otp")
	public String verifyOtp(@RequestParam int id, @RequestParam int otp) {
		return service.verifyOtp(id, otp);
	}

}
