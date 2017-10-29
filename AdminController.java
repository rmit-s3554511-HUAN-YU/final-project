package org.rmit.admin.controller;

import org.rmit.core.controller.BaseController;
import org.rmit.core.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
	
	protected final static Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@RequestMapping ( "/index" )
    public ModelAndView showIndex() throws Exception {
       ModelAndView modelAndView = new ModelAndView();
       modelAndView.setViewName("/WEB-INF/view/admin/home.jsp");
       modelAndView.addObject("user", (User)session("user"));
       return modelAndView;
    }
	
	@ResponseBody
	@RequestMapping("/logout")
	public String logout() throws Exception {
		logger.info("===>do logout");
		removeSession("admin");
		return render(setMessage("1", "logout success!"));
	}
	
}
