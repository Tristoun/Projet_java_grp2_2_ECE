package Application;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

import DAO.SpecialistDaoImpl;
import DAO.UserDaoImpl;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.layout.Pane;


// pour affichage viewtable dynamique priserdv
import Models.RDV;
import DAO.RDVDaoImpl;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.collections.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DrawApp {

    public static Button drawButton(AnchorPane root, double x, double y, String text, int size, double width, double height) {
        Button button = new Button();
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setText(text);
        button.setFont(new Font(size));
        button.setPrefWidth(width);
        button.setPrefHeight(height);

        root.getChildren().add(button);
        return button;
    }

    public static Label drawLabel(AnchorPane root, double x, double y, String text, int size, Color color) {
        Label label = new Label();
        label.setLayoutX(x);
        label.setLayoutY(y);
        label.setText(text);
        label.setFont(new Font(size));
        label.setTextFill(color);
        root.getChildren().add(label);
        return label;
    }

    

    public static Rectangle drawRectangle(AnchorPane root, double x, double y, double width, double height) {
        Rectangle rect = new Rectangle();

        LinearGradient gradient = new LinearGradient(
        0, 0, 1, 1, true, CycleMethod.NO_CYCLE,  
        new Stop[] {
            new Stop(0, Color.rgb(50, 184, 218)),
            new Stop(1, Color.rgb(87, 255, 178))    
        });

        rect.setLayoutX(x);
        rect.setLayoutY(y);
        rect.setWidth(width);
        rect.setHeight(height);
        rect.setFill(gradient);
        rect.setArcWidth(30);
        rect.setArcHeight(30);
        root.getChildren().add(rect);
        return rect;
    }

    public static Circle drawCircle(AnchorPane root, double radius, double x, double y) {
        Circle circle = new Circle();
        circle.setRadius(radius);
        circle.setLayoutX(x);
        circle.setLayoutY(y);

        root.getChildren().add(circle);
        return circle;
    }

    public static ChoiceBox<String> drawChoiceBox(AnchorPane root, ChoiceBox<String> choiceBox, double x, double y, int wi, int height) {
        choiceBox.setLayoutX(x);
        choiceBox.setLayoutY(y);
        choiceBox.setPrefWidth(wi);
        choiceBox.setPrefHeight(height);
        
        root.getChildren().add(choiceBox);
        
        return choiceBox;
    }

    public static DatePicker drawDatePicker(AnchorPane root, DatePicker datePicker, double x, double y, int wi, int hei) {
        datePicker.setLayoutX(x);
        datePicker.setLayoutY(y);
        datePicker.setPrefWidth(wi);
        datePicker.setPrefHeight(hei);

        root.getChildren().add(datePicker);
        return datePicker;
    }

    public static <T> TableView<T> drawTableView(AnchorPane root, TableView<T> tableView, double x, double y, int wi, int hei){
        tableView.setLayoutX(x);
        tableView.setLayoutY(y);
        tableView.setPrefWidth(wi);
        tableView.setPrefHeight(hei);

        root.getChildren().add(tableView);
        return tableView;
    }

    public static ImageView drawImage(AnchorPane root, String path, double x, double y, int wi, int hei) {
        Image img = new Image(DrawApp.class.getResource(path).toExternalForm());
        ImageView imageView = new ImageView(img);
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        imageView.setFitWidth(wi);
        imageView.setFitHeight(hei);

        root.getChildren().add(imageView);
        return imageView;
    }

    public static TextField drawTextField(AnchorPane root, double x, double y, int wi, int hei) {
        TextField input = new TextField();
        input.setLayoutX(x);
        input.setLayoutY(y);
        input.setPrefWidth(wi);
        input.setPrefHeight(hei);

        root.getChildren().add(input);
        return input;
    }

    public static void drawForm(AnchorPane root, AnchorPane form) {
        root.getChildren().add(form);
    }



    public static Button drawSpecialistSearch(AnchorPane root, String nom, String specialite, double note, double tarif, double x, double y) { //Must be improve by adding image 
        drawRectangle(root, x, y, 759.0, 135.0);
        drawImage(root, "../image/doctor.jpg", x+20, y+12, 106, 106);
        drawLabel(root, x+145.0, y+38.0, nom, 23, Color.WHITE);
        drawLabel(root, x+145.0, y+75.0, specialite, 23, Color.WHITE);
        String noteString = Double.toString(note)+"/5";
        drawLabel(root, x+380.0, y+40.0, noteString, 23, Color.WHITE); 
        String tarifString = Double.toString(tarif)+"$";
        drawLabel(root, x+380.0, y+65, tarifString, 23, Color.WHITE);
        Button button = drawButton(root, x+613.0, y+38.0, "Prendre RDV", 16, 126.0, 56.0);
        return button;

    }

    public static void drawHistoric(AnchorPane root, String nameUser, String nameSpe, Date heure, double note, String description, double x, double y, int wi, int hei, String addr) {
        drawRectangle(root, x, y, wi, hei);
        drawImage(root, "../image/doctor.jpg", x+20, y+12, 106, 106);
        drawLabel(root, x+145.0, y+38.0, "Client : " + nameUser, 23, Color.WHITE);
        drawLabel(root, x+145.0, y+75.0, "Docteur : " + nameSpe, 23, Color.WHITE);
        if(note != -1) {
            String noteString = Double.toString(note)+"/5";
            drawLabel(root, x+450.0, y+40.0, noteString, 23, Color.WHITE); 
        }
        else {
            drawLabel(root, x+320.0, y+40.0, addr, 16, Color.WHITE);
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = formatter.format(heure);
        drawLabel(root, x+450, y+80.0, formattedDate, 23, Color.WHITE);
    }

    public static void drawTakeRdv(AnchorPane root, String name, ArrayList<String> lstSpecialisation, String addr) {
        drawLabel(root, 455, 146, name, 25, Color.WHITE);
        double x = 350;
        double y = 182;
        for(String talent : lstSpecialisation) {
            drawLabel(root, x, y, talent, 16, Color.WHITE);
            y += 20;
        }
        drawLabel(root, x-120, y, addr, 23, Color.WHITE);
        drawImage(root, "../image/doctor.jpg", 50, 146, 160, 160);
    }


    public static void drawProfil(AnchorPane root, UserDaoImpl userDaoImpl, int idUser) {

        try {
            ResultSet res = userDaoImpl.getSpecific("idUser", idUser);
            System.out.println(idUser);
            if(res !=null) {
                if(res.next()) {
                    drawLabel(root, 350.0, 188.0, res.getString("name"), 25,Color.BLACK);
                }
            }
            else {
                System.out.println("CPT");
            }
        }catch(SQLException e) {
            e.getStackTrace();
        }
    }
}
