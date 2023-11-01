package com.shelter.peace.crawling.controller;

import io.github.bonigarcia.wdm.managers.ChromeDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class SeleniumController {
    @Bean
    public WebDriver getWebDriver() {
        // 다운로드 받은 ChromeDriver의 경로 설정
        Path path = Paths.get("/Users/mac/Downloads/chromedriver-mac-x64/chromedriver");

        // ChromeDriver의 경로를 시스템 속성으로 설정
        System.setProperty("webdriver.chrome.driver", path.toString());

        // Chrome 옵션 설정 (헤드리스 모드를 사용하려면 주석을 해제하세요)
        ChromeOptions options = new ChromeOptions();
        ChromeDriverManager.chromedriver().setup();

        // 브라우저 선택
        WebDriver driver = new ChromeDriver(options);

        //네이버뉴스의 사회면 - 사건사고
        driver.get("https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid1=102&sid2=249");

        // 웹 페이지가 로드될 때까지 기다립니다.
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // 웹 페이지에서 요소를 찾을 때까지 대기
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#wrap > table > tbody > tr > td.content")));

        List<WebElement> concertElementList = driver.findElements(By.cssSelector("#main_content > div.list_body.newsflash_body > ul.type06_headline, ul.type06 > li"));

        List<String> urlList = new ArrayList<>();

        for (WebElement concertEl : concertElementList) {
            WebElement dtElement1 = concertEl.findElement(By.cssSelector("dt a")); //뉴스 원문 주소
            WebElement dtElement2 = concertEl.findElement(By.cssSelector("img")); //뉴스 헤드라인
            WebElement dtElement3 = concertEl.findElement(By.cssSelector("dd span.lede")); //뉴스 내용 축약
            WebElement dtElement4 = concertEl.findElement(By.cssSelector("dd span.writing")); //신문사이름

            String href = dtElement1.getAttribute("href");
            String headline = dtElement2.getAttribute("alt");
            String detail = dtElement3.getText();
            String newspaper = dtElement4.getText();

            urlList.add(href); //뉴스 원문 주소
            urlList.add(headline); //뉴스 헤드라인
            urlList.add(detail); //뉴스 내용 축약
            urlList.add(newspaper); //신문사이름

            try {
                WebElement imgElement = concertEl.findElement(By.cssSelector("img"));
                String imgSrc = imgElement.getAttribute("src");
                urlList.add(imgSrc); //뉴스 사진
            } catch (NoSuchElementException e) {
                // 이미지가 없는 경우 예외 처리
                urlList.add("No Image Found");
            }
        }

        System.out.println("이거당" + urlList);

         //브라우저 종료
        driver.quit();

        return driver;
    }
}
