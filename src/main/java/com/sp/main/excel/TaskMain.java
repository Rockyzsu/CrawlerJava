package com.sp.main.excel;

import java.util.List;


public class TaskMain {

    private static final String URL = "http://benyouhuifile.it168.com/forum/day_100429/100429184087ade002f882000b.jpg";
    private static String mJsonInfo;

    public static void main(String[] args) {
        HelpUtils helpUtils = new HelpUtils();
        // 获取Json数据
        mJsonInfo = helpUtils.getHttpString(URL);
        // 将Json数据反序列化成java对象
        List<Bean> beans = helpUtils.changeJsonToList(mJsonInfo);
        //循环遍历下载图片
        for (int i = 0; i < beans.size(); i++) {
            helpUtils.makeImage(beans.get(i), "/home/gaochao/images/");
        }

    }

}