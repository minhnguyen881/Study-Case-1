package studyCase1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import studyCase1.configuration.Message;
import studyCase1.model.User;
import studyCase1.service.UserService;

@Controller
@RequestMapping("/")
public class UserController {

	private static final String LOGIN_PATH = "/user/login";
	private static final String LOGIN_FORM_PATH = "/user/loginForm";
	private static final String REGISTER_PATH = "/user/save";
	private static final String REGISTER_FORM_PATH = "/user/new";
	private static final String REGISTRATION_PAGE = "registration";
	private static final String DASHBOARD_PAGE = "dashboard";
	private static final String LOGIN_PAGE = "login";
	private static final String GET_USER_PATH = "/user/list";
	private static final String USERS_PAGE = "users";	
	@Autowired
	private UserService userService;
	@Autowired
	Message message;

	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String openHomePage() {
		return DASHBOARD_PAGE;
	}

	@RequestMapping(value = { REGISTER_FORM_PATH }, method = RequestMethod.GET)
	public String openRegistrationPage() {
		return REGISTRATION_PAGE;
	}

	@RequestMapping(value = { REGISTER_PATH }, method = RequestMethod.POST)
	public String save(@Valid User user, ModelMap model) {
		if (this.userService.checkExisting(user.getUsername())) {
			model.addAttribute("message", message.get("user.existed"));
			return REGISTRATION_PAGE;
		}
		this.userService.save(user);
		return LOGIN_PAGE;
	}

	@RequestMapping(value = { LOGIN_PATH }, method = RequestMethod.POST)
	public String login(@Valid User user, ModelMap model) {
		if (this.userService.login(user)) {
			model.addAttribute("username", user.getUsername());
			return DASHBOARD_PAGE;
		}
		model.addAttribute("message", message.get("login.failed"));
		model.addAttribute("user", user);
		return LOGIN_PAGE;
	}

	@RequestMapping(value = { LOGIN_FORM_PATH }, method = RequestMethod.GET)
	public String openLoginPage(ModelMap model) {
		User user = new User("", "");
		model.addAttribute("user", user);
		return LOGIN_PAGE;
	}

	@RequestMapping(value = { GET_USER_PATH }, method = RequestMethod.GET)
	public String getUsers(ModelMap model) {
		List<String> users = this.userService.getUsers();
		model.addAttribute("users", users);
		return USERS_PAGE;
	}

}
