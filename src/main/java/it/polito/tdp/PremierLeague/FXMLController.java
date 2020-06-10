/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Model;
import it.polito.tdp.PremierLeague.model.Opponent;
import it.polito.tdp.PremierLeague.model.Player;
import it.polito.tdp.PremierLeague.model.TopPlayer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnTopPlayer"
    private Button btnTopPlayer; // Value injected by FXMLLoader

    @FXML // fx:id="btnDreamTeam"
    private Button btnDreamTeam; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="txtGoals"
    private TextField txtGoals; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	Double x = null;
    	try {
    		x = Double.parseDouble(txtGoals.getText());
    	} catch (NumberFormatException e) {
    		txtResult.appendText("Inserire un numero minimo di goal nel formato corretto");
    		return;
    	}
    	this.model.creaGrafo(x);
    	txtResult.appendText("Grafo creato\n");
    	txtResult.appendText("# VERTICI: " + this.model.nVertici() + "\n");
    	txtResult.appendText("# ARCHI: " + this.model.nArchi());

    }

    @FXML
    void doDreamTeam(ActionEvent event) {
    	txtResult.clear();
    	
    	if(this.model.getGrafo() == null) {
    		txtResult.clear();
    		txtResult.appendText("Crea prima il grafo!\n");
    		return;
    	}
    	
    	Integer k = null;
    	
    	try {
    		k = Integer.parseInt(txtK.getText());
    	} catch(NumberFormatException e) {
        	txtResult.clear();
    		txtResult.appendText("Inserisce un valore intero per k");
    		return;
    	}
    	
    	List<Player> dreamTeam = this.model.getDreamTeam(k);
    	int degree = this.model.getBestDegree();
    	
    
    	
    	txtResult.appendText("DREAM TEAM - grado di titolarità: " + degree + "\n\n");
    	for(Player p : dreamTeam)
    		txtResult.appendText(p.toString() + "\n");
    }

    @FXML
    void doTopPlayer(ActionEvent event) {
    	txtResult.clear();
    	TopPlayer topPlayer = this.model.getTopPlayer();
    	if(topPlayer == null) {
    		txtResult.appendText("Crea il grafo!");
    		return;
    	}
    	
    	txtResult.appendText("TOP PLAYER: " + topPlayer.getPlayer().toString());
    	txtResult.appendText("\n\nAVVERSARI BATTUTI:\n");
    	
    	for(Opponent o : topPlayer.getOpponents()) {
    		txtResult.appendText(o.toString() + "\n");
    	}

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnTopPlayer != null : "fx:id=\"btnTopPlayer\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnDreamTeam != null : "fx:id=\"btnDreamTeam\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtGoals != null : "fx:id=\"txtGoals\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
