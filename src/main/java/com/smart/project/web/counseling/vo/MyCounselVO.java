
package com.smart.project.web.counseling.vo;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyCounselVO {

    private int offset;
    private int pCounselingIdx;
    private int uIdx;
    private String pCounselingCk;
    private int pGrade;
    private String pReview;
    private int counselIdx;
    private String counselDate;
    private String pPrice;
    private String uName;
    private String uTel;
    private String pImgName;
    private String pImgPath;
    private String pImgType;
}