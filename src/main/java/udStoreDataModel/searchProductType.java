package udStoreDataModel;

import org.jsoup.nodes.Document;

import udDataRobotHelper.UDConsoleDisplay;

public class searchProductType implements SearchDetails {

	private UDConsoleDisplay display = new UDConsoleDisplay();
	private String productType = SearchDetails.productType;
	private boolean testMode;

	
	public searchProductType(Document doc, boolean testMode) {
		this.testMode = testMode;
		productType = typeSearchingLogic(doc);
		if(this.productType.contains(SearchDetails.productType)){
			System.err.println("The product type could not be found");
			try {
				//This will make the error display not passed by easily or unnoticed 
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private String typeSearchingLogic(Document doc) {
		return productType;
		// TODO Auto-generated method stub
		
	}

	public String getResult() {
		// TODO Auto-generated method stub
		return productType;
	}

	public void displayResult() {
		String titleDisplay = "This is displayProductName Function which helps to Display the product type : USED FOR DEVELOPMENT Stage.";
		display.showResult(titleDisplay, this.getClass().getName(),null,getResult());
		
	}

}
