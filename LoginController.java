package org.rmit.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rmit.core.controller.BaseController;
import org.rmit.core.entity.User;
import org.rmit.core.exception.RmitException;
import org.rmit.core.service.UserService;
import org.rmit.core.util.DataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller("admin_login")
@RequestMapping("/admin/login")
public class LoginController extends BaseController {
	
	protected final static Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/index")
	public ModelAndView showLogin() {
		logger.info("===>Go to the login page");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/WEB-INF/view/admin/login.jsp");
		modelAndView.addObject("user", (User)session("key"));
		return modelAndView;
	}
	
	@ResponseBody
	@RequestMapping("/doLogin")
	public String doLogin(String username, 
			String type,
			String password) throws Exception {
		logger.info("===>do login");
		
		if(DataUtil.isEmpty(username)){
			throw new RmitException("0", "The username can not be empty!");
		}
		if(DataUtil.isEmpty(password)){
			throw new RmitException("0", "The password can not be empty !");
		}
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("name", username);
		param.put("type", type);
		List<User> users = userService.queryUser(param);
		if(null == users || users.size()<=0){
			throw new RmitException("0", "User does not exist !");
		}
		
		User user = users.get(0);
		if(!password.equals(user.getPassword())){
			throw new RmitException("0", "The password is incorrect !");
		}
		
		if("0".equals(user.getStatus())){
			throw new RmitException("0", "The user is inacitvated!");
		}
		
		session("user",user);
		return render(setMessage("1", "success"));
	}
	
	
}
