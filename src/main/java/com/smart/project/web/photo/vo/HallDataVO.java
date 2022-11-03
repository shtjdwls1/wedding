package com.smart.project.web.photo.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class HallDataVO {
    private int hIdx;
    private String hName;
    private int hPrice;
    private int hMin;
    private int hMax;
    private List<Integer> hTimeIdx;
    private List<String> sTime;
    private List<String> eTime;
    private List<MultipartFile> files;

}
