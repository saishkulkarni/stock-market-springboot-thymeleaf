package org.jsp.stocks.service.implementation;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

import org.jsp.stocks.dto.User;
import org.jsp.stocks.repository.UserRepository;
import org.jsp.stocks.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import jakarta.mail.internet.MimeMessage;

@Service
public class StockServiceImpl implements StockService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	JavaMailSender mailSender;

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
			user.setPassword(AES.encrypt(user.getPassword(), "123"));
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

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
			helper.setFrom("saishkulkarni7@gmail.com", "StockMarketApp");
			helper.setTo(user.getEmail());
			helper.setSubject("OTP for Account Creation");
			helper.setText("<h1>Hello " + user.getName() + " Your OTP is : " + user.getOtp() + "</h1>", true);
			mailSender.send(message);
		} catch (Exception e) {
		}
	}

	@Override
	public String login(String email, String password) {
		Optional<User> user = (Optional<User>) userRepository.findByEmail(email);
		if (user.isEmpty()) {
			return "redirect:/login";
		} else {
			if (user.get().getPassword().equals(AES.decrypt(password, "123"))) {

				if (user.get().isVerified()) {
					return "redirect:/";
				} else {
					user.get().setOtp(generateOtp());
					sendEmail(user.get());
					user.get().setPassword(AES.encrypt(user.get().getPassword(), "123"));
					userRepository.save(user.get());
					return "redirect:/{otp}" + user.get().getId();
				}

			}
			else{
				return "redirect:/login";
			}

		}

	}

}
