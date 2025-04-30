import DAO.DaoFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application {
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        DaoFactory.init("info_doctolib", "root", "patapouf");
        Parent root = FXMLLoader.load(getClass().getResource("/SceneDesign/login.fxml")); // Corrected path
        Scene scene = new Scene(root); // Set scene size and background color
        //String css = this.getClass().getResource("application.css").toExternalForm();
        //scene.getStylesheets().add(css);

        stage.setScene(scene);
        stage.show(); // Show the stage
    }
}
