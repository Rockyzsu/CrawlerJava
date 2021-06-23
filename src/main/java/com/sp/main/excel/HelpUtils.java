package com.sp.main.excel;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.imageio.ImageIO;

/**
 * 工具类集合
 *
 * @author Rocky
 *
 */
public class HelpUtils {
    /**
     * 根据所提供的url地址获取Json数据
     *
     * @param path
     * @return
     */
    public String getHttpString(String path) {
        // 存放获取到的数据
        String info = "";
        // 网络请求所需变量
        InputStream in = null;
        InputStreamReader reader = null;
        BufferedReader bufferedReader = null;
        try {
            URL url = new URL(path);
            // 根据Url打开地址，以utf-8编码的形式返回输入流
            in = url.openStream();
            reader = new InputStreamReader(in, "utf-8");
            bufferedReader = new BufferedReader(reader);
            // 临时接受数据变量
            String temp = null;
            while ((temp = bufferedReader.readLine()) != null) {
                info += temp;
            }
            return info;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                reader.close();
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 将所提供的Json数据反序列化成Java对象（List集合）
     *
     * @param json
     * @return
     */
    public List<Bean> changeJsonToList(String json) {
        // 利用Gson将JSON数据反序列化成JAVA对象
        Gson gson = new Gson();
        List<Bean> beans = gson.fromJson(json, new TypeToken<List<Bean>>() {
        }.getType());
        return beans;
    }

    /**
     * 下载图片，并按照指定的路径存储
     * @param bean
     * @param filePath
     */
    public void makeImage(Bean bean, String filePath) {
        // 网络请求所需变量
        try {
            //获取输入流
            BufferedInputStream in = new BufferedInputStream(new URL(bean.getUrl()).openStream());
            //创建文件流
            File file = new File(filePath + bean.getPhrase()+".gif");
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
            //缓冲字节数组
            byte[] data = new byte[2048];
            int length = in.read(data);
            while (length != -1) {
                out.write(data, 0, data.length);
                length = in.read(data);
            }
            System.out.println("正在执行下载任务：当前正在下载图片" + bean.getPhrase() + ".gif");
            in.close();
            out.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void makeImage(String imgUrl, String filePath, String fileName) {
        // 网络请求所需变量
        try {
            //获取输入流
            BufferedInputStream in = new BufferedInputStream(new URL(imgUrl).openStream());
            //创建文件流
            File file = new File(filePath + fileName + imgUrl.substring(imgUrl.length() - 4));
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
            //缓冲字节数组
            byte[] data = new byte[2048];
            int length = in.read(data);
            while (length != -1) {
                out.write(data, 0, data.length);
                length = in.read(data);
            }
            System.out.println("正在执行下载任务：当前正在下载图片" + imgUrl );
            in.close();
            out.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    /*
     * urlHttp = "http://img.7799520.com/00356c0e-1725-4dc2-b091-5db958b6c637"
     * path = "e:/picture"
     * */
    public static void getPicture2(String urlHttp, String path){
        FileOutputStream out = null;
        BufferedInputStream in = null;
        HttpURLConnection connection = null;
        byte[] buf = new byte[1024];
        int len = 0;
        try {
            URL url = new URL(urlHttp);
            connection = (HttpURLConnection)url.openConnection();
            connection.connect();
            in = new BufferedInputStream(connection.getInputStream());
            out = new FileOutputStream(path + "/" + new Date().getTime() + ".jpg");
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /*
     * urlHttp = "http://img.7799520.com/00356c0e-1725-4dc2-b091-5db958b6c637"
     * path = "e:/picture"
     * */
    public static void getPicture(String urlHttp, String path){
        String file = path + "/" + new Date().getTime() + ".jpg";
        try {
            URL url = new URL(urlHttp);
            BufferedImage img = ImageIO.read(url);
            ImageIO.write(img, "jpg", new File(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}