package udDataRobotHelper;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class SaveImage {
	
	private String pic_url;
	private String pic_name;
	private String path;
	
	
	public SaveImage(String pic_url, String path) throws IOException {
		super();
		this.pic_url = pic_url;
		this.path = path;
		downloadImage();
	}
	
	private void  downloadImage() throws IOException{
		URL url = new URL(pic_url);
		InputStream in = url.openStream();
		pic_name = getName();
		
		if(!path.isEmpty()){
	    	OutputStream out = new BufferedOutputStream(new FileOutputStream(path + pic_name));
	    	
	    	for(int b;(b = in.read())!= -1;){
	    		out.write(b);
	    	}
	    	out.close();
	    	in.close();
	    	}else{
	    		System.out.print("FilePath is null");
	    		System.out.print("Saving Data failed");
	    		in.close();
	    	}
		
	}

	private String getName() {
		return null;
	}
	

}
