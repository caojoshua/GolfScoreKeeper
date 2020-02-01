/************************************************
*Joshua Cao									    *
*8/22/13										*
*date class					    				*		
************************************************/

import java.io.*;

public class Date implements Serializable{
	
	private int month;
	private int day;
	private int year;
	
	public void setDate(int month, int day, int year){
		//check for valid months, days, and years
		if(month<0 && month>12){
			System.out.println("invalid month value");
			System.exit(0);
		}
		if(day<0 && day>31 && (month==1 || month==3 || month==5 || month==7 || month==8 || month==10 || month==12)){
			System.out.println("invalid day value");
			System.exit(1);
		}
		else if(day<0 && day>31 && (month==4 || month==6 || month==9 || month==11)){
			System.out.println("invalid day value");
			System.exit(2);
		}
		else if(day<0 && day>28 && month==2){
			System.out.println("invalid day value");
			System.exit(3);
		}
		if(year<0 && year>2013){
			System.out.println("invalid year value");
			System.exit(4);
		}
		this.month=month;
		this.day=day;
		this.year=year;
	}
	
	public String getDate(){
		return month + "/" + day + "/" + year;
	}

}