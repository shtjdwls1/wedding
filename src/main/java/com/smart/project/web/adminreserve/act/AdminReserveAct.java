package com.smart.project.web.adminreserve.act;

import com.smart.project.proc.AdminReserveService;
import com.smart.project.web.adminreserve.vo.AdminReserveVO;
import com.smart.project.web.home.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminReserveAct {

    final private AdminReserveService adminReserveService;


    @RequestMapping("/adminReserve")
    public String adminReserve(HttpServletRequest request, Model model) {
        String date = request.getParameter("date");

        if (date == null) {
            String now = LocalDate.now().toString();
            date = now;
        }
        model.addAttribute("Date", date);

        HttpSession session = request.getSession(false);
        if (session != null) {
            log.error("deleteSessionbefore ==>{}", session.getAttribute("loginSession"));
        }
        MemberVO loginMember = (MemberVO) session.getAttribute("loginSession");

        int uIdx = loginMember.getUIdx();
        log.error("idx : {}", uIdx);
        List<AdminReserveVO> list = adminReserveService.hallAll(uIdx);
        log.error("홀 5개 불러올 리스트 : {}", list);
        model.addAttribute("HallList", list);


        AdminReserveVO vo = new AdminReserveVO();
        vo.setUIdx(uIdx);
        vo.setDate(date);
        List<AdminReserveVO> list3 = adminReserveService.reserveUser(vo);
        log.error("예약한 손님 : {}", list3);
        model.addAttribute("HallTimeList", list3);
        return "pages/adminReservePage";
    }

}
