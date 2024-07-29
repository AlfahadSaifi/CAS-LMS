package org.nucleus.controller;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import org.apache.logging.log4j.LogManager;
import org.nucleus.dto.ReceiptDTO;
import org.nucleus.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class ReceiptReportController {
    private final ReceiptService receiptService;

    @Autowired
    public ReceiptReportController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @RequestMapping("/receipt-report")
    public String start() {
        return "receipt-report";
    }

    @PostMapping("/form")
    public String process(Model model, @RequestParam String loanAcc, @RequestParam String fromdate, @RequestParam String todate, @RequestParam Long receiptNo) {
        List<ReceiptDTO> list = new ArrayList<>();
        if (receiptNo != null && receiptNo != 0) {
            ReceiptDTO receipt = receiptService.getReceiptDTOById(receiptNo);
            if (receipt != null) list.add(receipt);
        } else {
            list = receiptService.getallReceiptbyLoanACC(loanAcc, Date.valueOf(fromdate), Date.valueOf(todate));
        }
        model.addAttribute("list", list);
        System.out.println(list);
        model.addAttribute("show", true);
        return "receipt-report";
    }

    @PostMapping("/getPDF/{id}")
    public String exe(@PathVariable Long id, Model model) {
        ReceiptDTO receipt = receiptService.getReceiptDTOById(id);
        List<ReceiptDTO> jasperEntityList = new ArrayList<>();
        jasperEntityList.add(receipt);
        model.addAttribute("list", jasperEntityList);
        model.addAttribute("show", true);
        return "receipt-report-download";
    }

    @PostMapping(value = "/getCSV/{id}")
    public ResponseEntity<byte[]> repayScheduleCSV(@PathVariable Long id) {
        ReceiptDTO receipt = receiptService.getReceiptDTOById(id);
        List<ReceiptDTO> jasperEntityList = new ArrayList<>();
        jasperEntityList.add(receipt);
        HttpHeaders headers = new HttpHeaders();
        byte[] csvData = new byte[0];
        try {
            InputStream inputStream = ReceiptReportController.class.getResourceAsStream("/receipt-report.jrxml");
            JasperReport compileReport = JasperCompileManager.compileReport(inputStream);

            JasperPrint report = JasperFillManager.fillReport(compileReport, new HashMap<>(), new JRBeanCollectionDataSource(jasperEntityList));
            ByteArrayOutputStream csvStream = new ByteArrayOutputStream();
            JRCsvExporter exporter = new JRCsvExporter();
            exporter.setExporterInput(new SimpleExporterInput(report));
            exporter.setExporterOutput(new SimpleWriterExporterOutput(csvStream));
            exporter.exportReport();
            csvData = csvStream.toByteArray();
            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.setContentDispositionFormData("CSV", "Receipt:"+id + ".csv");
        } catch (JRException e) {
            LogManager.getLogger(ReceiptReportController.class).error("Exception occured while generating the CSV");
            return null;
        }
        return ResponseEntity.ok().headers(headers).body(csvData);
    }

    @PostMapping(value = "/getXLS/{id}")
    public ResponseEntity<byte[]> repayScheduleXLS(@PathVariable Long id) {
        ReceiptDTO receipt = receiptService.getReceiptDTOById(id);
        List<ReceiptDTO> jasperEntityList = new ArrayList<>();
        jasperEntityList.add(receipt);
        HttpHeaders headers = new HttpHeaders();
        byte[] xlsData = new byte[0];
        try {
            InputStream inputStream = ReceiptReportController.class.getResourceAsStream("/receipt-report.jrxml");
            JasperReport compileReport = JasperCompileManager.compileReport(inputStream);
            JasperPrint report = JasperFillManager.fillReport(compileReport, new HashMap<>(), new JRBeanCollectionDataSource(jasperEntityList));
            ByteArrayOutputStream xlsStream = new ByteArrayOutputStream();
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(report));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsStream));
            exporter.exportReport();
            xlsData = xlsStream.toByteArray();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("XLS", "Receipt:"+id + ".xlsx");

        } catch (JRException e) {

            LogManager.getLogger(ReceiptReportController.class).error("Exception occured while generating the XLS");

            return null;

        }
        return ResponseEntity.ok().headers(headers).body(xlsData);

    }

    @PostMapping(value = "/getPDFDownload/{id}")
    public ResponseEntity<byte[]> repaySchedulePDF(@PathVariable Long id) {
        ReceiptDTO receipt = receiptService.getReceiptDTOById(id);
        List<ReceiptDTO> jasperEntityList = new ArrayList<>();
        jasperEntityList.add(receipt);
        HttpHeaders headers = new HttpHeaders();
        byte[] pdfData = new byte[0];
        try {
            InputStream inputStream = ReceiptReportController.class.getResourceAsStream("/receipt-report.jrxml");
            JasperReport compileReport = JasperCompileManager.compileReport(inputStream);
            JasperPrint report = JasperFillManager.fillReport(compileReport, new HashMap<>(), new JRBeanCollectionDataSource(jasperEntityList));
            pdfData = JasperExportManager.exportReportToPdf(report);
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "Receipt:"+id + ".pdf");
        } catch (JRException e) {
            LogManager.getLogger(ReceiptReportController.class).error("Exception occurred while generating the PDF", e);
            return null;
        }
        return ResponseEntity.ok().headers(headers).body(pdfData);
    }

    @PostMapping("/back")
    public String formredirect() {
        return "receipt-report";
    }
}