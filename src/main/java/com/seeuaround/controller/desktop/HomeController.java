package com.seeuaround.controller.desktop;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController
{	
	private static final Logger log = Logger.getLogger(HomeController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView getdata()
	{
		log.debug("HomerController :: getdata");

		ModelAndView model = new ModelAndView("index");

		return model;
	}
}