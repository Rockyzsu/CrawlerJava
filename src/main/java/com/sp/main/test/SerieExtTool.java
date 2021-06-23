package com.sp.main.test;

import java.util.HashMap;
import java.util.Map;
 
 
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 
/**
 * @author FANGYUKANG
 * @Title SerieExtTool
 * @Description TODO(根据车系ID查找车系：OPTINE内容，属性，颜色，内饰颜色)
 * @Date: 2016年05月17日  下午16:30:52
 */
public class SerieExtTool {
	
	/*设置网页抓取响应时间*/
	private static final int TIMEOUT = 10000;
	
	public static Map<String, Object> getSerieExtDetail(int serieId) throws Exception{
		
		/*车系参数配置页面*/
		String serieInfo = "http://car.autohome.com.cn/config/series/"+serieId+".html";
		
		/*用來封裝要保存的参数*/
		Map<String, Object> map = new HashMap<String, Object>();
		
		/*取得车系参数配置页面文档*/
		Document document = Jsoup.connect(serieInfo).timeout(TIMEOUT).get();
		
		/*取得script下面的JS变量*/
		Elements e = document.getElementsByTag("script").eq(6);
		
		/*循环遍历script下面的JS变量*/
		for (Element element : e) {
			
			/*取得JS变量数组*/
			String[] data = element.data().toString().split("var");
			
			/*取得单个JS变量*/
			for(String variable : data){
				
				/*过滤variable为空的数据*/
				if(variable.contains("=")){
					
					/*取到满足条件的JS变量*/
					if(variable.contains("option") || variable.contains("config") 
							|| variable.contains("color") || variable.contains("innerColor")){
						
						String[]  kvp = variable.split("=");
						
						/*取得JS变量存入map*/
						if(!map.containsKey(kvp[0].trim())) 
							map.put(kvp[0].trim(), kvp[1].trim().substring(0, kvp[1].trim().length()-1).toString());
					}
				}
			}
		}
		return map;
	}
	
}