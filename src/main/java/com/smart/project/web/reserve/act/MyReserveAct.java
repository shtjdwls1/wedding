package com.smart.project.web.reserve.act;

import com.smart.project.proc.MyReserveService;
import com.smart.project.web.counseling.vo.MyCounselVO;
import com.smart.project.web.home.vo.MemberVO;
import com.smart.project.web.reserve.vo.MyReserveVO;
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
public class MyReserveAct {

    final private MyReserveService myReserveService;

    @RequestMapping("/myReserve")
    public String myReserve(Model model, HttpServletRequest request,
                            @RequestParam(name = "offset") int offset,
                            @RequestParam(name = "ck") String ck) {
        MyReserveVO vo = new MyReserveVO();
        HttpSession session = request.getSession(false);
        if (session != null) {
            log.error("deleteSessionbefore ==>{}", session.getAttribute("loginSession"));
        }
        MemberVO loginMember = (MemberVO) session.getAttribute("loginSession");
        log.error("세션1 : {}", loginMember.getUIdx());

        vo.setUIdx(loginMember.getUIdx());
        vo.setOffset(offset);

        List<MyReserveVO> list = myReserveService.myReserve(vo);

        model.addAttribute("myReserves", list);

        return "pages/myReservePage";
    }

    @PostMapping("/myReserveData")
    @ResponseBody
    public List<MyReserveVO> myReserveData(Model model, HttpServletRequest request, @RequestBody MyReserveVO vo) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            log.error("deleteSessionbefore ==>{}", session.getAttribute("loginSession"));
        }
        MemberVO loginMember = (MemberVO) session.getAttribute("loginSession");
        log.error("세션1 : {}", loginMember.getUIdx());
        vo.setUIdx(loginMember.getUIdx());

        List<MyReserveVO> list = myReserveService.myReserve(vo);

        return list;
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
