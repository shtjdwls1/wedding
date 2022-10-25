package com.smart.project.proc;

import com.smart.project.annotation.Master;
import com.smart.project.web.photo.vo.PhotoVO;
import org.springframework.stereotype.Component;

@Master
@Component
public interface PhotoMapper {
    int slctIdx(String uName);
    int savePhoto(PhotoVO photo);
}
