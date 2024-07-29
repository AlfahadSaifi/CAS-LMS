package org.nucleus.controller;


import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nucleus.entity.permanent.JasperEntity;
import org.nucleus.service.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class DisbursalReportController {
    private static final Logger logger = LogManager.getLogger(DisbursalReportController.class);

    private final EmployeeServiceImpl employeeServiceImpl;
    @Autowired
    public DisbursalReportController(EmployeeServiceImpl employeeServiceImpl) {
        this.employeeServiceImpl = employeeServiceImpl;
    }

    List<JasperEntity> jasperEntityList ;
//    InputStream inputStream = DisbursalReportController.class.getResourceAsStream("/disbursal-report.jrxml");

    @GetMapping("getPDF.do")
    public ResponseEntity<byte[]> repaySchedulePDF(@RequestParam("loanAccountNumber")String loanAccountNumber) {
        try(InputStream inputStream = DisbursalReportController.class.getResourceAsStream("/disbursal-report.jrxml");) {
            JasperEntity answer = employeeServiceImpl.getEntry(loanAccountNumber);
            jasperEntityList = new ArrayList<>();
            jasperEntityList.add(answer);
            HttpHeaders headers = new HttpHeaders();
            byte[] pdfData = new byte[0];
            try {
                //compiles the jrxml into jasper report
                JasperReport compileReport = JasperCompileManager.compileReport(inputStream);
                //fills the generated report
                JasperPrint report = JasperFillManager.fillReport(compileReport, new HashMap<>(), new JRBeanCollectionDataSource(jasperEntityList));
                //exports the filled JasperPrint object to a byte array representing a PDF file, using JasperExportManager.
                pdfData = JasperExportManager.exportReportToPdf(report);
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDispositionFormData("filename", "DisbursalReportPDF.pdf");
            } catch (JRException e) {
                LogManager.getLogger().error("Exception occurred while generating the PDF");
                return null;
            }

            // constructs a ResponseEntity object with status code OK, sets the HTTP headers, and sets the response body to the byte array containing the PDF data
            return ResponseEntity.ok().headers(headers).body(pdfData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @RequestMapping("disbursal-report-xls.do")
    public ResponseEntity<byte[]> repayScheduleXLS()
    {
        try(InputStream inputStream = DisbursalReportController.class.getResourceAsStream("/disbursal-report.jrxml");){
        HttpHeaders headers = new HttpHeaders();
        byte[] xlsData = new byte[0];
        try {
            JasperReport compileReport = JasperCompileManager.compileReport(inputStream);
            JasperPrint report = JasperFillManager.fillReport(compileReport, new HashMap<>(), new JRBeanCollectionDataSource(jasperEntityList));

            ByteArrayOutputStream xlsStream = new ByteArrayOutputStream();
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(report));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsStream));
            exporter.exportReport();
            xlsData = xlsStream.toByteArray();

            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("XLS", "Disbursal_ReportXLS.xlsx");

            logger.info("Generated Successfully");
        } catch (JRException e) {
            LogManager.getLogger().error("Exception occurred while generating the XLS");
            return null;
        }
        return ResponseEntity.ok().headers(headers).body(xlsData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping("disbursal-report-csv.do")
    public ResponseEntity<byte[]> repayScheduleCSV()
    {
        try(InputStream inputStream = DisbursalReportController.class.getResourceAsStream("/disbursal-report.jrxml");) {
            HttpHeaders headers = new HttpHeaders();
            byte[] csvData = new byte[0];
            try {
                JasperReport compileReport = JasperCompileManager.compileReport(inputStream);
                JasperPrint report = JasperFillManager.fillReport(compileReport, new HashMap<>(), new JRBeanCollectionDataSource(jasperEntityList));

                ByteArrayOutputStream csvStream = new ByteArrayOutputStream();
                JRCsvExporter exporter = new JRCsvExporter();
                exporter.setExporterInput(new SimpleExporterInput(report));
                exporter.setExporterOutput(new SimpleWriterExporterOutput(csvStream));
                exporter.exportReport();
                csvData = csvStream.toByteArray();

                headers.setContentType(MediaType.TEXT_PLAIN);
                headers.setContentDispositionFormData("CSV", "DisbursalReportCSV.csv");
                logger.info("Generated Successfully");
            } catch (JRException e) {
                LogManager.getLogger().error("Exception occurred while generating the CSV");
                return null;
            }
            return ResponseEntity.ok().headers(headers).body(csvData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}