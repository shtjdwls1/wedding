package com.smart.project.web.counseling.act;

import com.smart.project.proc.MyCounselService;
import com.smart.project.web.counseling.vo.MyCounselUpdateVO;
import com.smart.project.web.counseling.vo.MyCounselVO;
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
public class MyCounselAct {

    final private MyCounselService myCounselService;

    @RequestMapping("/myCounsel")
    public String myCounsel(Model model, HttpServletRequest request,
                            @RequestParam(name = "offset") int offset,
                            @RequestParam(name = "ck") String ck) {

        MyCounselVO vo = new MyCounselVO();
        HttpSession session = request.getSession(false);
        if (session != null) {
            log.error("deleteSessionbefore ==>{}", session.getAttribute("loginSession"));
        }
        MemberVO loginMember = (MemberVO) session.getAttribute("loginSession");
        log.error("세션1 : {}", loginMember.getUIdx());

        vo.setUIdx(loginMember.getUIdx());
        vo.setOffset(offset);

        List<MyCounselVO> list = myCounselService.myCounsel(vo);

        model.addAttribute("myCounsels", list);

        return "pages/myCounselPage";
    }


    @PostMapping("/myCounselData")
    @ResponseBody
    public List<MyCounselVO> myCounselData(Model model, HttpServletRequest request, @RequestBody MyCounselVO vo) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            log.error("deleteSessionbefore ==>{}", session.getAttribute("loginSession"));
        }
        MemberVO loginMember = (MemberVO) session.getAttribute("loginSession");
        log.error("세션1 : {}", loginMember.getUIdx());
        vo.setUIdx(loginMember.getUIdx());

        List<MyCounselVO> list = myCounselService.myCounsel(vo);

        return list;
    }

    @PostMapping("/myCounsel/previewCnt")
    @ResponseBody
    public int previewCnt(@RequestBody MyCounselVO vo) {

        String review = vo.getPReview();
        log.error("cnt : {}", vo.getPReview().length());

        int bytes = 0;

        char[] strChar = review.toCharArray();
        char ch;
        int code;

        for (int i = 0; i < strChar.length; i++){
            ch = strChar[i];
            code = (int) ch;

            // 2bytes
            if ((ch < '0' || ch > '9') && (ch < 'A' || ch > 'Z') && code > 255) {
                bytes += 2;
            }

            // 1bytes
            else {
                bytes += 1;
            }
        }


        return bytes;
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