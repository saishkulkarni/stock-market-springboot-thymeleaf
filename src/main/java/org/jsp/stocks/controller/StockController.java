package org.jsp.stocks.controller;

import java.time.LocalDate;

import org.jsp.stocks.dto.User;
import org.jsp.stocks.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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

	@GetMapping("/register")
	public String loadRegister(User user, Model model) {
		model.addAttribute("user", user);
		return "register.html";
	}

	@PostMapping("/register")
	public String register(@ModelAttribute @Valid User user, BindingResult result) {
		if(!user.getPassword().equals(user.getConfirmPassword()))
			result.rejectValue("confirmPassword", "error.confirmPassword", "* Password and Confirm Password are Not Matching");
		if(LocalDate.now().getYear()-user.getDob().getYear()<18)
			result.rejectValue("dob", "error.dob", "* You should be 18+ to Create Account here");
		
		if (result.hasErrors()) {
			return "register.html";
		} else {
			System.err.println(user);
			return "otp.html";
		}
	}

}
