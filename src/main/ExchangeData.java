package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;

import com.google.gson.Gson;


public class ExchangeData {
	
	private GsonData exRate;
	private String[] countryCodes;
	private HashMap<String, Double> data;
	
	public ExchangeData(){
		exRate = new GsonData();
	}
	
	public void checkData(){
		this.setExchangeRates();  //Bedingungen setzen, hier wird entschieden ob neue Daten geladen werden oder die alten aktuell genug sind
	}
	
	public void setExchangeData(/*HashMap<String, Double> currencyRates*/){     //Erstellt neue Datei aus den gezogenen Daten
		try {
			FileWriter fWriter = new FileWriter("data.txt");
			BufferedWriter data = new BufferedWriter(fWriter);
	//		data.write(timestamp);
			data.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setExchangeRates(){                                     //Holt sich die neuen Daten von Openexchangerates.org und speichert den String der durch readURL() erstellt wurde in die Hashmap exRate
			Gson gson = new Gson();
			String url = "https://openexchangerates.org/api/latest.json?app_id=b55158a06b514c378cca1bf1274f0c52";
			String json = readURL(url);
			exRate = gson.fromJson(json, GsonData.class);
			data = exRate.rates;
			this.setCountryCodes();
	}
	
	public void setCountryCodes(){									//Liest die Abk�rzungen aus dem KeySet der erstellten Hashmap
		String[] countryCodes = exRate.rates.keySet().toArray(new String[exRate.rates.keySet().size()]);
		this.countryCodes = countryCodes;
	}
	
	public Double getExchangeData(String key){
		return data.get(key);
	}
	
	public String[] getCountryCodes(){
		return this.countryCodes;
	}
	
	private String readURL(String urlString) {						//Liest die Json Datei aus und speichert die Daten in einem String
		BufferedReader reader = null;
		try {
			URL url = new URL(urlString);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuffer buffer = new StringBuffer();
			char[] chars = new char[8192];
			int read;
			while ((read = reader.read(chars)) != -1) {
				buffer.append(chars, 0, read);
				return buffer.toString();
			}
		} catch (Exception e) {

		} finally {
			closeReader(reader);
		}
		return null;
	}

	private void closeReader(BufferedReader reader) {						//Schlie�t den BufferedReader 
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
