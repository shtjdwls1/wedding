package com.smart.project.web.planner.act;

import com.smart.project.proc.PlannerService;
import com.smart.project.web.home.vo.MemberVO;
import com.smart.project.web.planner.vo.PlannerVO;
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
public class PlannerAct {


    final private PlannerService plannerService;


    @RequestMapping("/plannerDetail")
    public String plannerDetail(Model model) {
        int u_idx = 4;
        log.error("세션1 : {}", u_idx);


        return "pages/plannerDetailpage";
    }


    @RequestMapping("/plannerCommentView")
    public String plannerCommentView(Model model, HttpServletRequest request) {


        float totalGrade=0;
        HttpSession session = request.getSession(false);
        if (session != null) {
            log.error("deleteSessionbefore ==>{}", session.getAttribute("loginSession"));
        }
        MemberVO loginMember = (MemberVO) session.getAttribute("loginSession");
        log.error("세션1 : {}", loginMember.getUIdx());

        List<PlannerVO> list = plannerService.plannerReview(loginMember.getUIdx());
        int cnt = plannerService.plannerReviewCnt(loginMember.getUIdx());
        model.addAttribute("reviewList", list);
        model.addAttribute("reviewCnt", cnt);

//        int lastId =0;
//        model.addAttribute("reviewCnt", lastId);





        for (PlannerVO vo : list) {
            log.error("ck === {}", vo);
            totalGrade += vo.getPGrade();
        }
        log.error("total === {}", totalGrade);
        float grade = (totalGrade/cnt)/2;
        log.error("grade === {}", grade);
        model.addAttribute("gradeCnt", String.format("%.1f", grade));

        log.error("ck2 ===={}", cnt);
        return "pages/plannerCommentView";
    }

}
