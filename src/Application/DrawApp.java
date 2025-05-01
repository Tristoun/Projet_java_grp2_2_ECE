package Application;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class DrawApp {

    public static Button drawButton(AnchorPane root, double x, double y, String text) {
        Button button = new Button();
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setText(text);

        root.getChildren().add(button);
        return button;
    }

    public static Label drawLabel(AnchorPane root, double x, double y, String text) {
        Label label = new Label();
        label.setLayoutX(x);
        label.setLayoutY(y);
        label.setText(text);

        root.getChildren().add(label);
        return label;
    }
    
}
