package org.jsp.stocks.service.implementation;

import java.time.LocalDate;
import java.util.Random;

import org.jsp.stocks.dto.User;
import org.jsp.stocks.repository.UserRepository;
import org.jsp.stocks.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Service
public class StockServiceImpl implements StockService {

	@Autowired
	UserRepository userRepository;

	@Override
	public String register(User user, Model model) {
		model.addAttribute("user", user);
		return "register.html";
	}

	@Override
	public String register(User user, BindingResult result) {
		if (!user.getPassword().equals(user.getConfirmPassword()))
			result.rejectValue("confirmPassword", "error.confirmPassword",
					"* Password and Confirm Password are Not Matching");
		if (user.getDob() != null) {
			if (LocalDate.now().getYear() - user.getDob().getYear() < 18)
				result.rejectValue("dob", "error.dob", "* You should be 18+ to Create Account here");
		}
		if (userRepository.existsByEmail(user.getEmail()))
			result.rejectValue("email", "error.email", "* Email should be Unique");

		if (userRepository.existsByMobile(user.getMobile()))
			result.rejectValue("mobile", "error.mobile", "* Mobile Number should be Unique");

		if (result.hasErrors()) {
			return "register.html";
		} else {
			user.setOtp(generateOtp());
			sendEmail(user);
			userRepository.save(user);
			return "redirect:/otp/" + user.getId();
		}
	}

	@Override
	public String verifyOtp(int id, int otp) {
		User user = userRepository.findById(id).get();
		if (user.getOtp() == otp) {
			user.setVerified(true);
			userRepository.save(user);
			return "redirect:/login";
		} else {
			return "redirect:/otp/" + id;
		}
	}

	int generateOtp() {
		return new Random().nextInt(100000, 1000000);
	}

	void sendEmail(User user) {
		System.err.println("Hello " + user.getName() + " Your OTP is : " + user.getOtp());
		
	}

}
