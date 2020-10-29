package de.azizothman.navigator.forms;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import com.codename1.io.Storage;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.diamonddevgroup.utils.Helper;

import de.azizothman.navigator.entities.JourneyData;
import de.azizothman.navigator.forms.StopPickerForm.ButtomEnum;
import de.azizothman.navigator.utils.Colors;
import de.azizothman.navigator.ws.StopsAPI;

public class MainForm extends Form {


	private Button fromButton;
	private Button toButton;

	private Button setDayButton;
	private Button setTimeButton;

	private Button setLocationToMyLocation;
	private Button changeFromTo;
	private Button searchButton;

	public MainForm(Resources theme) {
		super("Suchen... ",new BorderLayout());
		initGUI(theme);
	}

	private void initGUI(Resources theme) {

		//  the Toolbar in the Main Form
		this.getToolbar().setUIID("ToolbarBlue");
		new Helper(this.getToolbar().getTitleComponent()).textColor(Colors.LITE_GREY_COLOR).textCenter();
		//this.getToolbar().setVisible(false);

		// set the Background Color
		new Helper(this).bgColor(Colors.MAIN_COLOR);

		// Add the TextFields and Buttons
		Container toolsConatiner = new Container(BoxLayout.y());
		
		//Initialize the Buttons
		fromButton = new Button();
		// Check saved From
		if(Storage.getInstance().exists(ButtomEnum.FROM.getStringValue())) {
			try {
				fromButton.setText(StopsAPI.getStopByID(Integer.parseInt((String)Storage.getInstance().readObject(ButtomEnum.FROM.getStringValue()))).getName());
			} catch (IOException e) {
			}
		}else {
			fromButton.setText("Von...");
		}
		fromButton.addActionListener(l-> new StopPickerForm(ButtomEnum.FROM).show());
		
		toButton = new Button();
		
		// Check saved To
		if(Storage.getInstance().exists(ButtomEnum.TO.getStringValue())) {
			try {
				toButton.setText(StopsAPI.getStopByID(Integer.parseInt((String)Storage.getInstance().readObject(ButtomEnum.TO.getStringValue()))).getName());
			} catch (IOException e) {
			}
		}else {
			toButton.setText("Nach...");
		}
		toButton.addActionListener(l-> new StopPickerForm(ButtomEnum.TO).show());
		
		setLocationToMyLocation = new Button(FontImage.MATERIAL_GPS_FIXED);
		changeFromTo = new Button(FontImage.MATERIAL_IMPORT_EXPORT);
		
		Container fromLocContainer = BorderLayout.center(fromButton).add(BorderLayout.EAST,setLocationToMyLocation);
		Container toChangeContainer = BorderLayout.center(toButton).add(BorderLayout.EAST,changeFromTo);

		// the Day Button
		setDayButton = new Button(getDateAsString(new Date()));
		setDayButton.addActionListener(l->{
			Picker datePicker = new Picker();
			datePicker.setType(Display.PICKER_TYPE_DATE);
			
			setDayButton.setText(getDateAsString(datePicker.getDate()));
		});

		// the Time Button
		
		setTimeButton = new Button(getTimeAsString(0));
		setTimeButton.addActionListener(l->{
			Picker timePicker = new Picker();
			timePicker.setType(Display.PICKER_TYPE_TIME);
			
			setTimeButton.setText(getTimeAsString(timePicker.getTime()));
		});

		//Container dateTimeContainer = BorderLayout.center(setDayButton);

		// Search Button
		searchButton = new Button("Suchen");
		searchButton.addActionListener(l -> {
			try {
					JourneyData jd = new JourneyData();
					jd.setFromId((String) Storage.getInstance().readObject(ButtomEnum.FROM.getStringValue()));
					jd.setToId((String) Storage.getInstance().readObject(ButtomEnum.TO.getStringValue()));
					jd.setDate(setDayButton.getText());
					jd.setTime(setTimeButton.getText());
					
					new ShowJourneysForm(jd).show();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		new Helper(fromLocContainer,toChangeContainer).bgColor(Colors.MAIN_COLOR);
		new Helper(searchButton).bgColor(Colors.LITE_GREY_COLOR).textColor(Colors.MAIN_COLOR).pt3().pb3().ma0();
		new Helper(fromButton,setLocationToMyLocation ,toButton,changeFromTo,setDayButton,setTimeButton).bgColor(0x36444f).textColor(Colors.LITE_GREY_COLOR).pt3().pb3().textLeft().ma0();
		new Helper(fromLocContainer,toChangeContainer,setDayButton,setTimeButton,searchButton).borderAll(15).mb2();
		toolsConatiner.add(fromLocContainer);
		toolsConatiner.add(toChangeContainer);
		toolsConatiner.add(setDayButton);
		toolsConatiner.add(setTimeButton);
		toolsConatiner.add(searchButton);
		new Helper(toolsConatiner).ma2();
		this.add(BorderLayout.CENTER, toolsConatiner);
	}

	/**
	 * Return the Date as String in German Language
	 * @param date
	 * @return
	 */
	private String getDateAsString(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int day_week = calendar.get(Calendar.DAY_OF_WEEK);
		int month = calendar.get(Calendar.MONTH) + 1;
		String dateString = "";
		switch (day_week) {
		case Calendar.MONDAY:
			dateString = "Montag , " + day + "." + month + ".";
			break;
		case Calendar.TUESDAY:
			dateString = "Dienstag , " + day + "." + month + ".";
			break;
		case Calendar.WEDNESDAY:
			dateString = "Mittwoch , " + day + "." + month + ".";
			break;
		case Calendar.THURSDAY:
			dateString = "Donnerstag , " + day + "." + month + ".";
			break;
		case Calendar.FRIDAY:
			dateString = "Freitag , " + day + "." + month + ".";
			break;
		case Calendar.SATURDAY:
			dateString = "Samstag , " + day + "." + month + ".";
			break;
		case Calendar.SUNDAY:
			dateString = "Sonntag , " + day + "." + month + ".";
			break;

		default:
			break;
		}

		return dateString;
	}
	/**
	 * 
	 * @param time
	 * @return
	 */
	private String getTimeAsString(int time) {
		int hours = 0;
		int minutes = 0;
		if(time == 0) {
			Calendar cal = Calendar.getInstance();
			hours = cal.get(Calendar.HOUR_OF_DAY);
			minutes = cal.get(Calendar.MINUTE);
		}else {
			hours = time/60;
			minutes = time%60;
		}
		if(minutes< 10) {
			return hours +":0" + minutes + " Uhr";
		}
		else {
			return hours +":" + minutes + " Uhr";

		}
	}

}
