package main;

import java.text.NumberFormat;
import java.util.HashMap;

import gui.GUIClass;

public class MainClass {

	public static GUIClass gui;
	public static ExchangeData exData;
	private HashMap<String, Double> data;

	public static void main(String args[]) {
		MainClass mainClass = new MainClass();
		exData = new ExchangeData();
		exData.checkData();
		gui = new GUIClass(mainClass, exData);
	}

	public String calcToCurrency(double value, String toCode, String fromCode) {
		NumberFormat formatter = NumberFormat.getInstance();
		formatter.setMaximumFractionDigits(4);
		
		Double fromValue = exData.getExchangeData(fromCode);
		Double toValue = exData.getExchangeData(toCode);
		Double result = value/fromValue * toValue;
		return formatter.format(result).toString() + " " + toCode;
	}
}
