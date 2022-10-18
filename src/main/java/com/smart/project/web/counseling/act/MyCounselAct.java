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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MyCounselAct {

    final private MyCounselService myCounselService;
    private Object data;

    @RequestMapping ("/myCounsel/{u_idx}")
    public String main(Model model, @PathVariable int u_idx){
        List<MyCounselVO> list = myCounselService.myCounsel(u_idx);
        log.error("결과1 : {}", myCounselService.myCounsel(u_idx));
        for(MyCounselVO vo : list){
            log.error("플래너 : {}", vo.getPGrade());
        }
        model.addAttribute("myCounsels", list);

        return "pages/myCounselPage";
    }

    @PostMapping("/myCounsel/reviewWrite/{u_idx}")
    public String reviewWrite(@PathVariable String u_idx, @RequestBody ){
//        Map<String, Object> list = new HashMap<>();
//        MyCounselUpdateVO vo;
        String jsonString = request.getParameter("data");
        log.error("결과1 : {}", jsonString);
//         = myCounselService.myCounselUpdate(u_idx,reviewObj);
//        log.error("결과1 : {}", myCounselService.myCounsel(u_idx));
//
//            log.error("플래너번호 : {}", vo.getPIdx());
//            log.error("상담자번호 : {}", vo.getPGrade());
//            log.error(" : {}", vo.getPGrade());
//
////        private int pIdx;
////        private int pGrade;
////        private String pReview;
////        private int counselIdx;

        return "redirect:/myCounsel/{u_idx}";
    }




}