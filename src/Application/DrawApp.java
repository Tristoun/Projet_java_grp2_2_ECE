package Application;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import DAO.SpecialistDaoImpl;
import DAO.UserDaoImpl;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

// pour affichage viewtable dynamique priserdv
import Models.RDV;
import DAO.RDVDaoImpl;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.*;
import java.time.LocalDateTime;
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
        rect.setLayoutX(x);
        rect.setLayoutY(y);
        rect.setWidth(width);
        rect.setHeight(height);
        rect.setFill(Color.WHITE);
        rect.setStroke(Color.BLACK);

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


    public static void drawSpecialistSearch(AnchorPane root, String nom, String specialite, double note, double tarif, double x, double y) { //Must be improve by adding image 
        drawRectangle(root, x, y, 759.0, 135.0);
        drawCircle(root, 53.0, x+74.0, y+67.0);
        drawLabel(root, x+145.0, y+38.0, nom, 23, Color.BLACK);
        drawLabel(root, x+145.0, y+75.0, specialite, 23, Color.BLACK);
        String noteString = Double.toString(note)+"/5";
        drawLabel(root, x+380.0, y+40.0, noteString, 23, Color.BLACK); 
        String tarifString = Double.toString(tarif)+"$";
        drawLabel(root, x+380.0, y+65, tarifString, 23, Color.BLACK);
        drawButton(root, x+613.0, y+38.0, "Prendre RDV", 16, 126.0, 56.0);
    }

    public static void drawHistoric(AnchorPane root, String nameUser, String nameSpe, Date heure, int note, String description, double x, double y) {
        drawRectangle(root, x, y, 759.0, 135.0);
        drawCircle(root, 53.0, x+74.0, y+67.0);
        drawLabel(root, x+145.0, y+38.0, nameUser, 23, Color.BLACK);
        drawLabel(root, x+145.0, y+75.0, nameSpe, 23, Color.BLACK);
        String noteString = Integer.toString(note)+"/5";
        drawLabel(root, x+380.0, y+40.0, noteString, 23, Color.BLACK); 
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = formatter.format(heure);
        drawLabel(root, x+380, y+80.0, formattedDate, 23, Color.BLACK);
    }



    public static void drawProfil(AnchorPane root, UserDaoImpl userDaoImpl, int idUser) {

        try {
            ResultSet res = userDaoImpl.getSpecific("idUser", idUser);
            System.out.println(idUser);
            if(res !=null) {
                if(res.next()) {
                    drawLabel(root, 350.0, 142.0, res.getString("name"), 25,Color.BLACK);
                }
            }
            else {
                System.out.println("CPT");
            }
        }catch(SQLException e) {
            e.getStackTrace();
        }
    }


    public static void drawRDV(AnchorPane root ) {
        
    }
}
