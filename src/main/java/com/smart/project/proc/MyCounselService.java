package com.smart.project.proc;

import com.smart.project.annotation.Master;
import com.smart.project.web.counseling.vo.MyCounselUpdateVO;
import com.smart.project.web.counseling.vo.MyCounselVO;
import org.springframework.stereotype.Component;

import java.util.List;

@Master
@Component
public interface MyCounselService {

    List<MyCounselVO> myCounsel(int u_idx);
    void myCounselUpdate(MyCounselUpdateVO vo);
}
