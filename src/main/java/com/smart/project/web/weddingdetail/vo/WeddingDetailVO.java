package com.smart.project.web.weddingdetail.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeddingDetailVO {
    private int hIdx;
    private int uIdx;
    private int hPrice;
    private int hMin;
    private int hMax;
    private String hName;
}
