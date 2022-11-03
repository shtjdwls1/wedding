package com.smart.project.web.photo.act;

import com.smart.project.proc.PhotoMapper;
import com.smart.project.web.home.vo.MemberVO;
import com.smart.project.web.photo.service.AES256;
import com.smart.project.web.photo.service.HallHandler;
import com.smart.project.web.photo.service.PhotoHandler;
import com.smart.project.web.photo.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    final private HallHandler hh;
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

                files = ph.saveFile(uIdx,"common",photodata.getFiles());

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
                    // 기존파일 삭제 + 신규파일 저장
                    PhotoVO toFindThumbimg = new PhotoVO();
                    toFindThumbimg.setUIdx(uIdx);
                    toFindThumbimg.setCImgType("thumbnail");
                    PhotoVO oldImg = pm.selectThumbimg(toFindThumbimg);
                    ph.deleteFile(oldImg);
                    files = ph.saveFile(uIdx, "common", photodata.getFiles());

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

                    files = ph.saveFile(uIdx,"common",photodata.getFiles());

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
        log.error("decrypted imgUrl ==> {}",imgUrl);
        File img = new File(imgUrl) ;

        FileInputStream fls = null;

        int len = 0;
        byte[] fileArray = new byte[(int) img.length()];
        fls = new FileInputStream(img);
        fls.read(fileArray);

        fls.close();
        return fileArray;
    }// img 불러오기

    //홀 정보 및 이미지 등록
    @PostMapping("/data/hall/upload")
    public Map<String,Object> hallDataUpload(HallDataVO hallDataVO, HttpServletRequest req){
        log.error("join halldataUpload!");
        //TODO 데이터+세션 uIdx 각 VO에 나눠서 저장하기
        HttpSession session = req.getSession(false);
        MemberVO memberVO = (MemberVO) session.getAttribute("loginSession");
        int uIdx = memberVO.getUIdx();
        log.error("input data ====> {}",hallDataVO);
        //TODO 1. 홀정보 VO에 담기
        HallVO hallVO = new HallVO();
        hallVO.setHName(hallDataVO.getHName());
        hallVO.setHPrice(hallDataVO.getHPrice());
        hallVO.setHMin(hallDataVO.getHMin());
        hallVO.setHMax(hallDataVO.getHMax());
        hallVO.setUIdx(uIdx);
        pm.saveHall(hallVO);
        log.error("hallVO Test ==> {}",hallVO);
        //TODO 2. 홀시간 VO에 담기 -- 값이 들어있을 경우에만
        for(int i =0; i<hallDataVO.getSTime().size();i++){
            if(!Objects.equals(hallDataVO.getSTime().get(i), "")){
                HallTimeVO hallTimeVO = new HallTimeVO();
                hallTimeVO.setSTime(hallDataVO.getSTime().get(i));
                hallTimeVO.setETime(hallDataVO.getETime().get(i));
                hallTimeVO.setHName(hallDataVO.getHName());
                hallTimeVO.setUIdx(uIdx);
                pm.saveHallTime(hallTimeVO);
                log.error("hallTimeVO Test ==> {}",hallTimeVO);
            }
        }
        //TODO 3. 이미지가 있다면 이미지 VO에 담기
        log.error("photo ==>{}",hallDataVO.getFiles());

        Map<String,Object> data = new HashMap<>();
        List<HallImgVO> files = null;
        if(hallDataVO.getFiles()!=null){
            try{
                // 파일형식으로 저장하기
                for(MultipartFile photo : hallDataVO.getFiles()){
                    log.error("file ==>{}",photo.getOriginalFilename());
                    log.error("file ==>{}",photo.getContentType());
                }

                files = hh.saveFile(uIdx,hallDataVO.getHName(),"common",hallDataVO.getFiles());

                List<HallImgVO> temp = new ArrayList<>();
                for (HallImgVO photo : files) {
                    HallImgVO thumbnail;
                    HallImgVO small;
                    thumbnail = hh.sizeChange(uIdx,photo, 108, "thumbnail");
                    small = hh.sizeChange(uIdx,photo, 180, "small");
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
            hh.save(uIdx,files); // DB에 저장
            data.put("imgs",files);
        }
        return data;
    };

    // 홀 정보 및 이미지 수정
    @PostMapping("/data/hall/update")
    public Map<String,Object> hallDataUpdate(HallDataVO hallDataVO, HttpServletRequest req){
        log.error("HallDataUpdate Join!!");
        HttpSession session = req.getSession(false);
        MemberVO mvo = (MemberVO) session.getAttribute("loginSession");
        int uIdx = mvo.getUIdx();
        // TODO 폼 데이터 확인
        log.error("input form data ==> {}",hallDataVO);
        HallVO hallVO = new HallVO();
        hallVO.setHIdx(hallDataVO.getHIdx());
        hallVO.setHName(hallDataVO.getHName());
        hallVO.setHPrice(hallDataVO.getHPrice());
        hallVO.setHMin(hallDataVO.getHMin());
        hallVO.setHMax(hallDataVO.getHMax());
        hallVO.setUIdx(uIdx);
        pm.updateHall(hallVO);
        log.error("hallVO Test ==> {}",hallVO);
        // TODO 홀정보, 시간정보, 이미지정보 나눠서 저장하기
        for(int i =0; i<hallDataVO.getSTime().size();i++){
            if(!Objects.equals(hallDataVO.getSTime().get(i), "")){
                HallTimeVO hallTimeVO = new HallTimeVO();
                hallTimeVO.setHTimeIdx(hallDataVO.getHTimeIdx().get(i));
                hallTimeVO.setSTime(hallDataVO.getSTime().get(i));
                hallTimeVO.setETime(hallDataVO.getETime().get(i));
                hallTimeVO.setHName(hallDataVO.getHName());
                hallTimeVO.setUIdx(uIdx);
                pm.updateHallTime(hallTimeVO);
                log.error("hallTimeVO Test ==> {}",hallTimeVO);
            }
        }
        log.error("photo ==>{}",hallDataVO.getFiles());

        Map<String,Object> data = new HashMap<>();
        List<HallImgVO> files = null;
        if(hallDataVO.getFiles()!=null){
            try{
                // 파일형식으로 저장하기
                for(MultipartFile photo : hallDataVO.getFiles()){
                    log.error("file ==>{}",photo.getOriginalFilename());
                    log.error("file ==>{}",photo.getContentType());
                }

                files = hh.saveFile(uIdx,hallDataVO.getHName(),"common",hallDataVO.getFiles());

                List<HallImgVO> temp = new ArrayList<>();
                for (HallImgVO photo : files) {
                    HallImgVO thumbnail;
                    HallImgVO small;
                    thumbnail = hh.sizeChange(uIdx,photo, 108, "thumbnail");
                    small = hh.sizeChange(uIdx,photo, 180, "small");
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
            hh.save(uIdx,files); // DB에 저장
            data.put("imgs",files);
        }
        return null;
    }
    // DB에서 불러온 홀 이미지 삭제
    @GetMapping("/data/photo/deleteImg")
    public int hallDbImgDelete(HttpServletRequest req, Model model,@RequestParam String imgsrc){
        HttpSession session = req.getSession(false);
        MemberVO mvo =  (MemberVO) session.getAttribute("loginSession");
        int uidx = mvo.getUIdx();
        imgsrc = imgsrc.split("/")[4];
        log.error(imgsrc);
        imgsrc = aes.decrypt(imgsrc);
        log.error(imgsrc);
        imgsrc = imgsrc.substring(7,imgsrc.length());
        log.error(imgsrc);
        // TODO 파일 경로로 DB에서 삭제하기
        // 1. DB에서 삭제하기
        int cnt = pm.deleteHallImg(imgsrc);
        cnt += pm.deleteHallImg(imgsrc.replace("thumbnail","common"));
        cnt += pm.deleteHallImg(imgsrc.replace("thumbnail","small"));
        // TODO 로컬 디렉토리에서도 파일 삭제하기
        File thumbImg = new File("C:/test"+imgsrc);
        if(thumbImg.exists()){
            thumbImg.delete();
        }
        File commonImg = new File("C:/test"+imgsrc.replace("thumbnail","common"));
        if(commonImg.exists()){
            commonImg.delete();
        }
        File smallImg = new File("C:/test"+imgsrc.replace("thumbnail","small"));
        if(smallImg.exists()){
            smallImg.delete();
        }
        return cnt;

    }
}



