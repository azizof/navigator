package de.azizothman.navigator.entities;

import java.util.ArrayList;
import java.util.Map;

public class Stop {

	private String id;
	private String name;
	private double latitude;
	private double longitude;
	private ArrayList<Product> products = new ArrayList<Product>();
	private Map<String,Object> DBOpenTime;
	
	public Stop() {
	}

	public Stop(String id, String name, double latitude, double longitude) {
		super();
		this.id = id;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public ArrayList<Product> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}

	public Map<String,Object> getDBOpenTime() {
		return DBOpenTime;
	}

	public void setDBOpenTime(Map<String,Object> dBOpenTime) {
		DBOpenTime = dBOpenTime;
	}

	@Override
	public String toString() {
		return "Stop [id=" + id + ", name=" + name + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", products=" + products + ", DBOpenTime=" + DBOpenTime + "]";
	}
	
}
