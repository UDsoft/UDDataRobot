package udDataRobotHelper;

import java.net.UnknownHostException;
import java.util.LinkedHashSet;

import org.bson.Document;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class UDStoreDatabaseManager {
	
	static MongoCollection<Document> collection;
	private static MongoClient mongoClient;
	
	public UDStoreDatabaseManager() throws UnknownHostException {
		connect();
	}
	
	public void connect() throws UnknownHostException{
		mongoClient = new MongoClient("localHost",1990);
		MongoDatabase database = mongoClient.getDatabase("Retail");
		
		collection = database.getCollection("test");
		
		

	}
	
	
	public void addData(String nameProduct,String typeProduct,String brandName, String picLoc, LinkedHashSet<String> tag){
		Document doc = new Document("name",nameProduct).append("type", typeProduct).append("brand", brandName).append("pic", picLoc);
		collection.insertOne(doc);
	}
	

}
