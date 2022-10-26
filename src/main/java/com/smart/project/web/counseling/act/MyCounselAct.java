package com.smart.project.web.counseling.act;

import com.smart.project.proc.MyCounselService;
import com.smart.project.web.counseling.vo.MyCounselUpdateVO;
import com.smart.project.web.counseling.vo.MyCounselVO;
import com.smart.project.web.home.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MyCounselAct {

    final private MyCounselService myCounselService;

    @RequestMapping("/myCounsel")
    public String myCounsel(Model model, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            log.error("deleteSessionbefore ==>{}", session.getAttribute("loginSession"));
        }
        MemberVO loginMember = (MemberVO) session.getAttribute("loginSession");
        log.error("세션1 : {}", loginMember.getUIdx());

        List<MyCounselVO> list = myCounselService.myCounsel(loginMember.getUIdx());
        log.error("결과1 : {}", myCounselService.myCounsel(loginMember.getUIdx()));
        for (MyCounselVO vo : list) {
            log.error("플래너 : {}", vo.getPGrade());
        }
        model.addAttribute("myCounsels", list);

        return "pages/myCounselPage";
    }

    @PostMapping("/myCounsel/reviewWrite")
    @ResponseBody
    public int reviewWrite(@RequestBody MyCounselUpdateVO vo, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            log.error("deleteSessionbefore ==>{}", session.getAttribute("loginSession"));
        }
        MemberVO loginMember = (MemberVO) session.getAttribute("loginSession");
        log.error("세션1 : {}", loginMember.getUIdx());


        log.error("입력===>{}", vo);
        vo.setCounselIdx(loginMember.getUIdx());
        log.error("최종===>{}", vo);
        int result = myCounselService.myCounselUpdate(vo);
        return result;
    }

}