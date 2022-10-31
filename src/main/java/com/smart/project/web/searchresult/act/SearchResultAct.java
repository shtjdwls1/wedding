package com.smart.project.web.searchresult.act;

import com.smart.project.proc.SearchResultService;
import com.smart.project.web.searchresult.vo.ResultListVO;
import com.smart.project.web.searchresult.vo.ResultMemberVO;
import com.smart.project.web.searchresult.vo.ResultPlannerVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        String sorting = httpServletRequest.getParameter("sort");
        String offset = httpServletRequest.getParameter("offset");
        String columnData = httpServletRequest.getParameter("columnData");

        if (date == null) {
            date = "";
        }

        if (sorting == null) {
            sorting = "최신순▼";
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
                log.error("왜 : {}", vo4);
                if (vo4 != null) {
                    vo4.setClasify(clasify);
                    list3.add(vo4);
                }
                log.error("결과2 : {}", vo4);
            }
            log.error("결과3 : {}", list3);
            Collections.sort(list3, new CompareCIdxDesc());

            model.addAttribute("ResultDatas", list3);
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


    @GetMapping("/sortResult")
    public String sortResult(HttpServletRequest request, Model model,
                             @RequestParam(name = "columnData") String columnData,
                             @RequestParam(name = "offset") int offset,
                             @RequestParam(name = "sortData") String sortData,
                             @RequestParam(name = "location") String location,
                             @RequestParam(name = "clasify") String clasify,
                             @RequestParam(name = "sorting") String sorting,
                             @RequestParam(name = "date") String date,
                             @RequestParam(name = "ck") String ck) {
        List<String> originData = new ArrayList<>();
        originData.add(clasify);
        originData.add(location);
        originData.add(date);
        originData.add(sorting);
        log.error("오리진 데이터 : {}", originData);
        model.addAttribute("OriginData", originData);
        log.error("파람1 : {}", sortData);
        log.error("파람2 : {}", offset);
        log.error("파람3 : {}", columnData);


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
                vo3.setColumnData(columnData);
                vo3.setOffset(offset);
                vo3.setSort(sortData);
                ResultMemberVO vo4 = searchResultService.getSortList1(vo3);

                if (vo4 != null) {
                    vo4.setClasify(clasify);

                    list3.add(vo4);
                }
                log.error("결과12 : {}", vo4);
            }


            log.error("결과3 : {}", list3);
            if (columnData.equals("c_idx") && sortData.equals("desc")) {
                Collections.sort(list3, new CompareCIdxDesc());
                log.error("최신순정렬 : {}", list3);
            } else if (columnData.equals("h_price") && sortData.equals("desc")) {
                Collections.sort(list3, new CompareHPriceDesc());
                log.error("높은 가격순 정렬 : {}", list3);
            } else if (columnData.equals("h_price") && sortData.equals("asc")) {
                Collections.sort(list3, new CompareHPriceAsc());
                log.error("낮은 가격순 정렬 : {}", list3);
            }


            model.addAttribute("ResultDatas", list3);

        } else if (clasify.equals("플래너")) {
            ResultListVO vo = new ResultListVO();
            vo.setOffset(offset);
            vo.setULocation(location);
            vo.setSort(sortData);
            vo.setColumnData(columnData);

            List<ResultPlannerVO> list5 = new ArrayList<>();
            List<ResultPlannerVO> list4 = searchResultService.getList4(vo);
            //리스트 4에 클래시파이 추가해야됨.
            for (ResultPlannerVO vo5 : list4) {
                vo5.setClasify(clasify);
                list5.add(vo5);
            }

            log.error("플래너 결과: {}", list5);

            model.addAttribute("ResultDatas", list5);
        }


        return "pages/searchResultPage";
    }

    @GetMapping("/resultDataWed")
    public List<ResultMemberVO> resultWedData(Model model, HttpServletRequest request, @RequestBody ResultListVO vo) {
        List<ResultMemberVO> list3 = new ArrayList<>();

        List<ResultListVO> list1 = searchResultService.getList1();
        List<ResultListVO> list2 = searchResultService.getList2(vo.getDate());

        for (int i = 0; i < list1.size(); i++) {
            for (int j = 0; j < list2.size(); j++) {
                if (list1.get(i).getUIdx() == list2.get(j).getUIdx()) {
                    if (list1.get(i).getCount() == list2.get(j).getCount()) {
                        list1.remove(i);
                    }
                }
            }
        }
        log.error("결과4 : {}", list1);

        for (ResultListVO vo3 : list1) {
            vo3.setULocation(vo.getULocation());
            vo3.setOffset(vo.getOffset());
            ResultMemberVO vo4 = searchResultService.getSortList1(vo3);
            if (vo4 != null) {
                vo4.setClasify(vo.getClasify());
                list3.add(vo4);
            }
            log.error("결과5 : {}", vo4);
        }
        log.error("결과6 : {}", list3);
        Collections.sort(list3, new CompareCIdxDesc());


        return list3;
    }

//    @GetMapping("/resultDataPlan")
//    public List<ResultPlannerVO> resultPlanData(Model model, HttpServletRequest request, @RequestBody ResultListVO vo) {
//        List<ResultPlannerVO> list5 = new ArrayList<>();
//        List<ResultPlannerVO> list4 = searchResultService.getList4(vo.getULocation());
//        for (ResultPlannerVO vo5 : list4) {
//            vo5.setClasify(vo.getClasify());
//            list5.add(vo5);
//        }
//        log.error("플래너 결과: {}", list5);
//
//        return;
//    }

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




