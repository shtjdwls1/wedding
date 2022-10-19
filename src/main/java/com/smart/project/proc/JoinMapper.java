package com.smart.project.proc;

import com.smart.project.annotation.Master;
import com.smart.project.web.home.vo.MemberVO;
import org.springframework.stereotype.Component;

@Master
@Component
public interface JoinMapper {
    int save(MemberVO memberVO);
    MemberVO login(MemberVO memberVO);
    MemberVO chkId(MemberVO memberVO);
}
