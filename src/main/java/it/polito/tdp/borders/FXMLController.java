
package it.polito.tdp.borders;


import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML
    private Button btnRaggiungibile;

    @FXML
    private ComboBox<String> comboBox;


    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	
    	int anno ;
    	try {
    		anno = Integer.parseInt(txtAnno.getText()) ;
    	}
    	catch(NumberFormatException e) {
    		txtResult.setText("INSERIRE UN NUMERO");
    		return ;
    	}
    	if(anno < 1816 || anno > 2016) {
			txtResult.setText("INSERIRE UN ANNO COMPRESO TRA IL 1816 E IL 2016");
		}
		else {
			this.model.creaGrafo();
			this.model.aggiungiArco(anno);
			txtResult.setText("I CONFINI SONO : \n");
			txtResult.appendText(this.model.confini());
		}
    }
    @FXML
    void doStatiRaggiungibili(ActionEvent event) {
    	
    	String countryName = this.comboBox.getValue() ;
    	for(Country c : this.model.elencoStati()) {
    		if(c.getStateName().equals(countryName)) {
    			txtResult.setText("IL PERCORSO TROVATO E' : \n") ;
    	    	//txtResult.appendText(this.model.getComponenteConnessa(c));
    			txtResult.appendText(this.model.trovaPath(c).toString());
    	    	return;
    		}
    	}
    }

    public void setModel(Model model) {
    	this.model = model;
    	this.comboBox.getItems().addAll(this.model.elencoNomiStati());
    }
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    

}
