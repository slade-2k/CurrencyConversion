package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

import com.google.gson.Gson;


public class ExchangeData {
	
	private GsonData gsonData;
	private String[] countryCodes;
	private HashMap<String, Double> data;
	private String fileString = "data.txt";
	
	public ExchangeData(){
		gsonData = new GsonData();
	}
	
	public void checkData(){
		//this.setNewExchangeRates();  //Bedingungen setzen, hier wird entschieden ob neue Daten geladen werden oder die alten aktuell genug sind
		this.setExchangeRates();
	}
	
	public void setExchangeData(String json){     //Erstellt neue Datei aus den gezogenen Daten
		try {
			FileWriter fWriter = new FileWriter(fileString);
			BufferedWriter data = new BufferedWriter(fWriter);
			data.write(json);
			data.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setExchangeRates(){
		Gson gson = new Gson();
		gsonData = gson.fromJson(readURL(fileString), GsonData.class);
		data = gsonData.rates;
		this.setCountryCodes();
	}
	
	public void setNewExchangeRates() { // Holt sich die neuen Daten von
										// Openexchangerates.org und speichert
										// den String der durch readURL()
										// erstellt wurde in die Hashmap data
		String url = "https://openexchangerates.org/api/latest.json?app_id=b55158a06b514c378cca1bf1274f0c52";
		String json = readURL(url);
		this.setExchangeData(json);
	}
	
	public Double getExchangeData(String key){
		return data.get(key);
	}
	
	public void setCountryCodes(){									//Liest die Abkürzungen aus dem KeySet der erstellten Hashmap
		String[] countryCodes = gsonData.rates.keySet().toArray(new String[gsonData.rates.keySet().size()]);
		this.countryCodes = countryCodes;
	}
	
	public String[] getCountryCodes(){
		return this.countryCodes;
	}
	
	private String readFile(String fileString){
		BufferedReader reader = null;
		try {
			FileReader fReader = new FileReader(fileString);
			reader = new BufferedReader(fReader);
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

	private void closeReader(BufferedReader reader) {						//Schließt den BufferedReader 
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
