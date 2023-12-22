package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;

public class cardFavController implements Initializable {
    @FXML
    private AnchorPane favCard_form;
    @FXML
    private Label prod_name;
    @FXML
    private Label prod_difficulty;
    @FXML
    private Button fav_removeBtn;
    @FXML
    private Button fav_openBtn;
    @FXML
    private ImageView fav_imageView;

    private productData prodData;
    private Integer prodID;
    private String type;
    private Image image;
    private String prod_image;
    private Integer time;
    private Double bju;
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Alert alert;

    @FXML
    void handleButtonAction(ActionEvent event) {
        try {
            showProdOpen(this.prodData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setData(productData prodData) {
        this.prodData = prodData;

        prodID = prodData.getProductId();
        prod_name.setText(prodData.getProductName());
        type = prodData.getType();
        time = prodData.getTime();
        bju = prodData.getBju();
        prod_difficulty.setText(String.valueOf(prodData.getDifficulty()) + " / 5");

        prod_image = prodData.getImage();
        String path = "File:" + prodData.getImage();
        image = new Image(path, 260, 150, false, true); // CHANGE
        fav_imageView.setImage(image);
    }

    public void favRemoveBtn() {
        connect = database.connectDB();
        try {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Вы уверены, что хотите убрать данный рецепт из избранного?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get().equals(ButtonType.OK)) {
                String deleteData = "DELETE FROM fav WHERE prod_id = '" + prodID + "'";
                try {
                    prepare = connect.prepareStatement(deleteData);
                    prepare.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Рецепт удален!");
                    alert.showAndWait();

                    //MainDocumentController mForm = new MainDocumentController(); // разобраться с динамическим обновлением
                    //mForm.favDisplayCard();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Stage showProdOpen(productData prodData) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("cardOpen.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene(loader.load()));
        stage.setTitle(prodData.getProductName());

        cardOpenController controller = loader.getController();
        controller.initData(prodData);

        stage.show();
        return stage;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}