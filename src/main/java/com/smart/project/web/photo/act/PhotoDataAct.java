package com.smart.project.web.photo.act;

import com.smart.project.proc.PhotoMapper;
import com.smart.project.web.home.vo.MemberVO;
import com.smart.project.web.photo.service.AES256;
import com.smart.project.web.photo.service.PhotoHandler;
import com.smart.project.web.photo.vo.PhotoVO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PhotoDataAct {
    final private PhotoHandler ph;
    final private PhotoMapper pm;
    final private AES256 aes;
    @PostMapping("/data/photo/upload")
    public Map<String,Object> companyPhotoUpload(HttpServletRequest req, PhotoVO photodata){
        //TODO 세션에서 u_name, u_name으로 db에서 u_idx찾아오기
        HttpSession session = req.getSession(false);
        MemberVO mvo = (MemberVO)session.getAttribute("loginSession");
        String companyName = mvo.getUName();
        int uIdx = mvo.getUIdx();

        //TODO 폼에서 이미지, 주소,사업자등록번호 가져오기
        String cRegNum = photodata.getBusiness1()+"-"+photodata.getBusiness2()+"-"+photodata.getBusiness3();
        String cAddr = photodata.getAddress()+"/"+photodata.getDetailAddr();
        //company 테이블에 데이터 추가
        PhotoVO vo = new PhotoVO();
        vo.setUIdx(uIdx);
        vo.setCRegNum(cRegNum);
        vo.setCAddr(cAddr);

        //TODO 컨트롤러+핸들러에서 파일이름,타입,저장할 경로 지정해주기
        // 넘어온 데이터 확인하기
        log.error("photoData ==> {}",photodata);
        log.error("photo ==>{}",photodata.getFiles());

        Map<String,Object> data = new HashMap<>();
        List<PhotoVO> files = null;
        try{
            // 파일형식으로 저장하기
            for(MultipartFile photo : photodata.getFiles()){
                log.error("file ==>{}",photo.getOriginalFilename());
                log.error("file ==>{}",photo.getContentType());
            }

            files = ph.saveFile(companyName,"origin",photodata.getFiles());

            List<PhotoVO> temp = new ArrayList<>();
            for (PhotoVO photo : files) {
                PhotoVO thumbnail;
                PhotoVO big;
                thumbnail = ph.sizeChange(uIdx,photo, 108, "thumbnail");
                big = ph.sizeChange(uIdx,photo, 180, "main");
                if (thumbnail != null) {
                    temp.add(thumbnail);
                }
                if (big != null) {
                    temp.add(big);
                }
            }
            files.addAll(temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        pm.saveCompany(vo);
        ph.save(uIdx,files);
        data.put("imgs",files);
        return data;
    }

    @SneakyThrows
    @RequestMapping(value = "/image/{imgUrl}",produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE})
    public byte[] imgSearch(@PathVariable("imgUrl") String imgUrl) {
        imgUrl = aes.decrypt(imgUrl);
        // TODO imgUrl = "C:/자기프로젝트명"+imgUrl; 으로 바꿔주세요
        imgUrl = "C:/test"+imgUrl;
        File img = new File(imgUrl) ;

        FileInputStream fls = null;

        int len = 0;
        byte[] fileArray = new byte[(int) img.length()];
        fls = new FileInputStream(img);
        fls.read(fileArray);

        fls.close();
        return fileArray;
    }// img 불러오기

}



