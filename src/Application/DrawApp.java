package Application;

import java.sql.ResultSet;
import java.sql.SQLException;

import DAO.SpecialistDaoImpl;
import DAO.UserDaoImpl;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

public class DrawApp {

    public static Button drawButton(AnchorPane root, double x, double y, String text, int size) {
        Button button = new Button();
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setText(text);
        button.setFont(new Font(size));

        root.getChildren().add(button);
        return button;
    }

    public static Label drawLabel(AnchorPane root, double x, double y, String text, int size) {
        Label label = new Label();
        label.setLayoutX(x);
        label.setLayoutY(y);
        label.setText(text);
        label.setFont(new Font(size));

        root.getChildren().add(label);
        return label;
    }

    public static void drawProfil(AnchorPane root, UserDaoImpl userDaoImpl, int idUser) {
        SpecialistDaoImpl specialistDaoImpl = new SpecialistDaoImpl();


        try {
            ResultSet res = userDaoImpl.getSpecific("id_user", idUser);
            if(res !=null) {
                if(res.next()) {
                    drawLabel(root, 525.0, 305.0, res.getString("name"), 42);
                }
            }
        }catch(SQLException e) {
            e.getStackTrace();
        }
    }
}
