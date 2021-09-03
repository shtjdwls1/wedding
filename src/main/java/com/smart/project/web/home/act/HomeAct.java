package com.smart.project.web.home.act;

import com.smart.project.component.CommonCodeComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class HomeAct {

	@Autowired
	CommonCodeComponent commonCodeComponent;

	@RequestMapping("/")
	public String home(){
		log.error("{}",commonCodeComponent.getCodeList("style_f"));
		return "index";
	}

	@RequestMapping("/join")
	public String join(){
		log.error("{}",commonCodeComponent.getCodeList("style_f"));
		return "join";
	}

	@RequestMapping("/data")
	@ResponseBody
	public String homeData(){
		return "index";
	}
}
