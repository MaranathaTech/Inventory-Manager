package invmgr.Controllers;
import invmgr.AbstractClasses.Part;
import invmgr.Models.InHouse;
import invmgr.Models.Outsourced;
import invmgr.Models.Inventory;
import invmgr.AbstractClasses.ViewController;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

/**
 * <h1>PartController</h1>
 * This class handles the logical procedures necessary to initialize the view and control the various functions of that interface.
 * <p>
 * <b>Note:</b> PartController class is used to display both the add new part screen and the modify part screen.
 *
 * @author Ernie Lail
 * @version 1.0
 * @since 1.0
 */

public class PartController extends ViewController {

    private int currentId;

    @FXML
    private Label addPartLabel;

    @FXML
    private RadioButton outsourcedRadio;
    @FXML
    private RadioButton inHouseRadio;

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
    @FXML
    private TextField machineTextField;

    @FXML
    private Label machineLabel;

    /**
     * This method is used to set the machine label's text when inHouseRadio button is pressed.
     */
    @FXML
    private void inHouseRadioClick(ActionEvent event) {
        machineLabel.setText("Machine ID");
    }

    /**
     * This method is used to set the machine label's text when outsourcedRadio button is pressed.
     */
    @FXML
    private void outsourcedRadioClick(ActionEvent event) {
        machineLabel.setText("Company Name");
    }

    /**
     * This method is used to save the part to the PartsCollection.
     */
    @FXML
    private void addClick(ActionEvent event) throws IOException {

        String partName = nameTextField.getText();
        String partInv = invTextField.getText();
        String partPrice = priceTextField.getText();
        String partMin = minTextField.getText();
        String partMax = maxTextField.getText();

        if(partName.isEmpty() || partInv.isEmpty() || partPrice.isEmpty() && partMin.isEmpty() || partMax.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initModality(Modality.NONE);
            alert.setHeaderText("Empty Fields Not Allowed");
            alert.setContentText("Please fill any empty fields.");
            alert.showAndWait();
        }
        else{
            if(Integer.parseInt(partMin) > Integer.parseInt(partMax)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.NONE);
                alert.setHeaderText("Value Error");
                alert.setContentText("Min value must not be greater than max value.");
                alert.showAndWait();
                return;
            }

            if(Integer.parseInt(partMin) > Integer.parseInt(partInv)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.NONE);
                alert.setHeaderText("Value Error");
                alert.setContentText("Inventory must be greater than min value.");
                alert.showAndWait();
                return;
            }
            else if(Integer.parseInt(partMax) < Integer.parseInt(partInv)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.NONE);
                alert.setHeaderText("Value Error");
                alert.setContentText("Inventory must be less than max value.");
                alert.showAndWait();
                return;
            }


            if (inHouseRadio.isSelected() == true) {
                InHouse inPart = new InHouse(
                        currentId,
                        partName,Double.parseDouble(partPrice),
                        Integer.parseInt(partInv),
                        Integer.parseInt(partMin),
                        Integer.parseInt(partMax),
                        Integer.parseInt(machineTextField.getText())
                );

                if(StartScreenController.indexToModify == -1) {
                    Inventory.addPart(inPart);
                }
                else{
                    Inventory.updatePart(inPart);
                    StartScreenController.indexToModify = -1;
                }
            }
            else {
                Outsourced outPart = new Outsourced(
                        currentId,
                        partName,Double.parseDouble(partPrice),
                        Integer.parseInt(partInv),
                        Integer.parseInt(partMin),
                        Integer.parseInt(partMax),
                        machineTextField.getText()
                );

                if(StartScreenController.indexToModify == -1) {
                    Inventory.addPart(outPart);
                }
                else{
                    Inventory.updatePart(outPart);
                    StartScreenController.indexToModify = -1;
                }
            }

            Parent addPartCancel = FXMLLoader.load(getClass().getResource("/invmgr/Views/StartScreen.fxml"));
            Scene scene = new Scene(addPartCancel);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }

    }

    /**
     * This method is used to cancel the part's dialog'.
     */
    @FXML
    private void cancelClick(ActionEvent event) throws IOException {
        StartScreenController.indexToModify = -1;
        Parent addPartCancel = FXMLLoader.load(getClass().getResource("/invmgr/Views/StartScreen.fxml"));
        Scene scene = new Scene(addPartCancel);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
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
            addPartLabel.setText("Modify Part");

            Part part = Inventory.getPart(StartScreenController.indexToModify);
            currentId = part.getId();
            idTextField.setText("Auto-Gen: " + currentId);
            nameTextField.setText(part.getName());
            invTextField.setText(Integer.toString(part.getStock()));
            priceTextField.setText(Double.toString(part.getPrice()));
            minTextField.setText(Integer.toString(part.getMin()));
            maxTextField.setText(Integer.toString(part.getMax()));

            if (part instanceof InHouse) {
                machineLabel.setText("Machine ID");
                InHouse inHouse = (InHouse) part;
                machineTextField.setText(Integer.toString(inHouse.getMachineId()));
                inHouseRadio.setSelected(true);
            }
            else {
                machineLabel.setText("Company Name");
                Outsourced outSourced = (Outsourced) part;
                machineTextField.setText(outSourced.getCompanyName());
                outsourcedRadio.setSelected(true);
            }
        }
        else{
            currentId = Inventory.getNewPartId();
            idTextField.setText("Auto-Gen: " + currentId);
        }
    }

}