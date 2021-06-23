package com.sp.main;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import java.util.List;

/**
 * Created by Rocky on 2017/3/15 42du.cn.
 */
public class JDListTools {

    public static void getItems() throws Exception {
        WebClient webClient = new WebClient(BrowserVersion.BEST_SUPPORTED);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.waitForBackgroundJavaScript(600*1000);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        HtmlPage page = webClient.getPage("https://list.jd.com/list.html?cat=9987,653,655");
        List<HtmlDivision> divs = (List) page.getByXPath("//div[@id='plist']//ul//li[@class='gl-item']//div[@class='gl-i-wrap j-sku-item']");
        for(HtmlDivision  div :divs) {
            DomNodeList<DomNode> childs = div.getChildNodes();
            String name = "";
            String price = "";
            String comments = "";
            for(DomNode dn : childs) {
                NamedNodeMap map = dn.getAttributes();
                Node node = map.getNamedItem("class");
                if(node != null) {
                    String value = node.getNodeValue();
                    if(value.contains("p-name")) {
                        name = dn.asText();
                    } else if(value.contains("p-price")) {
                        price = dn.asText();
                    } else if(value.contains("p-commit")) {
                        comments = dn.asText();
                    }
                }
            }
            System.out.println(name+"//"+price+"//"+comments);
        }
    }

    public static void main(String[] args) throws Exception {
//        getItems();
        WebClient wc=new WebClient(BrowserVersion.CHROME);
        wc.setJavaScriptTimeout(5000);
        wc.getOptions().setUseInsecureSSL(true);//接受任何主机连接 无论是否有有效证书
        wc.getOptions().setJavaScriptEnabled(true);//设置支持javascript脚本
        wc.getOptions().setCssEnabled(false);//禁用css支持
        wc.getOptions().setThrowExceptionOnScriptError(false);//js运行错误时不抛出异常
        wc.getOptions().setTimeout(100000);//设置连接超时时间
        wc.getOptions().setDoNotTrackEnabled(false);
        HtmlPage page=wc.getPage("https://www.coupang.com/vp/products/1468004788?itemId=2524632298&vendorItemId=70698542274");

        String res=page.asText();
        //处理源码
//        deal(res);
        System.out.println(res);
    }
}