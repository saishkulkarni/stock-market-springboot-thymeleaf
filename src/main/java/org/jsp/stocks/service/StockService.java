package org.jsp.stocks.service;

import org.jsp.stocks.dto.Stock;
import org.jsp.stocks.dto.User;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import jakarta.servlet.http.HttpSession;

public interface StockService {

	String register(User user, Model model);

	String register(User user, BindingResult result, HttpSession session);

	String verifyOtp(int id, int otp, HttpSession session);

	String login(String email, String password, HttpSession session);

	String logout(HttpSession session);

	String addStock(HttpSession session);

	String addStock(HttpSession session, Stock stock);

}
