package org.jsp.stocks.controller;

import org.jsp.stocks.dto.Stock;
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

import com.razorpay.RazorpayException;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class StockController {

	@Autowired
	StockService service;

	@GetMapping("/")
	public String loadMain() {
		return "main.html";
	}

	@GetMapping("/register")
	public String loadRegister(User user, Model model) {
		return service.register(user, model);
	}

	@PostMapping("/register")
	public String register(@ModelAttribute @Valid User user, BindingResult result, HttpSession session) {
		return service.register(user, result, session);
	}

	@GetMapping("/otp/{id}")
	public String loadOtpPage(@PathVariable int id, Model model) {
		model.addAttribute("id", id);
		return "otp.html";
	}

	@PostMapping("/otp")
	public String verifyOtp(@RequestParam int id, @RequestParam int otp, HttpSession session) {
		return service.verifyOtp(id, otp, session);
	}

	@GetMapping("/login")
	public String loadLogin() {
		return "login.html";
	}

	@PostMapping("/login")
	public String login(@RequestParam String email, @RequestParam String password, HttpSession session) {
		return service.login(email, password, session);
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		return service.logout(session);
	}

	@GetMapping("/add-stock")
	public String loadAddStock(HttpSession session) {
		return service.addStock(session);
	}

	@PostMapping("/add-stock")
	public String addStock(HttpSession session,Stock stock) {
		return service.addStock(session,stock);
	}
	
	@GetMapping("/manage-stocks")
	public String manageStocks(HttpSession session,Model model) {
		return service.fetchStocks(session,model);
	}
	
	@GetMapping("/delete-stock/{ticker}")
	public String deleteStock(@PathVariable String ticker,HttpSession session) {
		return service.deleteStock(ticker,session);
	}
	
	@GetMapping("/view-stocks")
	public String viewStocks(HttpSession session,Model model,@RequestParam(required = false) String company) {
		return service.viewStocks(session,model,company);
	}
	
	@GetMapping("/wallet")
	public String viewWallet(HttpSession session,Model model) {
		return service.viewWallet(session,model);
	}
	
	@PostMapping("/wallet/{amount}")
	public String paymentSuccess(@PathVariable double amount,HttpSession session) {
		return service.paymentSuccess(amount,session);
	}
	
	@GetMapping("/recharge")
	public String rechargeWallet(@RequestParam double amount,HttpSession session,Model model) throws RazorpayException {
		return service.rechargeWallet(amount,session,model);
	}
}
