package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

import com.google.gson.Gson;

public class ExchangeDataHTO {

	private HashMap<String, Double> data;
	String timeStamp;

	private final static String fileString = "data.txt";
	private final static String urlString = "https://openexchangerates.org/api/latest.json?app_id=b55158a06b514c378cca1bf1274f0c52";

	public ExchangeDataHTO() {
		loadExchangeData();
	}

	public Double getExchangeRate(String key) {
		checkData(this.timeStamp);
		return data.get(key);
	}
	
	public String[] getCountryCodes() {
		checkData(this.timeStamp);
		return this.data.keySet().toArray(new String[data.keySet().size()]);
	}
	
	private void checkData(String timestamp) {
		if (dataObsolete(timestamp)) {
			GsonData gsonData = loadNewExchangeDataFromWeb();
			this.data = gsonData.rates;
			this.timeStamp = gsonData.timestamp;
		}
	}
	
	private void loadExchangeData() {
		GsonData gsonData = null;
		File file = new File("data.txt");
		if (file.exists()) {
			gsonData = (new Gson()).fromJson(readSource(fileString), GsonData.class);
		}
		
		if (gsonData==null || dataObsolete(gsonData.timestamp)) {
			gsonData = loadNewExchangeDataFromWeb();
		}
		
		this.data = gsonData.rates;
		this.timeStamp = gsonData.timestamp;
	}
	
	private boolean dataObsolete(String timestamp) {
		long now = System.currentTimeMillis() / 1000L;
		long lastFetch = Long.parseLong(timestamp);
		return (now - lastFetch > 3600);
	}
	
	private GsonData loadNewExchangeDataFromWeb() {
		String json = readSource(urlString);
		this.storeExchangeDataLocally(json);

		return (new Gson()).fromJson(json, GsonData.class);
	}

	/*
	 * Erstellt neue Datei
	 */
	private void storeExchangeDataLocally(String json) { 
		try {
			FileWriter fWriter = new FileWriter(fileString);
			BufferedWriter data = new BufferedWriter(fWriter);
			data.write(json);
			data.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String readSource(String source) {
		BufferedReader reader = null;
		try {
			if (source.equals(fileString)) {
				FileReader fReader = new FileReader(source);
				reader = new BufferedReader(fReader);
			} else {
				URL url = new URL(source);
				reader = new BufferedReader(new InputStreamReader(url.openStream()));
			}

			StringBuffer buffer = new StringBuffer();
			char[] chars = new char[8192];
			int read;
			while ((read = reader.read(chars)) != -1) {
				buffer.append(chars, 0, read);
				return buffer.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeReader(reader);
		}
		return null;
	}

	private void closeReader(BufferedReader reader) {
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
