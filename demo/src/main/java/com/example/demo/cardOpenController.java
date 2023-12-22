package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class cardOpenController implements Initializable {
    @FXML
    private AnchorPane open_form;
    @FXML
    private AnchorPane open_rec;
    @FXML
    private Label prod_name;
    @FXML
    private ImageView prod_imageView;
    @FXML
    private Label prod_type;
    @FXML
    private Label prod_time;
    @FXML
    private Label prod_bju;
    @FXML
    private Label prod_difficulty;
    @FXML
    private TableView<recipeData> prod_tableView;
    @FXML
    private TableColumn<ingredientData, String> prod_col_name;
    @FXML
    private TableColumn<recipeData, Integer> prod_col_weight;
    @FXML
    private TableColumn<recipeData, Integer> prod_col_kcal;
    @FXML
    private TableColumn<recipeData, Integer> prod_col_b;
    @FXML
    private TableColumn<recipeData, Integer> prod_col_j;
    @FXML
    private TableColumn<recipeData, Integer> prod_col_u;
    @FXML
    private Button add_ingrBtn;
    @FXML
    private Button change_ingrBtn;
    @FXML
    private Button add_stepBtn;
    @FXML
    private Button change_stepBtn;
    private productData prodData;
    private Integer prodID;
    private Image image;
    private String prod_image;
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Alert alert;

    public void setData(productData prodData) {
        this.prodData = prodData;

        prodID = prodData.getProductId();
        prod_name.setText(prodData.getProductName());
        prod_type.setText(prodData.getType());
        prod_time.setText(String.valueOf(prodData.getTime()));
        prod_bju.setText(String.valueOf(prodData.getBju()));
        prod_difficulty.setText(String.valueOf(prodData.getDifficulty()) + " / 5");

        prod_image = prodData.getImage();
        String path = "File:" + prodData.getImage();
        image = new Image(path, 260, 150, false, true); // CHANGE
        prod_imageView.setImage(image);
    }

    public ObservableList<recipeData> recipeGetData() {
        String sql = "SELECT * FROM recipe_table WHERE prod_id = '" + prodID + "'";
        ObservableList<recipeData> listData = FXCollections.observableArrayList();
        connect = database.connectDB();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            recipeData recipe;
            while (result.next()) {
                recipe = new recipeData(result.getInt("id")
                        , result.getInt("prod_id")
                        , result.getInt("ingredient_id")
                        , result.getInt("quantity")
                        , result.getInt("kcal")
                        , result.getInt("b")
                        , result.getInt("j")
                        , result.getInt("u")
                        , result.getString("instructions"));
                listData.add(recipe);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    public ObservableList<ingredientData> ingredientGetData() {
        String sql = "SELECT * FROM ingredient WHERE prod_id = '" + prodID + "'";
        ObservableList<ingredientData> listData = FXCollections.observableArrayList();
        connect = database.connectDB();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ingredientData ingredient;
            while (result.next()) {
                ingredient = new ingredientData(result.getInt("id")
                        , result.getInt("prod_id")
                        , result.getString("title")
                        , result.getString("type"));
                listData.add(ingredient);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<recipeData> recipeListData;

    private ObservableList<ingredientData> ingredientListData;

    public void recipeShowData() {
        recipeListData = recipeGetData();
        //prod_col_name.setCellValueFactory(new PropertyValueFactory<>("title"));
        prod_col_weight.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        prod_col_kcal.setCellValueFactory(new PropertyValueFactory<>("kcal"));
        prod_col_b.setCellValueFactory(new PropertyValueFactory<>("b"));
        prod_col_j.setCellValueFactory(new PropertyValueFactory<>("j"));
        prod_col_u.setCellValueFactory(new PropertyValueFactory<>("u"));
        prod_tableView.setItems(recipeListData);
    }

    void initData(productData prodData) {
        setData(prodData);
        recipeShowData();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
