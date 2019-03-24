package com.lxq.biz;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.lxq.common.WebContent;

public class BaiduNews {
	private WebContent wc=new WebContent();
	public HashMap<String, String> getBaiDu(final String s){
		final HashMap<String, String> hm = new HashMap<String, String>();
		final StringBuffer sb = new StringBuffer();
		String html = "";
		try {
			html = wc.getOneHtml(s);
		} catch (final Exception e) {
			e.getMessage();
		}
		String title = wc.outTag(wc.getTitle(html));
		final Pattern pa = Pattern.compile(
				"</div>(.*?)</p></div>", Pattern.DOTALL);
		final Matcher ma = pa.matcher(html);
		while (ma.find()) {
			sb.append(ma.group());
		}
		String temp = sb.toString();
		
		temp =wc.cleanContent(temp);
		hm.put("title", title);
		hm.put("p", wc.outTag(temp));
		return hm;
	}
}
