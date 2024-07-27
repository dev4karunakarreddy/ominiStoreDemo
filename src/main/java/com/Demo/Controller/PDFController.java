package com.Demo.Controller;

import com.Demo.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/pdf")
public class PDFController {

    @Autowired
    private PdfService pdfService;

    @RequestMapping(
            path = "/upload",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadPdf(@RequestParam("file") MultipartFile file) {
        try {
            pdfService.extractAndSaveData(file);
            return "File uploaded and data extracted successfully.";
        } catch (IOException e) {
            return "Failed to extract data from PDF: " + e.getMessage();
        }
    }
//    @GetMapping()
//    public List<PdfData> getPDFdata(){
//        List<PdfData> pdfData = pdfRepository.findAll();
//        return pdfData;
//    }
}

