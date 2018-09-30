package LoginApp;
import Teacher.assessementList;
import admin.AdminController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import Teacher.TeacherController;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
public class LoginController implements Initializable {
    LoginModel loginModel = new LoginModel();
    @FXML
    private Label lblDbStatus;
    @FXML
    private TextField txtUser;
    @FXML
    private PasswordField txtPass;
    @FXML
    private ComboBox<options> comboBox;
    @FXML
    private Button btnLogin;
    @FXML
    private Label loginStat, lblUserStat, lblPassStat,lblCombo;
    int count = 0;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(this.loginModel.isDatabaseConnected()){
            this.lblDbStatus.setText("Connected to DB");
        }else{
            this.lblDbStatus.setText("Not Connected to DB");
        }
        this.comboBox.setItems(FXCollections.observableArrayList(options.values()));
       txtUser.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                Login();
            }
        });
        comboBox.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                Login();
            }
        });
        txtPass.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                Login();
            }
        });

    }
    public void closeWindow(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Program close confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to close program?");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            System.exit(1);
        }
    }
    @FXML
    public void Login() {
            String Username, Password, opt;
            Username = this.txtUser.getText();
            Password = this.txtPass.getText();
            this.lblUserStat.setText("");
            this.lblPassStat.setText("");
            this.loginStat.setText("");
            this.lblCombo.setText("");
            if (!this.txtUser.getText().equals("") && !this.txtPass.getText().equals("") &&
                    !this.comboBox.getSelectionModel().isEmpty()) {
                try {
                    opt = this.comboBox.getValue().toString();
                    if (count < 3) {
                        if (this.loginModel.isLogin(Username, Password, opt)) {
                            Stage stage = (Stage) this.btnLogin.getScene().getWindow();
                            stage.close();

                            switch (this.comboBox.getValue().toString()) {
                                case "Admin":
                                    adminLogin();
                                    break;
                                case "Teacher":
                                    teacherLogin();
                                    break;
                            }
                        } else {
                            count++;
                            this.loginStat.setText("Username, Password or Division is incorrect");
                            this.txtPass.clear();
                        }
                    }
                    else{
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Login Information");
                        alert.setHeaderText(null);
                        alert.setContentText("Wrong Credentials entered more than 3 times");
                        alert.showAndWait();
                        System.exit(1);
                    }
                }catch (Exception localException) {
                    localException.printStackTrace();
                }
            }
        else{
            if (this.txtUser.getText().equals("")) {
                this.lblUserStat.setText("* Required Field");
                txtUser.clear();
            }
            if (this.txtPass.getText().equals("")) {
                txtPass.clear();
                this.lblPassStat.setText("* Required Field");
            }
            if (this.comboBox.getSelectionModel().isEmpty()) {
                comboBox.getSelectionModel().selectFirst();
                this.lblCombo.setText("* Choose Option");
            }
        }
    }
    public void teacherLogin(){
        try{
            //((Node)event.getSource()).getScene().getWindow().hide();
            Stage TeacherStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane)loader.load(getClass().getResource("/Teacher/TeacherFXML.fxml").openStream());
            TeacherController teacherController = (TeacherController)loader.getController();
            teacherController.DisplayLoginDetails(loginModel.getUserInfo(txtUser.getText()));
            teacherController.setGradeID(loginModel.getUserGrade(txtUser.getText()));
            teacherController.setTeacherID(txtUser.getText());
            Scene scene = new Scene(root);
            TeacherStage.setScene(scene);
            TeacherStage.setTitle("Teacher DashBoard - School Data Management System");
            TeacherStage.setResizable(false);
            TeacherStage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void adminLogin(){
        try {
            Stage AdminStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane)loader.load(getClass().getResource("/admin/Admin3.fxml").openStream());
            AdminController adminController = (AdminController)loader.getController();
            Scene scene = new Scene(root);
            AdminStage.setScene(scene);
            AdminStage.setTitle("Admin DashBoard - School Data Management System");
            AdminStage.setResizable(false);
            AdminStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}