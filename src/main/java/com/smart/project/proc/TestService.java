package com.smart.project.proc;

import com.smart.project.annotation.Master;
import com.smart.project.web.home.vo.TestMemberVO;
import com.smart.project.web.home.vo.TestResultVO;
import org.springframework.stereotype.Component;
import com.smart.project.web.home.vo.TestListVO;
import java.util.List;

@Master
@Component
public interface TestService {

    List<TestListVO> test1();
    List<TestListVO> test2();

    TestMemberVO test3(TestListVO vo);
    List<TestMemberVO> test4(TestResultVO vo);

}
