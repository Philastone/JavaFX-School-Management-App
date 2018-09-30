package LoginApp;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
public class Login extends Application {
    public void start (Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("login2.fxml"));
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.setTitle("School Data Management System");
        stage.setResizable(false);
        stage.show();
    }
    public static void main(String[] args){
        launch(args);
    }
}
