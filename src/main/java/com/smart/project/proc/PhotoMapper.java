package com.smart.project.proc;

import com.smart.project.annotation.Master;
import com.smart.project.web.photo.vo.CompanyVO;
import com.smart.project.web.photo.vo.PhotoVO;
import org.springframework.stereotype.Component;

@Master
@Component
public interface PhotoMapper {
    int selectIdx(String uName);
    int savePhoto(PhotoVO photo);
    int saveCompany(PhotoVO vo);
    CompanyVO findByIdx(int uIdx);
    int findPhotoByIdx(int uIdx);

    PhotoVO selectThumbimg(PhotoVO vo);

    int updatePhoto(PhotoVO photo);

    int updateCompany(PhotoVO vo);
}
