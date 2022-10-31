package com.smart.project.web.planner.act;

import com.smart.project.proc.PlannerService;
import com.smart.project.web.home.vo.MemberVO;
import com.smart.project.web.planner.vo.PlannerVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("/plannerCommentView")
    public String plannerCommentView(Model model, HttpServletRequest request,
                                     @RequestParam(name = "columnData") String columnData,
                                     @RequestParam(name = "offset") int offset,
                                     @RequestParam(name = "sortData") String sortData,
                                     @RequestParam(name = "ck") String ck) {
        PlannerVO vo = new PlannerVO();

        HttpSession session = request.getSession(false);
        if (session != null) {
            log.error("deleteSessionbefore ==>{}", session.getAttribute("loginSession"));
        }
        MemberVO loginMember = (MemberVO) session.getAttribute("loginSession");
        log.error("세션1 : {}", loginMember.getUIdx());

        int cnt = plannerService.plannerReviewCnt(loginMember.getUIdx());
        model.addAttribute("reviewCnt", cnt);

        vo.setColumnData(columnData);
        vo.setOffset(offset);
        vo.setSort(sortData);
        vo.setUIdx(loginMember.getUIdx());


        model.addAttribute("newSort", vo);

        List<PlannerVO> list = plannerService.plannerReview(vo);
        model.addAttribute("reviewList", list);

        float grade = plannerService.plannerReviewGrade(loginMember.getUIdx());

        model.addAttribute("gradeCnt", String.format("%.1f", grade / cnt / 2));

        log.error("ck2 ===={}", cnt);
        return "pages/plannerCommentView";
    }

    @PostMapping("/plannerReviewData")
    @ResponseBody
    public List<PlannerVO> plannerReviewData(Model model, HttpServletRequest request, @RequestBody PlannerVO vo) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            log.error("deleteSessionbefore ==>{}", session.getAttribute("loginSession"));
        }
        MemberVO loginMember = (MemberVO) session.getAttribute("loginSession");
        log.error("세션1 : {}", loginMember.getUIdx());
        vo.setUIdx(loginMember.getUIdx());

        List<PlannerVO> list = plannerService.plannerReview(vo);

        return list;
    }

}
