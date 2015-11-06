package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.NumberFormat;

import com.google.gson.Gson;

import gui.GUIClass;

public class MainClass {

	public String[] countryCode = {"test"};
	public static GUIClass gui;
	public ExchangeRates exRate;
	public static ExchangeData data;

	public static void main(String args[]) {
		MainClass mainClass = new MainClass();
		mainClass.getExchangeRate();
		gui = new GUIClass(mainClass);
		data = new ExchangeData();
	}

	public void getExchangeRate() {
		Gson gson = new Gson();
		URL url;
		String json = readURL("https://openexchangerates.org/api/latest.json?app_id=b55158a06b514c378cca1bf1274f0c52");
		exRate = gson.fromJson(json, ExchangeRates.class);
		this.readCountryCodes();
	}

	public String calcToCurrency(double value, String toCode, String fromCode) {
		NumberFormat formatter = NumberFormat.getInstance();
		formatter.setMaximumFractionDigits(4);
		
		Double fromValue = exRate.rates.get(fromCode);
		Double toValue = exRate.rates.get(toCode);
		Double result = value/fromValue * toValue;
		return formatter.format(result).toString() + " " + toCode;
	}

	private String readURL(String urlString) {
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

	private void closeReader(BufferedReader reader) {
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String[] readCountryCodes(){
		String[] countryCodes = exRate.rates.keySet().toArray(new String[exRate.rates.keySet().size()]);
		return countryCodes;
	}
}
