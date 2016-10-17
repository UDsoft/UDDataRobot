package udStoreDataModel;

import java.net.UnknownHostException;
import java.util.LinkedHashSet;

import udDataRobotHelper.UDStoreDatabaseManager;

public class Product {

	private String name;
	private String productType;
	private String bigPicName;
	private String smallPicName;
	private String brand;
	private String catogery;
	private LinkedHashSet<String> tags ;
	
	public Product() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getBigPicName() {
		return bigPicName;
	}

	public void setBigPicName(String picName) {
		this.bigPicName = picName;
	}
	
	public String getSmallPicName(){
		return smallPicName;
	}
	
	public void setSmallPicName(String picName){
		this.smallPicName = picName;
	}
	
	public void setBrand(String brand){
		this.brand = brand;
	}
	
	public void  Display() {
		System.out.println("product name : " + name);
		System.out.println("Product Type : " + productType);
		System.out.println("Product Pic : "  +  picName);
		System.out.println("Product Brand : " + brand);
	}
	
	public void saveData(UDStoreDatabaseManager db){

		db.addData(name, productType, brand, picName,tags);
	}
	
	public void setTag(String tag){
		this.tags.add(tag);
	}
	
	public void setCategory(String category){
		this.catogery = category;
	}
	
	
}
