
package com.smart.project.web.counseling.vo;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyCounselVO {

    private int pCounselingIdx;
    private int pIdx;
    private String pCounselingCk;
    private int pGrade;
    private String pReview;
    private int counselIdx;
    private String counselDate;
    private String pPrice;
    private String uName;
    private String uTel;
}