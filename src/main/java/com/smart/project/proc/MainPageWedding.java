package com.smart.project.proc;

import com.smart.project.annotation.Master;
import com.smart.project.web.mainpage.vo.MainCompanyVO;
import org.springframework.stereotype.Component;

import java.util.List;

@Master
@Component
public interface MainPageWedding {
    List<MainCompanyVO> getStartWed(String location);


}

