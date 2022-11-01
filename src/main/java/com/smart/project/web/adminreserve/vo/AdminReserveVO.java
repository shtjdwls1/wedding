package com.smart.project.web.adminreserve.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminReserveVO {
    private int uIdx;
    private int hIdx;
    private int hTimeIdx;
    private String date;
    private String hName;
    private String uName;
    private String sTime;
    private String eTime;
    private String uTel;
}
