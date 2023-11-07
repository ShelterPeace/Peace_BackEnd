package com.shelter.peace.shelterApi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DataUploadController {
    @GetMapping("/upload")
    public String showUploadForm() {
        return "upload.html";
    }

    @GetMapping("/excel/upload")
    public String excelForm() {
        return "excel";
    }

    @GetMapping("/area/upload")
    public String areaForm() {
        return "areaUpload";
    }
}
