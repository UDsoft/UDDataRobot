package udDataRobotHelper;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * This class manage the main connection 
 * @author sdarwin
 *
 */
public class UDConnect {

	//The main Document which will be used to extract data.
	Document doc;
	
	public UDConnect(String URL, String userAgent,int timeOut) {
		try {
			this.doc = Jsoup.connect(URL).userAgent(userAgent).timeout(timeOut).get();
			String displayMsg = " The Connection to " + URL + " using " + userAgent +" is a success. ";
			String display_window = "-";
			for( int x = 0 ; x < displayMsg.length(); x++){
				display_window = display_window + "_";
			}
			System.out.println(display_window);
			System.out.println();
			System.out.println(displayMsg);
			System.out.println();
			System.out.println( " Title of the Document is  -> " + doc.title());
			System.out.println();
			System.out.println(display_window);
			Random rn = new Random();
			int sleepTime = rn.nextInt(6) + 10;
			try {
				
				System.out.println("Sleep Time is " + sleepTime);
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		} catch (IOException e) {
			System.err.println("Connection Failed");
			e.printStackTrace();
		}
		
	}

	public Document getDocument() {
		return doc;
		// TODO Auto-generated method stub
		
	}
	
	public void htmlDisplay(){
		System.err.println(" Display the HTML file Source only to use for Development purpose of the automation.");
		System.out.println();
		System.out.println(doc);
	}
	
}
