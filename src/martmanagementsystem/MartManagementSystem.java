/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package martmanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.ConnectionException;
import model.MartManagementSystemModel;
import presenter.EmployeePresenter;
import view.login.LoginPageFXMLDocumentController;

/**
 *
 * @author sabin
 */
public class MartManagementSystem extends Application {
    
    EmployeePresenter employeePresenter;
    public static Stage stg;
    @Override
    public void start(Stage stage) throws Exception {
        this.stg = stage;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login/LoginPageFXMLDocument.fxml"));
                Parent root = loader.load();
                LoginPageFXMLDocumentController controller = loader.getController();
                stage.setTitle("Login");
        
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

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
