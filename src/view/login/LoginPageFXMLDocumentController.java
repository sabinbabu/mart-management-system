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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import martmanagementsystem.MartManagementSystem;
import model.ConnectionException;
import model.MartManagementSystemModel;
import presenter.EmployeePresenter;
import view.billing.AddEmployeeFXMLDocumentController;
import view.billing.BillingFXMLDocumentController;

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

        if ("admin".equals(lognUsername.getText().trim()) && "admin".equals(loginPassword.getText().trim())) {
          goToAdminHomepage();
        } else {
            Alert alert = new Alert(AlertType.NONE);
            alert.setAlertType(AlertType.ERROR);
            alert.setContentText("Incorrect username or password");
            alert.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private void goToAdminHomepage() {
          try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/billing/billingFXMLDocument.fxml"));
                Parent root = loader.load();
                BillingFXMLDocumentController controller = loader.getController();
                Stage stage = new Stage();
                stage.setTitle("Mart Management System");
                MartManagementSystemModel martManagementSystemModel = new MartManagementSystemModel();
                try {
                    // connecting to database   
                    martManagementSystemModel.connect();
                    martManagementSystemModel.initialise();
                } catch (ConnectionException e) {
                    System.err.println(e.getMessage());
                    e.getCause().printStackTrace();
                    System.exit(1);
                }
                EmployeePresenter employeePresenter = new EmployeePresenter(controller, martManagementSystemModel, martManagementSystemModel);
                controller.bind(employeePresenter);
                stage.setScene(new Scene(root));
                stage.show();
                MartManagementSystem.stg.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
