package com.smart.project.web.counseling.act;

import com.google.gson.Gson;
import com.smart.project.proc.MyCounselService;
import com.smart.project.web.counseling.vo.MyCounselUpdateVO;
import com.smart.project.web.counseling.vo.MyCounselVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MyCounselAct {

    final private MyCounselService myCounselService;

    @RequestMapping("/myCounsel")
    public String myCounsel(HttpServletRequest request,Model model) {
        HttpSession session = request.getSession();
        session.setAttribute("u_idx", 4);

        int u_idx = (int) session.getAttribute("u_idx");
        log.error("세션1 : {}", u_idx);

        List<MyCounselVO> list = myCounselService.myCounsel(u_idx);
        log.error("결과1 : {}", myCounselService.myCounsel(u_idx));
        for (MyCounselVO vo : list) {
            log.error("플래너 : {}", vo.getPGrade());
        }
        model.addAttribute("myCounsels", list);

        return "pages/myCounselPage";
    }

    @PostMapping("/myCounsel/reviewWrite")
    @ResponseBody
    public MyCounselUpdateVO reviewWrite(HttpServletRequest request,@RequestBody MyCounselUpdateVO vo) {
        log.error("입력===>{}", vo);
        HttpSession session = request.getSession();
        int u_idx = (int) session.getAttribute("u_idx");
        vo.setCounselIdx(u_idx);
        log.error("최종===>{}", vo);
        myCounselService.myCounselUpdate(vo);
        return vo;
    }

}