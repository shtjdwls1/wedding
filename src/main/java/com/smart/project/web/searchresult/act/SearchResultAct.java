package com.smart.project.web.searchresult.act;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SearchResultAct {

    @RequestMapping("/searchResult")
    public String searchResult(HttpServletRequest httpServletRequest, Model model) {
        String clasify = httpServletRequest.getParameter("clasify");
        String location = httpServletRequest.getParameter("location");
        String date = httpServletRequest.getParameter("date");

        log.error(clasify);
        log.error(location);
        log.error(date);

        return "pages/searchResultPage";
    }
}
