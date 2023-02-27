package com.departmenteom.web;

import com.departmenteom.dto.PlanInfoDTO;
import com.departmenteom.service.XlsFileReadService;
import com.departmenteom.service.XlsFileWriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.IOUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/xlsFiles")
@Slf4j

public class XlsxReportController {

    private final XlsFileWriteService xlsFileWriteService;
    private final XlsFileReadService xlsFileReadService;

    @GetMapping("/getFullPlanXlsFile/{id}")
    @PreAuthorize("hasAnyAuthority('depart:read')")
    public void getFullPlan(@PathVariable Long id, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachmnet; filename=file.xlsx");
        ByteArrayInputStream stream = xlsFileWriteService.getPlanInFile(id);
        IOUtils.copy(stream, response.getOutputStream());

        stream.close();
    }

    @GetMapping("/getPersonalPlanXlsFile")
    @PreAuthorize("hasAnyAuthority('depart:read')")
    public void getPersonalPlan(@RequestParam(name = "planId") Long planId,
                                @RequestParam(name = "studentId") Long studentId,
                                @RequestParam(name = "course") int course,
                                HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachmnet; filename=" + course + " course.xlsx");
        ByteArrayInputStream inputStream = xlsFileWriteService.getPersonalPlan(planId, studentId, course);
        IOUtils.copy(inputStream, response.getOutputStream());

        inputStream.close();
    }

    @PostMapping("/readFromFile")
    @PreAuthorize("hasAnyAuthority('depart:write')")
    public PlanInfoDTO addPlanInSystem(@RequestBody MultipartFile file) {
        log.info("name - {}, type - {}, or name - {}", file.getName(), file.getContentType(), file.getOriginalFilename());
        return xlsFileReadService.writeFromFileToSystem(file);
    }
}
