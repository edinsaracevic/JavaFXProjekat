package org.edin;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.edin.model.Contract;
import org.edin.parser.XMLParser;

import java.net.URL;
import java.util.ResourceBundle;

public class PrimaryController implements Initializable {

    @FXML
    private Button buttonLoadDatabase;
    @FXML
    private Label lblFirstName;
    @FXML
    private Label lblLastName;
    @FXML
    private Label lblAddress;
    @FXML
    private Label lblSpeed;
    @FXML
    private Label lblBandwidth;
    @FXML
    private Label lblDuration;
    @FXML
    private TextField fldFirstName;
    @FXML
    private TextField fldLastName;
    @FXML
    private TextField fldAddress;
    @FXML
    private ComboBox<Integer> fldSpeed;
    @FXML
    private ComboBox<String> fldBandwidth;
    @FXML
    private ComboBox<String> fldDuration;
    @FXML
    private TableView<Contract> tableContract;
    @FXML
    private TableColumn<Contract, String> colFirstName;
    @FXML
    private TableColumn<Contract, String> colLastName;
    @FXML
    private TableColumn<Contract, String> colAddress;
    @FXML
    private TableColumn<Contract, Integer> colSpeed;
    @FXML
    private TableColumn<Contract, String> colBandwidth;
    @FXML
    private TableColumn<Contract, String> colDuration;
    @FXML
    private Button buttonInsert;
    @FXML
    private Button buttonUpdate;
    @FXML
    private Button buttonDelete;
    @FXML
    private Label labelMessage;
    @FXML
    private TextField fieldSearch;
    @FXML
    private Button buttonSearch;
    @FXML
    private Button buttonExit;

