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
        String regnum1 = result.getCRegNum().split("-")[0];
        String regnum2 = result.getCRegNum().split("-")[1];
        String regnum3 = result.getCRegNum().split("-")[2];
        String address = result.getCAddr().split("/")[0];
        String detailAddr = result.getCAddr().split("/")[1];
        result.setBusiness1(regnum1);
        result.setBusiness2(regnum2);
        result.setBusiness3(regnum3);
        result.setAddress(address);
        result.setDetailAddr(detailAddr);
        PhotoVO toFindThumbimg = new PhotoVO();
        toFindThumbimg.setUIdx(uidx);
        toFindThumbimg.setCImgType("thumbnail");
        PhotoVO photoVO = pm.selectThumbimg(toFindThumbimg);
        model.addAttribute("companyDetailInfo",result);
        if (photoVO!=null){
            String dataurl = aes.encrypt(photoVO.getCImgPath());
            model.addAttribute("imgUrl", dataurl);
        }

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
        // 정보 유무 체크
        if(result==null){
            return "pages/wedInfoFormPage"; // 없으면 등록페이지
        }else{
            PhotoVO toFindThumbimg = new PhotoVO();
            toFindThumbimg.setUIdx(uidx);
            toFindThumbimg.setCImgType("thumbnail");
            PhotoVO photoVO = pm.selectThumbimg(toFindThumbimg);
            log.error("imgisNull ==> {}",photoVO);
            model.addAttribute("companyInfo",result);
            // 그리고 사진유무 체크
            if(photoVO==null){
                log.error("without photo");
                return "pages/wedInfoPage"; // 없으면 정보만가지고
            }else{
                // TODO imgUrl = "C:/자기프로젝트명"+imgUrl; 으로 바꿔주세요
                log.error("with photo");
                String imgPath = "C:/test"+photoVO.getCImgPath();
                String dataurl = aes.encrypt(imgPath);
                model.addAttribute("imgUrl", dataurl);
                log.error("imgURL ==> {}",dataurl);
                return "pages/wedInfoPage"; // 있으면 사진도 가지고
            }

        }

    }
}
