package com.smart.project.web.mainpage.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainPlannerVO {
    private int pIdx;
    private String uName;
    private float pGrade;
    private String pPrice;
    private String pImgName;
    private String pImgPath;
}
