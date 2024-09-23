package com.boot.music.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.boot.music.entity.Account;
import com.boot.music.entity.Role;
import com.boot.music.entity.User;
import com.boot.music.repositories.AccountRepo;
import com.boot.music.repositories.RoleRepo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Email;

@Validated
@Controller
@RequestMapping("/")
public class LoginController {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepo roleRepo;
	@Autowired
	private AccountRepo accRepo;
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("music");
	EntityManager e = emf.createEntityManager();

	@GetMapping("/login")
	public ModelAndView login(ModelAndView m, @ModelAttribute User u) {
		m = new ModelAndView("login");

		return m;
	}

	// ajax trang login
	@GetMapping("/loginHTML")
	public @ResponseBody String loginHTML() {

		return "		 	  <div class=\"bg-img\">\r\n" + "      <div class=\"content\">\r\n"
				+ "      <div id=\"close-div\"><button onclick=\"closeLog()\" id=\"close\">X</button></div>\r\n"
				+ "        <header id=\"he\" >ĐĂNG NHẬP</header>\r\n" + "   \r\n"
				+ "        <form method=\"post\" action=\"login\" >\r\n" + "        \r\n"
				+ "         <div class=\"field\">\r\n" + "            <span class=\"fa fa-user\"></span>\r\n"
				+ "           <input id=\"in-user\" name=\"username\" onblur=\"return isValidForm()\" required placeholder=\"Tài khoản\"/>\r\n"
				+ "          \r\n" + "          \r\n" + "          </div>\r\n"
				+ "          <div id=\"u-error\" style=\"height: 25px;color: #a2f125;font-weight: bold;\"></div>\r\n"
				+ "          <div class=\"field space\">\r\n" + "            <span class=\"fa fa-lock\"></span>\r\n"
				+ "            <input type=\"password\" name=\"password\"  id=\"pas\" class=\"pass-key\" onblur=\"return isValidForm()\" required placeholder=\"Mật khẩu\">\r\n"
				+ "            \r\n" + "          </div>\r\n"
				+ "          <div id=\"p-error\" style=\"height: 25px;color: #a2f125;font-weight: bold;\"></div>\r\n"
				+ "          <div class=\"pass\">\r\n" + "            \r\n" + "          </div>\r\n"
				+ "          <div class=\"field\">\r\n"
				+ "            <input id=\"login-submit\" type=\"submit\" value=\"Đăng nhập\">\r\n"
				+ "          </div>\r\n" + "        \r\n" + "        \r\n" + "        </form>\r\n" + "       \r\n"
				+ "       \r\n" + "        <div onclick=\"showReg()\" id=\"sa\" class=\"signup\">Chưa có tài khoản?\r\n"
				+ "          <a href=\"#\" onclick=\"showReg()\">Đăng kí ngay</a>\r\n" + "        </div>\r\n"
				+ "      </div>\r\n" + "    </div>\r\n" + "\r\n" + "\r\n"

				+ "  ";
	}

	// hàm này chỉ chạy khi các field đã đc check ok, nên ko cần bắt lỗi
	@PostMapping("/loginCheck")
	public String loginCheck(RedirectAttributes redirect, @RequestParam String username, @RequestParam String password,
			HttpSession session, ModelAndView m) {
		String des = "";

		Query q = e.createQuery("from Account where username=?1 and password=?2", Account.class);
		q.setParameter(1, username);
		q.setParameter(2, password);
		Account user = (Account) q.getResultList().get(0);
		session.setAttribute("Acc", user);
		if (user.getRole().getId() == 1) {
			redirect.addAttribute("p", "1");
			des = "redirect:adminCheck";
		} else {
			des = "redirect:home";
		}
		return des;
	}

	// check xem có tk này ko khi login
	@GetMapping("/loginCheckU")
	public @ResponseBody String loginCheckU(ModelAndView m, @RequestParam String username) {
		String res = "";

		Query q = e.createQuery("select id from Account where username=?1 or mail=?2", Account.class);
		q.setParameter(1, username);
		q.setParameter(2, username);
		q.getResultList();
		if (q.getResultList().size() == 0) {

			res = "Tài khoản không tồn tại!!";
		}

		return res;
	}

	// check password khi login
	@GetMapping("/loginCheckP")
	public @ResponseBody String loginCheckP(ModelAndView m, @RequestParam String username, @RequestParam String pw) {
		String res = "";

		Query q = e.createQuery("from Account where username=?1 or mail=?2", Account.class);
		q.setParameter(1, username);
		q.setParameter(2, username);
		q.getResultList();
		if (q.getResultList().size() != 0) {
			Account user = (Account) q.getResultList().get(0);
			if (!passwordEncoder.matches(pw, user.getPassword())) {
				res = "Password không đúng!!";
			}
		}

		System.out.println(q.getResultList().size());
		System.out.println(username);
		return res;
	}

