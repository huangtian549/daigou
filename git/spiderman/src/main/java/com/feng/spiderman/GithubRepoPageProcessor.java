package com.feng.spiderman;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

public class GithubRepoPageProcessor implements PageProcessor {
	 public static final String URL_POST = "\\./\\d+/\\w+\\.html";
//			 "http://www\\.bjstats\\.gov\\.cn/tjsj/yjdsj/fdc/2016/\\w+\\.html";

	 public static final String URL_PAGE = "http://www\\.bjstats\\.gov\\.cn/tjsj/yjdsj/fdc/2016/\\w+\\.html";
	    
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000).setUserAgent(
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

    public void process(Page page) {
    	System.out.println(page.getUrl());
    	if (!page.getUrl().regex(URL_PAGE).match()) {
//            page.addTargetRequests(page.getHtml().xpath("//div[@class=\"jdsj_bg2\"]").links().regex(URL_POST).all());
    		Selectable selectable = page.getHtml().links();
    		selectable.links();
    		List<String> list = page.getHtml().links().regex(URL_POST).all();
    		for (String url : list) {
    			page.addTargetRequest("http://www.bjstats.gov.cn/tjsj/yjdsj/fdc/2016" + url.substring(1));
				
			}
            //文章页
        } else {
            page.putField("title", page.getHtml().xpath("//div[@class='articalTitle']/h2"));
            page.putField("content", page.getHtml().xpath("//div[@id='articlebody']//div[@class='articalContent']"));
            page.putField("date",
                    page.getHtml().xpath("//div[@id='articlebody']//span[@class='time SG_txtc']").regex("\\((.*)\\)"));
        }
        
    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
    	
        Spider.create(new GithubRepoPageProcessor()).addUrl("http://www.bjstats.gov.cn/tjsj/yjdsj/fdc/2016/")
        .addPipeline(new JsonFilePipeline("/Users/liunaikun/git/data/")).thread(5).run();
    }
}