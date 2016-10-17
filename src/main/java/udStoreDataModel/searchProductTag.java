package udStoreDataModel;

import java.util.LinkedHashSet;

import org.jsoup.nodes.Document;

public class searchProductTag{
	
	private LinkedHashSet<String> tags = null;
	

	public searchProductTag(Document doc,boolean testMode) {
		
		tags = tagSearchingLogic(doc);
		
	}
	
	public LinkedHashSet<String> tagSearchingLogic(Document doc){
		return null;
	}
	
	public LinkedHashSet<String> getResult() {
		// TODO Auto-generated method stub
		return null;
	}

	public void displayResult() {
		// TODO Auto-generated method stub
		
	}



}
