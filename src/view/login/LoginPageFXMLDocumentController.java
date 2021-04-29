/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.login;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import martmanagementsystem.MartManagementSystem;

/**
 *
 * @author sabin
 */
public class LoginPageFXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private TextField lognUsername;
    @FXML
    private TextField loginPassword;
    @FXML
    private Button btnLogin;
   
    @FXML
    private void onLoginClicked(ActionEvent event) {
        
         try {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/billing/billingFXMLDocument.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Billing");
            stage.setScene(new Scene(root));  
            stage.show();
            MartManagementSystem.stg.close();
    } catch(Exception e) {
       e.printStackTrace();
      }
    }
 
   
 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
