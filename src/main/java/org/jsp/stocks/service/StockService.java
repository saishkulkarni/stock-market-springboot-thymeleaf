package org.jsp.stocks.service;

import org.jsp.stocks.dto.Stock;
import org.jsp.stocks.dto.User;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.razorpay.RazorpayException;

import jakarta.servlet.http.HttpSession;

public interface StockService {

	String register(User user, Model model);

	String register(User user, BindingResult result, HttpSession session);

	String verifyOtp(int id, int otp, HttpSession session);

	String login(String email, String password, HttpSession session);

	String logout(HttpSession session);

	String addStock(HttpSession session);

	String addStock(HttpSession session, Stock stock);

	String fetchStocks(HttpSession session, Model model);

	String deleteStock(String ticker, HttpSession session);

	String viewStocks(HttpSession session, Model model, String company);

	String viewWallet(HttpSession session, Model model);

	String rechargeWallet(double amount, HttpSession session, Model model) throws RazorpayException;

	String paymentSuccess(double amount, HttpSession session);

	String viewStock(HttpSession session, Model model, String ticker);

	String buyStock(String ticker, double quantity, HttpSession session, Model model);

	String confirmPurchase(HttpSession session, String ticker, double quantity, double price);

	String viewOverview(HttpSession session, Model model);

	String viewPortfolio(HttpSession session, Model model);

	String viewSell(String ticker, HttpSession session, Model model);

	String sellStocks(double quantity, String ticker, HttpSession session);

}
