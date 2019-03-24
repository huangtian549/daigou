package com.feng.spiderman;

import com.gargoylesoftware.htmlunit.BrowserVersion;  
import com.gargoylesoftware.htmlunit.WebClient;  
import com.gargoylesoftware.htmlunit.html.*;  
import org.junit.Assert;  
import org.junit.Test; 



import java.util.Iterator;  
import java.util.List;  

public class HtmlUnitTest {
	@Test
	public void getElements() throws Exception {  
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);  
        final HtmlPage page = webClient.getPage("https://translate.google.com/#en/zh-CN/busy");  
        final HtmlDivision div = page.getHtmlElementById("blog_actions");  
        //获取子元素  
        Iterator<DomElement> iter = div.getChildElements().iterator();  
        while(iter.hasNext()){  
            System.out.println(iter.next().getTextContent());  
        }  
        //获取所有输出链接  
        for(HtmlAnchor anchor : page.getAnchors()){  
            System.out.println(anchor.getTextContent()+" : "+anchor.getAttribute("href"));  
        }  
        webClient.close();
    }  

}
