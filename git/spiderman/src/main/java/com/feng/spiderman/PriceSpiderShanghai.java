package com.feng.spiderman;



	import java.awt.image.Kernel;
	import java.io.File;
	import java.io.IOException;
	import java.util.ArrayList;
	import java.util.HashMap;
	import java.util.Iterator;
	import java.util.List;
	import java.util.Map;
	import java.util.Map.Entry;
	import java.util.regex.Matcher;
	import java.util.regex.Pattern;

	import org.apache.commons.io.FileUtils;
	import org.jsoup.Jsoup;
	import org.jsoup.nodes.Document;
	import org.jsoup.nodes.Element;
	import org.jsoup.select.Elements;

	public class PriceSpiderShanghai {

		public static void main(String[] args) {
			try {
				parseLink();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		 private static void parseLink() throws IOException {  
			 Map<String, String> dataMap = new HashMap<String, String>();
			 for (int a = 1; a < 5; a++) {
				String PREFIX = "http://www.stats-sh.gov.cn/html/sjfb/ydsj/ydsj42/";
				String url2 = PREFIX;
				if (a > 1) {
					url2 = url2 + a + ".html";
				}
		        Document doc = Jsoup.connect(url2).timeout(10000).get(); ;// 解析HTML字符串返回一个Document实现  
		        Elements links = doc.select("a");// 查找第一个a元素  
		        
		        
		        String regEx = "html/sjfb/\\d+/*";
		        // 编译正则表达式
		        Pattern pattern = Pattern.compile(regEx);
		        // 忽略大小写的写法
		        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		        
		        List<String> list = new ArrayList<String>();
		        for (Element link : links) { 
		        	  String linkHref = link.attr("href"); 
		        	  String linkText = link.text(); 
		        	  Matcher matcher = pattern.matcher(linkHref);
		        	  if (matcher.find() && !linkHref.equals("/html/sjfb/201701/1000339.html")) {
						list.add(linkHref);
						System.out.println(linkHref + ":" + linkText);
					}
		        }
		        
		        
		        for (String url : list) {
		        	Document doc1 = Jsoup.connect("http://www.stats-sh.gov.cn" + url).timeout(10000).get();
		        	System.out.println(url);
		        	
		        	String head = doc1.select("#name").text();
		        	System.out.println(head);
		        	String[] array = head.split("年");
		        	String year = array[0];
		        	String month = "";
		        	
		        	String str= array[1];
		        	for(int n=0;n<str.length();n++){
		        		if(str.charAt(n)>=48 && str.charAt(n)<=57){
		        			month+=str.charAt(n);
		        		}
		        	}
		        	 
		        	System.out.println(year + ":" + month);
		        	if (month.length() < 2) {
						month = "0" + month;
					}
		        	
		        	if (month.equals("18")) {
						month = "08";
					}
		        	
		        	Elements elements1 = doc1.select("table").select("tr");
		        	boolean flag = false;
		        	for (int i = 0; i <=elements1.size() - 1; i++) {
		        		//获取每一行的列
		        		Elements tds = elements1.get(i).select("td");
		        		
		        		
		        		//对每一行中的某些你需要的列进行处理
		        		
		        		//获取第i行第j列的值
		        		String title  = tds.get(0).text();
		        		if (title != null && title.length() > 0) {
							if (title.trim().indexOf("销售面积") >= 0 && title.indexOf("万平方米") < 0) {
								flag = true;
							}
						}
		        		if (title != null && title.length() > 0 && title.trim().indexOf("住宅") >= 0 && flag == true) {
		        			String price = "";
		        			if (month.equals("02") && Integer.parseInt(year) > 2015) {
		        				price = tds.get(1).text();
							}else {
								price = tds.get(3).text();
								
							}
		        			dataMap.put(year + month, price);
//		        			System.out.println(title);
		        			System.out.println(price);
							
						}
		        		//接下来，进行你的操作
						
					}

		        }

		  
//					
//		        
			 
			 List<String> lines = new ArrayList<String>();
			 for (Iterator iterator = dataMap.entrySet().iterator(); iterator.hasNext();) {
				Entry<String, String> type = (Entry<String, String>) iterator.next();
				lines.add(type.getKey() + "," + type.getValue().toString());
			 }
			 FileUtils.writeLines(new File("/Users/liunaikun/git/data/houseprice_shanghai.csv"), lines);
			 
		    }  
		 }
		 

	}

