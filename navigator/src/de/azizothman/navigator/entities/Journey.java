package de.azizothman.navigator.entities;

import java.util.ArrayList;

public class Journey {

	private String id;
	private String fromID;
	private String toID;
	private String departure;
	private String departureDelay;
	private String arrival;
	private String line;
	private String departurePlatform;
	private boolean bike;
	private ArrayList<Stop> stops;
	private ArrayList<Product> products;

	public Journey() {
	}

	public Journey(String id, String fromID, String toID, String departure, String departureDelay, String arrival,
			String line, String departurePlatform, boolean bike, ArrayList<Stop> stops,
			ArrayList<Product> products) {
		super();
		this.id = id;
		this.fromID = fromID;
		this.toID = toID;
		this.departure = departure;
		this.departureDelay = departureDelay;
		this.arrival = arrival;
		this.line = line;
		this.departurePlatform = departurePlatform;
		this.bike = bike;
		this.stops = stops;
		this.products = products;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFromID() {
		return fromID;
	}

	public void setFromID(String fromID) {
		this.fromID = fromID;
	}

	public String getToID() {
		return toID;
	}

	public void setToID(String toID) {
		this.toID = toID;
	}

	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public String getDepartureDelay() {
		return departureDelay;
	}

	public void setDepartureDelay(String departureDelay) {
		this.departureDelay = departureDelay;
	}

	public String getArrival() {
		return arrival;
	}

	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getDeparturePlatform() {
		return departurePlatform;
	}

	public void setDeparturePlatform(String departurePlatform) {
		this.departurePlatform = departurePlatform;
	}

	public boolean isBike() {
		return bike;
	}

	public void setBike(boolean bike) {
		this.bike = bike;
	}

	public ArrayList<Stop> getStops() {
		return stops;
	}

	public void setStops(ArrayList<Stop> stops) {
		this.stops = stops;
	}

	public ArrayList<Product> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}

}
