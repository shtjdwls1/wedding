package com.smart.project.web.photo.act;

import com.smart.project.proc.PhotoMapper;
import com.smart.project.web.home.vo.MemberVO;
import com.smart.project.web.photo.service.PhotoHandler;
import com.smart.project.web.photo.vo.PhotoVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class photoDataAct {
    final private PhotoHandler ph;
    @PostMapping("/data/photo/upload")
    public Map<String,Object> companyPhotoUpload(HttpServletRequest req, PhotoVO photodata){
        //TODO 세션에서 u_name, u_name으로 db에서 u_idx찾아오기
        HttpSession session = req.getSession(false);
        MemberVO mvo = (MemberVO)session.getAttribute("loginSession");
        String companyName = mvo.getUName();

        //TODO 폼에서 이미지, 주소,사업자등록번호 가져오기
        String cRegNum = photodata.getBusiness1()+"-"+photodata.getBusiness2()+"-"+photodata.getBusiness3();
        String cAddr = photodata.getAddress()+"/"+photodata.getDetailAddr();

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
                thumbnail = ph.sizeChange(photo, 108, "thumbnail");
                big = ph.sizeChange(photo, 180, "main");
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
//        ph.save(files);
//        data.put("imgs",files);

        return data;
    }
}



