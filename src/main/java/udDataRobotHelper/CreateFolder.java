package udDataRobotHelper;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CreateFolder {
	
	private static Path path;
	
	/**
	 * The Constructor for this class
	 * @param fileName Filename is the last folder name which holds the data.
	 * @param pathName useful if there is in depth saving protocol
	 */
	public CreateFolder(String fileName,String pathName) {
		super();
		path = Paths.get(pathName +"/"+fileName);
	}

	/**
	 * This function should be called to create the Directory in the following filePath
	 * 
	 * @return True or False based to indicated if the Process was successful. 
	 * True is also return if the following path exist before 
	 */
	public boolean createDirectory(){
		
		if(Files.exists(path)){
			System.err.println("The FilePath Exist in the directory Path");
			return true;
		}else{
			try {
				Files.createDirectories(path);
				System.err.println("The FilePath is newly created");
				return true;
				
			} catch (Exception e) {
				System.err.println("There is AN Error to create the directory on the path" + path.toString());
				return false;
			}
		}
		
			
	}
	
	
	/**
	 * This function return the path which is been created in String
	 * @return String PathName
	 */
	public String getDirectoryPath(){
		return path.toString();
	}
	
	

}
