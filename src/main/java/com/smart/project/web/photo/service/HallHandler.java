package com.smart.project.web.photo.service;

import com.smart.project.proc.PhotoMapper;
import com.smart.project.web.photo.vo.HallImgVO;
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
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class HallHandler {

    private final PhotoMapper photoMapper;
    public List<HallImgVO> saveFile(int uIdx, String hallName, String type, List<MultipartFile> files){
        if (files.isEmpty()) {
            return null;
        } else {
            log.error("files ===> {}",files);
            // 반활할 파일리스트 디렉토리 확인후 생성
            List<HallImgVO> returnFiles = new ArrayList<>();
            String dirPath = "/src/main/resources/static/imgs/company/"+uIdx+"/"+type;
            // ex: /src/main/resource/static/imgs/company/1/common
            File file = new File("c:/test"+dirPath);
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
                    log.error("contentType ===> {}", contentType);
                    UUID uuid = UUID.randomUUID();
                    String newFileName = uuid.toString() +"."+contentType;
                    String newPath = "C:/test"+dirPath + "/" + newFileName;
                    log.error("newFileName ===> {}", newFileName);

                    File saveFile = new File(newPath);
                    try {
                        mfile.transferTo(saveFile);
                        newPath = dirPath + "/" + newFileName; // DB에는 c:// 를 빼고 저장
                        HallImgVO photo = new HallImgVO();
                        photo.setHImgName(newFileName);
                        photo.setHImgPath(newPath);
                        photo.setHImgName(hallName);
                        photo.setHImgType(type);
                        log.error("newFile ==> {}",photo);
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
    public HallImgVO sizeChange(int uidx, HallImgVO photo, int size, String type){
        log.error("!!!!sizeChange!!!!");
        try {
            // image load
            String fullPath = "C:/test"+photo.getHImgPath();
            BufferedImage image = ImageIO.read(new File(fullPath));
            // 원래 사이즈 확인
            int ori_width = image.getWidth(null);
            int ori_height = image.getHeight(null);
            double whrate =0;
            if(ori_width>ori_height) {
                whrate = (double) ori_width / (double) ori_height;
            }else {
                whrate = (double) ori_height / (double) ori_width;
            }
            log.error("before width/height ===> {}/{}",ori_width,ori_height);
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

            // 변경된 이미지 로컬에 저장
            File file = new File(fullPath.replace("common",type));
            if (!file.exists()) {
                file.mkdirs();
            };
            ImageIO.write(newImage, (String) getLastMem(photo.getHImgName().split("\\.")),file);
            //확인
            File imageFile = new File(fullPath.replace("common",type));
            if(imageFile.exists()){
                BufferedImage bi = ImageIO.read(imageFile);
                log.error("after width/height ===> {}/{}",bi.getWidth(),bi.getHeight());
            }
            // return
            HallImgVO newPhoto = new HallImgVO();
            newPhoto.setUIdx(uidx);
            newPhoto.setHImgName(photo.getHImgName());
            newPhoto.setHImgPath(photo.getHImgPath().replace("common",type));
            newPhoto.setHImgType(type);
            newPhoto.setHName(photo.getHName());
            return newPhoto;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public int update(int uidx,List<HallImgVO> photos) {
        int cnt = 0;
        HallImgVO photo1 = photos.get(0);
        photo1.setUIdx(uidx);
        photo1.setHImgType("common");
        HallImgVO photo2 = photos.get(1);
        photo2.setUIdx(uidx);
        photo2.setHImgType("thumbnail");
        HallImgVO photo3 = photos.get(2);
        photo3.setUIdx(uidx);
        photo3.setHImgType("small");
        cnt += photoMapper.updateHallImg(photo1);
        cnt += photoMapper.updateHallImg(photo2);
        cnt += photoMapper.updateHallImg(photo3);
        return cnt;
    }
    public int save(int uidx, List<HallImgVO> photos) {
        int cnt = 0;
        for (HallImgVO photo : photos) {
            photo.setUIdx(uidx);
            cnt += photoMapper.saveHallImg(photo);
        }
        return cnt;
    }
    public Object getLastMem(Object[] objArr) {
        return objArr[objArr.length-1];
    }

}