    private ObservableList<Contract> contractList = FXCollections.observableArrayList();
    private Contract contract;
    private final XMLParser xml = new XMLParser();

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contractList = xml.loadContract();
        showContract();
        ObservableList<Integer> speedList = FXCollections.observableArrayList();
        speedList.addAll(2, 5, 10, 20, 50, 100);
        fldSpeed.getItems().setAll(speedList);
        ObservableList<String> bandwidthList = FXCollections.observableArrayList();
        bandwidthList.addAll("1", "5", "10", "100", "Flat");
        fldBandwidth.getItems().addAll(bandwidthList);
        ObservableList<String> durationList = FXCollections.observableArrayList();
        durationList.addAll("1 year", "2 years");
        fldDuration.getItems().addAll(durationList);
        labelMessage.setTextFill(Color.YELLOW);
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == buttonInsert) {
            if (isValid()){
                addContract();
            }
        } else if (event.getSource() == buttonUpdate) {
            if (isValid()) {
                updatePerson();
            }
        } else if (event.getSource() == buttonDelete) {
            Contract c = null;
            c = tableContract.getSelectionModel().getSelectedItem();
            if (c == null) {
                AlertMessage.display("Warning", "Couldn't delete the contract as nothing is selected");
            } else {
                deleteContract(c);
            }
        } else if (event.getSource() == buttonLoadDatabase) {
            showContract();
        } else if (event.getSource() == buttonSearch) {
            if (fieldSearch.getText().isBlank()) {
                AlertMessage.display("Warning", "Can't search for nothing, right?");
            } else {
                showSearchedResult();
            }
        } else if (event.getSource() == buttonExit) {
            AlertMessage.display("Thank you", "Thank you for checking the app :)");
            System.exit(0);
        }
    }

    public ObservableList<Contract> searchResult() {
        ObservableList<Contract> list = FXCollections.observableArrayList();
        String search = fieldSearch.getText();
        for (Contract c : contractList) {
            if (c.getFirstName().toLowerCase().contains(search.toLowerCase()) && !list.contains(c)) {
                list.add(c);
            } else if (c.getLastName().toLowerCase().contains(search.toLowerCase()) && !list.contains(c)) {
                list.add(c);
            } else if (c.getAddress().toLowerCase().contains(search.toLowerCase()) && !list.contains(c)) {
                list.add(c);
            }
        }
        fieldSearch.clear();
        return list;
    }

    public ObservableList<Contract> getContractList() {
        return xml.loadContract();
    }

    public void showSearchedResult() {
        setTableItems();
        tableContract.setItems(searchResult());
    }

    public void showContract() {
        setTableItems();
        tableContract.setItems(contractList);
    }

    private void updatePerson() {
        for (Contract c : contractList) {
            if (c == (tableContract.getSelectionModel().getSelectedItem())) {
                c.setFirstName(fldFirstName.getText());
                c.setLastName(fldLastName.getText());
                c.setAddress(fldAddress.getText());
                c.setSpeed(fldSpeed.getSelectionModel().getSelectedItem());
                c.setBandwidth(fldBandwidth.getSelectionModel().getSelectedItem());
                c.setDuration(fldDuration.getSelectionModel().getSelectedItem());
            }
        }
        tableContract.refresh();
        xml.saveContracts(contractList);
    }

    private void deleteContract(Contract c) {
        contractList.remove(c);
        tableContract.refresh();
        xml.saveContracts(contractList);
    }

    @FXML
    private void handleMouseAction(MouseEvent mouseEvent) {
        if (!tableContract.getSelectionModel().isEmpty()) {
            Contract contract = tableContract.getSelectionModel().getSelectedItem();
            fldFirstName.setText(contract.getFirstName());
            fldLastName.setText(contract.getLastName());
            fldAddress.setText(contract.getAddress());
            fldSpeed.setValue(contract.getSpeed());
            fldBandwidth.setValue(contract.getBandwidth());
            fldDuration.setValue(contract.getDuration());
        }
    }

    @FXML
    private void addContract() {
        Contract c = new Contract();
        c.setFirstName(fldFirstName.getText());
        c.setLastName(fldLastName.getText());
        c.setAddress(fldAddress.getText());
        c.setSpeed(fldSpeed.getSelectionModel().getSelectedItem());
        c.setBandwidth(fldBandwidth.getSelectionModel().getSelectedItem());
        c.setDuration(fldDuration.getSelectionModel().getSelectedItem());

        boolean duplicate = false;
        for (Contract con: contractList){
            if ((con.getFirstName().equals(c.getFirstName()) && (con.getLastName().equals(c.getLastName())))) {
                duplicate = true;
                break;
            }
        }

        if (duplicate) {
            AlertMessage.display("Warning", "There is already customer with the same name and surname in the database");
        } else {
            contractList.add(c);
            tableContract.refresh();
            xml.saveContracts(contractList);
            clearFields();
            AlertMessage.display("Notification", "Successfully added new contract");
        }
    }

    @FXML
    private void closeForm() {
        Platform.exit();
    }

    private void setTableItems() {
        colFirstName.setCellValueFactory(new PropertyValueFactory<Contract, String>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<Contract, String>("lastName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<Contract, String>("address"));
        colSpeed.setCellValueFactory(new PropertyValueFactory<Contract, Integer>("speed"));
        colBandwidth.setCellValueFactory(new PropertyValueFactory<Contract, String>("bandwidth"));
        colDuration.setCellValueFactory(new PropertyValueFactory<Contract, String>("duration"));
    }

    private boolean isValid(){
        boolean valid = false;
        if (fldFirstName.getText().isEmpty()) {
            AlertMessage.display("Warning", "Please enter first name. First name can't be empty");
        } else if (fldLastName.getText().isBlank()) {
            AlertMessage.display("Warning", "Please enter last name. Last name can't be empty");
        } else if (fldAddress.getText().isBlank()) {
            AlertMessage.display("Warning", "Please enter address. Address can't be empty");
        } else if (fldSpeed.getSelectionModel().isEmpty()) {
            AlertMessage.display("Warning", "Please select speed in contract. Speed can't be empty");
        } else if (fldBandwidth.getSelectionModel().isEmpty()) {
            AlertMessage.display("Warning", "Please select desired bandwidth. Bandwidth can't be empty");
        } else if (fldDuration.getSelectionModel().isEmpty()) {
            AlertMessage.display("Warning", "Please select desired duration of contract. Duration can't be empty");
        } else{
            valid = true;
        }
        return valid;
    }

    @FXML
    private void clearFields() {
        fldFirstName.clear();
        fldLastName.clear();
        fldAddress.clear();
        fldSpeed.getSelectionModel().clearSelection();
        fldBandwidth.getSelectionModel().clearSelection();
        fldDuration.getSelectionModel().clearSelection();
    }
}
