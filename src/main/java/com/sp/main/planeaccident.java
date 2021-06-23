package com.sp.main;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * 飞机事故统计
 * @author Rocky
 *2017-08-11
 */
public class planeaccident {
        //数据获取存取链表
        private static List<String>  alldata=new ArrayList<>();

        public static void main(String args[]){
            getData("飞行事故数据统计_Since_1908.csv");
            alldata.remove(0);
            //System.out.println(alldata.size());
            //死亡人数最多的年份
            MaxDeadYear();
            //事故发生次数最多的年份
            MaxAccidentsYear();
            //事故各个时间段发生的次数
            FrequencyPeriod();
            //幸村率最高的一条数据
             MaximumSurvival();
        }

        /**
         * 从源文件爬取数据
         * getData(String filepath)
         * @param filepath
         */
        public static void getData(String filepath){
            File f=new File(filepath);
            //行读取数据
            try{
                BufferedReader br=new BufferedReader(new FileReader(f));
                String line=null;
                while((line=(br.readLine()))!=null){
                    alldata.add(line);
                }
                br.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        /**
         * 记录每年对应的死亡人数
         * @throws
         * 并输出死亡人数最多的年份，及该年死亡人数
         */
        public static void MaxDeadYear(){
            //记录年份对应死亡人数
            Map<Integer,Integer> map=new HashMap<>();
            //时间用date显示
            SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/YYYY");
            //循环所有数据
            for(String data:alldata){
                //用逗号将数据分离，第一个是年份，第11个是死亡人数
                String[] strs=data.split(",");
                if(strs[0]!=null){
                    //获取年份
                    try {
                        Date date=sdf.parse(strs[0]);
                        int year=date.getYear();
                        //判断map中是否记录过这个数据
                        if(map.containsKey(year)){
                            //已存在，则记录数+该年死亡人数
                            map.put(year, map.get(year)+Integer.parseInt(strs[10]));
                        }else{
                            map.put(year, Integer.parseInt(strs[10]));
                        }

                    } catch (Exception e) {
                        // TODO Auto-generated catch block

                    }

                }
            }
            //System.out.println(map);

            //记录死亡人数最多的年份
            int max_year=-1;
            //记录死亡人数
            int dead_count=0;
            //用set无序获取map中的key值，即年份
            Set<Integer> keyset=map.keySet();
            //
            for(int year:keyset){
                //当前年事故死亡最多的年份，记录年和次数
                if(map.get(year)>dead_count&&map.get(year)<10000){
                    max_year=year;
                    dead_count=map.get(year);
                }
            }

            System.out.println("死亡人数最多的年份:"+(max_year+1901)+"   死亡人数："+dead_count);
        }
        /**
         * 记录事故次数最多的年份
         * 输出该年及事故次数
         */
        public static void MaxAccidentsYear(){
            //存放年份，该年的事故次数
            Map<Integer,Integer> map=new HashMap<>();
            SimpleDateFormat sdf =new SimpleDateFormat("MM/dd/YYYY");
            //循环所有数据
            for(String data:alldata){
                String[] strs=data.split(",");
                if(strs[0]!=null){
                    try {
                        Date date=sdf.parse(strs[0]);
                        //获取年份
                        int year=date.getYear();
                        //判断是否存在记录
                        if(map.containsKey(year)){
                            //已存在记录，+1
                            map.put(year, map.get(year)+1);
                        }else{
                            map.put(year, 1);
                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                    }
                }
            }
            //记录事故次数最多的年份
            int max_year=0;
            //该年事故发生次数
            int acc_count=0;
            //循环所有数据，获取事故次数最多的年份
            Set<Integer> keyset=map.keySet();
            for(int year:keyset){
                if(map.get(year)>acc_count){
                    max_year=year;
                    acc_count=map.get(year);
                }
            }
            //输出结果
            System.out.println("事故次数最多的年份"+(max_year+1901)+"  该年事故发生次数："+acc_count);
        }
        /**
         * FrequencyPeriod()
         * 各个时间段发生事故的次数
         */
        public static void FrequencyPeriod(){
            //key为时间段，value为发生事故次数
            Map<String,Integer>  map=new HashMap<>();
            //String数组存放时间段
            String[] strsTime={"上午（6:00~12:00）","下午（12:00~18:00）","晚上（18:00~24:00）","凌晨（0:00~6:00）"};
            //小时：分钟
            SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");

            for(String data:alldata){
                String[] strs=data.split(",");
                //判断时间是否记录，未记录则忽略
                if(strs[1]!=null){
                    try {
                        Date date=sdf.parse(strs[1]);
                        //取得小时数
                        int hour=date.getHours();
                        //判断小时数在哪个范围中
                        int index=0;
                        if(hour>=12&&hour<18){
                            index=1;
                        }else if(hour>=18){
                            index=2;
                        }else if(hour<6){
                            index=3;
                        }
                        //记录到map中
                        if(map.containsKey(strsTime[index])){
                            map.put(strsTime[index], map.get(strsTime[index])+1);
                        }else{
                            map.put(strsTime[index], 1);
                        }
                    } catch (ParseException e) {
                    }
                }

            }
            /*
            System.out.println("各时间段发生事故次数：");
            for(int i=0;i<strsTime.length;i++){
            System.out.println(strsTime[i]+" : "+map.get(strsTime[i]));
            }
            */
            // 记录出事故最多的时间范围
            String maxTime = null;
            // 记录出事故最多的次数
            int maxCount = 0;

            Set<String> keySet = map.keySet();
            for (String timeScope : keySet) {
                if (map.get(timeScope) > maxCount) {
                    // 当前年就是出事故最多的年份，记录下年和次数
                    maxTime = timeScope;
                    maxCount = map.get(timeScope);
                }
            }
            System.out.println("发生事故次数最多的时间段：");
            System.out.println(maxTime+" : "+maxCount);
        }
        /**
         * 获取幸村率最高的一条数据的内容
         * 返回该内容及幸存率
         */
        public static void MaximumSurvival(){
            //存放事故信息以及该事故的幸村率
            Map<String,Float> map=new HashMap<>();
            //SimpleDateFormat sdf =new SimpleDateFormat("MM/dd/YYYY");
            //事故幸存率=1-死亡率，第十一个是死亡人数，第十个是总人数
            float survial=0;
            //循环所有数据
            for(String data:alldata){
                try{
                String[] strs=data.split(",");
                //计算幸存率
                float m=Float.parseFloat(strs[10]);
                float n=Float.parseFloat(strs[9]);
                survial=1-m/n;
                map.put(data, survial);
                }catch(Exception e){

                }
            }
            //记录事故次数最多的年份
            float max_survial=0;
            //幸存率最高的数据信息
            String this_data="null";
            //循环所有数据，获取事故次数最多的年份
            Set<String> keyset=map.keySet();
            for(String data:keyset){
                if(map.get(data)>max_survial){
                    this_data=data;
                    max_survial=map.get(data);
                }
            }
            System.out.println("幸存率最高的事故是："+this_data);
            System.out.println("幸存率为："+survial);
        }
}