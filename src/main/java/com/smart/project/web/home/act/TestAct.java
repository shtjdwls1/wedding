package com.smart.project.web.home.act;

import com.smart.project.web.home.vo.TestMemberVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.smart.project.proc.TestService;
import com.smart.project.web.home.vo.TestListVO;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TestAct {

    final private TestService testService;

    @RequestMapping("/testMain")
    public String test(Model model) {

        List<TestListVO> list1 = testService.test1();
        List<TestListVO> list2 = testService.test2();

        List<TestMemberVO> list3 = new ArrayList<>();

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
        for (TestListVO vo3 : list1) {
            TestMemberVO vo4 = testService.test3(vo3);
            if(vo4 != null){
            list3.add(vo4);

            }
            log.error("결과2 : {}", vo4);
        }
            log.error("결과3 : {}", list3);
        model.addAttribute("maindata", list3);

        return "pages/testData";
    }


}
