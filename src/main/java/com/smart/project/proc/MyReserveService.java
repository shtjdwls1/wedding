package com.smart.project.proc;

import com.smart.project.annotation.Master;
import com.smart.project.web.reserve.vo.MyReserveVO;
import org.springframework.stereotype.Component;

import java.util.List;

@Master
@Component
public interface MyReserveService {

    List<MyReserveVO> myReserve(MyReserveVO vo);

    int myReserveCancel(MyReserveVO vo);


}
