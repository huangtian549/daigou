package com.feng.spiderman;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.hash.Hashing;


public class Test1 {
	
	protected static String URL_TEMPLATE = "https://en.bab.la/dictionary/english-chinese/";

	public static void main(String[] args) {
		try {
			Vector<String> dataVector = new Vector<String>();
			
			Test1 test1 = new Test1();
			List<String> list = test1.readWords();
			
			for (String line : list) {
				if (line != null) {
					String[] arr = line.split(",");
					for (int i = 0; i < arr.length; i++) {
						if (arr[i] != null && arr[i].length() > 0) {
							try {
								String result = translate(arr[i]);
								dataVector.addElement(result);
								
							} catch (Exception e) {
								System.out.println("ERROR:" + arr[i]);
								e.printStackTrace();
							}
						}
					}
				}
			}
			
			File file3 = new File("/Users/liunaikun/git/data/frequency2.csv");
            exportCsv(file3, dataVector,false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public  List<String> readWords() throws IOException {
    	File f = new File(this.getClass().getResource("").getPath());
//    	System.out.println(f.getAbsolutePath());
		List<String> list = FileUtils.readLines(new File(f.getAbsolutePath() + File.separator + "sentimentwords.csv"));
		return list;
	}
	
	public static String translate(final String text)
			   throws Exception {
			  InputStream is = null;
			  Document doc = null;
			  Element ele = null;
			  StringBuilder sBuilder = new StringBuilder();
			  try {
			   // create URL string
			   String url = URL_TEMPLATE + text;

			   // connect & download html
//			   Thread.sleep(1000);
//			   is = HttpClientUtil.downloadAsStream(url);

			   // parse html by Jsoup
			   doc = Jsoup.connect(url).timeout(1000).post();
			   Elements elements = doc.select(".sense-group-results").first().select("li").select("a");
			   
			   for (int i = 0; i < elements.size() - 1; i++) {
				 String oldClose = elements.get(i).text();
				 sBuilder.append(oldClose).append(",");
				 
			   }
			   String result = sBuilder.toString();
			   return result;

			  } finally {
			   IOUtils.closeQuietly(is);
			   is = null;
			   doc = null;
			   ele = null;
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
	
	 public static boolean exportCsv(File file, Vector<String> dataList, boolean isAppend){
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

}
