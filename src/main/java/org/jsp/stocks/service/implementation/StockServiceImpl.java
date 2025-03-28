package org.jsp.stocks.service.implementation;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

import org.jsp.stocks.dto.Stock;
import org.jsp.stocks.dto.User;
import org.jsp.stocks.repository.UserRepository;
import org.jsp.stocks.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class StockServiceImpl implements StockService {
	
	@Value("${admin.email}")
	String adminEmail;
	
	@Value("${admin.password}")
	String adminPassword;

	@Value("${admin.api.key}")
	String apiKey;

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
	public String register(User user, BindingResult result,HttpSession session) {
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
			user.setPassword(AES.encrypt(user.getPassword()));
			userRepository.save(user);
			session.setAttribute("pass", "Otp Sent Success, check your email and Enter OTP");
			return "redirect:/otp/" + user.getId();
		}
	}

	@Override
	public String verifyOtp(int id, int otp,HttpSession session) {
		User user = userRepository.findById(id).get();
		if (user.getOtp() == otp) {
			user.setVerified(true);
			user.setOtp(0);
			userRepository.save(user);
			session.setAttribute("pass", "Account Created Success, Welcome "+user.getName());
			return "redirect:/login";
		} else {
			session.setAttribute("fail", "Invalid Otp Try Again");
			return "redirect:/otp/" + id;
		}
	}
	public void removeMessage() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes();
		HttpServletRequest req = attributes.getRequest();
		HttpSession session = req.getSession();
		session.removeAttribute("pass");
		session.removeAttribute("fail");
	}

	@Override
	public String login(String email, String password, HttpSession session) {
		if (email.equals(adminEmail) && password.equals(adminPassword)) {
			session.setAttribute("admin", "admin");
			session.setAttribute("pass", "Login Success - Welcome Admin");
			return "redirect:/";
		}

		Optional<User> user = userRepository.findByEmail(email);
		if (user.isEmpty()) {
			session.setAttribute("fail", "Invalid Email");
			return "redirect:/login";
		} else {
			if (AES.decrypt(user.get().getPassword()).equals(password)) {
				if (user.get().isVerified()) {
					session.setAttribute("user", user.get());
					session.setAttribute("pass", "Login Success, Welcome " + user.get().getName());
					return "redirect:/";
				} else {
					user.get().setOtp(generateOtp());
					sendEmail(user.get());
					userRepository.save(user.get());
					session.setAttribute("fail", "First Complete Verification in order to Login");
					return "redirect:/otp/" + user.get().getId();
				}
			} else {
				session.setAttribute("fail", "Invalid Password");
				return "redirect:/login";
			}
		}
	}

	@Override
	public String logout(HttpSession session) {
		User user = (User) session.getAttribute("user");
		session.removeAttribute("user");
		session.removeAttribute("admin");
		if (user != null)
			session.setAttribute("pass", "Logout Success, Sad to see you go Bye " + user.getName());
		return "redirect:/";
	}

	int generateOtp() {
		return new Random().nextInt(100000, 1000000);
	}
	
	void sendEmail(User user) {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
			helper.setFrom("saishkulkarni7@gmail.com", "StockMarketApp");
			helper.setTo(user.getEmail());
			helper.setSubject("OTP for Account Creation");
			helper.setText("<h1>Hello " + user.getName() + " Your OTP is : " + user.getOtp() + "</h1>", true);
			mailSender.send(message);
		} catch (Exception e) {
			System.err.println("Unable to Send Email");
			System.out.println("Hello " + user.getName() + " Your OTP is : " + user.getOtp());
		}
	}

	@Override
	public String login(String email, String password) {
		throw new UnsupportedOperationException("Unimplemented method 'login'");
	}

	@Override
	public String addStock(@RequestParam String ticker , HttpSession session) {
		if (session.getAttribute("admin") != null) {
			String addStockUrl =  "https://api.polygon.io/v2/aggs/ticker/"+ticker+"/range/1/minute/2024-03-27/2024-03-27?adjusted=true&sort=desc&limit=1&apiKey=" + apiKey;
			return "redirect:/";
		} else {
			session.setAttribute("fail", "Invalid Session, Login First");
			return "redirect:/login";
		}
	}

}
