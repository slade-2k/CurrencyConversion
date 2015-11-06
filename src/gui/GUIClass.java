package gui;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.text.NumberFormatter;

import main.MainClass;

public class GUIClass extends JFrame{
	
	private JLabel lblTitle = new JLabel("CurrencyCalculator");
	private JLabel lblToValue = new JLabel();
	
	private JPanel pnlInput = new JPanel();
	private JPanel pnlOutput = new JPanel();
	private JPanel pnlButtons = new JPanel();
	
	private JFormattedTextField txtEuros;
	
	private JComboBox cmbToRate;
	private JComboBox cmbBaseRate;
	
	private MainClass mainClass;
	
	public GUIClass(MainClass mainClass){
		this.mainClass = mainClass;
		this.setSize(500, 100);
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		Dimension dTxtField = new Dimension(150, 50);

		DecimalFormat df = new DecimalFormat("#.##");
		NumberFormatter formatter = new NumberFormatter(df);
		formatter.setAllowsInvalid(false);

		
		lblToValue.setText("");
		lblToValue.setMaximumSize(dTxtField);
		lblToValue.setPreferredSize(dTxtField);
		
		pnlInput.setLayout(new BoxLayout(pnlInput, BoxLayout.X_AXIS));
		pnlOutput.setLayout(new BoxLayout(pnlOutput, BoxLayout.X_AXIS));
		pnlButtons.setLayout(new BoxLayout(pnlButtons, BoxLayout.X_AXIS));
		
		txtEuros = new JFormattedTextField(formatter);
		txtEuros.setPreferredSize(dTxtField);
		txtEuros.setMaximumSize(dTxtField);
		
		cmbToRate	= new JComboBox(mainClass.readCountryCodes());
		cmbBaseRate = new JComboBox(mainClass.readCountryCodes());
		
		cmbBaseRate.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if(!txtEuros.getText().isEmpty()){
					setLabelText();
				}	
			}
		});
		
		cmbToRate.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if(!txtEuros.getText().isEmpty()){
					setLabelText();
				}	
			}
			
		});
	
		txtEuros.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if(Double.parseDouble(txtEuros.getText()) > 0 && !txtEuros.getText().isEmpty()){
					setLabelText();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
		
		getContentPane().add(pnlOutput);
		getContentPane().add(pnlInput);
		
		pnlOutput.add(lblTitle);
		pnlInput.add(txtEuros);
		pnlInput.add(cmbBaseRate);
		pnlInput.add(lblToValue);
		pnlInput.add(cmbToRate);
		
		validate();
	}
	
	private void setLabelText(){
		lblToValue.setText(mainClass.calcToCurrency(Double.parseDouble(txtEuros.getText()), (String) cmbToRate.getSelectedItem(), (String) cmbBaseRate.getSelectedItem()));
	}
}