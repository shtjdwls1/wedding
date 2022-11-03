package com.smart.project.proc;

import com.smart.project.annotation.Master;
import com.smart.project.web.photo.vo.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Master
@Component
public interface PhotoMapper {

    // INSERT
    int savePhoto(PhotoVO photo); // 업체 이미지 등록하기
    int saveCompany(PhotoVO vo); // 업체 정보 등록하기

    // UPDATE
    int updatePhoto(PhotoVO photo); // 업체 이미지 수정하기
    int updateCompany(PhotoVO vo); // 업체 정보 수정하기

    // SELECT
    int selectIdx(String uName); // 세션 유저이름으로 idx 값 확인
    CompanyVO findByIdx(int uIdx); // idx 값으로 업체정보 확인
    int findPhotoByIdx(int uIdx); // idx 값으로 업체이미지 확인
    PhotoVO selectThumbimg(PhotoVO vo); // idx로 썸네일 이미지 확인


    int saveHallImg(HallImgVO photo); // 홀 이미지 등록하기
    int saveHall(HallVO hallVO); // 홀 정보 등록
    int saveHallTime(HallTimeVO hallTimeVO); // 홀 시간정보 등록
    int updateHallImg(HallImgVO photo); // 홀 이미지 수정하기

    List<HallVO> selectHall(int uidx); // 홀 정보 불러오기
    List<HallTimeVO> selectHallTime(SelectVO selectVO); // 홀 시간 불러오기
    List<HallImgVO> selectHallImg(SelectVO selectVO); // 홀 이미지 불러오기
    
    int updateHall(HallVO hallVO); // 홀 정보 수정
    int updateHallTime(HallTimeVO hallTimeVO); // 홀 시간 수정
    int deleteHallImg(String hImgPath); // 홀 이미지 삭제
}
