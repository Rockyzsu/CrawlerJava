package com.sp.main;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sp.main.excel.HelpUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MainClass {
    // log4j的是使用，不会的请看之前写的文章
    static final Log logger = LogFactory.getLog(MainClass.class);

    static final String titles[] = {"productId", "itemId", "vendorItemId", "title", "salePrice"};
    private static Properties properties = new Properties();
    static String imageUrl = "https://www.coupang.com/vp/products/%s/items/%s/vendoritems/%s";
    private static HelpUtils helpUtils = new HelpUtils();

    public static void main(String[] args) throws Exception {

        //初始化一个httpclient
        List<String> listTit = Arrays.asList(titles);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //我们要爬取的一个地址，这里可以从数据库中抽取数据，然后利用循环，可以爬取一个URL队列
        String url = "https://store.coupang.com/vp/vendors/A00250830/product/lists?outboundShippingPlaceId=&attributeFilters=&brand=&componentId=&keyword=&maxPrice=&minPrice=&pageNum=1&rating=0&sortTypeValue=&scpLanding=true";

        //将抓取的数据插入数据库
        Map<String, String> header = new HashMap<>();
        header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36");
        header.put(":authority", "www.coupang.com");
        header.put(":method", "GET");
        header.put(":scheme", "https");
        header.put("accept", "*/*");
        header.put("accept-encoding", "gzip, deflate, br");
        header.put("origin", "https://store.coupang.com");
        header.put("referer", "https://store.coupang.com");
        JSONObject object = URLFecter.getMethod(url, header);
        JSONArray products = object.getJSONObject("data").getJSONArray("products");
        JSONObject product = null;
        List<Map<String, Object>> values = new ArrayList<>();
        Map<String, Object> value = null;
        int i = 1;
        StringBuffer imgPath = new StringBuffer();
        for (Object ob : products) {
            value = new HashMap<>();
            product = JSONObject.parseObject(ob.toString());
            value.put("productId", product.getString("productId"));
            value.put("itemId", product.getString("itemId"));
            value.put("vendorItemId", product.getString("vendorItemId"));
            value.put("title", product.getString("title"));
            value.put("salePrice", product.getString("salePrice"));
            values.add(value);
            JSONObject images = URLFecter.getMethod(String.format(imageUrl, product.getString("productId"), product.getString("itemId"), product.getString("vendorItemId")), header);
            System.out.println(images);
            List<String> iList = new ArrayList<>();
            for (Object o : images.getJSONArray("details")) {
                if ("HTML".equals(JSON.parseObject(o.toString()).getString("contentType"))){
                    iList.addAll(URLFecter.urlParseImage(JSON.parseObject(o.toString()).getJSONArray("vendorItemContentDescriptions").getJSONObject(0).getString("content")));
                } else if ("IMAGE_NO_SPACE".equals(JSON.parseObject(o.toString()).getString("contentType"))) {
                    iList.add("http:" + JSON.parseObject(o.toString()).getJSONArray("vendorItemContentDescriptions").getJSONObject(0).getString("content"));
                }
            }
//            List<String> iList = URLFecter.urlParseImage(images.getJSONArray("details").getJSONObject(0).getJSONArray("vendorItemContentDescriptions").getJSONObject(0).getString("content"));
            imgPath.append("e:\\Downloads\\img\\").append(product.getString("productId")).append("\\")
                    .append(product.getString("itemId")).append("\\").append(product.getString("vendorItemId")).append("\\");
            FileUtils.mkdirs(imgPath.toString());
            for (String string : iList) {
                try{
                    FileUtils.downLoadImage(imgPath.toString() + i + string.substring(string.length() - 4), string);
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
                Thread.sleep(1500);
                i++;
            }
            i = 1;
//            System.out.println(images);
            imgPath.setLength(0);
        }
//        ExcelUtils.writeExcel( "e:\\Downloads\\img\\eee.xls", "price", listTit, values);
    }

    public static String getConfig(String[] args, String key) {
        if (null == args || args.length < 1){
            System.out.println("请输入配置文件路径后重试");
            System.exit(0);
        }
        try {
            String path = args[0];
            if (!path.endsWith("\\")){
                path = path + "\\";
            }
            System.out.println(args[0] + "================");
            properties.load(new FileReader(new File(path + "config.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(key);
    }
}
