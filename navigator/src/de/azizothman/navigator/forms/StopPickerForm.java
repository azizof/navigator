package de.azizothman.navigator.forms;

import java.io.IOException;
import java.util.ArrayList;

import com.codename1.io.Storage;
import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.diamonddevgroup.utils.Helper;

import de.azizothman.navigator.entities.Stop;
import de.azizothman.navigator.utils.Colors;
import de.azizothman.navigator.ws.StopsAPI;

public class StopPickerForm extends Form {
	
	public static enum ButtomEnum{
		FROM("Von") , TO("Nach");


		private String value;
		 
		ButtomEnum(String value) {
	        this.value = value;
	    }
	 
	    public String getStringValue() {
	        return value;
	    }
	}
	
	public StopPickerForm(StopPickerForm.ButtomEnum type) {
		super(type.getStringValue(), BoxLayout.y());
		initGUI(type.getStringValue(), this.getToolbar());
	}

	private void initGUI(String type, Toolbar toolbar) {
		
		this.setScrollableY(true);
		toolbar.setUIID("ToolbarBlue");
		new Helper(toolbar.getTitleComponent()).textColor(Colors.LITE_GREY_COLOR).textCenter();
		toolbar.setBackCommand("zurück", l ->new MainForm(Resources.getGlobalResources()).show());

		TextField stopName = new TextField();
		stopName.addActionListener((vf) -> {
			if (stopName.getText().length() >= 3) {
				try {
					ArrayList<Stop> stops = StopsAPI.getStopsByName(stopName.getText());
					for(Stop stop : stops) {
						Button stopButton = new Button();
						stopButton.setText(stop.getName());
						stopButton.addActionListener(l->{
							Storage.getInstance().writeObject(type, stop.getId());
							new MainForm(Resources.getGlobalResources()).show();
						});
						new Helper(stopButton).bgColor(0x36444f).textColor(Colors.LITE_GREY_COLOR).textLeft();
						this.add(stopButton);
						this.revalidate();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});
		
		
		this.add(stopName);
	}
	
	
	
}