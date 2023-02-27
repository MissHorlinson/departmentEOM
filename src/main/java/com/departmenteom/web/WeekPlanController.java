package com.departmenteom.web;

import com.departmenteom.dto.WeekPlanDTO;
import com.departmenteom.typemapper.WeekPlanMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weekPlan")
@RequiredArgsConstructor
@Slf4j
public class WeekPlanController {
    private final WeekPlanMapper weekPlanMapper;

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('depart:write')")
    public void saveWeekPlanData(@RequestBody List<WeekPlanDTO> weekPlan) {
        weekPlanMapper.saveWeekPlanData(weekPlan);
    }

    @GetMapping("/getByPlan/{id}")
    @PreAuthorize("hasAnyAuthority('depart:read')")
    public List<WeekPlanDTO> getListByPlan(@PathVariable Long id) {
        return weekPlanMapper.getListByPlan(id);
    }
}
