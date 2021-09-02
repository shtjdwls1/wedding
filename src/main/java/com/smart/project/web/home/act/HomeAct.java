package com.smart.project.web.home.act;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeAct {

	@RequestMapping("/")
	public String home(){
		return "index";
	}

	@RequestMapping("/data")
	@ResponseBody
	public String homeData(){
		return "index";
	}
}
