package com.sp.main.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @author Coderinfo
 * @Email: coderinfo@163.com
 */
public class UpdateFile {
    private static final String URL = "file:///C:/Users/gao.chao.chao/Desktop/update_file.html";

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(URL);
// Use API:sendKeys to update file
        driver.findElement(By.id("update")).sendKeys("C:/Users/gao.chao.chao/Desktop/update_file.html");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.close();
    }
}