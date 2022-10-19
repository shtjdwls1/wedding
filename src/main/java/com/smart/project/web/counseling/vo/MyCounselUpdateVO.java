package com.smart.project.web.counseling.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyCounselUpdateVO {

    private String pIdx;
    private String pGrade;
    private String pReview;
    private int counselIdx;

}
