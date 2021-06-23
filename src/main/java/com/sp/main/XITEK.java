package com.sp.main;
import com.sp.main.excel.HelpUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/*
色影无忌图片下载库
 */
public class XITEK {
    // log4j的是使用，不会的请看之前写的文章
    static final Log logger = LogFactory.getLog(XITEK.class);

    static final String titles[] = {"productId", "itemId", "vendorItemId", "title", "salePrice"};
    private static Properties properties = new Properties();
    static String imageUrl = "https://www.coupang.com/vp/products/%s/items/%s/vendoritems/%s";
    static String baseURL = "https://she.xitek.com/visual/1580-%s.html";

    private static HelpUtils helpUtils = new HelpUtils();

    public static void main(String[] args) throws Exception {


        //将抓取的数据插入数据库
        Map<String, String> header = new HashMap<>();
        header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36");
        header.put(":authority", "she.xitek.com");
        header.put(":method", "GET");
        header.put(":scheme", "https");
        header.put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        header.put("accept-encoding", "gzip, deflate, br");
        header.put("referer", "https://she.xitek.com/visual/1580-2.html");

        for (int i = 30; i < 40; i++) {
            System.out.println("page "+ i);
            String seed_url = String.format(baseURL,i);
            System.out.println("seed url " +seed_url);
            List<String> hrefs = URLFecter.getMethodXiTek(seed_url, header);
            for (String url_:hrefs
            ) {
                System.out.println("专辑url-----> "+url_);
                URLFecter.DownloadImages(url_, header);
            }

        }

    }

    public static String getConfig(String[] args, String key) {
        if (null == args || args.length < 1) {
            System.out.println("请输入配置文件路径后重试");
            System.exit(0);
        }
        try {
            String path = args[0];
            if (!path.endsWith("\\")) {
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
