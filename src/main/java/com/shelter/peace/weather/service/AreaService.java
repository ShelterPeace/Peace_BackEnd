package com.shelter.peace.weather.service;

import com.shelter.peace.weather.dto.GeoAreaDTO;
import com.shelter.peace.weather.entity.InterestArea;
import com.shelter.peace.weather.entity.KoreaArea;
import com.shelter.peace.weather.repository.AreaRepository;
import com.shelter.peace.weather.repository.KoreaAreaRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AreaService {
    private final KoreaAreaRepository koreaAreaRepository;
    private final AreaRepository areaRepository;

    private String areaUrl = "https://api.openweathermap.org/geo/1.0/direct?q=";
//    https://api.openweathermap.org/geo/1.0/direct?q=%EC%88%98%EC%9B%90&limit=5&appid=?
    @Value("${open.weather.map.api.key}")
    private String apiKey;

    // 지역 이름으로 위도 경도 구하기
    public String getAreaLatLon(String areaName) {
        try {
            StringBuilder stringBuilder = new StringBuilder()
                    .append(areaUrl)
                    .append(areaName)
                    .append("&appid=")
                    .append(apiKey);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<List<GeoAreaDTO>> response = restTemplate.exchange(
                    new URI(stringBuilder.toString()),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );
            List<GeoAreaDTO> json = response.getBody();

            String returnStr = json.get(0).getLat() + "-" + json.get(0).getLon();

            return returnStr;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveInterestArea(long id, String areaName) {
        InterestArea interestArea = new InterestArea();
        interestArea.setUserPk(id);
        interestArea.setArea1Name(areaName);

        String areaInfo = getAreaLatLon(areaName);

        double lat = Double.parseDouble(areaInfo.split("-")[0]);
        double lon = Double.parseDouble(areaInfo.split("-")[1]);

        interestArea.setArea1Lat(lat);
        interestArea.setArea1Lon(lon);

        areaRepository.save(interestArea);
    }


    // 관리자가 지역구 엑셀파일로 데이터 넣기
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
