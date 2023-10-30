package com.shelter.peace.crawling.controller;

import io.github.bonigarcia.wdm.managers.ChromeDriverManager;
import org.openqa.selenium.By;
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
        // options.addArguments("--headless"); // 헤드리스 모드 활성화

        ChromeDriverManager.chromedriver().setup();
        // 브라우저 선택
        WebDriver driver = new ChromeDriver(options);

        // 웹 페이지 띄우기 (URL은 적절한 URL로 대체해야 합니다)
        //네이버뉴스의 사회면 - 사건사고
        driver.get("https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid1=102&sid2=249");

        // 웹 페이지가 로드될 때까지 기다립니다.
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // 웹 페이지에서 요소를 찾을 때까지 대기
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#ct > div > section.main_content > div.main_brick > div > div")));

        // 크롤링 로직을 이곳에 추가
        List<WebElement> newsArticles = driver.findElements(By.cssSelector("#ct > div > section.main_content > div.main_brick > div > div"));

        for (WebElement article : newsArticles) {
            String articleText = article.getText();
            System.out.println(articleText);
        }

        // 브라우저 종료
//        driver.quit();

        return driver;
    }
}
