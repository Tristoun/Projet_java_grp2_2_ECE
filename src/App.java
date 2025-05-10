import Application.Controller;
import DAO.DaoFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class App extends Application {
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        DaoFactory.init("info_doctolib", "root", "patapouf");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SceneDesign/login.fxml"));
        AnchorPane root = loader.load();
        Scene scene = new Scene(root); // Set scene size and background color
        //String css = this.getClass().getResource("application.css").toExternalForm();
        //scene.getStylesheets().add(css);

        stage.setScene(scene);
        stage.show();
    }
}
