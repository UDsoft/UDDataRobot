package udStoreDataModel;

import java.util.Scanner;

import org.jsoup.nodes.Document;

import udDataRobotHelper.UDConsoleDisplay;

public class SearchProductBrand implements SearchDetails{
	
	private UDConsoleDisplay display = new UDConsoleDisplay();
	
	private String brandName = SearchDetails.brandName; 
	private boolean testMode;
	private boolean isDirectBrandPage;

	private Scanner scan;

	public SearchProductBrand(Document doc, boolean testMode, boolean isDirectBrandPage) {
		this.isDirectBrandPage = isDirectBrandPage;
		this.testMode = testMode;
		brandSearchingLogic(doc);
		if (brandName.contains(SearchDetails.brandName)) {
			System.err.println("The product brand could not be found");
			try {
				// This will make the error display not passed by easily or
				// unnoticed
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	





	/*
	 * This function is the logic which changes as the website which we are going to look at the to grap the data.
	 */
	private void brandSearchingLogic(Document doc){
		if(isDirectBrandPage){
			brandName = doc.title();
		}else{
			
			String tempTitle = doc.title();
			String[] splitedTitle = tempTitle.split("-");
			System.out.println("Please choose the Brand of the product");
			int x = 1;
			for(String potentialTitle : splitedTitle){
				System.out.println(x +" : "+potentialTitle);
				
			}
			scan = new Scanner(System.in);
			int i = scan.nextInt();
			brandName = splitedTitle[i+1];
			
			
		}
		if(testMode){
			UDConsoleDisplay displayMsg = new UDConsoleDisplay();
			displayMsg.showResult("This is a test Msg for Search brand", this.getClass().getName(), null, brandName);
		}
	}

	public String getResult() {
		// TODO Auto-generated method stub
		return brandName;
	}

	public void displayResult() {
		// TODO Auto-generated method stub
		
	}

}
