package com.lxq.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.lxq.biz.BaiduNews;
import com.lxq.common.WebContent;

public class Test {
	public static void main(String[] args) throws IOException {
		WebContent wc = new WebContent();
		HashMap<String, String> hm = new HashMap<String, String>();

		List<String> listStr = wc
				.getLink(wc
						.getOneHtml("http://baijia.baidu.com/?tn=listarticle&labelid=101"));

		for (int i = 0; i < listStr.size(); i++) {
			hm = new BaiduNews().getBaiDu(listStr.get(i));
			System.out.println("文章标题:"+hm.get("title"));
			System.out.println("文章内容:"+hm.get("p")+"\n");
		}
	}
}
