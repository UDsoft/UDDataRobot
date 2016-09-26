package udDataRobotHelper;

import java.util.ArrayList;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HeadingType {
	
	private Element element;
	private String url;
	private String[] dataArray;
	private String typeHeading;
	
	public HeadingType(Element element , String type){
		this.element = element;
		setArray();
		setURL();
		setHeadingType(type);
		/*System.out.println(typeHeading);
		System.out.println(url);
		System.out.println();*/
	}
	
	private void  getTitle(){
		
		
	}
	
	private void setURL() {
		for (String a : dataArray) {
			if (a.contains(".html")) {
				String[] tempArray = a.split("\"");
				for (String b : tempArray) {
					if (b.contains(".html")) {
						url = b;
					}
				}
			}
		}
	}

	private void setArray(){
		dataArray = element.toString().split("\\s+");
	}
	
	private void setHeadingType(String type) {
		for (String a : dataArray) {
			if (a.contains("class=")) {
				String[] tempArray = a.split("=");
				for (String b : tempArray) {
					if (!b.contains("class=")) {
						String[]temp = b.split("\"");
						for(String c : temp){
							if(c.contains(type)){
								typeHeading = c;
							}
						}
						
					}
				}
			}
		}
	}
	
	public String getUrl() {
		return url;
	}
}
