package com.smart.project.proc;

import com.smart.project.annotation.Master;
import com.smart.project.web.mainpage.vo.MainpageVO;
import org.springframework.stereotype.Component;

@Master
@Component
public interface MainPageMapper {
    MainpageVO getStart(String location);
}
