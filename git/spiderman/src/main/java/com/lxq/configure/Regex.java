package com.lxq.configure;

public class Regex {
	//CSS樣式表達式
	public final static String CLEAR_CSS="<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
	//轉換換行
	public final static String CONVERSION_LINE="";
	//超链接正则
	public final static String HREF_A="<a\\s.*?href=\"([^\"]+)\"[^>]*>(.*?)</a>";
}
