package com.smart.project.web.photo.act;

import com.smart.project.proc.PhotoMapper;
import com.smart.project.web.photo.service.AES256;
import com.smart.project.web.photo.service.PhotoHandler;
import com.smart.project.web.photo.vo.CompanyVO;
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
    final private PhotoHandler ph;
    final private AES256 aes;

    @RequestMapping("/wedInfoForm")
    public String updateWedinfo(Model model,HttpServletRequest req){
        HttpSession session = req.getSession(false);
        MemberVO vo = (MemberVO) session.getAttribute("loginSession");
        int uidx = vo.getUIdx();
        CompanyVO result = pm.findByIdx(uidx);
        String regnum1 = result.getCRegNum().substring(0,3);
        String regnum2 = result.getCRegNum().substring(4,6);
        String regnum3 = result.getCRegNum().substring(7,12);
        String address = result.getCAddr().split("/")[0];
        String detailAddr = result.getCAddr().split("/")[1];
        result.setBusiness1(regnum1);
        result.setBusiness2(regnum2);
        result.setBusiness3(regnum3);
        result.setAddress(address);
        result.setDetailAddr(detailAddr);
        model.addAttribute("companyDetailInfo",result);
        return "pages/wedInfoFormPage";
    }
    // 업체정보가 있으면 업체정보페이지로 없으면 업체정보입력페이지로 이동
    @RequestMapping("/chkWedInfo")
    public String chkWedInfo(HttpServletRequest req, Model model){
        HttpSession session = req.getSession(false);
        MemberVO vo = (MemberVO) session.getAttribute("loginSession");
        int uidx = vo.getUIdx();
        CompanyVO result = pm.findByIdx(uidx);
        log.error("result ==> {}",result);
        if(result==null){
            return "pages/wedInfoFormPage";
        }else{
            PhotoVO toFindThumbimg = new PhotoVO();
            toFindThumbimg.setUIdx(uidx);
            toFindThumbimg.setCImgType("thumbnail");
            PhotoVO photoVO = pm.selectThumbimg(toFindThumbimg);

            String dataurl = aes.encrypt(photoVO.getCImgPath());
            model.addAttribute("companyInfo",result);
            model.addAttribute("imgUrl", dataurl);
            log.error("imgURL ==> {}",dataurl);
            return "pages/wedInfoPage";
        }

    }
}
