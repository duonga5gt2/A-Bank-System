import java.util.Random;

public class IDGenerator {
	 
	    private static final long MIN_ID = 1_000_000_000L; // Minimum ID value (adjust as needed)
	    private static final long MAX_ID = 9_999_999_999L; // Maximum ID value (adjust as needed)
	    private static final Random random = new Random();
	    private Backend backend = new Backend();

	    public long generateID() throws Exception {
	    	
	    	
	        long generatedID = MIN_ID + (long) (random.nextDouble() * (MAX_ID - MIN_ID));
	        if (backend.isIdUsed(generatedID)) {
	        	generateID();
	        }
	        return generatedID;
	    }
	    
	    
	    
}