	@PostMapping("/regisCheck")
	public String registry(HttpServletRequest request, @RequestParam String username, @RequestParam String password,
			@RequestParam String fullname, @Email @RequestParam String email, HttpSession session) {
		// Đăng ký
		String encodedPass = passwordEncoder.encode(password); // Mã hoá mật khẩu
		Account u = new User(username, encodedPass, fullname, "", email, roleRepo.findById(2).get());
		accRepo.save(u);

		// Đăng nhập sau khi đăng ký thành công
		try {
			request.login(username, password);
		} catch (Exception e) {

		}
		return "redirect:home";
	}

	// check mail khi dk
	@GetMapping("/regisCheckM")
	public @ResponseBody String regisCheckMail(@RequestParam String mail) {

		String res = "";

		Query q = e.createQuery("select id from Account where mail=?1 ", Account.class);
		q.setParameter(1, mail);
		q.getResultList();
		if (q.getResultList().size() != 0) {

			res = "Email này đã được sử dụng!!";
		}

		return res;
	}

	@GetMapping("/regisCheckU")
	public @ResponseBody String regisCheckUser(@RequestParam String username) {
		String res = "";

		Query q = e.createQuery("select id from Account where username=?1 ", Account.class);
		q.setParameter(1, username);
		q.getResultList();
		if (q.getResultList().size() != 0) {

			res = "Tên người dùng bị trùng!!";
		}

		return res;

	}

	@GetMapping("/registration")
	public String regisForm() {

		return "registry";
	}

	@GetMapping("/showReg")
	public @ResponseBody String showReg() {
		return "\r\n" + " 	 	  <div class=\"bg-img\">\r\n" + "      <div class=\"content\">\r\n"
				+ "      <div id=\"close-div\"><button onclick=\"closeLog()\" id=\"close\">X</button></div>\r\n"
				+ "        <header id=\"he\" >ĐĂNG KÝ</header>\r\n" + "   \r\n"
				+ "        <form method=\"post\" action=\"/regisCheck\" onsubmit=\"return isValidForm1()\" >\r\n"
				+ "        \r\n" + "         <div class=\"field\">\r\n"
				+ "            <span class=\"fa fa-user\"></span>\r\n"
				+ "            <input  name=\"username\"  id=\"in-user1\" type=\"text\" required placeholder=\"Tên người dùng\"/>\r\n"
				+ "          </div>\r\n"
				+ "          <div id=\"u-error\" style=\"height: 25px;color: #a2f125;font-weight: bold;\"></div>\r\n"
				+ "          \r\n" + "           <div class=\"field\">\r\n"
				+ "            <span class=\"fa fa-user\"></span>\r\n"
				+ "            <input  name=\"fullname\"  id=\"in-name1\" type=\"text\" required placeholder=\"Họ và tên\"/>\r\n"
				+ "          </div>\r\n"
				+ "          <div id=\"n-error\" style=\"height: 25px;color: #a2f125;font-weight: bold;\"></div>\r\n"
				+ "          \r\n" + "           <div class=\"field\">\r\n"
				+ "            <span class=\"fa fa-envelope\"></span>\r\n"
				+ "            <input  name=\"email\"  id=\"in-mail\" type=\"email\" required placeholder=\"Email\"/>\r\n"
				+ "          </div>\r\n"
				+ "          <div id=\"m-error\" style=\"height: 25px;color: #a2f125;font-weight: bold;\"></div>\r\n"
				+ "          \r\n" + "          \r\n" + "          \r\n"
				+ "          <div class=\"field space\" style=\"margin-top: 0px\">\r\n"
				+ "            <span class=\"fa fa-lock\"></span>\r\n"
				+ "            <input minlength=\"4\" name=\"password\"  type=\"password\" min=\"4\"  id=\"pas1\" class=\"pass-key\" required placeholder=\"Mật khẩu\">\r\n"
				+ "          </div>\r\n"
				+ "          <div id=\"p-error\" style=\"height: 25px;color: #a2f125;font-weight: bold;\"></div>           \r\n"
				+ "          \r\n" + "          \r\n" + "          <div class=\"field\">\r\n"
				+ "            <input id=\"login-submit\" type=\"submit\" value=\"Đăng ký\">\r\n"
				+ "          </div>\r\n" + "        </form>\r\n" + "       \r\n" + "      </div>\r\n" + "   </div>\r\n"
				+ "  ";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("Acc");
		return "redirect:home";
	}

	@GetMapping("/adminCheck")
	public String adminCheck(RedirectAttributes redirect, HttpSession session) {
		String des = "";
		Account ad = (User) session.getAttribute("Acc");
		if (ad.getRole().getId() == 1) {
			redirect.addAttribute("p", "1");
			des = "redirect:/admin/song";
		} else {
			des = "redirect:home";
		}
		return des;
	}
}
