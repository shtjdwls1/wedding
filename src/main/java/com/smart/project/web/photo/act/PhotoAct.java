package com.smart.project.web.photo.act;

import com.smart.project.proc.PhotoMapper;
import com.smart.project.web.photo.service.AES256;
import com.smart.project.web.photo.service.PhotoHandler;
import com.smart.project.web.photo.vo.*;
import com.smart.project.web.home.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

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
        String detailAddr;
        if(result.getCAddr().split("/").length!=2){
            detailAddr = "";
        }else{
            detailAddr = result.getCAddr().split("/")[1];
        }
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
            String dataurl = aes.encrypt("C:/test"+photoVO.getCImgPath());
            model.addAttribute("imgUrl", dataurl);
        }

        return "pages/wedInfoFormPage";
    }
    // ??????????????? ????????? ???????????????????????? ????????? ?????????????????????????????? ??????
    @RequestMapping("/chkWedInfo")
    public String chkWedInfo(HttpServletRequest req, Model model){
        HttpSession session = req.getSession(false);
        MemberVO vo = (MemberVO) session.getAttribute("loginSession");
        int uidx = vo.getUIdx(); // ?????? idx???
        CompanyVO result = pm.findByIdx(uidx);
        log.error("result ==> {}",result);
        // ?????? ?????? ??????
        if(result==null){
            return "pages/wedInfoFormPage"; // ????????? ???????????????
        }else{
            PhotoVO toFindThumbimg = new PhotoVO();
            toFindThumbimg.setUIdx(uidx);
            toFindThumbimg.setCImgType("thumbnail");
            PhotoVO photoVO = pm.selectThumbimg(toFindThumbimg);
            log.error("imgisNull ==> {}",photoVO);
            model.addAttribute("companyInfo",result);
            // ????????? ???????????? ??????
            if(photoVO==null){
                log.error("without photo");
                return "pages/wedInfoPage"; // ????????? ??????????????????
            }else{

                log.error("with photo");
                // TODO "C:/?????????????????????"+photoVO.getCImgPath(); ?????? ???????????????
                String dataurl = aes.encrypt("C:/test"+photoVO.getCImgPath());
                model.addAttribute("imgUrl", dataurl);
                log.error("imgURL ==> {}",dataurl);

                // TODO ???????????? ???????????? ????????????
                //  - 1.?????????,?????????,???????????? ????????????
                //  - 2.?????? ????????? ????????? ????????????

                // 1.
                List<HallVO> hallVO = pm.selectHall(uidx);
                for (HallVO hall : hallVO) {
                    List<HallTimeVO> hallTimeVO = pm.selectHallTime(new SelectVO(uidx, hall.getHName()));
                    hall.setHallTimeVO(hallTimeVO);
                    List<HallImgVO> hallImgVO = pm.selectHallImg(new SelectVO(uidx, hall.getHName()));
                    for (HallImgVO hivo : hallImgVO){
                        dataurl = aes.encrypt("C:/test"+hivo.getHImgPath());
                        hivo.setHImgPath(dataurl);
                    }
                    hall.setHallImgVO(hallImgVO);
                }

                model.addAttribute("hallList",hallVO);






                return "pages/wedInfoPage"; // ????????? ????????? ?????????
            }

        }

    }
}
