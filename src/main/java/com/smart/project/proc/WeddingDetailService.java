package com.smart.project.proc;

import com.smart.project.annotation.Master;
import com.smart.project.web.weddingdetail.vo.WeddingDetailVO;
import com.smart.project.web.weddingdetail.vo.WeddingSlideVO;
import com.smart.project.web.weddingdetail.vo.WeddingTimeVO;
import org.springframework.stereotype.Component;

import java.util.List;

@Master
@Component
public interface WeddingDetailService {
    List<WeddingDetailVO> weddingDetail(String uIdx);

    List<WeddingSlideVO> weddingSlide(String uIdx);

    List<WeddingTimeVO> weddingTime(String uIdx);

    List<WeddingTimeVO> hallName(String uIdx);
}
