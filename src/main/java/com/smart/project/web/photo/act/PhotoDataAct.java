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
    // 업체 정보 및 이미지 등록
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
        pm.saveCompany(vo); //업체 정보 저장하기

        //TODO 컨트롤러+핸들러에서 파일이름,타입,저장할 경로 지정해주기
        // 넘어온 데이터 확인하기
        log.error("photoData ==> {}",photodata);
        log.error("photo ==>{}",photodata.getFiles());

        Map<String,Object> data = new HashMap<>();
        List<PhotoVO> files = null;
        if(photodata.getFiles()!=null){
            try{
                // 파일형식으로 저장하기
                for(MultipartFile photo : photodata.getFiles()){
                    log.error("file ==>{}",photo.getOriginalFilename());
                    log.error("file ==>{}",photo.getContentType());
                }

                files = ph.saveFile(companyName,"common",photodata.getFiles());

                List<PhotoVO> temp = new ArrayList<>();
                for (PhotoVO photo : files) {
                    PhotoVO thumbnail;
                    PhotoVO small;
                    thumbnail = ph.sizeChange(uIdx,photo, 108, "thumbnail");
                    small = ph.sizeChange(uIdx,photo, 180, "small");
                    if (thumbnail != null) {
                        temp.add(thumbnail);
                    }
                    if (small != null) {
                        temp.add(small);
                    }
                }
                files.addAll(temp);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ph.save(uIdx,files);
            data.put("imgs",files);
            }
        return data;

    }

    // 업체 정보 및 이미지 수정
    @PostMapping("/data/photo/update")
    public Map<String,Object> companyPhotoUpdate(HttpServletRequest req, PhotoVO photodata){
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
        pm.updateCompany(vo); //업체 정보 저장하기

        //TODO 컨트롤러+핸들러에서 파일이름,타입,저장할 경로 지정해주기
        // 넘어온 이미지 데이터 확인하기
        log.error("photoData ==> {}",photodata);
        log.error("photo ==>{}",photodata.getFiles());

        Map<String,Object> data = new HashMap<>();
        List<PhotoVO> files = null;
        // TODO 넘어온 파일이 있을 경우
        if(photodata.getFiles()!=null) {
            // TODO DB에 이미지가 있을경우
            if (pm.findPhotoByIdx(uIdx) != 0) {
                try {
                    // 파일형식으로 저장하기
                    for (MultipartFile photo : photodata.getFiles()) {
                        log.error("file ==>{}", photo.getOriginalFilename());
                        log.error("file ==>{}", photo.getContentType());
                    }

                    files = ph.saveFile(companyName, "common", photodata.getFiles());

                    List<PhotoVO> temp = new ArrayList<>();
                    for (PhotoVO photo : files) {
                        PhotoVO thumbnail;
                        PhotoVO small;
                        thumbnail = ph.sizeChange(uIdx, photo, 108, "thumbnail");
                        small = ph.sizeChange(uIdx, photo, 180, "small");
                        if (thumbnail != null) {
                            temp.add(thumbnail);
                        }
                        if (small != null) {
                            temp.add(small);
                        }
                    }
                    files.addAll(temp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ph.update(uIdx, files);
                data.put("imgs", files);
            }else{
                // TODO DB에 이미지가 없을경우
                try{
                    // 파일형식으로 저장하기
                    for(MultipartFile photo : photodata.getFiles()){
                        log.error("file ==>{}",photo.getOriginalFilename());
                        log.error("file ==>{}",photo.getContentType());
                    }

                    files = ph.saveFile(companyName,"common",photodata.getFiles());

                    List<PhotoVO> temp = new ArrayList<>();
                    for (PhotoVO photo : files) {
                        PhotoVO thumbnail;
                        PhotoVO small;
                        thumbnail = ph.sizeChange(uIdx,photo, 108, "thumbnail");
                        small = ph.sizeChange(uIdx,photo, 180, "small");
                        if (thumbnail != null) {
                            temp.add(thumbnail);
                        }
                        if (small != null) {
                            temp.add(small);
                        }
                    }
                    files.addAll(temp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ph.save(uIdx,files);
                data.put("imgs",files);
            }
        }
        return data;
    };
    // 이미지 찾아서 미리보기 띄우기
    @SneakyThrows
    @RequestMapping(value = "/image/{imgUrl}",produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE})
    public byte[] imgSearch(@PathVariable("imgUrl") String imgUrl) {
        imgUrl = aes.decrypt(imgUrl);
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



