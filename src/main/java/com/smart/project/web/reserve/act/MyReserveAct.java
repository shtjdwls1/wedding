package com.smart.project.web.reserve.act;

import com.smart.project.proc.MyReserveService;
import com.smart.project.web.reserve.vo.MyReserveVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MyReserveAct {

    final private MyReserveService myReserveService;

    @RequestMapping("/myReserve")
    public String myReserve(Model model) {

        int u_idx = 26;
        log.error("세션1 : {}", u_idx);

        List<MyReserveVO> list = myReserveService.myReserve(u_idx);



        log.error("결과1 : {}", myReserveService.myReserve(u_idx));
        for (MyReserveVO vo : list) {
            log.error("예약번호 : {}", vo.getHReserveIdx());
        }

        model.addAttribute("myReserves", list);
        log.error("model확인 : {}", model);

        return "pages/myReservePage";
    }


    @PostMapping("/myReserve/cancel")
    @ResponseBody
    public MyReserveVO myReserveCancel(@RequestBody MyReserveVO vo) {
        log.error("입력===>{}", vo);


        return vo;
    }

}
