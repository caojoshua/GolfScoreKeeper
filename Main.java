import javax.swing.*;
import java.io.*;

public class Main{

	public static void main(String[]args){
		//check if file is new, if not create it
		File file = new File("file.dat");
		if(!file.exists()){
			try{
				file.createNewFile();
			}
			catch(IOException e){
				System.out.println("I/O exception occured while trying to create new file");
			}
		}
	
		//create frame
		JFrame frame = new MainFrame();
		frame.setVisible(true);
	}

}