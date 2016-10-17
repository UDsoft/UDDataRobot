package udDataRobotHelper;

public class UDConsoleDisplay {

	public UDConsoleDisplay() {
		super();
	}
	
	public void showResult(String title, String className, String personaliseMessage,String result){
		int windowLength;
		if(personaliseMessage.isEmpty()){
			personaliseMessage = "Result for this " + className + "is : " ;
		}
		
		int titleLength = title.length();
		int personalMessageLength = personaliseMessage.length();
		if(personalMessageLength > titleLength){
			windowLength = personalMessageLength;
		}else{
			windowLength = titleLength;
		}
		
		String windowBorder = "";
		for(int windowBoarderlength = 0 ; windowBoarderlength < windowLength;windowBoarderlength++){
			windowBorder = windowBorder + "-";
		}
		
		System.out.println(windowBorder);
		System.out.println(title + className);
		System.out.println();
		System.out.println(personaliseMessage + result);
		System.out.println();
		System.out.println(windowBorder);
	}

	
	
	
}
