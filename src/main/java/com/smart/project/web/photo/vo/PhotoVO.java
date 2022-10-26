package com.smart.project.web.photo.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class PhotoVO {
    private String cRegNum;
    private String business1;
    private String business2;
    private String business3;
    private String cAddr;
    private String address;
    private String detailAddr;
    private int uIdx;
    private String cImgIdx;
    private String cImgName;
    private List<MultipartFile> files;
    private String cImgPath;
    private String cImgType;
}
