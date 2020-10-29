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

import de.azizothman.navigator.entities.Product;
import de.azizothman.navigator.entities.Stop;
import de.azizothman.navigator.utils.StaticStrings;

public class StopsAPI {

	/**
	 * Get Stop data as Stop Object by entering the id
	 * 
	 * @param id the Id of the Stop
	 * 
	 * @return Stop Object if exist
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public static Stop getStopByID(int id) throws UnsupportedEncodingException, IOException {
		Stop stop = new Stop();

		ConnectionRequest request = new ConnectionRequest();
		request.setPost(false);
		request.setUrl(StaticStrings.STOPS + id);
		NetworkManager.getInstance().addToQueueAndWait(request);
		JSONParser parser = new JSONParser();
		Map<String, Object> result = parser
				.parseJSON(new InputStreamReader(new ByteArrayInputStream(request.getResponseData()), "UTF-8"));
		stop.setId(result.get("id").toString());
		stop.setName(result.get("name").toString());
		Map<String, Object> location = (Map<String, Object>) result.get("location");
		stop.setLatitude(Double.parseDouble(location.get("latitude").toString()));
		stop.setLongitude(Double.parseDouble(location.get("longitude").toString()));

		Map<String, Object> products = (Map<String, Object>) result.get("products");
		if (hasProduct(products, Product.NATIONAL_EXPRESS_ICE)) {
			stop.getProducts().add(Product.NATIONAL_EXPRESS_ICE);
		}
		if (hasProduct(products, Product.NATIONAL_IC_EC)) {
			stop.getProducts().add(Product.NATIONAL_IC_EC);
		}
		if (hasProduct(products, Product.REGIONAL_EXPRESS_RE)) {
			stop.getProducts().add(Product.REGIONAL_EXPRESS_RE);
		}
		if (hasProduct(products, Product.REGIONAL_RB)) {
			stop.getProducts().add(Product.REGIONAL_RB);
		}
		if (hasProduct(products, Product.SUBURBAHN_SBAHN)) {
			stop.getProducts().add(Product.SUBURBAHN_SBAHN);
		}
		if (hasProduct(products, Product.SUBWAY_UBAHN)) {
			stop.getProducts().add(Product.SUBWAY_UBAHN);
		}
		if (hasProduct(products, Product.TRAM)) {
			stop.getProducts().add(Product.TRAM);
		}
		if (hasProduct(products, Product.BUS)) {
			stop.getProducts().add(Product.BUS);
		}
		if (result.get("reisezentrumOpeningHours") != null) {
			Map<String, Object> openTime = (Map<String, Object>) ((Map<String, Object>) result.get("products"))
					.get("row");
			stop.setDBOpenTime(openTime);
		}
		return stop;
	}

	/**
	 * Get Stop data as Stop Object by entering the name of the Stop or the station
	 * 
	 * @param name the name of the Stop or the station
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public static ArrayList<Stop> getStopsByName(String name) throws UnsupportedEncodingException, IOException {
		ArrayList<Stop> stops = new ArrayList<Stop>();
		ConnectionRequest request = new ConnectionRequest();
		request.setPost(false);
		request.setUrl(StaticStrings.LOCATIONS + name);
		NetworkManager.getInstance().addToQueueAndWait(request);
		JSONParser parser = new JSONParser();
		Map<String, Object> result = parser
				.parseJSON(new InputStreamReader(new ByteArrayInputStream(request.getResponseData()), "UTF-8"));
		List<Map<String, Object>> stopsList = (List<Map<String, Object>>) result.get("root");
		for (Map<String, Object> stopItem : stopsList) {
			int id = Integer.parseInt(stopItem.get("id").toString());
				
				stops.add(getStopByID(id));
		}
		System.out.println(stops.get(0).getId());

		return stops;
	}

	/**
	 * Check if the stop or station has the product or not
	 * 
	 * @param map
	 * @param product
	 * @return
	 */
	private static boolean hasProduct(Map<String, Object> map, Product product) {
		String bool = map.get(product.getStringValue()).toString();
		return Boolean.parseBoolean(bool);
	}

}
