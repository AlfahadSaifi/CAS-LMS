package org.nucleus.controller;


import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import org.nucleus.dto.LoanSummaryDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class LoanSummaryController {
    List<LoanSummaryDTO> loanSummaries = new ArrayList<>();

    @PostMapping("/getPDF")
    public ResponseEntity<byte[]> repaySchedulePDF(@ModelAttribute("loanSummary") LoanSummaryDTO loanSummaryEntity) {
        HttpHeaders headers = new HttpHeaders();
        byte[] pdfData = new byte[0];
        try {
            loanSummaries.add(loanSummaryEntity);
            InputStream inputStream = ReceiptReportController.class.getResourceAsStream("/loan-summary.jrxml");
            JasperReport compileReport = JasperCompileManager.
                    compileReport(inputStream);
            JasperPrint report = JasperFillManager.fillReport(compileReport, new HashMap<>(), new JRBeanCollectionDataSource(loanSummaries));
            pdfData = JasperExportManager.exportReportToPdf(report);
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "Loan Summary.pdf");
            loanSummaries.remove(loanSummaryEntity);
        } catch (JRException e) {
            System.out.println("Exception occurred while generating the PDF");
            return null;
        }
        return ResponseEntity.ok().headers(headers).body(pdfData);
    }

    @PostMapping("/getCSV")
    public ResponseEntity<byte[]> repayScheduleCSV(@ModelAttribute("loanSummary") LoanSummaryDTO loanSummaryEntity) {
        HttpHeaders headers = new HttpHeaders();
        byte[] csvData = new byte[0];
        try {
            loanSummaries.add(loanSummaryEntity);
            InputStream inputStream = ReceiptReportController.class.getResourceAsStream("/loan-summary.jrxml");
            JasperReport compileReport = JasperCompileManager.
                    compileReport(inputStream);
            JasperPrint report = JasperFillManager.fillReport(compileReport, new HashMap<>(), new JRBeanCollectionDataSource(loanSummaries));
            ByteArrayOutputStream csvStream = new ByteArrayOutputStream();
            JRCsvExporter exporter = new JRCsvExporter();
            exporter.setExporterInput(new SimpleExporterInput(report));
            exporter.setExporterOutput(new SimpleWriterExporterOutput(csvStream));
            exporter.exportReport();
            csvData = csvStream.toByteArray();
            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.setContentDispositionFormData("CSV", "Loan_Summary.csv");
            loanSummaries.remove(loanSummaryEntity);
        } catch (JRException e) {
            System.out.println("Exception occurred while generating the CSV");
            return null;
        }
        return ResponseEntity.ok().headers(headers).body(csvData);
    }

    @PostMapping("/getXLS")
    public ResponseEntity<byte[]> repayScheduleXLS(@ModelAttribute("loanSummary") LoanSummaryDTO loanSummaryEntity) {
        HttpHeaders headers = new HttpHeaders();
        byte[] xlsData = new byte[0];
        try {
            loanSummaries.add(loanSummaryEntity);
            InputStream inputStream = ReceiptReportController.class.getResourceAsStream("/loan-summary.jrxml");
            JasperReport compileReport = JasperCompileManager.
                    compileReport(inputStream);
            JasperPrint report = JasperFillManager.fillReport(compileReport, new HashMap<>(), new JRBeanCollectionDataSource(loanSummaries));
            ByteArrayOutputStream xlsStream = new ByteArrayOutputStream();
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(report));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsStream));
            exporter.exportReport();
            xlsData = xlsStream.toByteArray();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("XLS", "Loan_Summary.xlsx");
            loanSummaries.remove(loanSummaryEntity);
        } catch (JRException e) {
            System.out.println("Exception occurred while generating the XLS");
            return null;
        }
        return ResponseEntity.ok().headers(headers).body(xlsData);
    }
}