package com.sp.main.excel;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
/**
 * 项目名称：测试项目
 * 类  名  称：URLConnectionDownloader
 * 类  描  述：下载网络图片
 * 创  建  人：Frist
 * 创建时间：2016年9月21日 下午5:23:19
 * 修  改  人：Frist
 * 修改时间：2016年9月21日 下午5:23:19
 * 修改备注：
 *
 * @version 1.0
 */
public class URLConnectionDownloader {
    public static void main(String[] args) throws Exception {
        download("http://image1.coupangcdn.com/image/vendor_inventory/4bfb/3f9c4f41060be06d6a937dcc3dbdd3c756d02bb7d1da4e076f30e607611d.jpg", "e:\\laozizhu.jpg");
    }
    /**
     * 下载文件到本地
     *
     * @param urlString
     *          被下载的文件地址
     * @param filename
     *          本地文件名
     * @throws Exception
     *           各种异常
     */
    public static void download(String urlString, String filename) throws Exception {
        // 构造URL
        URL url = new URL(urlString);
        // 打开连接
        URLConnection con = url.openConnection();
        // 输入流
        InputStream is = con.getInputStream();
        // 1K的数据缓冲
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        // 输出的文件流
        OutputStream os = new FileOutputStream(filename);
        // 开始读取
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        // 完毕，关闭所有链接
        os.close();
        is.close();
    }
}