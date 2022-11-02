package com.smart.project.proc;

import com.smart.project.annotation.Master;
import com.smart.project.web.adminreserve.vo.AdminReserveVO;
import org.springframework.stereotype.Component;

import java.util.List;

@Master
@Component
public interface AdminReserveService {

    List<AdminReserveVO> hallAll(int uIdx);
    

    List<AdminReserveVO> reserveUser(AdminReserveVO vo);
}
