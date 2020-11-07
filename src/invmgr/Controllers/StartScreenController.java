package invmgr.Controllers;
import invmgr.Models.Inventory;
import invmgr.Models.Inventory;
import invmgr.AbstractClasses.ViewController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Optional;
import javafx.event.ActionEvent;
import java.io.IOException;
import invmgr.AbstractClasses.Part;
import invmgr.Models.Product;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * <h1>StartScreenController</h1>
 * This class handles the logical procedures necessary to initialize the view and control the various functions of that interface.
 * <p>
 * <b>Note:</b> StartScreenController class is used to display the main menu. Future releases should include search and clear buttons for the search fields.
 *
 * @author Ernie Lail
 * @version 1.0
 * @since 1.0
 */

public class StartScreenController extends ViewController {

    /**
     * Variable is used to track the item that is to be modified and to notify other controllers that they should load the data for that item.
     */
    public static int indexToModify = -1;

    //products vars
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> prodIdCol;
    @FXML
    private TableColumn<Product, String> prodNameCol;
    @FXML
    private TableColumn<Product, Integer> prodInvCol;
    @FXML
    private TableColumn<Product, Double> prodPriceCol;
    @FXML
    private TextField productSearchBar;


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

    /**
     * This method is used to filter the parts table view.
     */
    @FXML
    private void handlePartsSearch(ActionEvent event) {
        String searchPart = partSearchBar.getText();
        ObservableList<Part> tempPartList = Inventory.lookupPart(searchPart);

        if(searchPart == "" || searchPart == " "){
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
     * This method is used to filter the products table view.
     */
    @FXML
    private void handleProductsSearch(ActionEvent event) {

        String searchProduct = productSearchBar.getText();
        ObservableList<Product> tempProdList = Inventory.lookupProduct(searchProduct);

        if(searchProduct == "" || searchProduct == " "){
            updateProductsTableView();
        }
        else if (tempProdList.size() > 0) {
            productTable.setItems(tempProdList);

        }
        else{
            updateProductsTableView();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initModality(Modality.NONE);
            alert.setHeaderText("No items found");
            alert.setContentText("Your search didn't yield any results.");
            alert.showAndWait();

            productSearchBar.setText("");
        }

    }

    /**
     * This method is used to initialize the add new products dialog.
     */
    @FXML
    private void openAddProductsScreen(ActionEvent event) throws IOException {
        Parent addProduct = FXMLLoader.load(getClass().getResource("/invmgr/Views/ProductDialog.fxml"));
        Scene addProductScene = new Scene(addProduct);
        Stage addProductStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addProductStage.setScene(addProductScene);
        addProductStage.show();
    }

    /**
     * This method is used to initialize the modify product dialog.
     */
    @FXML
    private void openModifyProductsScreen(ActionEvent event) throws IOException {
        if(productTable.getSelectionModel().getSelectedItem() != null) {
            Product product = productTable.getSelectionModel().getSelectedItem();
            StartScreenController.indexToModify = Inventory.getProductIndex(product);
            Parent addProduct = FXMLLoader.load(getClass().getResource("/invmgr/Views/ProductDialog.fxml"));
            Scene addProductScene = new Scene(addProduct);
            Stage addProductStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            addProductStage.setScene(addProductScene);
            addProductStage.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initModality(Modality.NONE);
            alert.setHeaderText("No selection");
            alert.setContentText("Please select an item from the list to modify.");
            alert.showAndWait();
        }

    }

    /**
     * This method is used to delete a product from the table view
     */
    @FXML
    private void deleteProduct(ActionEvent event) {
        if(productTable.getSelectionModel().getSelectedItem() != null) {
            Product product = productTable.getSelectionModel().getSelectedItem();

            if(product.getAllAssociatedParts().size() > 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.NONE);
                alert.setHeaderText("Error");
                alert.setContentText("This product can't be deleted because there are parts associated with it. Please remove all parts from the product before trying to delete.");
                alert.showAndWait();
                return;
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Delete Product");
            alert.setHeaderText("Confirm?");
            alert.setContentText("Are you sure you want to delete " + product.getName() + "?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                Inventory.deleteProduct(product);
                updateProductsTableView();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initModality(Modality.NONE);
            alert.setHeaderText("No selection");
            alert.setContentText("Please make a selection and try to delete again.");
            alert.showAndWait();
        }
    }

    /**
     * This method is used to initialize the add new part dialog.
     */
    @FXML
    private void openAddPartScreen(ActionEvent event) throws IOException {
        Parent addPart = FXMLLoader.load(getClass().getResource("/invmgr/Views/PartDialog.fxml"));
        Scene addPartScene = new Scene(addPart);
        Stage addPartStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addPartStage.setScene(addPartScene);
        addPartStage.show();
    }

    /**
     * This method is used to initialize the modify part dialog.
     */
    @FXML
    private void openModifyPartScreen(ActionEvent event) throws IOException {
        if(partTable.getSelectionModel().getSelectedItem() != null){
            Part part = partTable.getSelectionModel().getSelectedItem();
            StartScreenController.indexToModify = Inventory.getPartIndex(part);
            Parent addPart = FXMLLoader.load(getClass().getResource("/invmgr/Views/PartDialog.fxml"));
            Scene addPartScene = new Scene(addPart);
            Stage addPartStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            addPartStage.setScene(addPartScene);
            addPartStage.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initModality(Modality.NONE);
            alert.setHeaderText("No selection");
            alert.setContentText("Please select an item from the list to modify.");
            alert.showAndWait();
        }
    }

    /**
     * This method is used to delete a part from the table view
     */
    @FXML
    private void deletePart(ActionEvent event) {
        if(partTable.getSelectionModel().getSelectedItem() != null){
            Part part = partTable.getSelectionModel().getSelectedItem();

            //check if part is used in a product prior to delete
            if(Inventory.partExistsInProduct(part) == true) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.NONE);
                alert.setHeaderText("Error");
                alert.setContentText("This part can't be deleted because there are products that use it. Please remove the part from the products before trying to delete.");
                alert.showAndWait();
            }
            else{
                //confirm delete
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.initModality(Modality.NONE);
                alert.setTitle("Delete Part");
                alert.setHeaderText("Confirm?");
                alert.setContentText("Are you sure you want to delete " + part.getName() + "?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {
                    Inventory.deletePart(part);
                    updatePartsTableView();
                }
            }

        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initModality(Modality.NONE);
            alert.setHeaderText("No selection");
            alert.setContentText("Please make a selection and try to delete again.");
            alert.showAndWait();
        }
    }

    /**
     * This method is used to update the parts table view to the latest data in the PartsCollection.
     */
    private void updatePartsTableView() {
        partTable.setItems(Inventory.getAllParts());
    }

    /**
     * This method is used to update the products table view to the latest data in the Inventory.
     */
    private void updateProductsTableView() {
        productTable.setItems(Inventory.getAllProducts());
    }


    /**
     * This method is used to exit the application
     */
    @FXML
    private void exitButton(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("Exit");
        alert.setHeaderText("Confirm Exit");
        alert.setContentText("Are you sure you want to exit?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    /**
     * This method is used initialize the controller and set up the table cells.
     *
     * Received a runtime error on modifying a part when no selection was made.
     * Added "if(partTable.getSelectionModel().getSelectedItem() != null)" to verify part was selected prior to pressing the button
     * and returned error if part was not selected.
     *
     * @param url This is the URL
     * @param rb This is the ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partIdCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        partNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        partInvCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStock()).asObject());
        partPriceCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

        prodIdCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        prodNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        prodInvCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStock()).asObject());
        prodPriceCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

        updatePartsTableView();
        updateProductsTableView();
    }

}
