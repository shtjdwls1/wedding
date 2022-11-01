package com.smart.project.web.mainpage.act;

import com.smart.project.proc.MainPagePlanner;
import com.smart.project.proc.MainPageWedding;
import com.smart.project.web.home.vo.MemberVO;
import com.smart.project.web.mainpage.vo.MainCompanyVO;
import com.smart.project.web.mainpage.vo.MainPlannerVO;
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
public class MainPageAct {
    final private MainPageWedding mainPageMapper;
    final private MainPagePlanner mainPagePlanner;

    @RequestMapping("/")
    public String mainPage(HttpServletRequest request, Model model) {
        String u_location = null;

        HttpSession session = request.getSession(false);
        log.error("세션 뭐임 :{}", session);
        if (session == null) {
            u_location = "서울";
        } else if (session.getAttribute("loginSession") == null) {
            u_location = "서울";
        } else {
            MemberVO loginMember = (MemberVO) session.getAttribute("loginSession");
            u_location = loginMember.getULocation();
        }


        log.error("세션값 : {}", session);


        log.error("세션1 : {}", u_location);

        List<MainCompanyVO> wedList = mainPageMapper.getStartWed(u_location);
        List<MainPlannerVO> planList = mainPagePlanner.getStartPlan(u_location);
        for (MainCompanyVO vo : wedList) {
            log.error("플래너 : {}", vo.getUName());
        }
        for (MainPlannerVO vo : planList) {
            log.error("플래너 : {}", vo.getUName());
        }
        log.error("결과1 : {}", mainPageMapper.getStartWed(u_location));

        model.addAttribute("WeddingData", wedList);
        model.addAttribute("PlannerData", planList);

        return "index";

    }
}
