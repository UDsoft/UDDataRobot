package udDataRobotHelper;

import java.io.IOException;
import java.net.URLPermission;
import java.util.ArrayList;
import java.util.LinkedHashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BoxParse {
	
	private static final String LINKURL = "http://www.spicesofindia.co.uk/acatalog/";
	
	Document doc;
	
	public BoxParse(Document doc) {
		this.doc = doc;
	}
	
	
	//must be in array list so that all the string can be send once .. sending single string is not possible
	public ArrayList<String> resultURL() throws IOException, InterruptedException{
		
		Elements pages = doc.getElementsByClass("paginationCont");
		ArrayList<String> url1 = new ArrayList<String>();
		ArrayList<String> urlpages = new ArrayList<String>();
		
		if(pages.isEmpty()){
			System.out.println("There is no extra page in this section.");
		Elements boxs_conts = doc.select("div[class=BoxedCont]");
		for(Element box_cont : boxs_conts){
			Elements box_titles = box_cont.select("div[class=BoxedTitle");
			for(Element box_title : box_titles){
				Elements urls = box_title.getElementsByAttribute("href");
				for(Element url :urls){
					String temp = url.attr("href").toString().trim();
					url1.add(temp);
					}
				}
			}
		}else{
			//This part is all the part for the extra page available to search for 
			System.out.println("There is extra pages in this section");
			for(Element page:pages){
				Elements urls = page.getElementsByAttribute("href");
				for(Element eurl : urls){
					urlpages.add(eurl.attr("href"));
					
				}
			
			}
			
			//process deleting the duplicate and remove the last 3 urls >, >> and view all
			LinkedHashSet<String> lhs = new LinkedHashSet<String>();
			lhs.addAll(urlpages);
			urlpages.clear();
			urlpages.addAll(lhs);
			urlpages.remove(urlpages.size()-1);
			System.out.println(urlpages);
			
			Elements boxs_conts = doc.select("div[class=BoxedCont]");
			for(Element box_cont : boxs_conts){
				Elements box_titles = box_cont.select("div[class=BoxedTitle");
				for(Element box_title : box_titles){
					Elements urls = box_title.getElementsByAttribute("href");
					for(Element url :urls){
						String temp = url.attr("href").toString().trim();
						url1.add(temp);
						}
					}
				}
			
			for(String urlpage : urlpages){
				Document docpage = Jsoup.connect(LINKURL + urlpage).get();
				System.out.println("Connectiong to : " + LINKURL + urlpage);
				Thread.sleep(105);
				Elements boxs_conts1 = docpage.select("div[class=BoxedCont]");
				for(Element box_cont : boxs_conts1){
					Elements box_titles = box_cont.select("div[class=BoxedTitle");
					for(Element box_title : box_titles){
						Elements urls = box_title.getElementsByAttribute("href");
						for(Element url :urls){
							String temp = url.attr("href").toString().trim();
							url1.add(temp);
							}
						}
					}
			}
		}
		return url1;
	}
}
