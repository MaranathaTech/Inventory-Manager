package invmgr.Controllers;
import invmgr.AbstractClasses.Part;
import invmgr.AbstractClasses.ViewController;
import invmgr.Models.Inventory;
import invmgr.Models.Product;
import invmgr.Models.Inventory;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.BigDecimalStringConverter;
import javafx.util.converter.CurrencyStringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * <h1>ProductController</h1>
 * This class handles the logical procedures necessary to initialize the view and control the various functions of that interface.
 * <p>
 * <b>Note:</b> ProductController class is used to display both the add new product screen and the modify product screen.
 *
 * @author Ernie Lail
 * @version 1.0
 * @since 1.0
 */

public class ProductController extends ViewController {

    private int currentId;
    private ObservableList<Part> partsCollection = FXCollections.observableArrayList();

    @FXML
    private Label addProductLabel;

    //parts vars
    @FXML
    private TableView<Part> partTable;
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Integer> partInvCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private TextField partSearchBar;

    //parts collection vars
    @FXML
    private TableView<Part> partCollectionTable;
    @FXML
    private TableColumn<Part, Integer> partCollectionIdCol;
    @FXML
    private TableColumn<Part, String> partCollectionNameCol;
    @FXML
    private TableColumn<Part, Integer> partCollectionInvCol;
    @FXML
    private TableColumn<Part, Double> partCollectionPriceCol;

    //product text fields
    @FXML
    private TextField idTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField invTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField maxTextField;
    @FXML
    private TextField minTextField;

    /**
     * This method is used to add the selected item to the Product's parts table view.
     */
    @FXML
    private void addPart(ActionEvent event) {
        if(partTable.getSelectionModel().getSelectedItem() != null) {
            Part part = partTable.getSelectionModel().getSelectedItem();

            //check that the part has stock available
            if(part.getStock() == 0){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.initModality(Modality.NONE);
                alert.setHeaderText("No Stock Available");
                alert.setContentText("Please update the stock level in parts before trying to add the part to this product.");
                alert.showAndWait();
                return;
            }

            partsCollection.add(part);
            updatePartsCollectionTableView(partsCollection);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setHeaderText("No Selection");
            alert.setContentText("Please make a selection before trying to add.");
            alert.showAndWait();
        }
    }

    /**
     * This method is used to remove the selected item from the Product's parts table view.
     */
    @FXML
    private void removePart(ActionEvent event) {
        if(partCollectionTable.getSelectionModel().getSelectedItem() != null) {
            Part part = partCollectionTable.getSelectionModel().getSelectedItem();

            //confirm delete
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Remove Part");
            alert.setHeaderText("Confirm?");
            alert.setContentText("Are you sure you want to remove " + part.getName() + "?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                //PartsCollection.increaseStock(part);
                partsCollection.remove(part);
                updatePartsCollectionTableView(partsCollection);
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setHeaderText("No Selection");
            alert.setContentText("Please select a part to remove.");
            alert.showAndWait();
        }
    }

    /**
     * This method is used to cancel the products dialog'.
     */
    @FXML
    private void cancelClick(ActionEvent event) throws IOException {
        Parent addProductCancel = FXMLLoader.load(getClass().getResource("/invmgr/Views/StartScreen.fxml"));
        Scene scene = new Scene(addProductCancel);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     * This method is used to save the product to the Inventory.
     */
    @FXML
    private void addClick(ActionEvent event) throws IOException {
        String productName = nameTextField.getText();
        String productInv = invTextField.getText();
        String productPrice = priceTextField.getText();
        String productMin = minTextField.getText();
        String productMax = maxTextField.getText();
        ObservableList<Part> parts = partCollectionTable.getItems();

        if(productName.isEmpty() || productInv.isEmpty() || productPrice.isEmpty() && productMin.isEmpty() || productMax.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setHeaderText("Empty Fields Not Allowed");
            alert.setContentText("Please fill any empty fields.");
            alert.showAndWait();
        }
        else{
            if(Integer.parseInt(productMin) > Integer.parseInt(productMax)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.NONE);
                alert.setHeaderText("Value Error");
                alert.setContentText("Min value must not be greater than max value.");
                alert.showAndWait();
                return;
            }

            if(Integer.parseInt(productMin) > Integer.parseInt(productInv)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.NONE);
                alert.setHeaderText("Value Error");
                alert.setContentText("Inventory must be greater than min value.");
                alert.showAndWait();
                return;
            }
            else if(Integer.parseInt(productMax) < Integer.parseInt(productInv)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.NONE);
                alert.setHeaderText("Value Error");
                alert.setContentText("Inventory must be less than max value.");
                alert.showAndWait();
                return;
            }

            Product newProd = new Product(
                currentId,
                productName, Double.parseDouble(productPrice),
                Integer.parseInt(productInv),
                Integer.parseInt(productMin),
                Integer.parseInt(productMax),
                    parts
                );

            if(StartScreenController.indexToModify == -1){
                Inventory.addProduct(newProd);
            }
            else{
                Inventory.updateProduct(newProd);
                StartScreenController.indexToModify = -1;
            }

            Parent addProductCancel = FXMLLoader.load(getClass().getResource("/invmgr/Views/StartScreen.fxml"));
            Scene scene = new Scene(addProductCancel);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }



    }

    /**
     * This method is used to filter the parts table view.
     */
    @FXML
    private void handlePartsSearch(ActionEvent event) {
        String searchPart = partSearchBar.getText();
        ObservableList<Part> tempPartList = Inventory.lookupPart(searchPart);

        if(searchPart.equals("")  || searchPart.equals(" ")){
            updatePartsTableView();
        }
        else if (tempPartList.size() > 0) {
            partTable.setItems(tempPartList);
        }
        else{
            updatePartsTableView();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initModality(Modality.NONE);
            alert.setHeaderText("No items found");
            alert.setContentText("Your search didn't yield any results.");
            alert.showAndWait();

            partSearchBar.setText("");
        }
    }

    /**
     * This method is used to update the global parts table view to the latest data in the PartsCollection.
     */
    private void updatePartsTableView() {
        partTable.setItems(Inventory.getAllParts());
    }

    /**
     * This method is used to update the product's parts table view to the latest data in the partsCollection attribute.
     */
    private void updatePartsCollectionTableView(ObservableList<Part> partsCollection) {
        partCollectionTable.setItems(partsCollection);
    }

    /**
     * This method is used initialize the controller and set up the table cells.
     * @param url This is the URL
     * @param rb This is the ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        invTextField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        minTextField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        maxTextField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        priceTextField.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));

        if(StartScreenController.indexToModify != -1) {

            addProductLabel.setText("Modify Product");

            Product product = Inventory.getProduct(StartScreenController.indexToModify);
            currentId = product.getId();
            idTextField.setText("Auto-Gen: " + currentId);
            nameTextField.setText(product.getName());
            invTextField.setText(Integer.toString(product.getStock()));
            priceTextField.setText(Double.toString(product.getPrice()));
            minTextField.setText(Integer.toString(product.getMin()));
            maxTextField.setText(Integer.toString(product.getMax()));
            partsCollection = product.getAllAssociatedParts();
            updatePartsCollectionTableView(partsCollection);

        }
        else{
            currentId = Inventory.getNewProductId();
            idTextField.setText("Auto-Gen: " + currentId);
        }

        partIdCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        partNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        partInvCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStock()).asObject());
        partPriceCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

        partCollectionIdCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        partCollectionNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        partCollectionInvCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStock()).asObject());
        partCollectionPriceCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

        updatePartsTableView();
    }

}
