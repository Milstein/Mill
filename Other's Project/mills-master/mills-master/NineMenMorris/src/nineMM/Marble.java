package nineMM;

public class Marble {
	
	public enum MarbleType {
		EMPTY, 
		WHITE, 
		BLACK, 
		NaM
	}
	
	protected MarbleType owner; 
	
	protected Marble(MarbleType marble){
		owner = marble;  
	}
	
	protected MarbleType getOwner(){
		return owner; 
	}
}
