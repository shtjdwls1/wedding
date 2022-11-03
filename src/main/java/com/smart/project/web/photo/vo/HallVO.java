package com.smart.project.web.photo.vo;

import lombok.Data;

import java.util.List;

@Data
public class HallVO {
    private int hIdx;
    private String hName;
    private int hPrice;
    private int hMin;
    private int hMax;
    private int uIdx;

    // 홀 시간 정보
    private List<HallTimeVO> hallTimeVO;
    private List<HallImgVO> hallImgVO;
}
