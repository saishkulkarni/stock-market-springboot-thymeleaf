package org.jsp.stocks.service;

import org.jsp.stocks.dto.User;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

public interface StockService {

	String register(User user, Model model);

	String register(User user, BindingResult result);

	String verifyOtp(int id, int otp);

    String login(String email, String password);

}
