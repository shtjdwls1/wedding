package com.smart.project.web.home.act;

import com.smart.project.component.CommonCodeComponent;
import com.smart.project.component.LocCodeComponent;
import com.smart.project.component.data.CodeObject;
import com.smart.project.oauth.Role;
import com.smart.project.proc.JoinMapper;
import com.smart.project.web.home.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HomeDataAct {

	final private
	CommonCodeComponent commonCodeComponent;

	final private LocCodeComponent locCodeComponent;
	final private JoinMapper join;
	@PostMapping("/data/wantLoc")
	public Map<String, Object> getWantLoc(@RequestBody Map param){
		Map<String, Object> data = new HashMap<>();
		String keyData = String.valueOf(param.get("key"));

		log.error("key===>{}", keyData);

		String[] key = keyData.split(",");


		List<String> keyList = new ArrayList<>();
		if(StringUtils.isNotEmpty(keyData)){
			keyList = Arrays.asList(keyData.split(","));
		}
		List<CodeObject.Code> wishLocData = commonCodeComponent.getCodeList("wishLoc");
		if(wishLocData != null){
			for(CodeObject.Code codeData : wishLocData){
				String keyArr = keyList.stream().filter(a -> a.equals(codeData.getCode())).findAny().orElse(null);
				if(StringUtils.isNotEmpty(keyArr)){
					log.error("keyArr===>{}", keyArr);
					codeData.setChecked(true);
				}else{
					codeData.setChecked(false);
				}
				for(int i = 0; i < key.length; i++){
					if(codeData.getCode().equals(key[i])){
						log.error("key===>{}", key[i]);
					}
				}
			}
			log.error("{}", wishLocData);
		}
		data.put("wishLoc", wishLocData);

		return data;
	}

	@PostMapping("/data/loc")
	public Map<String, Object> getLoc(@RequestBody Map param){
		Map<String, Object> data = new HashMap<>();


		locCodeComponent.getCodeList("m002");

		return data;
	}

	@PostMapping("/data/locMiddle")
	public Map<String, Object> getLocMiddle(@RequestBody Map param){
		Map<String, Object> data = new HashMap<>();


		locCodeComponent.getCodeList("m003e");

		return data;
	}

	@PostMapping("/data/join")
	public int MemberJoin(@RequestBody MemberVO vo){
		String telfull = vo.getUTelFront()+"-"+vo.getUTelMid()+"-"+vo.getUTelEnd();
		vo.setUTel(telfull);
		log.error("vo===>{}",vo);
		int data = join.save(vo);
		log.error("joindata===>{}",data);
		return data;
	}

	@PostMapping("/data/login")
	public int MemberLogin(@RequestBody MemberVO vo, HttpServletRequest request){
		log.error("value ==> {}",vo);
		MemberVO result = join.login(vo);
		log.error("result ==> {}",result);
		// 로그인 실행후 결과값이 널이 아니면 세션생성, 리턴 1
		// 널이면 리턴 0
		if(result!=null){
			HttpSession session = request.getSession();
			session.setAttribute("loginSession", result);
			log.error("session ==> {}",session.getAttribute("loginSession"));
			return 1;
		}else {
			return 0;
		}
	}
	@PostMapping("/data/checkId")
	public int CheckId(@RequestBody MemberVO vo){
		log.error("value ==> {}",vo);
		MemberVO result = join.chkId(vo);
		log.error("result ==> {}",result);
		if(result==null){
			return 1;
		}else {
			return 0;
		}
	}
}
