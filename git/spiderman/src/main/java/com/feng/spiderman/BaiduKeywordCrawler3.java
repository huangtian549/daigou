package com.feng.spiderman;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;  
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;  
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
import java.sql.Statement;  
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;  
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.collation.CollationAttributeFactory;
//  
import org.apache.poi.poifs.filesystem.DirectoryEntry;  
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.eclipse.jetty.io.ArrayByteBufferPool;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;  
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import cn.edu.hfut.dmic.contentextractor.ContentExtractor;  
import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;  
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;  
import cn.edu.hfut.dmic.webcollector.model.Page;  
import cn.edu.hfut.dmic.webcollector.plugin.ram.RamCrawler;  
  
public class BaiduKeywordCrawler3 extends RamCrawler{  
	
	static Map<String, Float> WORD_MAP = new HashMap<String, Float>();
  
    private Connection connection;    
    private PreparedStatement pstatement;   
    // 连接MySql数据库，用户名root，密码mahao  
    String databaseurl = "jdbc:mysql://45.33.62.141:3306/jfinaluibv3?characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false&zeroDateTimeBehavior=convertToNull";  
    String username = "root";  
    String password = "1fdg,wfmk";  
    //保存抽取到的数据  
    StringBuilder result = new StringBuilder();  
    
   static Float SUM_SCORE = 0f;   //
    
    static Vector<String> DATA_LIST = new Vector<String>();
    
    static ConcurrentHashMap<String, Integer> SCORE_MAP = new ConcurrentHashMap<String, Integer>();
    public  BaiduKeywordCrawler3(String from, String to,String keyword, int maxPageNum) throws Exception {  
    	int pageNum = 0;
            String url = createUrl(from, to,keyword, pageNum);  
            System.out.println(url);
            
            List<String> urList = getPaginationUrl2(url);
            
            CrawlDatum datum = new CrawlDatum(url)  
                    .meta("keyword", keyword)  
                    .meta("pageNum", pageNum + "")  
                    .meta("pageType", "searchEngine")  
                    .meta("depth", "1");  
            addSeed(datum);  
            
            
            for (String link : urList) {
            	CrawlDatum datum1 = new CrawlDatum(link)  
                        .meta("keyword", keyword)  
                        .meta("pageNum", pageNum++ + "")  
                        .meta("pageType", "searchEngine")  
                        .meta("depth", "1");  
                addSeed(datum1);  
			}
    }  
    public void visit(Page page, CrawlDatums next) {  
        String keyword = page.meta("keyword");  
        String pageType = page.meta("pageType");  
        int depth = Integer.valueOf(page.meta("depth"));  
        if (pageType.equals("searchEngine")) {  
            int pageNum = Integer.valueOf(page.meta("pageNum"));  
            System.out.println("成功抓取关键词" + keyword + "的第" + pageNum + "页搜索结果");  
            // || div[class=result-op c-container xpath-log ]>h3>a  
            Elements results = page.select("div[class=result]>h3>a");  
        //  Elements results1 = page.select("div[class=result-op c-container xpath-log]>h3>a");//,div[id=result-op c-container xpath-log]>h3>a  
            //System.out.println(results1.get(0));  
            //results.add(results1.get(0));  
            for (int rank = 0; rank < results.size(); rank++) {  
                Element result = results.get(rank);  
                /* 
                 * 我们希望继续爬取每条搜索结果指向的网页，这里统称为外链。 
                 * 我们希望在访问外链时仍然能够知道外链处于搜索引擎的第几页、第几条， 
                 * 所以将页号和排序信息放入后续的CrawlDatum中，为了能够区分外链和 
                 * 搜索引擎结果页面，我们将其pageType设置为outlink，这里的值完全由 用户定义，可以设置一个任意的值 
                 * 在经典爬虫中，每个网页都有一个refer信息，表示当前网页的链接来源。 
                 * 例如我们首先访问新浪首页，然后从新浪首页中解析出了新的新闻链接， 
                 * 则这些网页的refer值都是新浪首页。WebCollector不直接保存refer值， 
                 * 但我们可以通过下面的方式，将refer信息保存在metaData中，达到同样的效果。 
                 * 经典爬虫中锚文本的存储也可以通过下面方式实现。 
                 * 在一些需求中，希望得到当前页面在遍历树中的深度，利用metaData很容易实现 
                 * 这个功能，在将CrawlDatum添加到next中时，将其depth设置为当前访问页面 的depth+1即可。 
                 */  
                CrawlDatum datum = new CrawlDatum(result.attr("abs:href"))  
                        .meta("keyword", keyword)  
                        .meta("pageNum", pageNum + "")  
                        .meta("rank", rank + "")  
                        .meta("pageType", "outlink")  
                        .meta("depth", (depth + 1) + "")  
                        .meta("refer", page.url());  
                next.add(datum);  
            } 
            
            
        } else if (pageType.equals("outlink")) {  
            /*int pageNum = Integer.valueOf(page.getMetaData("pageNum")); 
            int rank = Integer.valueOf(page.getMetaData("rank")); 
            String refer = page.getMetaData("refer");*/  
            try {  
                String content = ContentExtractor.getContentByUrl(page.url());  
                /*String line = String.format( 
                        "第%s页第%s个结果:标题:%s(%s字节)\tdepth=%s\trefer=%s", pageNum, 
                        rank + 1, page.getDoc().title(), content, 
                        depth, refer);*/  
                HashMap<String, String> data = new HashMap<String,String>();  
                Date currentDate = new java.util.Date();  
                SimpleDateFormat myFmt = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");  
                TimeZone timeZoneChina = TimeZone.getTimeZone("Asia/Shanghai");// 获取中国的时区  
                myFmt.setTimeZone(timeZoneChina);// 设置系统时区  
                String grabTime = myFmt.format(currentDate);// new Date()为获取当前系统时间  
                data.put("title", page.doc().title());  
                data.put("from", page.url());  
                data.put("content", content);  
                data.put("grabTime", grabTime);  
                
                parseContent(data);
                String line = String.format("标题：%s,得分：%s,关键字：%s\n来源：%s\n正文：%s", page.doc().title(),data.get("score"), data.get("words"),page.url(),content);  
                //String line = String.format("标题：%s\n", page.getDoc().title());  
                //持久化到word文档中  
                //是否为线程安全？？？  
                //synchronized(this) {  
                    String destFile = "/Users/liunaikun/git/data/"+keyword+".csv";  
                    result.append(line);  
                    DATA_LIST.add(line);
                    
                    //将result写到doc文件中  
//                    write2File(destFile,result.toString()); 
//                    write2csv(line);
                    //添加到数据库中  
//                    addResultData(data);  
                //}  
                System.out.println(line);  
            } catch (Exception e) {  
                //e.printStackTrace();  
                System.out.println("链接"+page.url()+"失效");  
            }  
        }  
    }  
    
    
    
