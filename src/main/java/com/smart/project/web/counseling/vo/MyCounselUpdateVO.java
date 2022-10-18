package com.smart.project.web.counseling.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyCounselUpdateVO {

    private int pIdx;
    private int pGrade;
    private String pReview;
    private int counselIdx;

}
