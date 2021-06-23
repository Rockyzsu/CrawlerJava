package com.sp.main.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;


import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;

/**
 * @author rongrong
 */
public class TestUploadByRobot {
//    WebDriver driver;

    @BeforeClass
    public static void beforeClass() {
//        System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
//        driver = new ChromeDriver();
    }

    @Test
    public void testUploadByRobot() throws AWTException {
        try {
            WebDriver driver = new ChromeDriver();
            driver.get("http://localhost:8000/update_file.html");
            driver.manage().window().maximize();
            //Ñ¡ÔñÎÄ¼þ
//            driver.findElement(By.id("upload")).click();
            Actions action=new Actions(driver);
            action.click(driver.findElement(By.id("upload"))).build().perform();
            setClipboardData("E:\\test.txt");
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_TAB);
            robot.delay(100);
            robot.keyRelease(KeyEvent.VK_TAB);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.delay(100);
            robot.keyRelease(KeyEvent.VK_TAB);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.delay(100);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.delay(100);
            robot.keyRelease(KeyEvent.VK_TAB);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.delay(100);
            robot.keyRelease(KeyEvent.VK_ENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setClipboardData(String data) {
        StringSelection stringSelection = new StringSelection(data);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
    }


    @AfterClass
    public static void afterClass() {
        //driver.quit();
    }

}