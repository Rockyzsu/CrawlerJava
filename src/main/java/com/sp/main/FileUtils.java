package com.sp.main;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

/**
 * @author Rocky
 * @date 2020/12/11
 */
public class FileUtils {

    private static Properties properties = new Properties();
    public FileUtils(){

    }
    public static void mkdirs(String path){
        File dir = new File(path);
        if (!dir.exists()){
            dir.mkdirs();
        }
    }

    /**
     * 下载网络图片
     * @param filePath 文件保存路径
     * @param imgUrl 图片URL
     */
    public static void downLoadImage(String filePath, String imgUrl){
        try {
            URL url1 = new URL(imgUrl);
            URLConnection uc = url1.openConnection();
            InputStream inputStream = uc.getInputStream();

            FileOutputStream out = new FileOutputStream(filePath);
            int j = 0;
            while ((j = inputStream.read()) != -1) {
                out.write(j);
            }
            out.close();
            inputStream.close();
        } catch (IOException e) {
            System.out.printf("---------downLoadImage-------------error {}", e.getMessage());
        }

    }

    public static String getConfig(String key) {
        try {
            properties.load(new FileReader(new File(FileUtils.class.getClassLoader().getResource("") + "config.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(key);
    }

    public static boolean fileExists(String path){
        File file=new File(path);
        if(!file.exists())
        {
            return false;
        }
        else {
            return true;
        }
    }
//    public static void main(String[] args) {
//        FileUtils.mkdirs("H:\\Downloads\\img\\2195443234\\3735650930\\73229775917\\");
//        FileUtils.downLoadImage("");
//        System.out.println(FileUtils.fileExists("D:\\java\\image\\7586-2010121J219.jpg"));
//    }
}
