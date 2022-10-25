package com.smart.project.web.weddingdetail.act;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WeddingDetailAct {


    @RequestMapping("/weddingDetail")
    public String weddingDetail(HttpServletRequest request, Model model) {
        ArrayList sList = new ArrayList();

        
        sList.add("11:00");
        sList.add("13:30");
        sList.add("15:00");
        sList.add("16:30");
        sList.add("18:00");

        ArrayList eList = new ArrayList();

        eList.add("13:30");
        eList.add("15:00");
        eList.add("16:30");
        eList.add("18:00");
        eList.add("19:30");

        model.addAttribute("SList", sList);
        model.addAttribute("EList", eList);

        return "pages/weddingDetailPage";

    }
}
