package de.azizothman.navigator.forms;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.diamonddevgroup.utils.Helper;

import de.azizothman.navigator.entities.Journey;
import de.azizothman.navigator.entities.JourneyData;
import de.azizothman.navigator.utils.Colors;
import de.azizothman.navigator.ws.JourneysAPI;
import de.azizothman.navigator.ws.StopsAPI;

public class ShowJourneysForm extends Form {

	private ArrayList<Journey> journeys;

	public ShowJourneysForm(JourneyData jd) {
		super("verbinungen", BoxLayout.y());
		try {
			initGUI(jd);
		} catch (NumberFormatException | IOException e) {
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initGUI(JourneyData jd) throws NumberFormatException, UnsupportedEncodingException, IOException, ParseException {

		this.getToolbar().setUIID("ToolbarBlue");
		new Helper(this.getToolbar().getTitleComponent()).textColor(Colors.LITE_GREY_COLOR).textCenter();

		// Details Container
		Container details = new Container(BoxLayout.y());
		Label fromLabel = new Label();
		try {
			fromLabel.setText("Von : " + StopsAPI.getStopByID(Integer.parseInt(jd.getFromId())).getName());
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Label toLabel = new Label();
		try {
			toLabel.setText("Nach : " + StopsAPI.getStopByID(Integer.parseInt(jd.getToId())).getName());
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		new Helper(details).borderAll(19).bgColor(Colors.LITE_GREY_COLOR).ma1();
		new Helper(fromLabel, toLabel).textColor(Colors.MAIN_COLOR).textLeft();
		details.add(fromLabel);
		details.add(toLabel);
		//details.add(FlowLayout.encloseIn(dayLabel, timeLabel));
		// details.add(timeLabel);

		this.add(details);

		try {
			journeys = JourneysAPI.getJourneys(jd.getFromId(), jd.getToId());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (Journey journey : journeys) {

			this.add(getJourneyContainer(journey));
		}

	}

	private Container getJourneyContainer(Journey journey) throws NumberFormatException, UnsupportedEncodingException, IOException, ParseException {
		Container container = new Container(new GridLayout(3));
		Label platform = new Label();

		Label fromLabel = new Label(StopsAPI.getStopByID(Integer.parseInt(journey.getFromID())).getName());
		Label toLabel = new Label(StopsAPI.getStopByID(Integer.parseInt(journey.getToID())).getName());
		
		Label fromTime = new Label(StringToHoursMin(journey.getDeparture()));
		Label ToTime = new Label(StringToHoursMin(journey.getArrival()));
		if(journey.getDeparturePlatform() != null) {
			platform.setText("Gleis " + journey.getDeparturePlatform());
		}
		Label line = new Label(journey.getLine());
		
		new Helper(fromLabel,toLabel,fromTime,ToTime,platform,line).textColor(Colors.MAIN_COLOR).textLeft().pa1();
		
		container.add(fromLabel);
		container.add(fromTime);
		container.add(line);
		container.add(toLabel);
		container.add(ToTime);
		container.add(platform);

		new Helper(container).bgColor(Colors.LITE_GREY_COLOR).pa2().ma1().borderAll(15);
		return container;
	}
	
	private String StringToHoursMin(String date) throws ParseException {
		String fromatDate = date.substring(0, date.indexOf("+"));
		System.out.println(fromatDate);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date hmDate = format.parse(fromatDate);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(hmDate);
		
		int hours = cal.get(Calendar.HOUR_OF_DAY);
		int minutes = cal.get(Calendar.MINUTE);
		

		if(minutes< 10) {
			return hours +":0" + minutes + " Uhr";
		}
		else {
			return hours +":" + minutes + " Uhr";

		}
	}

}
