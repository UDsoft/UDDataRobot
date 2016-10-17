package udStoreDataModel;

public interface SearchDetails {
	String productName = "UDPRODUCT_NAME_ERROR";
	String brandName = "UDPRODUCT_BRAND_ERROR";
	String productType = "UDPRODUCT_TYPE_ERROR";

	
	String getResult();
	void displayResult();
	
}
