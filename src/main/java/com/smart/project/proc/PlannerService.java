package com.smart.project.proc;

import com.smart.project.annotation.Master;
import com.smart.project.web.planner.vo.PlannerVO;
import org.springframework.stereotype.Component;

import java.util.List;

@Master
@Component
public interface PlannerService {

    List<PlannerVO> plannerReview(int u_idx);

    int plannerReviewCnt(int u_idx);

}
