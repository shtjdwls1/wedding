package com.smart.project.web.reserve.act;

import com.smart.project.proc.MyReserveService;
import com.smart.project.web.home.vo.MemberVO;
import com.smart.project.web.reserve.vo.MyReserveVO;
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
public class MyReserveAct {

    final private MyReserveService myReserveService;

    @RequestMapping("/myReserve")
    public String myReserve(Model model, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            log.error("deleteSessionbefore ==>{}", session.getAttribute("loginSession"));
        }
        MemberVO loginMember = (MemberVO) session.getAttribute("loginSession");
        log.error("세션1 : {}", loginMember.getUIdx());

        List<MyReserveVO> list = myReserveService.myReserve(loginMember.getUIdx());



        log.error("결과1 : {}", myReserveService.myReserve(loginMember.getUIdx()));
        for (MyReserveVO vo : list) {
            log.error("예약번호 : {}", vo.getHReserveIdx());
        }

        model.addAttribute("myReserves", list);
        log.error("model확인 : {}", model);

        return "pages/myReservePage";
    }


    @PostMapping("/myReserve/cancel")
    @ResponseBody
    public int myReserveCancel(@RequestBody MyReserveVO vo) {
        log.error("입력===>{}", vo);
        int result = myReserveService.myReserveCancel(vo);
        log.error("결과===>{}", result);
        return result;
    }

}
