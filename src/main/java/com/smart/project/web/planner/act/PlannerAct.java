package com.smart.project.web.planner.act;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@Controller
@RequiredArgsConstructor
public class PlannerAct {



    @RequestMapping("/plannerDetail")
    public String plannerDetail(Model model) {
        int u_idx = 4;
        log.error("세션1 : {}", u_idx);



        return "pages/plannerDetailpage";
    }


}
