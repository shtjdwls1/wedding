package com.smart.project.web.reserve.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyReserveVO {

    private int hReserveIdx;
    private String reserveDate;
    private String sTime;
    private String eTime;
    private String hName;
    private String hPrice;
    private String uName;
    private String uTel;
    private String cImgName;
    private String cImgPath;
    private String cImgType;

}
