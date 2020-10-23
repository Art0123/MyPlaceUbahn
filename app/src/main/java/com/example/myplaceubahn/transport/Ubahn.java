package com.example.myplaceubahn.transport;

public class Ubahn {
	private String line, timeUntilArrival, lastStation;
	
	public Ubahn(String line, String timeUntilArrival, String lastStation) {
		this.line = line;
		this.timeUntilArrival = timeUntilArrival;
		this.lastStation = lastStation;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getLastStation() {
		return lastStation;
	}

	public void setLastStation(String lastStation) {
		this.lastStation = lastStation;
	}

	public String getTimeUntilArrival() {
		return timeUntilArrival;
	}

	public void setTimeUntilArrival(String timeUntilArrival) {
		this.timeUntilArrival = timeUntilArrival;
	}
	
	@Override
	public String toString() {
		return this.timeUntilArrival + " " + this.line + "\n" + this.lastStation + "\n";
	}
}
