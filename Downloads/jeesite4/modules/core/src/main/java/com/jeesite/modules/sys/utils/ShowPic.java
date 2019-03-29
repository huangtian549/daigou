package com.jeesite.modules.sys.utils;

import org.beetl.core.Context;
import org.beetl.core.Function;



public class ShowPic implements Function {

	@Override
	public Object call(Object[] paras, Context arg1) {
		int a = 1;
		int b  = a + 1;
		Object o = paras[0];
		StringBuilder sBuilder = new StringBuilder();
		if (o != null) {
			String image = (String) o;
			if (image != null && image.length() > 0) {
				
				String[] imageArray = image.split(",");
				for (String s : imageArray) {
					if (s != null && s.length() > 0) {
							sBuilder.append("<a class=\"fancybox1\" rel=\"group\"  href=\"/js/userfiles/fileupload/").append(s).append("\">");
							sBuilder.append("<img src=\"/js/userfiles/fileupload/").append(s).append("\"height=\"150\" width=\"150\" alt=\"\"/>");
							sBuilder.append("</a>");
					}
				}
			}
		}
		
		return sBuilder.toString();
	}

}
