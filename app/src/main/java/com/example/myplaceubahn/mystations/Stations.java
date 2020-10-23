package com.example.myplaceubahn.mystations;


import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.myplaceubahn.scrapper.ScrapMVG;

import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Stations extends ScrapMVG {
	private ArrayList<String> ubahnLine;

	public Stations() {
		this.ubahnLine = new ArrayList<>();
	}

	public String homeStation() {
		ubahnLine.add("theresienwiese");
		ubahnLine.add("westendstraße");
		return super.getTimeScheduleUbahn("böhmerwaldplatz", ubahnLine);
	}

	public String fromOdeonToHome() {
		ubahnLine.add("arabellapark");
		return super.getTimeScheduleUbahn("odeonsplatz", ubahnLine);
	}

	public String fromWorkToOdeon() {
		ubahnLine.add("garching-forschungszentrum");
		ubahnLine.add("fröttmaning");
		ubahnLine.add("moosach");
		return super.getTimeScheduleUbahn("sendlinger tor", ubahnLine);
	}

	public String fromOdeonToWork() {
		ubahnLine.add("klinikum großhadern");
		ubahnLine.add("fürstenried west");
		return super.getTimeScheduleUbahn("odeonsplatz", ubahnLine);
	}

	public String homeBusToShop() {
		return super.getTimeScheduleBus("böhmerwaldplatz", "giesing bf.");
	}

	public String busFromShopToHome() {
		return super.getTimeScheduleBus("ampfingstraße", "ackermannbogen");
	}
}
