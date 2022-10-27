package com.smart.project.web.searchresult.act;

import com.smart.project.proc.SearchResultService;
import com.smart.project.web.searchresult.vo.ResultListVO;
import com.smart.project.web.searchresult.vo.ResultMemberVO;
import com.smart.project.web.searchresult.vo.ResultPlannerVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SearchResultAct {

    final private SearchResultService searchResultService;

    @RequestMapping("/searchResult")
    public String searchResult(HttpServletRequest httpServletRequest, Model model) {
        String clasify = httpServletRequest.getParameter("clasify");
        String location = httpServletRequest.getParameter("location");
        String date = httpServletRequest.getParameter("date");

        log.error("분류 : {}", clasify);
        log.error("위치 : {}", location);
        log.error("날짜 : {}", date);

        if (clasify.equals("웨딩홀")) {
            List<ResultListVO> list1 = searchResultService.getList1();
            List<ResultListVO> list2 = searchResultService.getList2(date);

            List<ResultMemberVO> list3 = new ArrayList<>();

            for (int i = 0; i < list1.size(); i++) {
                for (int j = 0; j < list2.size(); j++) {
                    if (list1.get(i).getUIdx() == list2.get(j).getUIdx()) {
                        if (list1.get(i).getCount() == list2.get(j).getCount()) {
                            list1.remove(i);
                        }
                    }
                }
            }
            log.error("결과1 : {}", list1);
            for (ResultListVO vo3 : list1) {
                vo3.setULocation(location);
                ResultMemberVO vo4 = searchResultService.getList3(vo3);
                if (vo4 != null) {
                    vo4.setClasify(clasify);
                    list3.add(vo4);
                }
                log.error("결과2 : {}", vo4);
            }
            log.error("결과3 : {}", list3);
            model.addAttribute("ResultDatas", list3);
        } else if (clasify.equals("플래너")) {
            List<ResultPlannerVO> list5 = new ArrayList<>();
            List<ResultPlannerVO> list4 = searchResultService.getList4(location);
            for (ResultPlannerVO vo5 : list4) {
                vo5.setClasify(clasify);
                list5.add(vo5);
            }
            log.error("플래너 결과: {}", list5);
            model.addAttribute("ResultDatas", list5);

        } else {
            return "/";
        }

        return "pages/searchResultPage";
    }
}
