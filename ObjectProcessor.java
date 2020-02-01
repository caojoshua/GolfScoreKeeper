import java.io.*;
import java.util.*;

public class ObjectProcessor{

	//store information on "file.dat"
	private String binaryFile = "file.dat";
	
	//read record
	@SuppressWarnings("unchecked")
	public Object[] readRecord() throws IOException, ClassNotFoundException, EOFException{
		
		ObjectInputStream in = new ObjectInputStream(
							   new BufferedInputStream(
							   new FileInputStream(binaryFile)));
		
		//read the object list
		Object[] objectList = new Object[2];
		objectList[0] = (ArrayList< GolfScore >) in.readObject();
		objectList[1] = (ArrayList< GolfCourse >) in.readObject();
		return objectList;
	
	}
		
	//write record
	public void writeRecord(ArrayList< GolfScore > golfScore, ArrayList< GolfCourse > golfCourse) throws IOException{ 

		ObjectOutputStream out = new ObjectOutputStream(
			  new BufferedOutputStream(
			  new FileOutputStream(binaryFile)));
		out.writeObject(golfScore);
		out.writeObject(golfCourse);		
		out.close();
				
	}

}

//possiblties:
//write objects overwrite, golf course overwrites golf score
//when reading, reading the golf course reads the score instead, because it is at beginning of stream - probably teh problem