    private void parseContent(HashMap<String, String> data) {
    	String content = data.get("content");
		if (content != null && content.length() > 0) {
			Float sum = 0f;
			StringBuffer words = new StringBuffer();
			for (Iterator iterator = WORD_MAP.entrySet().iterator(); iterator.hasNext();) {
				Entry<String, Float> entry = (Entry<String, Float>) iterator.next();
				String word = entry.getKey();
				Float score = entry.getValue();
				int count = count(content, word);
				if(count > 0){
					sum = sum + score;
					words.append(word).append(",");
					if (SCORE_MAP.containsKey(word)) {
						SCORE_MAP.put(word, SCORE_MAP.get(word) + 1);
					}else {
						SCORE_MAP.put(word, count);
					}
				}
			}
			
			SUM_SCORE = SUM_SCORE + sum;
			data.put("words", words.toString());
			data.put("score", sum.toString());
		}
	}
    
    public static int count(String text,String sub){
        int count =0, start =0;
        while((start=text.indexOf(sub,start))>=0){
            start += sub.length();
            count ++;
        }
        return count;
    }
       //将数据保存到mysql数据库中  
    private void addResultData(HashMap<String, String> data) {  
          
        String title = data.get("title");  
        String source_url = data.get("from");  
        String content = data.get("content").replaceAll("\\?{2,}", "");//去掉字符串中出现的多个连续问号。  
        //抓取时间  
        String grabTime = data.get("grabTime");  
        String score = data.get("score");
        String words = data.get("words");
        /*SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss"); 
        Date date = null; 
        try { 
            date = format.parse(grabTime); 
        } catch (Exception e) { 
            e.printStackTrace(); 
        }*/  
        //System.out.println("抓取时间"+grabTime);  
        try {  
            connection = DriverManager.getConnection(databaseurl, username, password);  
            String sql = "INSERT INTO wjd_keyword_search_table(TITLE,GRAP_TIME,CONTENT,SOURCE_URL,SCORE,WORDS) VALUES(?,?,?,?,?,?)";  
            String checkSql = "select 1 from wjd_keyword_search_table where TITLE='" + title + "'";  
            Statement statement = connection.prepareStatement(checkSql);  
            ResultSet result = statement.executeQuery(checkSql);  
            if (!result.next()) {  
                // 如果数据库中不存在该记录，则添加到数据库中  
                pstatement = connection.prepareStatement(sql);  
                pstatement.setString(1, title);  
                //pstatement.setString(2, date);  
                pstatement.setString(2,grabTime);  
                pstatement.setString(3, content);  
                pstatement.setString(4, source_url); 
                pstatement.setString(5, score);  
                pstatement.setString(6, words);  
                pstatement.executeUpdate();  
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }  
    
    
    private void clearDataTable() {  
        
        try {  
            connection = DriverManager.getConnection(databaseurl, username, password);  
            String sql = "Truncate table wjd_keyword_search_table";  
            Statement statement = connection.createStatement();  
            statement.execute(sql);  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }  
    
    /** 
     * 将数据持久化到本地doc文件中 
     * @param destFile 
     * @param line 
     */  
    private void write2File(String destFile, String line) {  
        try {  
            //doc content  
            ByteArrayInputStream bais = new ByteArrayInputStream(line.getBytes());  
            POIFSFileSystem fs = new POIFSFileSystem();  
            DirectoryEntry directory = fs.getRoot();   
            directory.createDocument("WordDocument", bais);  
            FileOutputStream ostream = new FileOutputStream(destFile);  
            fs.writeFilesystem(ostream);  
            bais.close();  
            ostream.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
    
    private void write2csv(String line) {
//    	 FileUtils.writeLines(new File("/Users/liunaikun/git/data/houseprice_shanghai.csv"), lines);
    	try {
			FileUtils.writeStringToFile(new File("/Users/liunaikun/git/data/houseprice_baidu.csv"), line);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    
    
    public  boolean exportCsv(File file, Vector<String> dataList, boolean isAppend){
        boolean isSucess=false;
        
        FileOutputStream out=null;
        OutputStreamWriter osw=null;
        BufferedWriter bw=null;
        try {
            out = new FileOutputStream(file,isAppend);
            osw = new OutputStreamWriter(out, "gbk");
            bw =new BufferedWriter(osw);
            if(dataList!=null && !dataList.isEmpty()){
                for(String data : dataList){
                    bw.append(data).append("\r");
                }
            }
            isSucess=true;
        } catch (Exception e) {
            isSucess=false;
        }finally{
            if(bw!=null){
                try {
                    bw.close();
                    bw=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
            if(osw!=null){
                try {
                    osw.close();
                    osw=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
            if(out!=null){
                try {
                    out.close();
                    out=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
        }
        
        return isSucess;
    }
    public static void main(String[] args) throws Exception {  
        String keyword = "北京房价";  
        int pageToal =1;  
        int year_init = 20170301;
        for (int i = 0; i < 3; i++) {
        	
        	SUM_SCORE = 0f;   //重新初始化
            
            DATA_LIST = new Vector<String>();//重新初始化
        	
        	String date = year_init + i * 100 + "";
        	Date date2 = ToolDateTime.parse(date, "yyyyMMdd");
        	
        	String toDate = getLastDayOfMonth(date2);
			 
        	BaiduKeywordCrawler3 crawler = new BaiduKeywordCrawler3(date,toDate, keyword, pageToal);  
        			
        	crawler.readWords();
            
//            crawler.clearDataTable();
            
            
            crawler.start();  
            
            File file = new File("/Users/liunaikun/git/data/"+keyword+"_" + date +".csv");
            crawler.exportCsv(file, DATA_LIST,false);
            
            Float average = SUM_SCORE / DATA_LIST.size();
            File file2 = new File("/Users/liunaikun/git/data/"+keyword+"_score.csv");
            Vector<String> averageScoreList = new Vector<String>();
            averageScoreList.add(Integer.parseInt(date)/100 + "," + average );
            crawler.exportCsv(file2, averageScoreList,true);
            
            
            Vector<String> frequency = new Vector<String>();
            for (Iterator iterator = SCORE_MAP.entrySet().iterator(); iterator.hasNext();) {
				Entry<String, Integer> entry = (Entry<String, Integer>) iterator.next();
				frequency.add(entry.getKey() + "," + entry.getValue());
			}
            
            File file3 = new File("/Users/liunaikun/git/data/frequency.csv");
            crawler.exportCsv(file3, frequency,false);
            
        }  
    }  
      
    /** 
     * 计算搜索的开始时间和结束时间
     */  
    public static String createUrl(String from, String to, String keyword, int pageNum)  
            throws Exception {  
    	
		String dt = "1970-01-01";
		
		Date date1 = ToolDateTime.parse(from, ToolDateTime.SHORT_DATE_FORMAT_NO_DASH);
		Date date2 = ToolDateTime.parse(to, ToolDateTime.SHORT_DATE_FORMAT_NO_DASH);
		Date date = ToolDateTime.parse(dt, ToolDateTime.pattern_ymd);
		Long bstd = (date1.getTime() - date.getTime())/1000;
    	long mbstd = bstd - 28800;
    	
    	Long estd  = (date2.getTime() - date.getTime())/1000;
    	long mestd = estd - 28800 + 86399;
    	
        int first = (pageNum-1) * 10;  
        keyword = URLEncoder.encode(keyword, "utf-8"); 
        
        StringBuilder sBuilder = new StringBuilder("http://news.baidu.com/ns?from=news&cl=2&bt=").append(mbstd).append("&et=").append(mestd).append("&y0=");
        sBuilder.append(from.substring(0,4)).append("&m0=").append(from.substring(4,6)).append("&d0=").append(from.substring(6,8))
        .append("&y1=").append(to.substring(0,4)).append("&m1=").append(to.substring(4,6)).append("&d1=").append(to.substring(6,8))
        .append("&q1=").append(keyword).append("&submit=%B0%D9%B6%C8%D2%BB%CF%C2&q3=&q4=&mt=0&lm=&s=2&begin_date=2017-6-1&end_date=2017-6-30&tn=newsdy&ct1=1&ct=1&rn=50&q6=");
        
        
        return sBuilder.toString();
    }
    
    
    public static List<String> getPaginationUrl(String url) throws IOException {
    	List<String> dataList = new ArrayList<String>();
    	Document doc = Jsoup.connect(url).timeout(10000).get();
    	Elements pages = doc.select("#page>a");  
    	for (int i = 0; i < pages.size(); i++) { 
    		Element result = pages.get(i);  
    		String pageurl = result.attr("abs:href");
    		dataList.add(pageurl);
    		
    	}
		return dataList;
	}
    
    
    public static List<String> getPaginationUrl2(String url) throws IOException {
    	List<String> dataList = new ArrayList<String>();
    	Document doc = Jsoup.connect(url).timeout(10000).get();
    	Elements pages = doc.select("#page>a");  
    		Element result = pages.get(0);  
    		String pageurl = result.attr("abs:href");
    		
    	for(int i=0; i < 10; i++){
    		int pageNum = i *  50;
    		String s = "pn=" + pageNum;
    		String[] arr = pageurl.split("pn=");
    		int index = arr[1].indexOf("&");
    		
    		String tmp = arr[0] + s + arr[1].substring(index);
    		dataList.add(tmp);
    	}
		return dataList;
	}
    
    public  void readWords() throws IOException {
    	File f = new File(this.getClass().getResource("").getPath());
//    	System.out.println(f.getAbsolutePath());
		List<String> list = FileUtils.readLines(new File(f.getAbsolutePath() + File.separator + "opinion_word_simplified.csv"));
		for (String line : list) {
			if (line != null && line.length() > 0) {
				String arr[] = line.split(",");
				WORD_MAP.put(arr[0], Float.parseFloat(arr[1]));
			}
		}
	}
    
    public static String getLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		Date firstDayOfMonth = calendar.getTime();  
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		Date lastDayOfMonth = calendar.getTime();
		
		return ToolDateTime.format(lastDayOfMonth, "yyyyMMdd");
	}

} 
