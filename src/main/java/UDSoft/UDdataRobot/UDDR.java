package UDSoft.UDdataRobot;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import udDataRobotHelper.BoxParse;
import udDataRobotHelper.HeadingType;
import udDataRobotHelper.UDConnect;
import udDataRobotHelper.UDStoreDatabaseManager;
import udStoreDataModel.Product;

/**
 * Hello world!
 *
 */
public class UDDR {

	private static final String FOLDERPATH = "Pictures_DB/testNew";
	private static final String LINKURL = "http://www.spicesofindia.co.uk/";
	private static final String USERAGENT = "Mozilla";

	public static void main(String[] args) {
		
		//Initial Homepage 
		String url = LINKURL + "index.html";
		UDConnect connect = new UDConnect(url, USERAGENT, 1000);
		Document doc = connect.getDocument();
		System.out.println(doc);
		
		//Collect data
		
		
		
	}
	
	
	
	public static void test1() throws IOException, InterruptedException{
		HeadingType dropheading;
		BoxParse boxparse;
		
		UDStoreDatabaseManager db = new UDStoreDatabaseManager();
		db.connect();

		// UDStoreDatabaseManager dbManager = new UDStoreDatabaseManager();

		// Holds all the important IDs for reference to get more data out
		ArrayList<String> ID_list = new ArrayList<String>();
		// This is the URl of all the main Navigation Menus dropHeadings.
		ArrayList<String> containerOfMainURls = new ArrayList<String>();

		ArrayList<String> containerOfCategory = new ArrayList<String>();
		// Store the url for the brand category
		ArrayList<String> containerOfBrand = new ArrayList<String>();

		ArrayList<String> containerOFType = new ArrayList<String>();
		ArrayList<String> containerOFBrandedProducts = new ArrayList<String>();

		// This is the array of all products
		ArrayList<String> containerOfProductsUnbranded = new ArrayList<String>();

		// making the connection to the url and saving it as the doc
		UDConnect connect_main_page = new UDConnect(LINKURL, USERAGENT, 2000);
		Document document = connect_main_page.getDocument();
		// Comment this out after development.
		// connect_main_page.htmlDisplay();
		// -------------------------------------------------------------

		Elements navigationBar = document.select("div[class = navigation-bar]");
		Elements navigation_menus = document.getElementsByClass("navigation-bar");
		// System.out.println(rowClass);

		// iterate the document from Navigation bar class for all the ids
		// available.
		for (Element i : navigation_menus) {

			Elements ids = i.getElementsByAttribute("id");

			// getting the IDs from class id
			for (Element id : ids) {
				String idString = id.attr("id");
				// check if the idstring from the navigation_menus contains main
				// as a string and
				// and exclude the link0 or link8 which one of the data which is
				// not useful.
				if (idString.contains("main") && !(idString.contains("link0") || idString.contains("link8"))) {
					ID_list.add(idString);
					// System.out.println(ID_list);
				}

			}

		}

		// find URL for the IDs of the navigation bar menus
		for (String id : ID_list) {
			Element urltemp = document.getElementById(id);
			dropheading = new HeadingType(urltemp, "url");
			String url = dropheading.getUrl();
			containerOfMainURls.add(url);
			// System.err.println("The URL FOR ID : "+ id+ "= "+url);
			// System.out.println();
		}
		ID_list.clear();
		// System.out.println("ID_list is clear and current size is " +
		// ID_list.size());

		for (String url_dropHeading : containerOfMainURls) {
			String temp_Url = LINKURL + url_dropHeading;

			UDConnect connect_menu_each_dropHeading = new UDConnect(temp_Url, USERAGENT, 1000);

			Document doc = connect_menu_each_dropHeading.getDocument();

			boxparse = new BoxParse(doc);
			// System.out.println(boxparse.resultURL());
			containerOfCategory.addAll(boxparse.resultURL());
		}
		containerOfMainURls.clear();
		// System.out.println("dropHeading_url_list is clear and current size is
		// " + dropHeading_url_list.size());

		for (String urltemp1 : containerOfCategory) {
			if (!isOfferURL(urltemp1)) {
				String tempURL = LINKURL + "acatalog/" + urltemp1;
				boxparse = new BoxParse(new UDConnect(tempURL, USERAGENT, 1000).getDocument());
				if (isBrandURL(urltemp1)) {
					containerOfBrand.addAll(boxparse.resultURL());
					System.out.println("Brand Array : " + containerOfBrand);
				} else {
					containerOFType.addAll(boxparse.resultURL());
					System.out.println("Type Array :" + containerOFType);
				}

			} else {
				// System.out.println("Offer is Detected");
			}
		}

		for (String url_type : containerOFType) {

			String tempURltype = LINKURL + "acatalog/" + url_type;
			Document document_type = new UDConnect(tempURltype, USERAGENT, 1000).getDocument();
			String productType = titleCorrection(document_type.title());

			System.out.println("type_product : " + productType);
			boxparse = new BoxParse(document_type);
			containerOfProductsUnbranded.addAll(boxparse.resultURL());

			for (String urlProduct : containerOfProductsUnbranded) {

				Product productObject = new Product();
				String tempURL = LINKURL + "acatalog/" + urlProduct;
				Document doc_product = new UDConnect(tempURL, USERAGENT, 1000).getDocument();
				String productName = doc_product.title();
				System.out.println("Downloading Picture " + doc_product.title());

				productObject.setName(productName);
				productObject.setProductType(productType);

				Elements eles = doc_product.getElementsByClass("lbfixp2");

				for (Element ele : eles) {
					String picUrl = getPicOfProducts(ele);
					getImage(LINKURL + "acatalog/" + picUrl, doc_product.title(), FOLDERPATH, productObject);
				
					
				}
				
				productObject.saveData(db);
			}
			containerOfProductsUnbranded.clear();

		}
		
		

		for (String url_Brand : containerOfBrand) {
			Product productObject = new Product();
			String tempURl = LINKURL + "acatalog/" + url_Brand;
			Document document_type = new UDConnect(tempURl, USERAGENT, 1000).getDocument();
			String BrandTitle = titleCorrection(document_type.title());
			System.out.println("Brand: " + BrandTitle);
			productObject.setBrand(BrandTitle);
			boxparse = new BoxParse(document_type);

			containerOFBrandedProducts.addAll(boxparse.resultURL());

			for (String brandURL : containerOFBrandedProducts) {

				String tempURLbrand = LINKURL + "acatalog/" + brandURL;
				Document doc_product = new UDConnect(tempURLbrand, USERAGENT, 1000).getDocument();
				String productName = doc_product.title();

				productObject.setName(productName);

				System.out.println("Downloading Picture " + doc_product.title());

				Elements eles2 = doc_product.getElementsByClass("lbfixp2");

				for (Element ele1 : eles2) {
					String picUrl = getPicOfProducts(ele1);
					getImage(LINKURL + "acatalog/" + picUrl, doc_product.title(), FOLDERPATH, productObject);
				}
			}
			productObject.saveData(db);
			containerOFBrandedProducts.clear();
		}
	}

