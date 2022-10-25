package com.smart.project.web.mainpage.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainCompanyVO {
    private int cIdx;
    private String uName;
    private String cAddr;
    private String hPrice;
    private String cImgName;
    private String cImgPath;
}
