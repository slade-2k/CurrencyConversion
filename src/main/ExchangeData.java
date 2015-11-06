package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;


public class ExchangeData {

	public HashMap<String, Double> getExchangeData(Date date){
		return null;
	}
	
	public void createExchangeData(/*HashMap<String, Double> currencyRates*/){
		try {
			FileWriter fWriter = new FileWriter("data.txt");
			BufferedWriter data = new BufferedWriter(fWriter);
	//		data.write(timestamp);
			data.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
