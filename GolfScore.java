//class for score
import java.io.*;

public class GolfScore implements Serializable{

	private int[] scores = new int[18];
	private GolfCourse golfCourse = new GolfCourse();
	private Date date = new Date();
	int totalScore;
	
	//set methods
	public void setScore(int index, int score){
		scores[index] = score;
	}
	public void setPar(int index, int parValue){
		golfCourse.setPar(index, parValue);
	}
	public void setDate(Date date){
		this.date = date;
	}
	public void setCourse(GolfCourse golfCourse){
		this.golfCourse = golfCourse;
	}
	
	//get methods
	public int getScore(int index){
		return scores[index];
	}
	public int getPar(int index){
		return golfCourse.getPar(index);
	}
	public GolfCourse getGolfCourse(){
		return golfCourse;
	}
	public String getDateString(){
		return date.getDate();
	}
	public Date getDateObject(){
		return date;
	}
	public int getTotalScore(){
		totalScore = 0;
		for(int i = 0; i < scores.length; i++){
			totalScore += scores[i];
		}	
		return totalScore;
	}

}