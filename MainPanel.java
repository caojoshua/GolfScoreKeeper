//main panel

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class MainPanel extends JPanel implements ActionListener{

	//swing componenets
	private JComboBox selectScore, selectCourse;
	private JLabel  holeLabel, parLabel, scoreLabel;
	private JButton addScore, editScore, addCourse, editCourse, accept, cancel, delete, exit;
	private JPanel comboBoxPanel, tablePanel, buttonPanelOne, buttonPanelTwo;
	
	//the labels for info in the table
	private ArrayList< JLabel > holeLabels = new ArrayList< JLabel >();
	private ArrayList< JTextField > parLabels = new ArrayList< JTextField >();
	private ArrayList< JTextField > scoreLabels = new ArrayList< JTextField >();
	
	//array list for the saved scores and courses, along with the object reader/writer
	private ArrayList< GolfScore > scoreList;
	private ArrayList< GolfCourse > courseList;
	ObjectProcessor processor = new ObjectProcessor();
	
	private boolean isAdd; //find out if adding or editing
	private boolean isScore; //find out if adding score or course
	
	private int currentSelection; //find the currently selected score
	int currentState = 0;		//1 = add score, 2 = edit score, 3 = add course, 4 = edit course
	int selectedIndex = 0;		//the current selected item
	String courseName = null;	//string to hold course name
	boolean emptyFields = true;		//conditional if there are empty fields
	
	//objects for score, course, and date
	private GolfScore s = new GolfScore();
	private GolfCourse c = new GolfCourse();
	private Date d = new Date();
	private Object[] objectList = new Object[2];
	
	@SuppressWarnings("unchecked")
	public MainPanel(){
	
		//read the saved scores and courses
		try{
			objectList = processor.readRecord();
			scoreList = (ArrayList< GolfScore >)objectList[0];
			courseList = (ArrayList< GolfCourse >)objectList[1];
		}
		catch(EOFException e){
			scoreList = new ArrayList< GolfScore >();
			courseList = new ArrayList< GolfCourse >();
		}
		catch(IOException e){
			System.out.println("I/O exception occured");
		}
		catch(ClassNotFoundException e){
			System.out.println("class not found exception occured");
		}
		
		//set the values of the jcomboboxes
		selectScore = new JComboBox();
		selectCourse = new JComboBox();
		selectCourse.setEnabled(false);
		for(int i = 0; i < scoreList.size(); i++){
			s = scoreList.get(i);
			selectScore.addItem(s.getDateString() + ": " + s.getTotalScore());
		}
		for(int i = 0; i < courseList.size(); i++){
			c = courseList.get(i);
			selectCourse.addItem(c.getName());
		}
		
		//labels for table
		for(int i = 0; i < 18; i++){
			holeLabels.add(i, new JLabel(Integer.toString(i + 1)));
		}
		for(int i = 0; i < 18; i++){
			parLabels.add(i, new JTextField());
		}
		for(int i = 0; i < 18; i++){
			scoreLabels.add(i, new JTextField());
		}
		
		//labels 
		holeLabel = new JLabel("hole");
		parLabel = new JLabel("par");
		scoreLabel = new JLabel("score");
		
		//buttons
		addScore = new JButton("add score");
		editScore = new JButton("edit score");
		addCourse = new JButton("add course");
		editCourse = new JButton("edit course");
		delete = new JButton("delete");
		accept = new JButton("accept");
		cancel = new JButton("cancel");
		exit = new JButton("exit");
		accept.setEnabled(false);
		cancel.setEnabled(false);
		
		//panels
		comboBoxPanel = new JPanel();
		tablePanel = new JPanel();
		buttonPanelOne = new JPanel();
		buttonPanelTwo = new JPanel();
		
		//add action listeners
		selectScore.addActionListener(this);
		selectCourse.addActionListener(this);
		addScore.addActionListener(this);
		editScore.addActionListener(this);
		addCourse.addActionListener(this);
		editCourse.addActionListener(this);
		accept.addActionListener(this);
		cancel.addActionListener(this);
		delete.addActionListener(this);
		exit.addActionListener(this);
		
		//disabled cancel and accept
		cancel.setEnabled(false);
		accept.setEnabled(false);
		
		//add combobox to a panel next to each other
		comboBoxPanel.add(selectScore);
		comboBoxPanel.add(selectCourse);
		
		//disable fields under certain circumstances
		if(courseList.size() == 0){
			addScore.setEnabled(false);
			editScore.setEnabled(false);
			editCourse.setEnabled(false);
		}
		if(scoreList.size() == 0){
			editScore.setEnabled(false);
			editCourse.setEnabled(false);
		}
		
		//add information to the table
		tablePanel.setLayout(new GridLayout(3, 19));
		tablePanel.add(holeLabel);
		for(int i = 0; i < holeLabels.size(); i++){
			holeLabels.get(i).setText(Integer.toString(i + 1));
			tablePanel.add(holeLabels.get(i));
		}
		tablePanel.add(parLabel);
		for(int i = 0; i < 18; i++){
			 tablePanel.add(parLabels.get(i));
			 parLabels.get(i).setEditable(false);
		}
		tablePanel.add(scoreLabel);
		for(int i = 0; i < 18; i++){
			tablePanel.add(scoreLabels.get(i));
			scoreLabels.get(i).setEditable(false);
		}
		
		//add information to the two button layouts, each layout is a row
		buttonPanelOne.add(addScore);
		buttonPanelOne.add(editScore);
		buttonPanelOne.add(addCourse);
		buttonPanelOne.add(editCourse);
		
		buttonPanelTwo.add(accept);
		buttonPanelTwo.add(cancel);
		buttonPanelTwo.add(delete);
		buttonPanelTwo.add(exit);
		
		//add panels to the main panel in a vertical box layout
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(comboBoxPanel);
		this.add(tablePanel);
		this.add(buttonPanelOne);
		this.add(buttonPanelTwo);
		
		//set selected items of the jcomboboxes
		if(selectScore.getItemCount() > 0){
			selectScore.setSelectedIndex(0);
		}
	}

	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e){
	
		Object source = e.getSource();
		s = new GolfScore();
		c = new GolfCourse();
		
		//when add score is pressed
		if(source == addScore){
			currentState = 0;
			isScore = true;
			selectedIndex = selectScore.getSelectedIndex();
			enableFields(isScore);
			d.setDate(Integer.parseInt(JOptionPane.showInputDialog(this, "what is the month(number of month)")),
						Integer.parseInt(JOptionPane.showInputDialog(this, "what is the day")),
						Integer.parseInt(JOptionPane.showInputDialog(this, "what is the year")));
			for(int i = 0; i < scoreLabels.size(); i++){
				scoreLabels.get(i).setText("");
			}
		}
		
		//edit score
		if(source == editScore){
			currentState = 1;
			isScore = true;
			selectedIndex = selectScore.getSelectedIndex();
			enableFields(isScore);
			d = scoreList.get(selectedIndex).getDateObject();
		}
		
		//add course
		if(source == addCourse){
			currentState = 2;
			isScore = false;
			selectedIndex = selectScore.getSelectedIndex();
			enableFields(isScore);
			courseName = JOptionPane.showInputDialog(this, "what is the name of the course");
			for(int i = 0; i < parLabels.size(); i++){
				parLabels.get(i).setText("");
			}
		}
		
		//edit course
		if(source == editCourse){
			currentState = 3;
			isScore = false;
			selectedIndex = selectScore.getSelectedIndex();
			enableFields(isScore);
		}
		
		//accept, conditional statements to check what action is being performed
		if(source == accept){
			if(!emptyScoreFields()){
				disableFields();
				//accepting add score
				if(currentState == 0){
					for(int i = 0; i < scoreLabels.size(); i++){
						s.setScore(i, Integer.parseInt(scoreLabels.get(i).getText()));
					}
					s.setDate(d);
					s.setCourse((GolfCourse)courseList.get(selectCourse.getSelectedIndex()));
					scoreList.add(s);
					selectScore.addItem(s.getDateString() + ": " + s.getTotalScore());
					write(scoreList, courseList);
					selectScore.setSelectedIndex(selectScore.getItemCount() - 1 );
					disableFields();			//necessary, score only added at this point, score size needs to be greater than zero to enable edit score
				}
			}
			//edit score
			else if(currentState == 1){
				if(!emptyScoreFields()){
					for(int i = 0; i < scoreLabels.size(); i++){
						s.setScore(i, Integer.parseInt(scoreLabels.get(i).getText()));
					}
					s.setDate(d);
					s.setCourse((GolfCourse)courseList.get(selectCourse.getSelectedIndex()));
					scoreList.remove(selectedIndex);
					scoreList.add(s);
					selectScore.addItem(s.getDateString() + ": " + s.getTotalScore());
					selectScore.removeItemAt(selectedIndex);
					write(scoreList, courseList);
					selectScore.setSelectedIndex(selectScore.getItemCount() - 1 );
				}
			}
			//add course
			else if(currentState == 2){
				if(!emptyCourseFields()){
					for(int i = 0; i < parLabels.size(); i++){
						c.setPar(i, Integer.parseInt(parLabels.get(i).getText()));
					}
					c.setName(courseName);
					courseList.add(c);
					selectCourse.addItem(c.getName());
					write(scoreList, courseList);
				}
			}
			//edit course
			else if(currentState == 3){
				if(!emptyCourseFields()){
					for(int i = 0; i < parLabels.size(); i++){
						c.setPar(i, Integer.parseInt(parLabels.get(i).getText()));
					}
					courseList.remove(selectCourse.getSelectedIndex());
					courseList.add(c);
					selectScore.addItem(c.getName());
					selectScore.removeItem(selectedIndex);
					write(scoreList, courseList);
				}
			}
		}
		
		//cancel
		if(source == cancel){
			disableFields();
			selectScore.setSelectedItem(0);
		}
		
		//delete
		if(source == delete){
			selectedIndex = selectScore.getSelectedIndex();
			scoreList.remove(selectedIndex);
			selectScore.removeItemAt(selectedIndex);
			write(scoreList, courseList);
		}
		
		//exit
		if(source == exit){
			System.exit(0);
		}
		
		//change the labels to the appropriate values based on the score selected
		if(source == selectScore){
			selectedIndex = selectScore.getSelectedIndex();
			if(selectedIndex > -1){
				s = scoreList.get(selectedIndex);
			}
			for(int i = 0; i < scoreLabels.size(); i++){
				scoreLabels.get(i).setText(Integer.toString(s.getScore(i)));
			}
			for(int i = 0; i < selectCourse.getItemCount(); i++){
				if(selectCourse.getItemAt(i).equals(s.getGolfCourse().getName())){
					selectCourse.setSelectedIndex(i);
				}
			}
		}
		
		//change teh par labels to the appropriate values based on teh course selected
		if(source == selectCourse){
			c = (GolfCourse)courseList.get(selectCourse.getSelectedIndex());
			for(int i = 0; i < parLabels.size(); i++){
				parLabels.get(i).setText(Integer.toString(c.getPar(i)));
			}
		}
	
	}
	
	//enable fields, used when adding or editing
	private void enableFields(boolean isScore){
		if( isScore ){
			for(int i = 0; i < scoreLabels.size(); i++){
				scoreLabels.get(i).setEditable(true);
			}
		}
		else{
			for(int i = 0; i < parLabels.size(); i++){
				parLabels.get(i).setEditable(true);
			}
		}
		accept.setEnabled(true);
		cancel.setEnabled(true);
		addScore.setEnabled(false);
		editScore.setEnabled(false);
		addCourse.setEnabled(false);
		editCourse.setEnabled(false);
		selectScore.setEnabled(false);
		selectCourse.setEnabled(true);
	}
	//diable fields, used when accepting or deleting
	private void disableFields(){
		for(int i = 0; i < scoreLabels.size(); i++){
				scoreLabels.get(i).setEditable(false);
		}
		for(int i = 0; i < parLabels.size(); i++){
				parLabels.get(i).setEditable(false);
		}
		accept.setEnabled(false);
		cancel.setEnabled(false);
		if(courseList.size() > 0);
			addScore.setEnabled(true);
			editCourse.setEnabled(true);
		if(scoreList.size() > 0){
			editScore.setEnabled(true);
		}
		addCourse.setEnabled(true);
		selectScore.setEnabled(true);
		selectCourse.setEnabled(false);	
	}
	//write the objects to binary file
	private void write(ArrayList< GolfScore > golfScore, ArrayList< GolfCourse > golfCourse){
		try{
			processor.writeRecord(golfScore, golfCourse);
		}
		catch(IOException e){
			System.out.println("I/O exception occured");
			System.out.println(e);
		}
	}
	//check for empty score values when adding or editing score
	private boolean emptyScoreFields(){
		boolean isEmpty = false;
		for(int i = 0; i < scoreLabels.size(); i++){
			if(scoreLabels.get(i).getText().equals("")){
				isEmpty = true;
			}
		}
		if(isEmpty){
			JOptionPane.showMessageDialog(this, "a score value was left blank", "invalid value", JOptionPane.ERROR_MESSAGE);
			return isEmpty;
		}
		else
			return isEmpty;
	}
	//check for empty par values when adding or editing course
	private boolean emptyCourseFields(){
		boolean isEmpty = false;
		for(int i = 0; i < parLabels.size(); i++){
			if(parLabels.get(i).getText().equals("")){
				isEmpty = false;
			}
		}
		if(isEmpty){
			JOptionPane.showMessageDialog(this, "a par value was left blank", "invalid value", JOptionPane.ERROR_MESSAGE);
			return isEmpty;
		}
		else
			return isEmpty;
	}
}