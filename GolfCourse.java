//class for golf course
import java.io.*;

public class GolfCourse implements Serializable{

	private int[] par = new int[18];
	private String name;
	
	//set methods 
	public void setPar(int index, int parValue){
		par[index] = parValue;
	}
	public void setName(String name){
		this.name = name;
	}
	
	//get methods
	public int getPar(int index){
		return par[index];
	}
	public String getName(){
		return name;
	}

}