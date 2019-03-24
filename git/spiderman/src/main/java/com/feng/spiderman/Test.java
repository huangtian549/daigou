package com.feng.spiderman;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Test {

	public static void main(String[] args) {
		Test test = new Test();
		System.out.println("我们".length());
	}
	
	
	public void name() throws IOException {
		
        	Document doc1 = Jsoup.connect("http://www.stats-sh.gov.cn/html/sjfb/201303/253866.html").timeout(10000).get();
//        	System.out.println(url);
        	
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
        			if (month.equals("029")) {
        				price = tds.get(1).text();
					}else {
						price = tds.get(3).text();
						
					}
        			System.out.println(month);
        			System.out.println(price);
					
				}
        		//接下来，进行你的操作
				
			}
		
	}

}
