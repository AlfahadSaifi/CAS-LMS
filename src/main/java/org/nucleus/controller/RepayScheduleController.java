package org.nucleus.controller;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import org.apache.logging.log4j.LogManager;
import org.nucleus.dto.RepayScheduleDTO;
import org.nucleus.service.RepayScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;


@Controller
public class RepayScheduleController {

    private List<RepayScheduleDTO> repayScheduleList = null;
    private final RepayScheduleService repayScheduleService;

    @Autowired
    public RepayScheduleController(RepayScheduleService repayScheduleService) {
        this.repayScheduleService = repayScheduleService;
    }

    @RequestMapping("/repay-schedule-details")
    public String getLoanId() {
        return "repay-schedule-report";
    }

    @RequestMapping("/loan-input")
    public ModelAndView showRepaySchedule(@RequestParam("loanId") String loanId) {
        ModelAndView m = new ModelAndView();
        repayScheduleList = repayScheduleService.fetchRepaySchedule(loanId);
        m.addObject("repaySchedule", repayScheduleList);
        m.setViewName("repay-schedule-report");
        return m;
    }

    @RequestMapping("/repay-schedule-pdf")
    public ResponseEntity<byte[]> repaySchedulePDF() {
        HttpHeaders headers = new HttpHeaders();
        byte[] pdfData;
        if(repayScheduleList == null || repayScheduleList.isEmpty()){
            String msg = "No PDF data available";
            return ResponseEntity.status(HttpStatus.OK).body(msg.getBytes());
        }
        try {
            InputStream inputStream = RepayScheduleController.class.getResourceAsStream("/repay-schedule.jrxml");
            JasperReport compileReport = JasperCompileManager.compileReport(inputStream);
            JasperPrint report = JasperFillManager.fillReport(compileReport, new HashMap<>(), new JRBeanCollectionDataSource(repayScheduleList));
            pdfData = JasperExportManager.exportReportToPdf(report);
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "Repay_Schedule_Report.pdf");

        } catch (JRException e) {
            LogManager.getLogger(RepayScheduleController.class).error("Exception occurred while generating the PDF");
            return null;
        }
        return ResponseEntity.ok().headers(headers).body(pdfData);
    }

    @RequestMapping("/repay-schedule-xls")
    public ResponseEntity<byte[]> repayScheduleXLS() {
        HttpHeaders headers = new HttpHeaders();
        byte[] xlsData;
        if(repayScheduleList == null || repayScheduleList.isEmpty()){
            String msg = "No XLS data available";
            return ResponseEntity.status(HttpStatus.OK).body(msg.getBytes());
        }
        try {
            InputStream inputStream = RepayScheduleController.class.getResourceAsStream("/repay-schedule.jrxml");
            JasperReport compileReport = JasperCompileManager.compileReport(inputStream);
            JasperPrint report = JasperFillManager.fillReport(compileReport, new HashMap<>(), new JRBeanCollectionDataSource(repayScheduleList));

            ByteArrayOutputStream xlsStream = new ByteArrayOutputStream();
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(report));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsStream));
            exporter.exportReport();
            xlsData = xlsStream.toByteArray();

            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("XLS", "Repay_Schedule_Report.xlsx");

        } catch (JRException e) {
            LogManager.getLogger(RepayScheduleController.class).error("Exception occurred while generating the XLS");
            return null;
        }
        return ResponseEntity.ok().headers(headers).body(xlsData);
    }

    @RequestMapping("/repay-schedule-csv")
    public ResponseEntity<byte[]> repayScheduleCSV() {
        HttpHeaders headers = new HttpHeaders();
        byte[] csvData;
        if(repayScheduleList == null || repayScheduleList.isEmpty()){
            String msg = "No CSV data available";
            return ResponseEntity.status(HttpStatus.OK).body(msg.getBytes());
        }
        try {
            InputStream inputStream = RepayScheduleController.class.getResourceAsStream("/repay-schedule.jrxml");
            JasperReport compileReport = JasperCompileManager.compileReport(inputStream);
            JasperPrint report = JasperFillManager.fillReport(compileReport, new HashMap<>(), new JRBeanCollectionDataSource(repayScheduleList));

            ByteArrayOutputStream csvStream = new ByteArrayOutputStream();
            JRCsvExporter exporter = new JRCsvExporter();
            exporter.setExporterInput(new SimpleExporterInput(report));
            exporter.setExporterOutput(new SimpleWriterExporterOutput(csvStream));
            exporter.exportReport();
            csvData = csvStream.toByteArray();

            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.setContentDispositionFormData("CSV", "Repay_Schedule_Report.csv");

        } catch (JRException e) {
            LogManager.getLogger(RepayScheduleController.class).error("Exception occurred while generating the CSV");
            return null;
        }
        return ResponseEntity.ok().headers(headers).body(csvData);
    }
}