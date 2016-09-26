package UDSoft.UDdataRobot;


import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URL;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import udDataRobotHelper.BoxParse;
import udDataRobotHelper.CreateFolder;
import udDataRobotHelper.HeadingType;
import udDataRobotHelper.PictureJsonData;

/**
 * Hello world!
 *
 */
public class UDDR 
{
	
	private static final String FOLDERPATH = "Pictures_DB/testNew";
	private static final String LINKURL = "http://www.spicesofindia.co.uk/";
	
    public static void main( String[] args ) throws IOException, InterruptedException
    {
    	HeadingType dropheading;
    	BoxParse boxparse;
    	
    	//Holds all the important IDs for reference to get more data out
    	ArrayList<String> ID_list = new ArrayList<String>();
    	ArrayList<String> URL_list = new ArrayList<String>();
    	ArrayList<String> Box_Url_List = new ArrayList<String>();
    	ArrayList<String> Box_Url_List1 = new ArrayList<String>();
    	
    	//This is the array of all products
    	ArrayList<String> Box_Url_List2 = new ArrayList<String>();
    	
			Document document = Jsoup.connect(LINKURL).userAgent("Mozilla").get();
			//get page title
			String title = document.title();
			System.out.println("Title : "+ title);
			Elements navigationBar = document.select("div[class = navigation-bar]");
			Elements rowClass = document.getElementsByClass("navigation-bar");
			//System.out.println(rowClass);
			
			//iterate the document from Navication bar class for all the ids available.
			for(Element i : rowClass){
				
				Elements ids = i.getElementsByAttribute("id");
				
				//getting the IDs from class id
				for (Element id : ids){
					String idString = id.attr("id");
					if( idString.contains("main") && !idString.contains("link0")){
						ID_list.add(idString);
						System.out.println(ID_list);
					}

				}	
				
			}
			
			//find URL for the IDs 
			for(String id : ID_list){
				Element urltemp = document.getElementById(id);
				dropheading = new HeadingType(urltemp, "url");
				String url = dropheading.getUrl();
				URL_list.add(url);
				System.err.println("The URL FOR ID : "+ id+ "= "+url);
			}
			
			
			
			
			//Test if reached the last page
			Elements controlTest = document.select("div[class = lbo-pdiv]");
			System.out.println(controlTest);
			if(!controlTest.isEmpty()){
				System.out.println("reached the last page");
			}
			
			
			//visit each Main ID and get the URL to respective <div id="deptNavCont"> elements
			
			//Check how many IDs
			int IDstotal = ID_list.size();
			System.err.println(IDstotal);
			for(int x = 0 ; x < IDstotal; x++){
				Document doc = Jsoup.connect(LINKURL + URL_list.get(x)).userAgent("Mozilla").get();
				boxparse = new BoxParse(doc);
				//System.out.println(boxparse.resultURL());
				Box_Url_List.addAll(boxparse.resultURL());
				}
			
			
			for(String urltemp1 : Box_Url_List){
				Document doc = Jsoup.connect(LINKURL + "acatalog/" + urltemp1).userAgent("Mozilla").get();
				boxparse = new BoxParse(doc);
				Box_Url_List1.addAll(boxparse.resultURL());
			}
			System.out.println(Box_Url_List1);
			
			for(String urltemp2 : Box_Url_List1){
				Document doc = Jsoup.connect(LINKURL + "acatalog/" + urltemp2).userAgent("Mozilla").get();
				System.err.println("connectiong to : " + LINKURL + "acatalog/"+ urltemp2);
				boxparse = new BoxParse(doc);
				Box_Url_List2.addAll(boxparse.resultURL());
				Thread.sleep(100);
				System.out.println("thread awake");
			}
			System.out.println(Box_Url_List2);
			PrintWriter out = new PrintWriter("Output.txt");
			for(String text:Box_Url_List2){
				out.println(text);
			}
			out.close();
			
			for(String urlProduct : Box_Url_List2){
				Document doc = Jsoup.connect(LINKURL + "acatalog/" +urlProduct).userAgent("Mozilla").get();
				String titlepic = doc.title();
				System.out.println("Connection to get the Pic of the product " + titlepic);
				
				Elements eles = doc.getElementsByClass("lbfixp2");

				for(Element ele : eles){
					String picUrl = getPicOfProducts(ele);
					getImage(LINKURL+"acatalog/"+picUrl, titlepic, FOLDERPATH);
					Thread.sleep(102);
				}
				
			}
			
		 
			
 }
    
   public static String getPicOfProducts(Element id) {
    	String picURL = null;		
    	Elements vars = id.getElementsByClass("lbfixp2");
    	for(Element var : vars){
    		Elements url = var.getElementsByTag("script");
    				for(Element var2 : url){
    					String string = var2.toString();
    					System.out.println();
    					String[] trimout = string.split(",");
    					String[] urlTrimOut = trimout[1].split("}");   					
    					String[] urlReal = urlTrimOut[0].split(":");    					
    					String[] urllastTrim = urlReal[1].split("\'");
    					String urllast = urllastTrim[1].trim();    					
    					if(urllast.endsWith("jpg")){
    						picURL = urllast;
    					}
    				}
    	}
		return picURL;
	}

	private static void getImage(String url,String picname,String filePath) throws IOException{
    
		if(picname.contains("/")){
			String[] temp = picname.split("/");
			picname = null;
			for(String name :temp){
				picname = picname + name;
			}
		}
    	String name = picname + ".jpg" ;
    	
    	//Open a URL Stream
    	URL urls = new URL(url);
    	InputStream in = urls.openStream();
    	if(!filePath.isEmpty()){
    	OutputStream out = new BufferedOutputStream(new FileOutputStream(filePath + name));
    	
    	for(int b;(b = in.read())!= -1;){
    		out.write(b);
    	}
    	out.close();
    	in.close();
    	}else{
    		System.out.print("FilePath is null");
    		System.out.print("Saving Data failed");
    		in.close();
    	}
    }

    
}
