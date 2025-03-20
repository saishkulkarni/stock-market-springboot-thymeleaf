package org.jsp.stocks.service;

import org.jsp.stocks.dto.User;

import jakarta.servlet.http.HttpSession;

public interface StockService {

	String login(User user, HttpSession session);

}
