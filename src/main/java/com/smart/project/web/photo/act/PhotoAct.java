package com.smart.project.web.photo.act;

import com.smart.project.proc.PhotoMapper;
import com.smart.project.web.photo.vo.PhotoVO;
import com.smart.project.web.home.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
@Slf4j
@Controller
@RequiredArgsConstructor
public class PhotoAct {

    final private PhotoMapper pm;
    // 업체정보가 있으면 업체정보페이지로 없으면 업체정보입력페이지로 이동
    @RequestMapping("/chkWedInfo")
    public String chkWedInfo(HttpServletRequest req, Model model){
        HttpSession session = req.getSession(false);
        MemberVO vo = (MemberVO) session.getAttribute("loginSession");
        int uidx = vo.getUIdx();
        int result = pm.findByIdx(uidx);
        log.error("result ==> {}",result);

        if(result==0){
            return "pages/wedInfoFormPage";
        }else{
            PhotoVO toFindThumbimg = new PhotoVO();
            toFindThumbimg.setUIdx(uidx);
            toFindThumbimg.setCImgType("origin");
            PhotoVO photoVO = pm.selectThumbimg(toFindThumbimg);
            model.addAttribute("companyData", photoVO);
            return "pages/wedInfoPage";
        }

    }
}
