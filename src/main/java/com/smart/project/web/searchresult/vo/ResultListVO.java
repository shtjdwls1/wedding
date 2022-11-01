package com.smart.project.web.searchresult.vo;

import lombok.Data;

@Data
public class ResultListVO {
    private int count;
    private int uIdx;
    private String uLocation;
    private String clasify;
    private String columnData;
    private String sort;
    private int offset;
    private String date;
    
}
