package com.sp.main;

/**
 * ��ȡ�������»�е������ݣ������з���
 * Rocky   2017-08-13  20��00
 */
import java.io.*;
import java.net.*;

public class URLDemo {
    public static void main(String args[]){
        //ȷ����ȡ����ҳ��ַ���˴�Ϊ�������ѻ�е����ʾ����ҳ
        //��ַΪ        http://search.dangdang.com/?key=%BB%FA%D0%B5%B1%ED&act=input
        String strurl="http://search.dangdang.com/?key=%BB%FA%D0%B5%B1%ED&act=input";
        //����url��ȡ���Ķ���
        try {
            URL url=new URL(strurl);
            //ͨ��url��������ҳ������
            URLConnection conn=url.openConnection();
            //ͨ������ȡ����ҳ���ص�����
            InputStream is=conn.getInputStream();

            System.out.println(conn.getContentEncoding());
            //һ�㰴�ж�ȡ��ҳ���ݣ����������ݷ���
            //�����BufferedReader��InputStreamReader���ֽ���ת��Ϊ�ַ����Ļ�����
            //����ת��ʱ����Ҫ��������ʽ����
            BufferedReader br=new BufferedReader(new InputStreamReader(is,"UTF-8"));

            //���ж�ȡ����ӡ
            String line=null;
            while((line=br.readLine())!=null){
                System.out.println(line);
            }

            br.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}