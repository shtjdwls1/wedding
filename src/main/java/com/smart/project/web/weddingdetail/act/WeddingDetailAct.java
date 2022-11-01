package com.smart.project.web.weddingdetail.act;

import com.smart.project.proc.WeddingDetailService;
import com.smart.project.web.weddingdetail.vo.WeddingDetailVO;
import com.smart.project.web.weddingdetail.vo.WeddingSlideVO;
import com.smart.project.web.weddingdetail.vo.WeddingTimeVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WeddingDetailAct {

    final private WeddingDetailService weddingDetailService;

    @RequestMapping("/weddingDetail")
    public String weddingDetail(HttpServletRequest httpServletRequest, Model model) {
        String uIdx = httpServletRequest.getParameter("uIdx");
        String date = httpServletRequest.getParameter("date");
        String wedName = httpServletRequest.getParameter("wedName");

        model.addAttribute("WedName", wedName);
        if (date == null) {
            String now = LocalDate.now().toString();
            date = now;
        }
        log.error("uIdx : {}", uIdx);
        log.error("날짜 : {}", date);

        List<WeddingDetailVO> wedList = weddingDetailService.weddingDetail(uIdx);
        log.error("결과1 : {}", wedList);
        model.addAttribute("WedList", wedList);


        List<WeddingSlideVO> imgList = weddingDetailService.weddingSlide(uIdx);
        log.error("이미지결과: {}", imgList);
        model.addAttribute("ImgList", imgList);

        List<WeddingTimeVO> timeList = weddingDetailService.weddingTime(uIdx);
        log.error("시간 결과 : {}", timeList);
        model.addAttribute("TimeList", timeList);

        List<WeddingTimeVO> hallName = weddingDetailService.hallName(uIdx);
        log.error("홀 이름 결과 : {}", hallName);
        model.addAttribute("HallName", hallName);


        return "pages/weddingDetailPage";

    }
}
