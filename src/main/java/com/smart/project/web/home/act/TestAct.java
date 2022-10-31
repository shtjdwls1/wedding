package com.smart.project.web.home.act;

import com.smart.project.web.home.vo.TestMemberVO;
import com.smart.project.web.home.vo.TestResultVO;
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

        TestResultVO vo = new TestResultVO();
        vo.setReserveDate("2022-10-25");
        vo.setULocation("서울");
        List<TestMemberVO> list = testService.test4(vo);



        model.addAttribute("maindata", list);

        return "pages/testData";
    }


}
