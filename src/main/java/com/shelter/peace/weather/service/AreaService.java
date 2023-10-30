package com.shelter.peace.weather.service;

import com.shelter.peace.weather.entity.KoreaArea;
import com.shelter.peace.weather.repository.KoreaAreaRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AreaService {
    private final KoreaAreaRepository koreaAreaRepository;

    public void parseExcel(MultipartFile file) {
        try {
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());

            if (!extension.equals("xlsx") && !extension.equals("xls")) {
                throw new RuntimeException("엑셀 파일이 아닙니다.");
            }

            Workbook workbook = null;
            int workSheetIndex = 0;

            if (extension.equals("xlsx")) {
                workbook = new XSSFWorkbook(file.getInputStream());
                workSheetIndex = workbook.getNumberOfSheets();
            } else if (extension.equals("xls")) {
                workbook = new HSSFWorkbook(file.getInputStream());
                workSheetIndex = workbook.getNumberOfSheets();
            }

            System.out.println("시트 수: " + workSheetIndex);


            for (int i = 0; i < workSheetIndex; i++) {
                Sheet workSheet = workbook.getSheetAt(i);
                for (int j = 1; j < workSheet.getPhysicalNumberOfRows(); j++) { // 제목 열 제외
                    Row row = workSheet.getRow(j);

                    if (row == null) {
                        break;
                    }

                    if (row.getCell(0) == null|| row.getCell(0).getStringCellValue().equals("END")) {
                        break;
                    } else {
                        KoreaArea area = new KoreaArea();

                        if (row.getCell(0) != null) {
                            area.setSido(row.getCell(0).getStringCellValue());
                        }
                        if (row.getCell(1) != null) {
                            area.setSigungu(row.getCell(1).getStringCellValue());
                        }
                        if (row.getCell(2) != null) {
                            area.setUupmyundong(row.getCell(2).getStringCellValue());
                        }
                        if (row.getCell(3) != null) {
                            area.setUupmyunleedong(row.getCell(3).getStringCellValue());
                        }
                        if (row.getCell(4) != null) {
                            area.setLee(row.getCell(4).getStringCellValue());
                        }
                        if (row.getCell(5) != null) {
                            area.setUpdo(row.getCell(5).getNumericCellValue());
                        }
                        if (row.getCell(6) != null) {
                            area.setGyungdo(row.getCell(6).getNumericCellValue());
                        }

                        koreaAreaRepository.save(area);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("excel 업로드 실패" + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
