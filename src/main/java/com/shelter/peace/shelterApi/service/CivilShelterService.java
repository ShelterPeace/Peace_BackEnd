package com.shelter.peace.shelterApi.service;

import com.shelter.peace.shelterApi.entity.CivilShelterData;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

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
            for (CSVRecord csvRecord : csvParser) {
                CivilShelterData civilShelterData = new CivilShelterData();
                civilShelterData.set번호(csvRecord.get("번호"));
                civilShelterData.set개방서비스명(csvRecord.get("개방서비스명"));
                civilShelterData.set개방서비스아이디(csvRecord.get("개방서비스아이디"));
                civilShelterData.set개방자치단체코드(csvRecord.get("개방자치단체코드"));
                civilShelterData.set관리번호(csvRecord.get("관리번호"));
                civilShelterData.set인허가일자(csvRecord.get("인허가일자"));
                civilShelterData.set인허가취소일자(csvRecord.get("인허가취소일자"));
                civilShelterData.set영업상태구분코드(csvRecord.get("영업상태구분코드"));
                civilShelterData.set영업상태명(csvRecord.get("영업상태명"));
                civilShelterData.set상세영업상태코드(csvRecord.get("상세영업상태코드"));
                civilShelterData.set상세영업상태명(csvRecord.get("상세영업상태명"));
                civilShelterData.set폐업일자(csvRecord.get("폐업일자"));
                civilShelterData.set휴업시작일자(csvRecord.get("휴업시작일자"));
                civilShelterData.set휴업종료일자(csvRecord.get("휴업종료일자"));
                civilShelterData.set재개업일자(csvRecord.get("재개업일자"));
                civilShelterData.set소재지전화(csvRecord.get("소재지전화"));
                civilShelterData.set소재지면적(csvRecord.get("소재지면적"));
                civilShelterData.set소재지우편번호(csvRecord.get("소재지우편번호"));
                civilShelterData.set소재지전체주소(csvRecord.get("소재지전체주소"));
                civilShelterData.set도로명전체주소(csvRecord.get("도로명전체주소"));
                civilShelterData.set도로명우편번호(csvRecord.get("도로명우편번호"));
                civilShelterData.set사업장명(csvRecord.get("사업장명"));
                civilShelterData.set최종수정시점(csvRecord.get("최종수정시점"));
                civilShelterData.set데이터갱신구분(csvRecord.get("데이터갱신구분"));
                civilShelterData.set데이터갱신일자(csvRecord.get("데이터갱신일자"));
                civilShelterData.set업태구분명(csvRecord.get("업태구분명"));
                civilShelterData.set좌표정보_x(csvRecord.get("좌표정보(x)"));
                civilShelterData.set좌표정보_y(csvRecord.get("좌표정보(y)"));
                civilShelterData.set비상시설위치(csvRecord.get("비상시설위치"));
                civilShelterData.set시설구분명(csvRecord.get("시설구분명"));
                civilShelterData.set시설명건물명(csvRecord.get("시설명건물명"));
                civilShelterData.set해제일자(csvRecord.get("해제일자"));

                civilShelterDataList.add(civilShelterData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return civilShelterDataList;
    }

    private void saveData(List<CivilShelterData> civilShelterDataList) {
        String sql = "INSERT INTO civil_shelter_data (번호, 개방서비스명, 개방서비스아이디, 개방자치단체코드, 관리번호, 인허가일자, 인허가취소일자, " +
                "영업상태구분코드, 영업상태명, 상세영업상태코드, 상세영업상태명, 폐업일자, 휴업시작일자, 휴업종료일자, 재개업일자, " +
                "소재지전화, 소재지면적, 소재지우편번호, 소재지전체주소, 도로명전체주소, 도로명우편번호, 사업장명, 최종수정시점, " +
                "데이터갱신구분, 데이터갱신일자, 업태구분명, 좌표정보_x, 좌표정보_y, 비상시설위치, 시설구분명, 시설명건물명, 해제일자) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, civilShelterDataList, civilShelterDataList.size(),
                (ps, civilShelterData) -> {
                    ps.setString(0, civilShelterData.get번호());
                    ps.setString(1, civilShelterData.get개방서비스명());
                    ps.setString(2, civilShelterData.get개방서비스아이디());
                    ps.setString(3, civilShelterData.get개방자치단체코드());
                    ps.setString(4, civilShelterData.get관리번호());
                    ps.setString(5, civilShelterData.get인허가일자());
                    ps.setString(6, civilShelterData.get인허가취소일자());
                    ps.setString(7, civilShelterData.get영업상태구분코드());
                    ps.setString(8, civilShelterData.get영업상태명());
                    ps.setString(9, civilShelterData.get상세영업상태코드());
                    ps.setString(10, civilShelterData.get상세영업상태명());
                    ps.setString(11, civilShelterData.get폐업일자());
                    ps.setString(12, civilShelterData.get휴업시작일자());
                    ps.setString(13, civilShelterData.get휴업종료일자());
                    ps.setString(14, civilShelterData.get재개업일자());
                    ps.setString(15, civilShelterData.get소재지전화());
                    ps.setString(16, civilShelterData.get소재지면적());
                    ps.setString(17, civilShelterData.get소재지우편번호());
                    ps.setString(18, civilShelterData.get소재지전체주소());
                    ps.setString(19, civilShelterData.get도로명전체주소());
                    ps.setString(20, civilShelterData.get도로명우편번호());
                    ps.setString(21, civilShelterData.get사업장명());
                    ps.setString(22, civilShelterData.get최종수정시점());
                    ps.setString(23, civilShelterData.get데이터갱신구분());
                    ps.setString(24, civilShelterData.get데이터갱신일자());
                    ps.setString(25, civilShelterData.get업태구분명());
                    ps.setString(26, civilShelterData.get좌표정보_x());
                    ps.setString(27, civilShelterData.get좌표정보_y());
                    ps.setString(28, civilShelterData.get비상시설위치());
                    ps.setString(29, civilShelterData.get시설구분명());
                    ps.setString(30, civilShelterData.get시설명건물명());
                    ps.setString(31, civilShelterData.get해제일자());
                });
    }

}
