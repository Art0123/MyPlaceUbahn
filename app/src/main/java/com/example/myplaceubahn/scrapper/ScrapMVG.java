package com.example.myplaceubahn.scrapper;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.myplaceubahn.transport.Ubahn;

import java.io.IOException;
import java.net.URLEncoder;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@RequiresApi(api = Build.VERSION_CODES.O)
public abstract class ScrapMVG {
	private ArrayList<Ubahn> ubahnList;
	private ZonedDateTime zdt;

	public ScrapMVG() {
		this.ubahnList = new ArrayList<>();
		this.zdt = ZonedDateTime.now();
	}

	public String getTimeScheduleUbahn(String stationName, ArrayList<String> stationDirections) {
		Document doc;

		try {
			String url = "http://www.mvg-live.de/ims/dfiStaticAuswahl.svc?haltestelle=" + URLEncoder.encode(stationName, "ISO-8859-1") + "&ubahn=checked";
			doc = Jsoup.connect(url).get();

			Element el = doc.select("table").get(0);
			Elements allRows = el.select("tbody").select("tr");

			for (Element e : allRows) {
				if (e.className().equals("rowOdd") || e.className().equals("rowEven")) {
					Elements rows = e.select("td");

					for (int i = 0; i < stationDirections.size(); i++) {
						if (rows.get(1).text().equalsIgnoreCase(stationDirections.get(i)) && ubahnList.size() < 3) {
							ubahnList.add(new Ubahn(rows.get(0).text(), rows.get(2).text(), rows.get(1).text()));
						}
					}
				}

			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		Collections.sort(ubahnList, (u1, u2) -> Integer.parseInt(u1.getTimeUntilArrival()) - Integer.parseInt(u2.getTimeUntilArrival()));

		for (Ubahn u : ubahnList) {
			u.setTimeUntilArrival(formatTime(zdt.plusMinutes(Long.parseLong(u.getTimeUntilArrival()))));
		}

		return formatUbahn(ubahnList);
	}

	public String getTimeScheduleBus(String stationName, String direction) {
		ArrayList<String> busTimesList = new ArrayList<>();
		ArrayList<String> busTimesFormated = new ArrayList<>();
		Document doc;
		String busNumber = "";

		try {
			String url = "http://www.mvg-live.de/ims/dfiStaticAuswahl.svc?haltestelle=" + URLEncoder.encode(stationName, "ISO-8859-1") + "&bus=checked";
			doc = Jsoup.connect(url).get();

			Element el = doc.select("table").get(0);
			Elements allRows = el.select("tbody").select("tr");

			for (Element e : allRows) {
				if (e.className().equals("rowOdd") || e.className().equals("rowEven")) {
					Elements rows = e.select("td");

					if (rows.get(1).text().equalsIgnoreCase(direction) && busTimesList.size() < 4) {
						busNumber = rows.get(0).text();
						busTimesList.add(rows.get(2).text());
					}
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		for (String t : busTimesList) {
			busTimesFormated.add(formatTime(zdt.plusMinutes(Long.parseLong(t))) + "\n");
		}

		return "Bus " + busNumber + "\n" + "\n" + formatBus(busTimesFormated);
	}

	private String formatBus(ArrayList<String> list) {
		return list.toString()
				.replace("[", "")
				.replace("]", "")
				.replace(", ", "\n")
				.trim();
	}

	private String formatTime(ZonedDateTime time) {
		return DateTimeFormatter.ofPattern("kk:mm").format(time);
	}

	private String formatUbahn(ArrayList<Ubahn> list) {
		return list.toString()
				.replace("[", "")
				.replace("]", "")
				.replace(", ", "\n")
				.trim();
	}
}