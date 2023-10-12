package com.shelter.peace.shelterApi.service;

import com.shelter.peace.shelterApi.entity.CivilShelterData;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CivilShelterService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void processCsvFile(MultipartFile file) {
        // CSV 파일 읽기 및 파싱
        List<CivilShelterData> civilShelterDataList = parseCSV(file);
        saveData(civilShelterDataList);
    }

    private List<CivilShelterData> parseCSV(MultipartFile file) {
        List<CivilShelterData> civilShelterDataList = new ArrayList<>();

        try (Reader reader = new InputStreamReader(file.getInputStream());
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            civilShelterDataList = csvParser.getRecords().stream()
                    .map(record -> {
                        CivilShelterData civilShelterData = new CivilShelterData();
                        civilShelterData.set번호(record.get("번호"));
                        civilShelterData.set개방서비스명(record.get("개방서비스명"));
                        civilShelterData.set개방서비스아이디(record.get("개방서비스아이디"));
                        civilShelterData.set개방자치단체코드(record.get("개방자치단체코드"));
                        civilShelterData.set관리번호(record.get("관리번호"));
                        civilShelterData.set인허가일자(record.get("인허가일자"));
                        civilShelterData.set영업상태구분코드(record.get("영업상태구분코드"));
                        civilShelterData.set영업상태명(record.get("영업상태명"));
                        civilShelterData.set상세영업상태코드(record.get("상세영업상태코드"));
                        civilShelterData.set상세영업상태명(record.get("상세영업상태명"));
                        civilShelterData.set소재지면적(record.get("소재지면적"));
                        civilShelterData.set소재지전체주소(record.get("소재지전체주소"));
                        civilShelterData.set도로명전체주소(record.get("도로명전체주소"));
                        civilShelterData.set도로명우편번호(record.get("도로명우편번호"));
                        civilShelterData.set사업장명(record.get("사업장명"));
                        civilShelterData.set최종수정시점(record.get("최종수정시점"));
                        civilShelterData.set데이터갱신구분(record.get("데이터갱신구분"));
                        civilShelterData.set데이터갱신일자(record.get("데이터갱신일자"));
                        if(!record.get("좌표정보(x)").isEmpty()) {
                            civilShelterData.set좌표정보_x(Double.parseDouble(record.get("좌표정보(x)")));
                            civilShelterData.set좌표정보_y(Double.parseDouble(record.get("좌표정보(y)")));
                        }
                        civilShelterData.set비상시설위치(record.get("비상시설위치"));
                        civilShelterData.set시설구분명(record.get("시설구분명"));
                        civilShelterData.set시설명건물명(record.get("시설명건물명"));
                        return civilShelterData;
                    })
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return civilShelterDataList;
    }

    private void saveData(List<CivilShelterData> civilShelterDataList) {
        String sql = "INSERT INTO civil_shelter_data (번호, 개방서비스명, 개방서비스아이디, 개방자치단체코드, 관리번호, 인허가일자, " +
                "영업상태구분코드, 영업상태명, 상세영업상태코드, 상세영업상태명, " +
                "소재지면적, 소재지전체주소, 도로명전체주소, 도로명우편번호, 사업장명, 최종수정시점, " +
                "데이터갱신구분, 데이터갱신일자, 좌표정보_x, 좌표정보_y, 비상시설위치, 시설구분명, 시설명건물명) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, civilShelterDataList, civilShelterDataList.size(),
                (ps, civilShelterData) -> {
                    ps.setString(1, civilShelterData.get번호());
                    ps.setString(2, civilShelterData.get개방서비스명());
                    ps.setString(3, civilShelterData.get개방서비스아이디());
                    ps.setString(4, civilShelterData.get개방자치단체코드());
                    ps.setString(5, civilShelterData.get관리번호());
                    ps.setString(6, civilShelterData.get인허가일자());
                    ps.setString(7, civilShelterData.get영업상태구분코드());
                    ps.setString(8, civilShelterData.get영업상태명());
                    ps.setString(9, civilShelterData.get상세영업상태코드());
                    ps.setString(10, civilShelterData.get상세영업상태명());
                    ps.setString(11, civilShelterData.get소재지면적());
                    ps.setString(12, civilShelterData.get소재지전체주소());
                    ps.setString(13, civilShelterData.get도로명전체주소());
                    ps.setString(14, civilShelterData.get도로명우편번호());
                    ps.setString(15, civilShelterData.get사업장명());
                    ps.setString(16, civilShelterData.get최종수정시점());
                    ps.setString(17, civilShelterData.get데이터갱신구분());
                    ps.setString(18, civilShelterData.get데이터갱신일자());
                    ps.setString(19, String.valueOf(civilShelterData.get좌표정보_x()));
                    ps.setString(20, String.valueOf(civilShelterData.get좌표정보_y()));
                    ps.setString(21, civilShelterData.get비상시설위치());
                    ps.setString(22, civilShelterData.get시설구분명());
                    ps.setString(23, civilShelterData.get시설명건물명());
                });
    }


}
