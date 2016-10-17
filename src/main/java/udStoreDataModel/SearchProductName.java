package udStoreDataModel;

import org.jsoup.nodes.Document;

import udDataRobotHelper.UDConsoleDisplay;

/*
 * This Class is used to search for the product name and the logic where it can be found. 
 * For each database this is the place where the whole thing should be changed
 * 
 * The main Framework of UDDR should be touched unless major update is required.
 */

public class SearchProductName implements SearchDetails {

	private UDConsoleDisplay display = new UDConsoleDisplay();
	private String productName = SearchDetails.productName;
	
	public SearchProductName(Document doc, boolean testMode) {
		nameSearchingLogic(doc);
		if(productName.contains(SearchDetails.productName)){
			System.err.println("The product name could not be found");
			try {
				//This will make the error display not passed by easily or unnoticed 
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void nameSearchingLogic(Document doc){
		String temp = doc.title();
		String[] tempSplit = temp.split("-");
		productName = tempSplit[0];
		
	}
	

	public String getResult() {
		return productName;
	}

	public void displayResult() {
		String titleDisplay = "This is displayProductName Function which helps to Display the product name : USED FOR DEVELOPMENT Stage.";
		display.showResult(titleDisplay, this.getClass().getName(),null,getResult());
	}
	
	
}
