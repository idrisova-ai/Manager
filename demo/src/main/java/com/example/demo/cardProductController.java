package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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
import java.util.ResourceBundle;


public class cardProductController implements Initializable {
    @FXML
    private AnchorPane prodCard_form;
    @FXML
    private Label prod_name;
    @FXML
    private Label prod_difficulty;
    @FXML
    private Button cart_addBtn;
    @FXML
    private Button fav_addBtn;
    @FXML
    private Button prod_openBtn;
    @FXML
    private ImageView prod_imageView;
    @FXML
    private Spinner<Integer> prod_spinner;

    private SpinnerValueFactory<Integer> spin;
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
        prod_imageView.setImage(image);
    }

    private int qty;

    public void setQuantity() {
        spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        prod_spinner.setValueFactory(spin);
    }

    public void cartAddBtn() {
        qty = prod_spinner.getValue();
        connect = database.connectDB();
        try {
            if (qty == 0) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Укажите количество");
                alert.showAndWait();
            } else {
                String check = "SELECT * FROM cart WHERE prod_id = '" + prodID + "'";
                prepare = connect.prepareStatement(check);
                result = prepare.executeQuery();

                if (result.next()) {
                    String cartQty = "SELECT quantity FROM cart WHERE prod_id = '" + prodID + "'";
                    prepare = connect.prepareStatement(cartQty);
                    ResultSet resultSet1 = prepare.executeQuery();
                    if (resultSet1.next()) {
                        String prod_qty = resultSet1.getString("quantity");
                        qty += Integer.parseInt(prod_qty);
                    }

                    String updateData = "UPDATE cart SET quantity = '" + qty + "' WHERE prod_id = '" + prodID + "'";
                    prepare = connect.prepareStatement(updateData);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Рецепт добавлен в корзину!");
                    alert.showAndWait();
                } else {
                    String insertData = "INSERT INTO cart "
                            + "(id, prod_id, prod_name, type, time, bju, difficulty, quantity, image) "
                            + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    prepare = connect.prepareStatement(insertData);
                    prepare.setInt(1, prodID);
                    prepare.setInt(2, prodID);
                    prepare.setString(3, prod_name.getText());
                    prepare.setString(4, type);
                    prepare.setInt(5, time);
                    prepare.setDouble(6, bju);
                    prepare.setInt(7, prodData.getDifficulty());
                    prepare.setInt(8, qty);
                    if (prod_image == null) {
                        prepare.setString(9, null);
                    } else {
                        prod_image = prod_image.replace("\\", "\\\\");
                        prepare.setString(9, prod_image);
                    }
                    //prepare.setString(9, prod_image);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Рецепт добавлен в корзину!");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void favAddBtn() {
        connect = database.connectDB();
        try {
            String check = "SELECT * FROM fav WHERE prod_id = '" + prodID + "'";
            prepare = connect.prepareStatement(check);
            result = prepare.executeQuery();

            if (result.next()) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Рецепт уже добавлен в избранное!");
                alert.showAndWait();
            } else {
                String insertData = "INSERT INTO fav "
                        + "(id, prod_id, prod_name, type, time, bju, difficulty, image) "
                        + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
                prepare = connect.prepareStatement(insertData);
                prepare.setInt(1, prodID);
                prepare.setInt(2, prodID);
                prepare.setString(3, prod_name.getText());
                prepare.setString(4, type);
                prepare.setInt(5, time);
                prepare.setDouble(6, bju);
                prepare.setInt(7, prodData.getDifficulty());
                if (prod_image == null) {
                    prepare.setString(8, null);
                } else {
                    prod_image = prod_image.replace("\\", "\\\\");
                    prepare.setString(8, prod_image);
                }
                //prepare.setString(8, prod_image);
                prepare.executeUpdate();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Рецепт добавлен в избранное!");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setQuantity();
    }
}
