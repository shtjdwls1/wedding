package com.smart.project.web.home.vo;

import com.smart.project.oauth.Provider;
import com.smart.project.oauth.Role;
import lombok.Data;

@Data
public class MemberVO {
    private int u_idx;
    private String u_id;
    private String u_pw;
    private String u_name;
    private String u_tel_front;
    private String u_tel_mid;
    private String u_tel_end;
    private String u_tel;
    private String u_location;
    private String u_auth;
    private Provider join_path;
    private String join_date;
}
