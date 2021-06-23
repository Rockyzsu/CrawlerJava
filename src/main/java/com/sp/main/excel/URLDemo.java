package com.sp.main.excel;
/**
 * ��ȡ����������ҳͼ������ݣ������з���
 * ��ȡ���Ϊ2
 * ��ȥ���ݴ洢��E:/dangdang_book/Ŀ¼�£������д���
 * ���ĸ�   2017-08-13  20��00
 */

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

public class URLDemo {
    //��ȡ�����ݴ�ŵ���Ŀ¼��
    private static String savepath = "E:/dangdang_book/";
    //�ȴ���ȡ��url
    private static List<String> allwaiturl = new ArrayList<>();
    //��ȡ����url
    private static Set<String> alloverurl = new HashSet<>();
    //��¼����url����Ƚ�����ȡ�ж�
    private static Map<String, Integer> allurldepth = new HashMap<>();
    //��ȡ�����
    private static int maxdepth = 2;
    //�������󣬰��������̵߳ĵȴ�����
    private static Object obj = new Object();
    //��¼���߳���5��
    private static int MAX_THREAD = 5;
    //��¼���е��߳���
    private static int count = 0;

    public static void main(String args[]) {
        //ȷ����ȡ����ҳ��ַ���˴�Ϊ��������ҳ�ϵ�ͼ������ȥ����ҳ
        //��ַΪ        http://book.dangdang.com/
//        String strurl="http://search.dangdang.com/?key=%BB%FA%D0%B5%B1%ED&act=input";
        String strurl = "http://book.dangdang.com/";

        //workurl(strurl,1);
        addurl(strurl, 0);
        for (int i = 0; i < MAX_THREAD; i++) {
            new URLDemo().new MyThread().start();
        }
    }

    /**
     * ��ҳ������ȡ
     *
     * @param strurl
     * @param depth
     */
    public static void workurl(String strurl, int depth) {
        //�жϵ�ǰurl�Ƿ���ȡ��
        if (!(alloverurl.contains(strurl) || depth > maxdepth)) {
            //����߳��Ƿ�ִ��
            System.out.println("��ǰִ�У�" + Thread.currentThread().getName() + " ��ȡ�̴߳�����ȡ��" + strurl);
            //����url��ȡ���Ķ���
            try {
                URL url = new URL(strurl);
                //ͨ��url��������ҳ������
                URLConnection conn = url.openConnection();
                //ͨ������ȡ����ҳ���ص�����
                InputStream is = conn.getInputStream();

                //��ȡtext���͵�����
                if (conn.getContentType().startsWith("text")) {

                }
                System.out.println(conn.getContentEncoding());
                //һ�㰴�ж�ȡ��ҳ���ݣ����������ݷ���
                //�����BufferedReader��InputStreamReader���ֽ���ת��Ϊ�ַ����Ļ�����
                //����ת��ʱ����Ҫ��������ʽ����
                BufferedReader br = new BufferedReader(new InputStreamReader(is, "GB2312"));

                //���ж�ȡ����ӡ
                String line = null;
                //������ʽ��ƥ�������ȡ����ҳ������
                Pattern p = Pattern.compile("<a .*href=.+</a>");
                //����һ������������ڱ����ļ�,�ļ���Ϊִ��ʱ�䣬�Է��ظ�
                PrintWriter pw = new PrintWriter(new File(savepath + System.currentTimeMillis() + ".txt"));

                while ((line = br.readLine()) != null) {
                    //System.out.println(line);
                    //��д����ƥ�䳬���ӵ�ַ
                    pw.println(line);
                    Matcher m = p.matcher(line);
                    while (m.find()) {
                        String href = m.group();
                        //�ҵ������ӵ�ַ����ȡ�ַ���
                        //��������
                        href = href.substring(href.indexOf("href="));
                        if (href.charAt(5) == '\"') {
                            href = href.substring(6);
                        } else {
                            href = href.substring(5);
                        }
                        //��ȡ�����Ż��߿ո���ߵ�">"����
                        try {
                            href = href.substring(0, href.indexOf("\""));
                        } catch (Exception e) {
                            try {
                                href = href.substring(0, href.indexOf(" "));
                            } catch (Exception e1) {
                                href = href.substring(0, href.indexOf(">"));
                            }
                        }
                        if (href.startsWith("http:") || href.startsWith("https:")) {
                    /*
                    //�������ҳ���ڵ�����
                    //System.out.println(href);
                    //��url��ַ�ŵ�������
                    allwaiturl.add(href);
                    allurldepth.put(href,depth+1);
                    */
                            //����addurl����
                            addurl(href, depth);
                        }

                    }

                }
                pw.close();
                br.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                //e.printStackTrace();
            }
            //����ǰurl���е�alloverurl��
            alloverurl.add(strurl);
            System.out.println(strurl + "��ҳ��ȡ��ɣ�����ȡ������" + alloverurl.size() + "��ʣ����ȡ������" + allwaiturl.size());
        }
        /*
        //�õݹ�ķ���������ȡ��������
        String nexturl=allwaiturl.get(0);
        allwaiturl.remove(0);
        workurl(nexturl,allurldepth.get(nexturl));
        */
        if (allwaiturl.size() > 0) {
            synchronized (obj) {
                obj.notify();
            }
        } else {
            System.out.println("��ȡ����.......");
        }

    }

    /**
     * ����ȡ��url����ȴ������У�ͬʱ�ж��Ƿ��Ѿ��Ź�
     *
     * @param href
     * @param depth
     */
    public static synchronized void addurl(String href, int depth) {
        //��url�ŵ�������
        allwaiturl.add(href);
        //�ж�url�Ƿ�Ź�
        if (!allurldepth.containsKey(href)) {
            allurldepth.put(href, depth + 1);
        }
    }

    /**
     * �Ƴ���ȡ��ɵ�url����ȡ��һ��δ��ȡ��url
     *
     * @return
     */
    public static synchronized String geturl() {
        String nexturl = allwaiturl.get(0);
        allwaiturl.remove(0);
        return nexturl;
    }

    /**
     * �̷߳�������
     */
    public class MyThread extends Thread {
        @Override
        public void run() {
            //�趨һ����ѭ�������߳�һֱ����
            while (true) {
                //�ж��Ƿ������ӣ������ȡ
                if (allwaiturl.size() > 0) {
                    //��ȡurl���д���
                    String url = geturl();
                    //����workurl������ȡ
                    workurl(url, allurldepth.get(url));
                } else {
                    System.out.println("��ǰ�߳�׼���������ȴ�������ȡ��" + this.getName());
                    count++;
                    //����һ���������߳̽���ȴ�״̬����wait����
                    synchronized (obj) {
                        try {
                            obj.wait();
                        } catch (Exception e) {

                        }
                    }
                    count--;
                }
            }
        }

    }
}