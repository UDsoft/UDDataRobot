package udStoreDataModel;


import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import udDataRobotHelper.UDConsoleDisplay;

public class searchProductPicture {
	
	boolean testMode = false;
	
	private String[] ERROR = {"DEFAULT_SMALLPIC_ERROR_MSG","DEFAULT_BIGPIC_ERROR_MSG"};
	
	private UDConsoleDisplay display = new UDConsoleDisplay();
	
	private String smallPic = ERROR[0];
	private String bigPic = ERROR[1];

	public searchProductPicture(Document doc , boolean testMode) {
		this.testMode = testMode;
		picSearchingLogic(doc);
		//check if the small picture name is found or not
		if(smallPic.contains(ERROR[0])){
			System.err.println("The product small picture could not be found");
			try {
				//This will make the error display not passed by easily or unnoticed 
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//check if the small pic is found or not 
		if(bigPic.contains(ERROR[1])){
			System.err.println("The product big picture could not be found");
			try {
				//This will make the error display not passed by easily or unnoticed 
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	//the First value in the array is the small pic name, 2nd value is the Big pic name.
	private void picSearchingLogic(Document doc){
		
		bigPic(doc);
		smallPic(doc);
	}


	public void displayResult() throws IOException {
		String titleDisplay = "This is displayProductName Function which helps "
				+ "to Display the product small pic name : USED FOR DEVELOPMENT Stage.";
		display.showResult(titleDisplay, this.getClass().getName(),null,smallPic);
		
		String titleDisplay2 = "This is displayProductName Function which helps "
				+ "to Display the product small pic name : USED FOR DEVELOPMENT Stage.";
		display.showResult(titleDisplay2, this.getClass().getName(),null,bigPic);

		
	}
	
	private String smallPic(Document doc){
		return smallPic;
		
	}
	public String getSmallPic(){
		return smallPic;
	}
	
	private String bigPic(Document doc){
		Elements eles2 = doc.getElementsByClass("lbfixp2");
		for (Element ele1 : eles2) {
			bigPic = filterBigPic(ele1);
		}
		return bigPic;
		
	}
	
	public String getBigPic(){
		return bigPic;
	}
	
	private static String filterBigPic(Element id) {
		String picURL = null;
		Elements vars = id.getElementsByClass("lbfixp2");
		for (Element var : vars) {
			Elements url = var.getElementsByTag("script");
			for (Element var2 : url) {
				String string = var2.toString();
				System.out.println();
				String[] trimout = string.split(",");
				String[] urlTrimOut = trimout[1].split("}");
				String[] urlReal = urlTrimOut[0].split(":");
				String[] urllastTrim = urlReal[1].split("\'");
				String urllast = urllastTrim[1].trim();
				if (urllast.endsWith("jpg")) {
					picURL = urllast;
				}
			}
		}
		return picURL;
	}
	
	private static void downloadImage(String url, String picname, String filePath, Product productObject)
			throws IOException {

		if (picname.contains("/")) {
			String[] temp = picname.split("/");
			picname = null;
			for (String name : temp) {
				picname = picname + name;
			}
		}
		String name = picname + ".jpg";
		productObject.setPicName(name);
		productObject.Display();
		// Open a URL Stream
		URL urls = new URL(url);
		InputStream in = urls.openStream();
		if (!filePath.isEmpty()) {
			OutputStream out = new BufferedOutputStream(new FileOutputStream(filePath + name));

			for (int b; (b = in.read()) != -1;) {
				out.write(b);
			}
			out.close();
			in.close();
		} else {
			System.out.print("FilePath is null");
			System.out.print("Saving Data failed");
			in.close();
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

}
