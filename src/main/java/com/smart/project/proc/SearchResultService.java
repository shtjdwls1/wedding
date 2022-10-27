package com.smart.project.proc;

import com.smart.project.annotation.Master;
import com.smart.project.web.searchresult.vo.ResultListVO;
import com.smart.project.web.searchresult.vo.ResultMemberVO;
import com.smart.project.web.searchresult.vo.ResultPlannerVO;
import org.springframework.stereotype.Component;

import java.util.List;

@Master
@Component
public interface SearchResultService {

    List<ResultListVO> getList1();

    List<ResultListVO> getList2(String date);

    ResultMemberVO getList3(ResultListVO vo);

    List<ResultPlannerVO> getList4(String uLocation);
}
