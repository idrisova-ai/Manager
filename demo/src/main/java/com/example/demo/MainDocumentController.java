package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainDocumentController implements Initializable {
    @FXML
    private AnchorPane all_form;
    @FXML
    private AnchorPane all_rec;
    @FXML
    private GridPane menu_gridPane;
    @FXML
    private ScrollPane menu_scrollPane;

    @FXML
    private AnchorPane fav_form;
    @FXML
    private AnchorPane fav_rec;
    @FXML
    private GridPane fav_gridPane;
    @FXML
    private ScrollPane fav_scrollPane;

    @FXML
    private AnchorPane cart_form;
    @FXML
    private AnchorPane cart_rec;
    @FXML
    private TableView<productData> cart_tableView;
    @FXML
    private TableColumn<productData, String> cart_col_prodId;
    @FXML
    private TableColumn<productData, String> cart_col_prodName;
    @FXML
    private TableColumn<productData, String> cart_col_type;
    @FXML
    private TableColumn<productData, Double> cart_col_bju;
    @FXML
    private TableColumn<productData, Integer> cart_col_quantity;
    @FXML
    private Button cart_payBtn;
    @FXML
    private Button cart_openBtn;
    @FXML
    private Button cart_removeBtn;

    @FXML
    private AnchorPane open_form;
    @FXML
    private AnchorPane open_rec;
    // добавить остальные элементы

    @FXML
    private AnchorPane add_form;
    @FXML
    private AnchorPane add_rec;
    @FXML
    private AnchorPane add_table;
    @FXML
    private TableView<productData> add_tableView;
    @FXML
    private TableColumn<productData, String> add_col_prodId;
    @FXML
    private TableColumn<productData, String> add_col_prodName;
    @FXML
    private TableColumn<productData, String> add_col_type;
    @FXML
    private TableColumn<productData, Integer> add_col_time;
    @FXML
    private TableColumn<productData, Double> add_col_bju;
    @FXML
    private TableColumn<productData, Integer> add_col_difficulty;
    @FXML
    private TextField add_prodID;
    @FXML
    private TextField add_prodName;
    @FXML
    private TextField add_time;
    @FXML
    private TextField add_bju;
    @FXML
    private ComboBox<String> add_type;
    @FXML
    private ComboBox<String> add_difficulty;
    @FXML
    private ImageView add_imageView;
    @FXML
    private Button add_imageBtn;
    @FXML
    private Button add_prodBtn;
    @FXML
    private Button add_importBtn;
    @FXML
    private Button add_changeBtn;
    @FXML
    private Button add_removeBtn;


    @FXML
    private Button all_recBtn;
    @FXML
    private Button add_recBtn;
    @FXML
    private Button fav_recBtn;
    @FXML
    private Button cart_recBtn;
    @FXML
    private ComboBox<String> sort;
    @FXML
    private Button sort_Btn;

    private Alert alert;
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    private Image image;
    private ObservableList<productData> cardListData = FXCollections.observableArrayList();

    public ObservableList<productData> menuGetData() {
        String sql = "SELECT * FROM product";
        ObservableList<productData> listData = FXCollections.observableArrayList();
        connect = database.connectDB();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            productData prod;
            while (result.next()) {
                prod = new productData(result.getInt("id")
                        , result.getInt("prod_id")
                        , result.getString("prod_name")
                        , result.getString("type")
                        , result.getInt("time")
                        , result.getDouble("bju")
                        , result.getInt("difficulty")
                        , result.getString("image"));
                listData.add(prod);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    public ObservableList<productData> menuGetDataByType(String type) {
        String sql = "SELECT * FROM product WHERE type = '" + type + "'";
        ObservableList<productData> listData = FXCollections.observableArrayList();
        connect = database.connectDB();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            productData prod;
            while (result.next()) {
                prod = new productData(result.getInt("id")
                        , result.getInt("prod_id")
                        , result.getString("prod_name")
                        , result.getString("type")
                        , result.getInt("time")
                        , result.getDouble("bju")
                        , result.getInt("difficulty")
                        , result.getString("image"));
                listData.add(prod);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    public void menuDisplayCard() {
        cardListData.clear();
        cardListData.addAll(menuGetData());
        int row = 0;
        int column = 0;
        if (!(menu_gridPane == null)) {
            menu_gridPane.getChildren().clear();
            menu_gridPane.getRowConstraints().clear();
            menu_gridPane.getColumnConstraints().clear();
        }
        for (int q = 0; q < cardListData.size(); q++) {
            try {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("cardProduct.fxml"));
                AnchorPane pane = load.load();
                cardProductController cardC = load.getController();
                cardC.setData(cardListData.get(q));
                if (column == 3) {
                    column = 0;
                    row += 1;
                }
                menu_gridPane.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(10));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void menuDisplayCardByType(String type) {
        cardListData.clear();
        cardListData.addAll(menuGetDataByType(type));
        int row = 0;
        int column = 0;
        if (!(menu_gridPane == null)) {
            menu_gridPane.getChildren().clear();
            menu_gridPane.getRowConstraints().clear();
            menu_gridPane.getColumnConstraints().clear();
        }
        for (int q = 0; q < cardListData.size(); q++) {
            try {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("cardProduct.fxml"));
                AnchorPane pane = load.load();
                cardProductController cardC = load.getController();
                cardC.setData(cardListData.get(q));
                if (column == 3) {
                    column = 0;
                    row += 1;
                }
                menu_gridPane.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(10));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String[] typeList = {"Первое", "Второе", "Десерт", "Напиток", "Салат"};

    public void menuTypeList() {
        List<String> typeL = new ArrayList<>();
        for (String data : typeList) {
            typeL.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(typeL);
        sort.setItems(listData);
    }

    public void sortBtn() {
        String text = sort.getSelectionModel().getSelectedItem();
        if(add_form.isVisible() || cart_form.isVisible()){
            return;
        } else if (text.equals("Первое")) {
            menuDisplayCardByType("Первое");
            favDisplayCardByType("Первое");
        } else if (text.equals("Второе")) {
            menuDisplayCardByType("Второе");
            favDisplayCardByType("Второе");
        } else if (text.equals("Десерт")) {
            menuDisplayCardByType("Десерт");
            favDisplayCardByType("Десерт");
        } else if (text.equals("Напиток")) {
            menuDisplayCardByType("Напиток");
            favDisplayCardByType("Напиток");
        } else if (text.equals("Салат")) {
            menuDisplayCardByType("Салат");
            favDisplayCardByType("Салат");
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Выберите значение!");
            alert.showAndWait();
        }
    }


    public void addShowData() {
        cardListData = menuGetData();
        add_col_prodId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        add_col_prodName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        add_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        add_col_time.setCellValueFactory(new PropertyValueFactory<>("time"));
        add_col_bju.setCellValueFactory(new PropertyValueFactory<>("bju"));
        add_col_difficulty.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
        add_tableView.setItems(cardListData);
    }

    public void addSelectOrder() {
        productData prod = add_tableView.getSelectionModel().getSelectedItem();
        int num = add_tableView.getSelectionModel().getSelectedIndex();
        if ((num - 1) < -1) return;
        getid = prod.getId();
    }

    private String[] add_typeList = {"Первое", "Второе", "Десерт", "Напиток", "Салат"};

    public void addTypeList() {
        List<String> typeL = new ArrayList<>();
        for (String data : add_typeList) {
            typeL.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(typeL);
        add_type.setItems(listData);
    }

    private String[] add_difficultyList = {"1", "2", "3", "4", "5"};

    public void addDifficultyList() {
        List<String> typeL = new ArrayList<>();
        for (String data : add_difficultyList) {
            typeL.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(typeL);
        add_difficulty.setItems(listData);
    }

    public void addAddBtn() {
        if (add_prodID.getText().isEmpty()
                || add_prodName.getText().isEmpty()
                || add_type.getSelectionModel().getSelectedItem() == null
                || add_time.getText().isEmpty()
                || add_bju.getText().isEmpty()
                || add_difficulty.getSelectionModel().getSelectedItem() == null) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Заполните все поля!");
            alert.showAndWait();
        } else {
            String checkProdID = "SELECT prod_id FROM product WHERE prod_id = '"
                    + add_prodID.getText() + "'";
            connect = database.connectDB();
            try {
                statement = connect.createStatement();
                result = statement.executeQuery(checkProdID);
                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Данный рецепт уже существует!");
                    alert.showAndWait();
                } else {
                    String insertData = "INSERT INTO product "
                            + "(id, prod_id, prod_name, type, time, bju, difficulty, image) "
                            + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, add_prodID.getText());
                    prepare.setString(2, add_prodID.getText());
                    prepare.setString(3, add_prodName.getText());
                    prepare.setString(4, (String) add_type.getSelectionModel().getSelectedItem());
                    prepare.setString(5, add_time.getText());
                    prepare.setString(6, add_bju.getText());
                    prepare.setString(7, (String) add_difficulty.getSelectionModel().getSelectedItem());
                    if (data.path == null) {
                        prepare.setString(8, null);
                    } else {
                        String path = data.path;
                        path = path.replace("\\", "\\\\");
                        prepare.setString(8, path);
                    }
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Рецепт добавлен!");
                    alert.showAndWait();

                    addShowData();
                    addClearBtn();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addChangeBtn() {
        connect = database.connectDB();
        if (getid == 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Выделите рецепт, который хотите изменить!");
            alert.showAndWait();
        } else {
            try {
                String check = "SELECT * FROM product WHERE id = " + getid;
                prepare = connect.prepareStatement(check);
                ResultSet resultSet = prepare.executeQuery();
                if (!resultSet.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Выделите рецепт, который хотите изменить!");
                    alert.showAndWait();
                } else {
                    if (add_prodID.getText().isEmpty()
                            & add_prodName.getText().isEmpty()
                            & add_type.getSelectionModel().getSelectedItem() == null
                            & add_time.getText().isEmpty()
                            & add_bju.getText().isEmpty()
                            & add_difficulty.getSelectionModel().getSelectedItem() == null) {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Все поля пусты!");
                        alert.showAndWait();
                    } else {
                        try {
                            alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation Message");
                            alert.setHeaderText(null);
                            alert.setContentText("Вы уверены, что хотите изменить данный рецепт?");
                            Optional<ButtonType> option = alert.showAndWait();

                            if (option.get().equals(ButtonType.OK)) {
                                if (!(add_prodID.getText().isEmpty())) {
                                    alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Error Message");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Нельзя изменить номер рецепта!");
                                    alert.showAndWait();
//                                    String checkProdID = "SELECT prod_id FROM product WHERE prod_id = '"
//                                            + add_prodID.getText() + "'";
//                                    connect = database.connectDB();
//                                    try {
//                                        statement = connect.createStatement();
//                                        result = statement.executeQuery(checkProdID);
//                                        if (result.next()) {
//                                            alert = new Alert(Alert.AlertType.ERROR);
//                                            alert.setTitle("Error Message");
//                                            alert.setHeaderText(null);
//                                            alert.setContentText("Данный номер уже занят!");
//                                            alert.showAndWait();
//                                        }else{
//                                            String updateData = "UPDATE product SET prod_id = '" + add_prodID.getText() +
//                                                    "' WHERE id = '" + getid + "'";
//                                            String updateDataID = "UPDATE product SET id = '" + getid + "' WHERE id = '" + getid + "'";
//                                            prepare = connect.prepareStatement(updateData);
//                                            prepare.executeUpdate();
//                                            prepare = connect.prepareStatement(updateDataID);
//                                            prepare.executeUpdate();
//                                        }
//                                    }catch(Exception e){e.printStackTrace();}
                                }else {
                                    if (!(add_prodName.getText().isEmpty())) {
                                        String checkProdID = "SELECT prod_name FROM product WHERE prod_name = '"
                                                + add_prodName.getText() + "'";
                                        connect = database.connectDB();
                                        try {
                                            statement = connect.createStatement();
                                            result = statement.executeQuery(checkProdID);
                                            if (result.next()) {
                                                alert = new Alert(Alert.AlertType.ERROR);
                                                alert.setTitle("Error Message");
                                                alert.setHeaderText(null);
                                                alert.setContentText("Данное название уже занято!");
                                                alert.showAndWait();
                                            } else {
                                                String updateData = "UPDATE product SET prod_name = '" + add_prodName.getText() + "' WHERE id = '" + getid + "'";
                                                prepare = connect.prepareStatement(updateData);
                                                prepare.executeUpdate();
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    if (!(add_type.getSelectionModel().getSelectedItem() == null)) {
                                        String updateData = "UPDATE product SET type = '" + add_type.getSelectionModel().getSelectedItem()
                                                + "' WHERE id = '" + getid + "'";
                                        prepare = connect.prepareStatement(updateData);
                                        prepare.executeUpdate();
                                    }
                                    if (!(add_time.getText().isEmpty())) {
                                        String updateData = "UPDATE product SET time = '" + add_time.getText() + "' WHERE id = '" + getid + "'";
                                        prepare = connect.prepareStatement(updateData);
                                        prepare.executeUpdate();
                                    }
                                    if (!(add_bju.getText().isEmpty())) {
                                        String updateData = "UPDATE product SET bju = '" + add_bju.getText() + "' WHERE id = '" + getid + "'";
                                        prepare = connect.prepareStatement(updateData);
                                        prepare.executeUpdate();
                                    }
                                    if (!(add_difficulty.getSelectionModel().getSelectedItem() == null)) {
                                        String updateData = "UPDATE product SET difficulty = '" + add_difficulty.getSelectionModel().getSelectedItem()
                                                + "' WHERE id = '" + getid + "'";
                                        prepare = connect.prepareStatement(updateData);
                                        prepare.executeUpdate();
                                    }
//                                    String path = data.path;
//                                    if (data.path != null) {
//                                        path = path.replace("\\", "\\\\");
//                                        String updateData = "UPDATE product SET image = '" + path + "' WHERE id = '" + getid + "'";
//                                        prepare = connect.prepareStatement(updateData);
//                                        prepare.executeUpdate();
//                                    }

                                    alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Information Message");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Рецепт изменён!");
                                    alert.showAndWait();

                                    cartUpdateBtn(getid);
                                    favUpdateBtn(getid);
                                    addClearBtn();
                                    addShowData();
                                    getid = 0;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        getid = 0;
                        addShowData();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addClearBtn() {
        add_prodID.setText("");
        add_prodName.setText("");
        add_type.getSelectionModel().clearSelection();
        add_time.setText("");
        add_bju.setText("");
        add_difficulty.getSelectionModel().clearSelection();
        data.path = "";
        data.id = 0;
        add_imageView.setImage(null);
    }

    public void addImportBtn() {

    }

    public void addRemoveBtn() {
        if (getid == 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Выделите рецепт, который хотите удалить!");
            alert.showAndWait();
        } else {
            try {
                String check = "SELECT * FROM product WHERE id = " + getid;
                prepare = connect.prepareStatement(check);
                ResultSet resultSet = prepare.executeQuery();
                if (!resultSet.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Выделите рецепт, который хотите удалить!");
                    alert.showAndWait();
                } else {
                    String deleteData = "DELETE FROM product WHERE id = " + getid;
                    connect = database.connectDB();
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Вы уверены, что хотите удалить данный рецепт?");
                    Optional<ButtonType> option = alert.showAndWait();
                    if (option.get().equals(ButtonType.OK)) {
                        prepare = connect.prepareStatement(deleteData);
                        prepare.executeUpdate();
                        cartUpdateBtn(getid);
                        favUpdateBtn(getid);
                    }
                    getid = 0;
                    addShowData();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    @FXML
    void addHandleButtonAction(ActionEvent event) {
        if (getid == 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Выделите рецепт, который хотите открыть!");
            alert.showAndWait();
        } else {
            try {
                String check = "SELECT * FROM product WHERE id = " + getid;
                prepare = connect.prepareStatement(check);
                result = prepare.executeQuery();
                if (!result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Выделите рецепт, который хотите открыть!");
                    alert.showAndWait();
                } else {
                    int index = add_tableView.getSelectionModel().getSelectedIndex();
                    productData prodData = add_tableView.getItems().get(index);
                    showProdOpen(prodData);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addImageBtn() {
        connect = database.connectDB();
        if (getid == 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Выделите рецепт, к которому хотите добавить изображение!");
            alert.showAndWait();
        } else {
            try {
                String check = "SELECT * FROM product WHERE id = " + getid;
                prepare = connect.prepareStatement(check);
                ResultSet resultSet = prepare.executeQuery();
                if (!resultSet.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Выделите рецепт, к которому хотите добавить изображение!");
                    alert.showAndWait();
                } else {
                    FileChooser openFile = new FileChooser();
                    openFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image File", "*png", "*jpg"));
                    File file = openFile.showOpenDialog(add_form.getScene().getWindow());
                    if (file != null) {
                        data.path = file.getAbsolutePath();
                        image = new Image(file.toURI().toString(), 120, 127, false, true); // изображение импорта
                        add_imageView.setImage(image);
                        try {
                            String path = data.path;
                            connect = database.connectDB();

                            alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation Message");
                            alert.setHeaderText(null);
                            alert.setContentText("Сделать данное изображение обложкой рецепта?");
                            Optional<ButtonType> option = alert.showAndWait();
                            if (option.get().equals(ButtonType.OK)) {
                                path = path.replace("\\", "\\\\");
                                String updateData = "UPDATE product SET image = '" + path + "' WHERE id = '" + getid + "'";
                                prepare = connect.prepareStatement(updateData);
                                prepare.executeUpdate();

                                alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Information Message");
                                alert.setHeaderText(null);
                                alert.setContentText("Изображение добавлено");

                                cartUpdateImage(getid);
                                favUpdateImage(getid);
                                addClearBtn();
                                addShowData();
                                getid = 0;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public ObservableList<productData> cartGetData() {
        String sql = "SELECT * FROM cart";
        ObservableList<productData> listData = FXCollections.observableArrayList();
        connect = database.connectDB();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            productData prod;
            while (result.next()) {
                prod = new productData(result.getInt("id")
                        , result.getInt("prod_id")
                        , result.getString("prod_name")
                        , result.getString("type")
                        , result.getInt("time")
                        , result.getDouble("bju")
                        , result.getInt("difficulty")
                        , result.getInt("quantity")
                        , result.getString("image"));
                listData.add(prod);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }
    private ObservableList<productData> cartListData;

    public void cartShowData() {
        cartListData = cartGetData();
        cart_col_prodId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        cart_col_prodName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        cart_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        cart_col_bju.setCellValueFactory(new PropertyValueFactory<>("bju"));
        cart_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        cart_tableView.setItems(cartListData);
    }
    private int getid;
    public void cartSelectOrder(){
        productData prod = cart_tableView.getSelectionModel().getSelectedItem();
        int num = cart_tableView.getSelectionModel().getSelectedIndex();
        if((num - 1) < -1) return;
        getid = prod.getId();
    }

    public void cartRemoveBtn() {
        if (getid == 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Выделите рецепт, который хотите удалить!");
            alert.showAndWait();
        } else {
            try {
                String check = "SELECT * FROM cart WHERE id = " + getid;
                prepare = connect.prepareStatement(check);
                ResultSet resultSet = prepare.executeQuery();
                if (!resultSet.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Выделите рецепт, который хотите удалить!");
                    alert.showAndWait();
                } else {
                    String deleteData = "DELETE FROM cart WHERE id = " + getid;
                    connect = database.connectDB();
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Вы уверены, что хотите удалить данный рецепт?");
                    Optional<ButtonType> option = alert.showAndWait();
                    if (option.get().equals(ButtonType.OK)) {
                        prepare = connect.prepareStatement(deleteData);
                        prepare.executeUpdate();
                    }
                    getid = 0;
                    cartShowData();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void cartUpdateBtn(int index) {
        try {
            String check = "SELECT * FROM cart";
            prepare = connect.prepareStatement(check);
            result = prepare.executeQuery();
            while (result.next()) {
                String checkID = "SELECT * FROM product WHERE prod_id = '" + index + "'";
                connect = database.connectDB();
                try {
                    prepare = connect.prepareStatement(checkID);
                    result = prepare.executeQuery();
                    if (!result.next()) {
                        String deleteData = "DELETE FROM cart WHERE id = '" + index + "'";
                        prepare = connect.prepareStatement(deleteData);
                        prepare.executeUpdate();
                        cartShowData();
                    } else {
                        String updateData = "UPDATE cart SET prod_name = '"
                                + result.getString("prod_name") + "', type = '"
                                + result.getString("type") + "', time = '"
                                + result.getInt("time") + "', bju = '"
                                + result.getDouble("bju") + "', difficulty = '"
                                + result.getInt("difficulty") + "', image = '"
                                + result.getString("image") + "' WHERE id = '" + index + "'";
                        prepare = connect.prepareStatement(updateData);
                        prepare.executeUpdate();
                        cartShowData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cartUpdateImage(int index) {
        try {
            String check = "SELECT * FROM cart";
            prepare = connect.prepareStatement(check);
            result = prepare.executeQuery();
            while (result.next()) {
                String checkID = "SELECT * FROM product WHERE prod_id = '" + index + "'";
                connect = database.connectDB();
                try {
                    prepare = connect.prepareStatement(checkID);
                    result = prepare.executeQuery();
                    if (!result.next()) {
                        String deleteData = "DELETE FROM cart WHERE id = '" + index + "'";
                        prepare = connect.prepareStatement(deleteData);
                        prepare.executeUpdate();
                        cartShowData();
                    } else {
                        String path = data.path;
                        if (data.path != null) {
                            path = path.replace("\\", "\\\\");
                        }
                        String updateData = "UPDATE cart SET image = '"
                                + path + "' WHERE id = '" + index + "'";
                        prepare = connect.prepareStatement(updateData);
                        prepare.executeUpdate();
                        cartShowData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cartPayBtn() {
        try {
            String check = "SELECT * FROM cart";
            prepare = connect.prepareStatement(check);
            result = prepare.executeQuery();
            if (!result.next()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Корзина пуста!");
                alert.showAndWait();
            } else {
                String deleteData = "DELETE FROM cart";
                connect = database.connectDB();
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Подтвердите покупку");
                Optional<ButtonType> option = alert.showAndWait();
                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(deleteData);
                    prepare.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Покупка оформлена!");
                    alert.showAndWait();
                }
                cartShowData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void cartHandleButtonAction(ActionEvent event) {
        if (getid == 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Выделите рецепт, который хотите открыть!");
            alert.showAndWait();
        } else {
            try {
                String check = "SELECT * FROM cart WHERE id = " + getid;
                prepare = connect.prepareStatement(check);
                ResultSet resultSet = prepare.executeQuery();
                if (!resultSet.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Выделите рецепт, который хотите открыть!");
                    alert.showAndWait();
                } else {
                    int index = cart_tableView.getSelectionModel().getSelectedIndex();
                    productData prodData = cart_tableView.getItems().get(index);
                    showProdOpen(prodData);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public ObservableList<productData> favGetData() {
        String sql = "SELECT * FROM fav";
        ObservableList<productData> listData = FXCollections.observableArrayList();
        connect = database.connectDB();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            productData prod;
            while (result.next()) {
                prod = new productData(result.getInt("id")
                        , result.getInt("prod_id")
                        , result.getString("prod_name")
                        , result.getString("type")
                        , result.getInt("time")
                        , result.getDouble("bju")
                        , result.getInt("difficulty")
                        , result.getString("image"));
                listData.add(prod);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }
    public ObservableList<productData> favGetDataByType(String type) {
        String sql = "SELECT * FROM fav WHERE type = '" + type + "'";
        ObservableList<productData> listData = FXCollections.observableArrayList();
        connect = database.connectDB();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            productData prod;
            while (result.next()) {
                prod = new productData(result.getInt("id")
                        , result.getInt("prod_id")
                        , result.getString("prod_name")
                        , result.getString("type")
                        , result.getInt("time")
                        , result.getDouble("bju")
                        , result.getInt("difficulty")
                        , result.getString("image"));
                listData.add(prod);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }
    public void favDisplayCard(){
        cardListData.clear();
        cardListData.addAll(favGetData());
        int row = 0;
        int column = 0;
        if(!(fav_gridPane == null)) {
            fav_gridPane.getChildren().clear();
            fav_gridPane.getRowConstraints().clear();
            fav_gridPane.getColumnConstraints().clear();
        }
        for(int q = 0; q < cardListData.size(); q++){
            try{
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("cardFav.fxml"));
                AnchorPane pane = load.load();
                cardFavController cardC = load.getController();
                cardC.setData(cardListData.get(q));
                if(column == 3){
                    column = 0;
                    row+=1;
                }
                fav_gridPane.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(10));
            }catch(Exception e){e.printStackTrace();}
        }
    }
    public void favDisplayCardByType(String type){
        cardListData.clear();
        cardListData.addAll(favGetDataByType(type));
        int row = 0;
        int column = 0;
        if(!(fav_gridPane == null)) {
            fav_gridPane.getChildren().clear();
            fav_gridPane.getRowConstraints().clear();
            fav_gridPane.getColumnConstraints().clear();
        }
        for(int q = 0; q < cardListData.size(); q++){
            try{
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("cardFav.fxml"));
                AnchorPane pane = load.load();
                cardFavController cardC = load.getController();
                cardC.setData(cardListData.get(q));
                if(column == 3){
                    column = 0;
                    row+=1;
                }
                fav_gridPane.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(10));
            }catch(Exception e){e.printStackTrace();}
        }
    }

    public void favUpdateBtn(int index) {
        try {
            String check = "SELECT * FROM fav";
            prepare = connect.prepareStatement(check);
            result = prepare.executeQuery();
            while (result.next()) {
                String checkID = "SELECT * FROM product WHERE id = '" + index + "'";
                connect = database.connectDB();
                try {
                    prepare = connect.prepareStatement(checkID);
                    result = prepare.executeQuery();
                    if (!result.next()) {
                        String deleteData = "DELETE FROM fav WHERE id = '" + index + "'";
                        prepare = connect.prepareStatement(deleteData);
                        prepare.executeUpdate();
                        favDisplayCard();
                    } else {
                        String updateData = "UPDATE fav SET prod_name = '"
                                + result.getString("prod_name") + "', type = '"
                                + result.getString("type") + "', time = '"
                                + result.getInt("time") + "', bju = '"
                                + result.getDouble("bju") + "', difficulty = '"
                                + result.getInt("difficulty") + "' WHERE id = '" + index + "'";
                        prepare = connect.prepareStatement(updateData);
                        prepare.executeUpdate();
                        favDisplayCard();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void favUpdateImage(int index) {
        try {
            String check = "SELECT * FROM fav";
            prepare = connect.prepareStatement(check);
            result = prepare.executeQuery();
            while (result.next()) {
                String checkID = "SELECT * FROM product WHERE id = '" + index + "'";
                connect = database.connectDB();
                try {
                    prepare = connect.prepareStatement(checkID);
                    result = prepare.executeQuery();
                    if (!result.next()) {
                        String deleteData = "DELETE FROM fav WHERE id = '" + index + "'";
                        prepare = connect.prepareStatement(deleteData);
                        prepare.executeUpdate();
                        favDisplayCard();
                    } else {
                        String path = data.path;
                        if (data.path != null) {
                            path = path.replace("\\", "\\\\");
                        }
                        String updateData = "UPDATE fav SET image = '"
                                + path + "' WHERE id = '" + index + "'";
                        prepare = connect.prepareStatement(updateData);
                        prepare.executeUpdate();
                        favDisplayCard();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void switchForm(ActionEvent event){
        if(event.getSource() == all_recBtn) {
            all_form.setVisible(true);
            add_form.setVisible(false);
            fav_form.setVisible(false);
            cart_form.setVisible(false);
            menuDisplayCard();
        }else if(event.getSource() == add_recBtn){
            all_form.setVisible(false);
            add_form.setVisible(true);
            fav_form.setVisible(false);
            cart_form.setVisible(false);
            addShowData();
        }else if(event.getSource() == fav_recBtn){
            all_form.setVisible(false);
            add_form.setVisible(false);
            fav_form.setVisible(true);
            cart_form.setVisible(false);
            favDisplayCard();
        }else if(event.getSource() == cart_recBtn) {
            all_form.setVisible(false);
            add_form.setVisible(false);
            fav_form.setVisible(false);
            cart_form.setVisible(true);
            cartShowData();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        menuDisplayCard();
        favDisplayCard();
        cartShowData();
        addShowData();
        menuTypeList();
        addTypeList();
        addDifficultyList();
    }
}
