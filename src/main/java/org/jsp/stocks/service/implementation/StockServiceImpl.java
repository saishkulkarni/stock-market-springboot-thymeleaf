package org.jsp.stocks.service.implementation;

import org.jsp.stocks.dto.User;
import org.jsp.stocks.service.StockService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

@Service
public class StockServiceImpl implements StockService {
	
	@Value("${admin.username}")
	String adminUsername;
	@Value("${admin.password}")
	String adminPassword;

	@Override
	public String login(User user, HttpSession session) {
		if (user.getUsername().equals("user")) {
			if (user.getPassword().equals("user")) {
				session.setAttribute("user", "user");
				return "redirect:/";
			}
		} else {
			if (user.getUsername().equals(adminUsername)) {
				if (user.getPassword().equals(adminPassword)) {
					session.setAttribute("admin", "admin");
					return "redirect:/";
				}
			} else {
				return "redirect:/";
			}
			return "redirect:/";
		}
		return null;
	}
}
