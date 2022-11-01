package com.smart.project.web.searchresult.act;

import com.smart.project.proc.SearchResultService;
import com.smart.project.web.home.vo.MemberVO;
import com.smart.project.web.searchresult.vo.ResultListVO;
import com.smart.project.web.searchresult.vo.ResultMemberVO;
import com.smart.project.web.searchresult.vo.ResultPlannerVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SearchResultAct {

    final private SearchResultService searchResultService;

    @RequestMapping("/searchResult")
    public String searchResult(HttpServletRequest request, Model model) {
        String clasify = request.getParameter("clasify");
        String location = request.getParameter("location");
        String date = request.getParameter("date");
        String sorting = request.getParameter("sorting");
        String offset = request.getParameter("offset");
        String columnData = request.getParameter("columnData");
        String sortData = request.getParameter("sortData");


        HttpSession session = request.getSession(false);

        if (location == null) {
            if (session != null) {
                log.error("deleteSessionbefore ==>{}", session.getAttribute("loginSession"));
                MemberVO loginMember = (MemberVO) session.getAttribute("loginSession");
                location = loginMember.getULocation();
                log.error("로케이션 세션에서  : {}", location);

            } else {
                location = "대전";
                log.error("로케이션 없을때는 : {}", location);
            }
        }


        if (date == null) {
            if (clasify.equals("웨딩홀")) {
                String now = LocalDate.now().toString();
                date = now;
            } else {
                date = "";
            }
        }

        if (sorting == null) {
            sorting = "최신순▼";
        }
        if (sortData == null) {
            sortData = "desc";
        }
        if (offset == null) {
            offset = "0";
        }

        if (columnData == null) {
            if (clasify.equals("웨딩홀")) {
                columnData = "c_idx";
            } else {
                columnData = "p_idx";
            }
        }

        List<String> originData = new ArrayList<>();
        originData.add(clasify);
        originData.add(location);
        originData.add(date);
        originData.add(sorting);

        log.error("오리진 데이터 : {}", originData);
        model.addAttribute("OriginData", originData);

        log.error("분류 : {}", clasify);
        log.error("위치 : {}", location);
        log.error("날짜 : {}", date);

        if (clasify.equals("웨딩홀")) {
            ResultListVO vo = new ResultListVO();
            vo.setDate(date);
            vo.setULocation(location);
            vo.setColumnData(columnData);
            vo.setSort(sortData);
            vo.setOffset(Integer.parseInt(offset));


            List<ResultMemberVO> list = searchResultService.getList5(vo);

            model.addAttribute("ResultDatas", list);

        } else if (clasify.equals("플래너")) {
            ResultListVO vo = new ResultListVO();
            vo.setOffset(Integer.parseInt(offset));
            vo.setULocation(location);
            vo.setSort("desc");
            vo.setColumnData("p_idx");
            vo.setClasify(clasify);
            List<ResultPlannerVO> list4 = searchResultService.getList4(vo);


            log.error("플래너 결과: {}", list4);

            model.addAttribute("ResultDatas", list4);

        } else {
            return "/";
        }

        return "pages/searchResultPage";
    }

    @PostMapping("/resultDataWed")
    @ResponseBody
    public List<ResultMemberVO> resultDataWed(Model model, HttpServletRequest request, @RequestBody ResultListVO vo) {
        List<ResultMemberVO> list = searchResultService.getList5(vo);
        return list;
    }


    @PostMapping("/resultDataPlan")
    @ResponseBody
    public List<ResultPlannerVO> resultPlanData(Model model, HttpServletRequest request, @RequestBody ResultListVO vo) {
        List<ResultPlannerVO> list = searchResultService.getList4(vo);
        return list;
    }

    /*플래너 정렬 클래스*/
    static class ComparePIdxDesc implements Comparator<ResultPlannerVO> {
        @Override
        public int compare(ResultPlannerVO vo1, ResultPlannerVO vo2) {
            return vo1.getPIdx() > vo2.getPIdx() ? -1 : vo1.getPIdx() < vo2.getPIdx() ? 1 : 0;
        }
    }

    static class ComparePPriceDesc implements Comparator<ResultPlannerVO> {
        @Override
        public int compare(ResultPlannerVO vo1, ResultPlannerVO vo2) {
            return vo1.getPPrice() > vo2.getPPrice() ? -1 : vo1.getPPrice() < vo2.getPPrice() ? 1 : 0;
        }
    }

    static class ComparePPriceAsc implements Comparator<ResultPlannerVO> {
        @Override
        public int compare(ResultPlannerVO vo1, ResultPlannerVO vo2) {
            return vo1.getPPrice() < vo2.getPPrice() ? -1 : vo1.getPPrice() > vo2.getPPrice() ? 1 : 0;
        }
    }

    static class ComparePGradeDesc implements Comparator<ResultPlannerVO> {
        @Override
        public int compare(ResultPlannerVO vo1, ResultPlannerVO vo2) {
            return vo1.getPGrade() > vo2.getPGrade() ? -1 : vo1.getPGrade() < vo2.getPGrade() ? 1 : 0;
        }
    }

    static class CompareCommentDesc implements Comparator<ResultPlannerVO> {
        @Override
        public int compare(ResultPlannerVO vo1, ResultPlannerVO vo2) {
            return vo1.getComment() > vo2.getComment() ? -1 : vo1.getComment() < vo2.getComment() ? 1 : 0;
        }
    }


    /* 웨딩홀 정렬 클래스*/
    static class CompareCIdxDesc implements Comparator<ResultMemberVO> {
        @Override
        public int compare(ResultMemberVO vo1, ResultMemberVO vo2) {
            return vo1.getCIdx() > vo2.getCIdx() ? -1 : vo1.getCIdx() < vo2.getCIdx() ? 1 : 0;
        }
    }

    static class CompareHPriceDesc implements Comparator<ResultMemberVO> {
        @Override
        public int compare(ResultMemberVO vo1, ResultMemberVO vo2) {
            return vo1.getHPrice() > vo2.getHPrice() ? -1 : vo1.getHPrice() < vo2.getHPrice() ? 1 : 0;
        }
    }

    static class CompareHPriceAsc implements Comparator<ResultMemberVO> {
        @Override
        public int compare(ResultMemberVO vo1, ResultMemberVO vo2) {
            return vo1.getHPrice() < vo2.getHPrice() ? -1 : vo1.getHPrice() > vo2.getHPrice() ? 1 : 0;
        }
    }
}




