package udProject;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.nodes.Document;

import udDataRobotHelper.BoxParse;
import udDataRobotHelper.UDConnect;
import udStoreDataModel.Product;
import udStoreDataModel.SearchProductBrand;
import udStoreDataModel.SearchProductName;
import udStoreDataModel.searchProductPicture;


public class ShamFoodWebsite {
	//data collected from the home page -- Groceries , Herb ,curries,...
	private ArrayList<String> containerDataHomePage;
	
	private ArrayList<String> containerCategory;
	private ArrayList<String> containerType;
	private ArrayList<String> containerBrand;
	private ArrayList<String> containerProducts;
	
	private boolean testMode = true;
	
	private final String MAINURL = "http://www.spicesofindia.co.uk/";
	//adding MAINURL with SUB_CATEGORY AND other container
	//saved Url will give access to other useful pages.
	private final String SUB_CATEGORY = "acategory/";
	//adding MAINURL and HOMEINDEXURL is the homepage
	private final String HOMEINDEXURL = "index.html";

	public ShamFoodWebsite(String USERAGENT,int timeOut) throws IOException, InterruptedException {
		UDConnect connect = new UDConnect(MAINURL+HOMEINDEXURL, USERAGENT, timeOut);
		containerDataHomePage = CollectDatahomePage(connect.getDocument());
		
		//Testing Purpose
		
		//Access second page and collect data
		for(String urlSeconPage : containerDataHomePage){
			UDConnect connectCategory = new UDConnect(MAINURL+SUB_CATEGORY+urlSeconPage, USERAGENT, timeOut);
			containerCategory.addAll(CollectDataCategory(connectCategory.getDocument()));
		}
		
		for(String categoryURL : containerCategory){
			//check if the brand url exist and carry out with brand search
			String[] tempCategoryURL = categoryURL.split("-");
			
			for(String checkURL : tempCategoryURL){
				Product newProduct = new Product();
				if(checkURL.contains("Brand.html")){
					UDConnect connectBrand = new UDConnect(MAINURL+SUB_CATEGORY+checkURL, USERAGENT, timeOut);
					BoxParse boxBrand = new BoxParse(connectBrand.getDocument(), MAINURL+SUB_CATEGORY);
					containerBrand.addAll(boxBrand.resultURL());
					
					for(String brandURl : containerBrand){
						UDConnect connectEachBrand = new UDConnect(MAINURL+SUB_CATEGORY+brandURl, USERAGENT, timeOut);
						SearchProductBrand productBrand = new SearchProductBrand(connectBrand.getDocument(), testMode, true);
						newProduct.setBrand(productBrand.getResult());
						BoxParse boxBrandedProduct = new BoxParse(connectBrand.getDocument(), MAINURL+SUB_CATEGORY);
						containerProducts.addAll(boxBrandedProduct.resultURL());
						
						for(String productURl : containerProducts){
							UDConnect connectProductOfBrand = new UDConnect(MAINURL+SUB_CATEGORY+productURl, USERAGENT, timeOut);
							SearchProductName productName = new SearchProductName(connectProductOfBrand.getDocument(), testMode);
							newProduct.setName(productName.getResult());
							searchProductPicture productPic = new searchProductPicture(connectEachBrand.getDocument(), testMode);
							newProduct.setBigPicName(productPic.getBigPic());
							newProduct.setSmallPicName(productPic.getSmallPic());
							//How to set tag for the product
							
							//This is where im stoping now 
							newProduct.setTag(tag);
						}
						
						
					}
					containerBrand.clear();
				}else{
					
				}
			}
			
		}
	}
	
	public ArrayList<String> CollectDatahomePage(Document doc) throws IOException, InterruptedException{

		BoxParse boxParse = new BoxParse(doc,(MAINURL+SUB_CATEGORY));
		return ExceptionalRules(boxParse.resultURL());
		
	}
	
	//Function which act on the second page
	public ArrayList<String> CollectDataCategory(Document doc) throws IOException, InterruptedException{
		BoxParse boxParse = new BoxParse(doc, MAINURL + SUB_CATEGORY);
		return ExceptionalRules(boxParse.resultURL());
	}
	
	private ArrayList<String> ExceptionalRules(ArrayList<String> urls){
		ArrayList<String> tempContainer = new ArrayList<String>();
		for(String url : urls){
			//Check if the url is books and gifts.. it is not useful
			if(!url.contains("Books-Gifts-Festivals.html")){
				String[] tempSplit= url.split("-");
				for(String tempString : tempSplit ){
					//check if the url contains offers.. which is also not required
					if(!tempString.contains("Offers")||!tempString.contains("Offers.html")){
						tempContainer.add(url);
					}
				}
			}
		}
		
		return tempContainer;
	}

	
}
