package com.sp.main.excel;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
/**
 * ��Ŀ���ƣ�������Ŀ
 * ��  ��  �ƣ�URLConnectionDownloader
 * ��  ��  ������������ͼƬ
 * ��  ��  �ˣ�Frist
 * ����ʱ�䣺2016��9��21�� ����5:23:19
 * ��  ��  �ˣ�Frist
 * �޸�ʱ�䣺2016��9��21�� ����5:23:19
 * �޸ı�ע��
 *
 * @version 1.0
 */
public class URLConnectionDownloader {
    public static void main(String[] args) throws Exception {
        download("http://image1.coupangcdn.com/image/vendor_inventory/4bfb/3f9c4f41060be06d6a937dcc3dbdd3c756d02bb7d1da4e076f30e607611d.jpg", "e:\\laozizhu.jpg");
    }
    /**
     * �����ļ�������
     *
     * @param urlString
     *          �����ص��ļ���ַ
     * @param filename
     *          �����ļ���
     * @throws Exception
     *           �����쳣
     */
    public static void download(String urlString, String filename) throws Exception {
        // ����URL
        URL url = new URL(urlString);
        // ������
        URLConnection con = url.openConnection();
        // ������
        InputStream is = con.getInputStream();
        // 1K�����ݻ���
        byte[] bs = new byte[1024];
        // ��ȡ�������ݳ���
        int len;
        // ������ļ���
        OutputStream os = new FileOutputStream(filename);
        // ��ʼ��ȡ
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        // ��ϣ��ر���������
        os.close();
        is.close();
    }
}