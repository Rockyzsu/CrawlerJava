package com.sp.main;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * �ɻ��¹�ͳ��
 * @author Rocky
 *2017-08-11
 */
public class planeaccident {
        //���ݻ�ȡ��ȡ����
        private static List<String>  alldata=new ArrayList<>();

        public static void main(String args[]){
            getData("�����¹�����ͳ��_Since_1908.csv");
            alldata.remove(0);
            //System.out.println(alldata.size());
            //���������������
            MaxDeadYear();
            //�¹ʷ��������������
            MaxAccidentsYear();
            //�¹ʸ���ʱ��η����Ĵ���
            FrequencyPeriod();
            //�Ҵ�����ߵ�һ������
             MaximumSurvival();
        }

        /**
         * ��Դ�ļ���ȡ����
         * getData(String filepath)
         * @param filepath
         */
        public static void getData(String filepath){
            File f=new File(filepath);
            //�ж�ȡ����
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
         * ��¼ÿ���Ӧ����������
         * @throws
         * �������������������ݣ���������������
         */
        public static void MaxDeadYear(){
            //��¼��ݶ�Ӧ��������
            Map<Integer,Integer> map=new HashMap<>();
            //ʱ����date��ʾ
            SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/YYYY");
            //ѭ����������
            for(String data:alldata){
                //�ö��Ž����ݷ��룬��һ������ݣ���11������������
                String[] strs=data.split(",");
                if(strs[0]!=null){
                    //��ȡ���
                    try {
                        Date date=sdf.parse(strs[0]);
                        int year=date.getYear();
                        //�ж�map���Ƿ��¼���������
                        if(map.containsKey(year)){
                            //�Ѵ��ڣ����¼��+������������
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

            //��¼���������������
            int max_year=-1;
            //��¼��������
            int dead_count=0;
            //��set�����ȡmap�е�keyֵ�������
            Set<Integer> keyset=map.keySet();
            //
            for(int year:keyset){
                //��ǰ���¹�����������ݣ���¼��ʹ���
                if(map.get(year)>dead_count&&map.get(year)<10000){
                    max_year=year;
                    dead_count=map.get(year);
                }
            }

            System.out.println("���������������:"+(max_year+1901)+"   ����������"+dead_count);
        }
        /**
         * ��¼�¹ʴ����������
         * ������꼰�¹ʴ���
         */
        public static void MaxAccidentsYear(){
            //�����ݣ�������¹ʴ���
            Map<Integer,Integer> map=new HashMap<>();
            SimpleDateFormat sdf =new SimpleDateFormat("MM/dd/YYYY");
            //ѭ����������
            for(String data:alldata){
                String[] strs=data.split(",");
                if(strs[0]!=null){
                    try {
                        Date date=sdf.parse(strs[0]);
                        //��ȡ���
                        int year=date.getYear();
                        //�ж��Ƿ���ڼ�¼
                        if(map.containsKey(year)){
                            //�Ѵ��ڼ�¼��+1
                            map.put(year, map.get(year)+1);
                        }else{
                            map.put(year, 1);
                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                    }
                }
            }
            //��¼�¹ʴ����������
            int max_year=0;
            //�����¹ʷ�������
            int acc_count=0;
            //ѭ���������ݣ���ȡ�¹ʴ����������
            Set<Integer> keyset=map.keySet();
            for(int year:keyset){
                if(map.get(year)>acc_count){
                    max_year=year;
                    acc_count=map.get(year);
                }
            }
            //������
            System.out.println("�¹ʴ����������"+(max_year+1901)+"  �����¹ʷ���������"+acc_count);
        }
        /**
         * FrequencyPeriod()
         * ����ʱ��η����¹ʵĴ���
         */
        public static void FrequencyPeriod(){
            //keyΪʱ��Σ�valueΪ�����¹ʴ���
            Map<String,Integer>  map=new HashMap<>();
            //String������ʱ���
            String[] strsTime={"���磨6:00~12:00��","���磨12:00~18:00��","���ϣ�18:00~24:00��","�賿��0:00~6:00��"};
            //Сʱ������
            SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");

            for(String data:alldata){
                String[] strs=data.split(",");
                //�ж�ʱ���Ƿ��¼��δ��¼�����
                if(strs[1]!=null){
                    try {
                        Date date=sdf.parse(strs[1]);
                        //ȡ��Сʱ��
                        int hour=date.getHours();
                        //�ж�Сʱ�����ĸ���Χ��
                        int index=0;
                        if(hour>=12&&hour<18){
                            index=1;
                        }else if(hour>=18){
                            index=2;
                        }else if(hour<6){
                            index=3;
                        }
                        //��¼��map��
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
            System.out.println("��ʱ��η����¹ʴ�����");
            for(int i=0;i<strsTime.length;i++){
            System.out.println(strsTime[i]+" : "+map.get(strsTime[i]));
            }
            */
            // ��¼���¹�����ʱ�䷶Χ
            String maxTime = null;
            // ��¼���¹����Ĵ���
            int maxCount = 0;

            Set<String> keySet = map.keySet();
            for (String timeScope : keySet) {
                if (map.get(timeScope) > maxCount) {
                    // ��ǰ����ǳ��¹�������ݣ���¼����ʹ���
                    maxTime = timeScope;
                    maxCount = map.get(timeScope);
                }
            }
            System.out.println("�����¹ʴ�������ʱ��Σ�");
            System.out.println(maxTime+" : "+maxCount);
        }
        /**
         * ��ȡ�Ҵ�����ߵ�һ�����ݵ�����
         * ���ظ����ݼ��Ҵ���
         */
        public static void MaximumSurvival(){
            //����¹���Ϣ�Լ����¹ʵ��Ҵ���
            Map<String,Float> map=new HashMap<>();
            //SimpleDateFormat sdf =new SimpleDateFormat("MM/dd/YYYY");
            //�¹��Ҵ���=1-�����ʣ���ʮһ����������������ʮ����������
            float survial=0;
            //ѭ����������
            for(String data:alldata){
                try{
                String[] strs=data.split(",");
                //�����Ҵ���
                float m=Float.parseFloat(strs[10]);
                float n=Float.parseFloat(strs[9]);
                survial=1-m/n;
                map.put(data, survial);
                }catch(Exception e){

                }
            }
            //��¼�¹ʴ����������
            float max_survial=0;
            //�Ҵ�����ߵ�������Ϣ
            String this_data="null";
            //ѭ���������ݣ���ȡ�¹ʴ����������
            Set<String> keyset=map.keySet();
            for(String data:keyset){
                if(map.get(data)>max_survial){
                    this_data=data;
                    max_survial=map.get(data);
                }
            }
            System.out.println("�Ҵ�����ߵ��¹��ǣ�"+this_data);
            System.out.println("�Ҵ���Ϊ��"+survial);
        }
}