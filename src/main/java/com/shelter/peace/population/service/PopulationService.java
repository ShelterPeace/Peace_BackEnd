package com.shelter.peace.population.service;

import com.shelter.peace.population.entity.SeoulArea;
import com.shelter.peace.population.repository.SeoulAreaRepository;
import com.shelter.peace.population.service.dto.PopulationDTO;
import com.shelter.peace.population.service.dto.SeoulRtdCitydataPpltnDTO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PopulationService {

    private final SeoulAreaRepository seoulAreaRepository;

    private final String utf = "UTF-8";
    private final String apiUrl = "http://openapi.seoul.go.kr:8088";

    @Value("${seoul.population.api.key}")
    private String API_KEY;

    public PopulationDTO population(String area) {
        try {
            /*URL*/
            StringBuilder urlBuilder = new StringBuilder(apiUrl);
            urlBuilder.append("/" + URLEncoder.encode(API_KEY, utf)); /*인증키 (sample사용시에는 호출시 제한됩니다.)*/
            urlBuilder.append("/" + URLEncoder.encode("json", utf)); /*요청파일타입 (xml,xmlf,xls,json) */
            urlBuilder.append("/" + URLEncoder.encode("citydata_ppltn", utf)); /*서비스명 (대소문자 구분 필수입니다.)*/
            urlBuilder.append("/" + URLEncoder.encode("1", utf)); /*요청시작위치 (sample인증키 사용시 5이내 숫자)*/
            urlBuilder.append("/" + URLEncoder.encode("1", utf)); /*요청종료위치(sample인증키 사용시 5이상 숫자 선택 안 됨)*/
            urlBuilder.append("/" + URLEncoder.encode(area, utf));

            String url = urlBuilder.toString();

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<>(headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<SeoulRtdCitydataPpltnDTO> response = restTemplate.exchange(new URI(url), HttpMethod.GET, entity, SeoulRtdCitydataPpltnDTO.class);
            System.out.println("response: " + response);
            System.out.println();
            System.out.println("response.getStatusCode: " + response.getStatusCode());
            System.out.println();
            System.out.println("response.getHeaders: " + response.getHeaders());
            System.out.println();
            System.out.println("response.getBody: " + response.getBody());
            return response.getBody().getPopulationDTOList().get(0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("인구혼잡도 api 호출 오류");
        }
    }


    public void parseExcel(MultipartFile file) {
        try {
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());

            if (!extension.equals("xlsx") && !extension.equals("xls")) {
                throw new RuntimeException("엑셀 파일이 아닙니다.");
            }

            Workbook workbook = null;

            if (extension.equals("xlsx")) {
                workbook = new XSSFWorkbook(file.getInputStream());
            } else if (extension.equals("xls")) {
                workbook = new HSSFWorkbook(file.getInputStream());
            }

            Sheet workSheet = workbook.getSheetAt(0);

            for (int i = 1; i < workSheet.getPhysicalNumberOfRows(); i++) { // 제목 열 제외
                Row row = workSheet.getRow(i);

                if (row == null) {
                    break;
                }

                if (row.getCell(0) == null|| row.getCell(0).getStringCellValue().equals("END")) {
                    break;
                } else {
                    SeoulArea seoulArea = new SeoulArea();
                    seoulArea.setCategory(row.getCell(0).getStringCellValue());
                    seoulArea.setNo((int)row.getCell(1).getNumericCellValue());
                    seoulArea.setAreaCd(row.getCell(2).getStringCellValue());
                    seoulArea.setAreaNm(row.getCell(3).getStringCellValue());

                    seoulAreaRepository.save(seoulArea);
                }
            }
        } catch (Exception e) {
            System.out.println("excel 업로드 실패" + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

}
