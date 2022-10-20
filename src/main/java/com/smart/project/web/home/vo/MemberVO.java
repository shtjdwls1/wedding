package com.smart.project.web.home.vo;

import com.smart.project.oauth.Provider;
import com.smart.project.oauth.Role;
import lombok.Data;

@Data
public class MemberVO {
    private int uIdx;
    private String uId;
    private String uPw;
    private String uName;
    private String uTelFront;
    private String uTelMid;
    private String uTelEnd;
    private String uTel;
    private String uLocation;
    private String uAuth;
    private String joinPath;
    private String joinDate;
}
