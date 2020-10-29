package de.azizothman.navigator;

import static com.codename1.ui.CN.addNetworkErrorListener;
import static com.codename1.ui.CN.getCurrentForm;
import static com.codename1.ui.CN.updateNetworkThreadCount;

import com.codename1.io.Log;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;

import de.azizothman.navigator.forms.MainForm;

/**
 * Author Aziz
 */
public class Navigator {

	private Form current;
	private Resources theme;

	public void init(Object context) {
		// use two network threads instead of one
		updateNetworkThreadCount(2);

		theme = UIManager.initFirstTheme("/theme");

		// Enable Toolbar on all Forms by default
		Toolbar.setGlobalToolbar(true);

		// Pro only feature, uncomment if you have a pro subscription
		Log.bindCrashProtection(true);

		addNetworkErrorListener(err -> {
			// prevent the event from propagating
			err.consume();
			if (err.getError() != null) {
				Log.e(err.getError());
			}
			Log.sendLogAsync();
			Dialog.show("Connection Error",
					"There was a networking error in the connection to " + err.getConnectionRequest().getUrl(), "OK",
					null);
		});
	}

	public void start() {
		if (current != null) {
			current.show();
			return;
		}
		new MainForm(theme).show();

	}

	public void stop() {
		current = getCurrentForm();
		if (current instanceof Dialog) {
			((Dialog) current).dispose();
			current = getCurrentForm();
		}
	}

	public void destroy() {
	}

}
