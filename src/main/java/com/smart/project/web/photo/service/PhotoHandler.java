package com.smart.project.web.photo.service;

import com.smart.project.proc.PhotoMapper;
import com.smart.project.web.photo.vo.PhotoVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PhotoHandler {

    private final PhotoMapper photoMapper;

    public List<PhotoVO> saveFile(String companyName, String type, List<MultipartFile> files){
        if (files.isEmpty()) {
            return null;
        } else {
            log.error("files ===> {}",files);
            // 반활할 파일리스트
            int uIdx = photoMapper.selectIdx(companyName); // 업체 인덱스 번호
            List<PhotoVO> returnFiles = new ArrayList<>();
            String dirPath = "C:/img/"+uIdx+"/representative";
            File file = new File(dirPath);
            if (!file.exists()) {
                file.mkdirs();
            }

            // 파일 저장
            for (MultipartFile mfile : files) {
                // log.error("mfile ===> {}",mfile);
                if (!mfile.isEmpty()) {

                    String originFileName = mfile.getOriginalFilename();
                    log.error("originalFileName ==> {}",originFileName);
                    String contentType = (String) getLastMem(originFileName.split("\\."));
                    log.error("확장자명 ===> {}", contentType);

                    String newFileName = "represent" + type +"."+contentType;
                    String newPath = dirPath + "/" + newFileName;
                    log.error("파일명 ===> {}", newFileName);

                    File saveFile = new File(newPath);
                    try {
                        mfile.transferTo(saveFile);
                        PhotoVO photo = new PhotoVO();
                        photo.setCImgName(newFileName);
                        photo.setCImgPath(newPath);
                        photo.setCImgType(type);
                        returnFiles.add(photo);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            return returnFiles;
        }
    }
    // https://code-zzolbo.tistory.com/55 참조
    public PhotoVO sizeChange(int uidx, PhotoVO photo, int size, String type){
        try {
            // image load
            BufferedImage image = ImageIO.read(new File(photo.getCImgPath()));
            // 원래 사이지 확인
            int ori_width = image.getWidth(null);
            int ori_height = image.getHeight(null);
            double whrate =0;
            if(ori_width>ori_height) {
                whrate = (double) ori_width / (double) ori_height;
            }else {
                whrate = (double) ori_height / (double) ori_width;
            }
            log.error("변경 전 width/height ===> {}/{}",ori_width,ori_height);
            // 사이즈 변경(원본 가로세로 비율 확인해서 더 긴쪽을 기준으로 비율맞추기)
            Image resizing;
            BufferedImage newImage;
            if (ori_width>ori_height) {
                resizing = image.getScaledInstance((int)(size*whrate),size , Image.SCALE_SMOOTH);
                newImage = new BufferedImage((int)(size*whrate),size , BufferedImage.TYPE_INT_RGB);
            }else{
                resizing = image.getScaledInstance(size, (int)(size*whrate), Image.SCALE_SMOOTH);
                newImage = new BufferedImage(size, (int)(size*whrate), BufferedImage.TYPE_INT_RGB);
            }
            Graphics g= newImage.getGraphics();
            g.drawImage(resizing, 0, 0, null);
            g.dispose();

            // 변경된 이미지 저장
            ImageIO.write(newImage, (String) getLastMem(photo.getCImgName().split("\\.")), new File(photo.getCImgPath().replace("origin",type)));
            //확인
            File imageFile = new File(photo.getCImgPath().replace("origin",type));
            if(imageFile.exists()){
                BufferedImage bi = ImageIO.read(imageFile);
                log.error("변경 후 width/height ===> {}/{}",bi.getWidth(),bi.getHeight());
            }
            // return
            PhotoVO newPhoto = new PhotoVO();
            newPhoto.setUIdx(uidx);
            newPhoto.setCImgName(photo.getCImgName().replace("origin",type));
            newPhoto.setCImgPath(photo.getCImgPath().replace("origin",type));
            newPhoto.setCImgType(type);
            return newPhoto;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
//    @SneakyThrows
//    public String encodeDataURL(String path) {
//        File img = new File(path);
//        byte[] fileContent = Files.readAllBytes(img.toPath());
//        String dataurl = Base64.getEncoder().encodeToString(fileContent);
//        return dataurl;
//    }
//
    public int save(int uidx,List<PhotoVO> photos) {
        int cnt = 0;
        for (PhotoVO photo : photos) {
            photo.setUIdx(uidx);
            cnt += photoMapper.savePhoto(photo);
        }
        return cnt;
    }
    public Object getLastMem(Object[] objArr) {
        return objArr[objArr.length-1];
    }

}
