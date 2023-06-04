package com.sakcode.consumer.service;

import com.sakcode.consumer.domain.BillerBillDetail;
import com.sakcode.consumer.repository.BillerBillDetailRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
public class UploadService {

    private int batchSize = 50;

    @Autowired
    private BillerBillDetailRepository billerBillDetailRepository;

    public void upload(MultipartFile multipartFile, Integer numberOfSheet) {
        String status = "SUCCESS";

        try {
            long startTime = System.currentTimeMillis();
            XSSFWorkbook workbook = new XSSFWorkbook(multipartFile.getInputStream());
            if (numberOfSheet == null || numberOfSheet < 0) {
                numberOfSheet = workbook.getNumberOfSheets();
            }

            List<BillerBillDetail> billerBillDetails = new ArrayList<>();
            for (int index = 0; index < numberOfSheet; index++) {
                billerBillDetails.clear();

                Sheet sheet = workbook.getSheetAt(index);
                log.info("Reading Data in sheet {} - {}", index, sheet.getSheetName());
                Iterator<Row> rowIteratorData = sheet.rowIterator();
                while (rowIteratorData.hasNext()) {
                    Row row = rowIteratorData.next();

                    int billerNumber = (int) row.getCell(0).getNumericCellValue();
                    String customerName = row.getCell(1) == null ? null :  row.getCell(1).getStringCellValue() ;
                    String currency = row.getCell(2) == null ? null : row.getCell(2).getStringCellValue();

                    BillerBillDetail billerBillDetail = new BillerBillDetail(String.valueOf(billerNumber), customerName, currency);
                    billerBillDetails.add(billerBillDetail);
                }
                log.warn("Time performed read from file: {} ms", (System.currentTimeMillis() - startTime));

                int totalObjects = billerBillDetails.size();
                for (int i = 0; i < totalObjects; i += batchSize) {
                    if (i + batchSize > totalObjects) {
                        List<BillerBillDetail> tempBillerBillDetail = billerBillDetails.subList(i, totalObjects - 1);
                        billerBillDetailRepository.saveAll(tempBillerBillDetail);
                        break;
                    }
                    List<BillerBillDetail> temBillerBillDetails2 = billerBillDetails.subList(i, i + batchSize);
                    billerBillDetailRepository.saveAll(temBillerBillDetails2);
                }
                log.warn("Time performed add to DB from file: {} ms", (System.currentTimeMillis() - startTime));
            }

            log.warn("Time performed: {} ms", (System.currentTimeMillis() - startTime));

        } catch (Exception exception) {
            exception.printStackTrace();
            status = "FAILED";
        }

        log.warn(status);
    }
}
