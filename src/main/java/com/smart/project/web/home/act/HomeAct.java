package com.smart.project.web.home.act;

import com.smart.project.common.vo.InternCookie;
import com.smart.project.component.CommonCodeComponent;
import com.smart.project.proc.MainPagePlanner;
import com.smart.project.proc.MainPageWedding;
import com.smart.project.proc.Test;
import com.smart.project.security.StudyCookieService;
import com.smart.project.web.mainpage.vo.MainCompanyVO;
import com.smart.project.web.mainpage.vo.MainPlannerVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeAct {

    final private
    CommonCodeComponent commonCodeComponent;

    final private Test test;

    // 메인 들어올때
//    @RequestMapping("/")
//    public String home(Model model, InternCookie cookie, HttpServletRequest request) {
//        if (StringUtils.isNotEmpty(cookie.getUserId())) {
//            log.error("cookie check==>{}//{}//{}", cookie.getUserId(), cookie.getName(), cookie.getEmpNo());
//        }
//        // 서블릿 HTTP 세션 사용
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            MemberVO loginMember = (MemberVO) session.getAttribute("loginSession");
//        }
//        return "index";
//    }
    final private MainPageWedding mainPageMapper;
    final private MainPagePlanner mainPagePlanner;

    @RequestMapping("/login")
    public String login(Model model, InternCookie cookie, HttpServletRequest request) {
        return "pages/login";
    }

    @RequestMapping("/cookie/add2")
    public String cookieAdd(HttpServletResponse response) throws java.io.UnsupportedEncodingException {
        StudyCookieService.createCookie(response, "USER_ID", "mygod76");
        StudyCookieService.createCookie(response, "NAME", URLEncoder.encode("김남현", "UTF-8"));
        StudyCookieService.createCookie(response, "EMP_NO", URLEncoder.encode("emp_no=1234", "UTF-8"));
        return "cookie";
    }

    @GetMapping(value = "cookie3")
    public void cookieAdd2(HttpServletResponse response, Model model) {
        model.addAttribute("aaa", "aaa");
        log.error("aaaaaa");
    }

    //로컬 회원가입
    @RequestMapping("/localJoin")
    public String localJoin() {
        return "localJoinPage";
    }

    //소셜 회원가입
    @RequestMapping("/socialJoin")
    public String socialJoin() {
        return "pages/socialJoinPage";
    }

    @RequestMapping("/changeMyInfo")
    public String changeMyInfo() {
        return "pages/changeMyInfoPage";
    }

    @RequestMapping("/changeMyInfoForm")
    public String changeMyInfoForm() {
        return "pages/changeMyInfoFormPage";
    }

    @RequestMapping("/payment")
    public String payment() {
        return "pages/paymentPage";
    }

    @RequestMapping("/loadingPayment")
    public String loadingPayment() {
        return "pages/loadingPaymentPage";
    }


    @RequestMapping("/wedInfo")
    public String wedInfo() {
        return "pages/wedInfoPage";
    }

    @RequestMapping("/plannerInfoFrom")
    public String plannerInfoFrom() {
        return "pages/plannerInfoFromPage";
    }

    @RequestMapping("/data")
    @ResponseBody
    public String homeData() {
        return "index";
    }

    // 로그아웃
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            log.error("deleteSessionbefore ==>{}", session.getAttribute("loginSession"));
            session.invalidate();
        }
        String u_location = "서울";

        List<MainCompanyVO> wedList = mainPageMapper.getStartWed(u_location);
        List<MainPlannerVO> planList = mainPagePlanner.getStartPlan(u_location);


        model.addAttribute("WeddingData", wedList);
        model.addAttribute("PlannerData", planList);

        return "index";
    }

}
