package com.departmenteom.web;

import com.departmenteom.dto.PlanInfoDTO;
import com.departmenteom.typemapper.PlanInfoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/planInfo")
public class PlanInfoController {
    private final PlanInfoMapper planInfoMapper;

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('depart:read')")
    public List<PlanInfoDTO> getAllPlans() {
        return planInfoMapper.getAllPlans();
    }

    @GetMapping("/getById/{id}")
    @PreAuthorize("hasAnyAuthority('depart:read')")
    public PlanInfoDTO getPlanById(@PathVariable Long id) {
        return planInfoMapper.getPlanById(id);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('depart:write')")
    public PlanInfoDTO savePlanInfoData(@RequestBody PlanInfoDTO planInfo) {
        log.info(planInfo.toString());
        return planInfoMapper.savePlanInfoData(planInfo);
    }

    @GetMapping("/getSemesterNum/{id}")
    @PreAuthorize("hasAnyAuthority('depart:read')")
    public int getPlanSemesterNum(@PathVariable Long id) {
        return planInfoMapper.getPlanSemesterNum(id);
    }

    @GetMapping("/getByGroupStream")
    @PreAuthorize("hasAnyAuthority('depart:read')")
    public long getPlanByGroupStream(@RequestParam(name="streamId") Long streamId) {
        return planInfoMapper.getPlanByGroupStream(streamId);
    }

}
