package com.example.demo;

import org.springframework.boot.SpringApplication;
import java.net.URL;
import java.io.InputStream;
import java.io.FileOutputStream;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class DemoApplication {

    public static void main(String[] args)throws Exception {
        SpringApplication.run(DemoApplication.class, args);

                // Устанавливаем путь к chromedriver
                System.setProperty("webdriver.chrome.driver", "C:\\Tools\\chromedriver.exe");

                // Опции для portable Chrome
                ChromeOptions options = new ChromeOptions();
                options.setBinary("C:\\PortableChrome114\\chrome.exe");

                WebDriver driver = new ChromeDriver(options);

                int maxPages = 496;

                for (int page = 495; page <= maxPages; page++) {
                    String url = "https://reallib.org/reader?file=452643&pg=" + page;
                    System.out.println("🔎 Загружаем: " + url);
                    driver.get(url);

                    try {
                        // Ждём до 10 секунд появления img по XPath
                        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                        WebElement img = wait.until(ExpectedConditions.presenceOfElementLocated(
                                By.xpath("/html/body/table/tbody/tr[2]/td/div/div[3]/div[1]/img")));

                        String imageUrl = img.getAttribute("src");
                        System.out.println("✅ Картинка найдена: " + imageUrl);

                        // Скачиваем изображение по URL
                        try (InputStream in = new URL(imageUrl).openStream();
                             FileOutputStream out = new FileOutputStream("page_" + page + ".png")) {
                            byte[] buffer = new byte[4096];
                            int n;
                            while ((n = in.read(buffer)) != -1) {
                                out.write(buffer, 0, n);
                            }
                        }

                        System.out.println("💾 Сохранено: page_" + page + ".png");

                    } catch (Exception e) {
                        System.out.println("⚠️ Картинка не найдена или ошибка на странице " + page + ": " + e.getMessage());
                    }
                }

                driver.quit();
                System.out.println("🎉 Готово!");
    }
}
