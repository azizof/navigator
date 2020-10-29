package de.azizothman.navigator.entities;

public class JourneyData {

	private String fromId;
	private String toId;
	private String date;
	private String time;
	
	public JourneyData() {
	}

	public JourneyData(String fromId, String toId, String date, String time) {
		super();
		this.fromId = fromId;
		this.toId = toId;
		this.date = date;
		this.time = time;
	}


	public String getFromId() {
		return fromId;
	}
	public void setFromId(String fromId) {
		this.fromId = fromId;
	}
	public String getToId() {
		return toId;
	}
	public void setToId(String toId) {
		this.toId = toId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	
}
