package UDSoft.UDdataRobot;

import java.util.ArrayList;
import java.util.Map;

public interface UDDataCollector {

	void setName(String name);
	void setCategory(String category);
	void setImage(String ImageName);
	void setSizes(ArrayList<String> sizeAvailable);
	void setArticleNumber(String articleNumber);
	void setCodes(Map<String,String> dic);
	void setBrand(String brandName);
}
