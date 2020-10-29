package de.azizothman.navigator.ws;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;

import de.azizothman.navigator.entities.Journey;
import de.azizothman.navigator.utils.StaticStrings;

public class JourneysAPI {
	
	

	public static ArrayList<Journey> getJourneys(String fromId , String toId ) throws UnsupportedEncodingException, IOException {
		ArrayList<Journey> journeys = new ArrayList<>();
		ConnectionRequest request = new ConnectionRequest();
		request.setPost(false);
		request.setUrl(StaticStrings.JOURNEYS);
		request.addArgument("from", fromId);
		request.addArgument("to", toId);
		NetworkManager.getInstance().addToQueueAndWait(request);
		JSONParser parser = new JSONParser();
		Map<String, Object> result = parser
				.parseJSON(new InputStreamReader(new ByteArrayInputStream(request.getResponseData()), "UTF-8"));
		List<Map<String, Object>> journeysList = (List<Map<String, Object>>) result.get("journeys");

		for(Map<String, Object> journeyMap : journeysList) {
			List<Map<String, Object>> Jlist = (List<Map<String, Object>>) journeyMap.get("legs");
			Map<String,Object> journeyDetails = Jlist.get(0);
			Journey journey = new Journey();
			journey.setFromID(fromId);
			journey.setToID(toId);
			journey.setDeparture(journeyDetails.get("plannedDeparture").toString());
			journey.setArrival(journeyDetails.get("plannedArrival").toString());
			if(journeyDetails.containsKey("departurePlatform")) {
				journey.setDeparturePlatform(journeyDetails.get("departurePlatform").toString());

			}
			Map<String,Object> lineDetails = (Map<String,Object>)journeyDetails.get("line");
			
			journey.setLine(lineDetails.get("name").toString());
			journeys.add(journey);
		}
		

		return journeys;
	}
	

}
