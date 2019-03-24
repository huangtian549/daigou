package com.lxq.common;

import java.net.URL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lxq.configure.Para;
import com.lxq.configure.Regex;

public class WebContent {
	/**
	 * 读取一个网页全部内容
	 */
	public String getOneHtml(final String htmlurl) throws IOException {
		URL url;
		String temp;
		final StringBuffer sb = new StringBuffer();
		try {
			url = new URL(htmlurl);
			final BufferedReader in = new BufferedReader(new InputStreamReader(
					url.openStream(), "utf-8"));// 读取网页全部内容
			while ((temp = in.readLine()) != null) {
				sb.append(temp);
			}
			in.close();
		} catch (final MalformedURLException me) {
			System.out.println("你输入的URL格式有问题！请仔细输入");
			me.getMessage();
			throw me;
		} catch (final IOException e) {
			e.printStackTrace();
			throw e;
		}
		return sb.toString();
	}

	/**
	 * 
	 * @param s
	 * @return 获得网页标题
	 */
	public String getTitle(final String s) {
		String regex;
		String title = "";
		final List<String> list = new ArrayList<String>();
		regex = "<title>.*?</title>";
		final Pattern pa = Pattern.compile(regex, Pattern.CANON_EQ);
		final Matcher ma = pa.matcher(s);
		while (ma.find()) {
			list.add(ma.group());
		}
		for (int i = 0; i < list.size(); i++) {
			title = title + list.get(i);
		}
		return outTag(title);
	}

	/**
	 * 
	 * @param s
	 * @return 获得文章有效链接
	 */
	public List<String> getLink(final String s) {
		final List<String> list1 = new ArrayList<String>();
		final List<String> list2 = new ArrayList<String>();
		final List<String> list3 = new ArrayList<String>();
		final List<String> list4 = new ArrayList<String>();
		final Pattern pa = Pattern.compile(Regex.HREF_A, Pattern.DOTALL);
		final Matcher ma = pa.matcher(s);
		while (ma.find()) {
			list1.add(ma.group());
		}
		// 查找tab下具体文章链接
		for (int i = 0; i < list1.size(); i++) {
			int index1 = list1.get(i).indexOf("/article/");
			int index2 = list1.get(i).indexOf("img src");
			if (index1 != -1 && index2 == -1) {
				list2.add(list1.get(i));
			}
		}
		//去除<a href=\"
		for (int i = 0; i < list2.size(); i++) {
			int index = list2.get(i).indexOf("<a href=\"");
			if (index != -1)
				list3.add(list2.get(i).substring(9));
		}
		//控制取多少条链接,从常量获取Para.SCRAP_NUM
		for (int i = 0; i < list3.size()-Para.SCRAP_NUM; i++) {
			int index = list3.get(i).indexOf("\" target=");
			if (index != -1) {
				list4.add(list3.get(i).substring(0, index));
			}
		}
		return list4;
	}

	/**
	 * TEST
	 * 
	 * @param args
	 * @throws IOException
	 */
//	public static void main(String[] args) throws IOException {
//		final WebContent wc = new WebContent();
//		List<String> listStr = wc.getLink(wc.getOneHtml("http://baijia.baidu.com/?tn=listarticle&labelid=105"));
//		for (String string : listStr) {
//			System.out.println(string);
//		}
//	}

	/**
	 * 
	 * @param s
	 * @return 获得脚本代码
	 */
	public List<String> getScript(final String s) {
		String regex;
		final List<String> list = new ArrayList<String>();
		regex = "<script.*?</script>";
		final Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
		final Matcher ma = pa.matcher(s);
		while (ma.find()) {
			list.add(ma.group());
		}
		return list;
	}

	/**
	 * 
	 * @param s
	 * @return 获得CSS
	 */
	public List<String> getCSS(final String s) {
		String regex;
		final List<String> list = new ArrayList<String>();
		regex = "<style.*?</style>";
		final Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
		final Matcher ma = pa.matcher(s);
		while (ma.find()) {
			list.add(ma.group());
		}
		return list;
	}

	/**
	 * 
	 * @param s
	 * @return 去掉标记
	 */
	public String outTag(final String s) {
		return s.replaceAll("<.*?>", "");
	}

	/**
	 * 净化文章内容
	 * 
	 * @param content
	 * @return
	 */
	public String cleanContent(String content) {
		String temp = content.replaceAll("(<br>)+?", "\n");// 转化换行
		temp = temp.replaceAll("<p><em>.*?</em></p>", "");// 去图片注释;
		Pattern p = Pattern.compile(Regex.CLEAR_CSS);
		Matcher m = p.matcher(content.toLowerCase());
		if (m.find()) {
			temp = m.replaceAll("");
		}
		return temp;
	}
}