	private static String titleCorrection(String title) {
		String[] title_split = title.split("\\s+");
		String validTitle = "";
		for (String validChunk : title_split) {

			// insert any condition to end the title search
			if (validChunk.contains("Page")) {
				return validTitle;

			}

			// checks if ValidTitle is empty
			if (validTitle.isEmpty()) {
				validTitle = validTitle + validChunk;
			} else {
				validTitle = validTitle + " " + validChunk;
			}

		}
		validTitle.trim();
		return validTitle;
	}

	public static String getPicOfProducts(Element id) {
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

	private static void getImage(String url, String picname, String filePath, Product productObject)
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

/*	private static boolean pagesExist(Document doc) {
		Elements pages = doc.getElementsByClass("paginationCont");
		if (pages.isEmpty()) {
			return false;
		}
		return true;
	}*/

/*	private static boolean isOfferURL(String URL) {
		System.out.println("isOffer :" + URL);
		String[] tempChuck = URL.split("-");
		for (String isOffer : tempChuck) {
			if (isOffer.equals("Offers") || URL.contains("Offers.html")) {
				System.err.println("Offers url found");
				return true;
			}

		}
		return false;
	}

	private static boolean isBrandURL(String URL) {

		System.out.println("isBrand :" + URL);
		String[] tempChuck = URL.split("-");
		for (String isBrand : tempChuck) {
			if (isBrand.contains("Brand")) {
				System.out.println("Brand URL is found");
				return true;
			}

		}
		return false;

	}*/
	
	
}

