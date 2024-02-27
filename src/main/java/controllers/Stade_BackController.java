/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import entities.Reservation;
import entities.ImagesStade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import entities.Stade;
import javafx.stage.FileChooser;
import services.Reservation_Service;
import services.Stade_Service;


public class Stade_BackController implements Initializable {

    @FXML
    private Pane pnGestionStade;
    @FXML
    private TextField tfSearchStade;
    @FXML
    private ComboBox<String> cbLieuStade;
    @FXML
    private TableView<Stade> tvStade;
    @FXML
    private TableColumn<Stade, String> colNomStade;
    @FXML
    private TableColumn<Stade, String> colLieuStade;
    @FXML
    private TableColumn<Stade, ImageView> colImageStade;
    @FXML
    private Button btnPreviousStade;
    @FXML
    private ImageView ImgViewStade;
    @FXML
    private Button btnNextStade;
    @FXML
    private VBox hbDetailsStade;
    @FXML
    private Label lbNomStade;
    @FXML
    private Label lbLieuStade;
    @FXML
    private Label lbCapacityStade;
    @FXML
    private Label lbNumeroStade;
    @FXML
    private Button btnAddStade;
    @FXML
    private Pane pnReservation;
    @FXML
    private Button btnUpdateStade;
    @FXML
    private Button btnDeleteStade;
    @FXML
    private Pane pnAddUpdateStade;
    @FXML
    private Label lbAddStade;
    @FXML
    private Label lbUpdateStade;
    @FXML
    private TextField tfNameStadeAdd;
    @FXML
    private TextField tfPlaceStadeAdd;
    @FXML
    private TextField tfCapacityStadeAdd;
    @FXML
    private TextField tfNumeroStadeAdd;
    @FXML
    private Button btnSelectImage;
    @FXML
    private VBox vbBoxAddStade;
    @FXML
    private Button btnPreviousStadeAdd;
    @FXML
    private ImageView ImgViewStadeAdd;
    @FXML
    private Button btnNextStadeAdd;
    @FXML
    private Button btnDeleteImageStade;
    @FXML
    private Button btnConfirmAddStade;
    @FXML
    private Button btnConfirmUpdateStade;
    @FXML
    private Button btnStadeList;
    @FXML
    private Button btnReservationList;
    @FXML
    private Button btn_logOut;
    @FXML
    private Button btnAppliquer;
    @FXML
    private TableView<Reservation> tvReservation;
    @FXML
    private Button btnDeleteReservation;
    @FXML
    private TableColumn<Reservation, Date> colDateReservation;
    @FXML
    private TableColumn<Reservation, Integer> colDeuEqReservation;
    @FXML
    private TableColumn<Reservation, Integer> colIdOrgaReservation;
    @FXML
    private TableColumn<Reservation, Integer> colIdPreEqReservation;
    @FXML
    private TableColumn<Reservation, String> colStadeReservation;
    @FXML
    private TextField tfSearchReservation;
    @FXML
    private Pane pnDetails;
    @FXML
    private Button btnDetailsStade;
    @FXML
    private Button btnBackStade;

    private int currentIndex = 0;
    private List<String> imagePaths = new ArrayList<>();
    private int currentIndexAdd = 0;
    private Reservation_Service rs = new Reservation_Service();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tfNumeroStadeAdd.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tfNumeroStadeAdd.setText(oldValue);
            }
        });
        tfNameStadeAdd.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z]*")) {

                tfNameStadeAdd.setText(oldValue);
            }
        });
        tfCapacityStadeAdd.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tfCapacityStadeAdd.setText(oldValue);
            }
        });
        tfPlaceStadeAdd.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z]*")) {

                tfPlaceStadeAdd.setText(oldValue);
            }
        });
        pnGestionStade.toFront();
        ObservableList<String>  list =FXCollections.observableArrayList("All","Tunis","Manouba","Ariana","Ben Arous"); // TODO
        cbLieuStade.setItems(list);
        hbDetailsStade.setVisible(true);
        btnDeleteStade.setVisible(false);
        btnUpdateStade.setVisible(false);
        btnDetailsStade.setVisible(false);
        this.currentIndex = 0;
        fnshow();
    }
    private void updateImageView() {
        String imagePath = imagePaths.get(currentIndexAdd);
        Image img = new Image(imagePath);
        ImgViewStadeAdd.setImage(img);
    }

    private void updateButtonsVisibility() {
        if (imagePaths.size() > 1) {
            btnNextStadeAdd.setVisible(true);
            btnPreviousStadeAdd.setVisible(true);
        } else {
            btnNextStadeAdd.setVisible(false);
            btnPreviousStadeAdd.setVisible(false);

        }

        if (imagePaths.size() >= 1) {
            btnDeleteImageStade.setVisible(true);
        }else {
            btnDeleteImageStade.setVisible(false);
        }
    }

    Stade_Service ss = new Stade_Service();

    public void fnshow(){
        fnshow("");
    }
    public void fnshow(String filter){
        ObservableList<Stade> list;
        if(!filter.equals("") && !filter.equals("All")) {
            list = FXCollections.observableList(ss.GetStadeFiltred(filter));
        }else{
            list = FXCollections.observableList(ss.Get());
        }




        colNomStade.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        colLieuStade.setCellValueFactory(new PropertyValueFactory<>("Lieu"));
        colImageStade.setCellValueFactory(new PropertyValueFactory<>("imageIntiale"));
        tvStade.setItems(list);


        FilteredList<Stade> filteredData = new FilteredList<>(list, b -> true);

        tfSearchStade.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(stade -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;

                }

                String lowerCaseFilter = newValue.toLowerCase();

                    if (stade.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                        return true;
                    }
                    else
                        return false;


            });
        });

        SortedList<Stade> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(tvStade.comparatorProperty());

        tvStade.setItems(sortedData);
        tvStade.setRowFactory(tv -> new TableRow<Stade>() {

            protected void updateItem(Stade item, boolean empty){

            }
        });
    }
    
    @FXML
    private void fnSelectedStade(MouseEvent event) {
        Stade p = tvStade.getSelectionModel().getSelectedItem();

        btnDeleteStade.setVisible(true);
        btnUpdateStade.setVisible(true);
        btnDetailsStade.setVisible(true);
        if (ss.getImages(p.getId()).size() > 1) {
            btnNextStade.setVisible(true);
            btnPreviousStade.setVisible(true);
        } else {
            btnNextStade.setVisible(false);
            btnPreviousStade.setVisible(false);

        }
       String nom = p.getNom();
        lbNomStade.setText(nom);
        lbCapacityStade.setText(p.getCapacity()+"");
        String lieu = p.getLieu();
        lbLieuStade.setText(lieu);
        lbNumeroStade.setText(p.getNumero()+"");
        String name = ss.getImages(p.getId()).get(0);
        Image img = new Image(name);
        ImgViewStade.setImage(img);
        if(ss.getImages(p.getId()).size()>1){
            btnNextStade.setVisible(true);
            btnPreviousStade.setVisible(true);
        }
    }

    @FXML
    private void fnSearchStade(ActionEvent event) {
    }

    @FXML
    private void fnAppliquer (ActionEvent event){
        fnshow(cbLieuStade.getValue());
    }

    @FXML
    private void fnPreviousStade(ActionEvent event) {
        currentIndex--;
        if (currentIndex < 0) {
            currentIndex = ss.getImages(tvStade.getSelectionModel().getSelectedItem().getId()).size() - 1;
        }
        updateImage();
    }


    @FXML
    private void fnNext(ActionEvent event) {
        currentIndex++;
        if (currentIndex >= ss.getImages(tvStade.getSelectionModel().getSelectedItem().getId()).size()) {
            currentIndex = 0;
        }
        updateImage();
    }

    private void updateImage() {
        String imageName = ss.getImages(tvStade.getSelectionModel().getSelectedItem().getId()).get(currentIndex);
        Image img = new Image(imageName);
        ImgViewStade.setImage(img);
    }

    @FXML
    private void fnAddStade(ActionEvent event) {


        tfCapacityStadeAdd.setText("");
        tfNameStadeAdd.setText("");
        tfNumeroStadeAdd.setText("");
        tfPlaceStadeAdd.setText("");
        imagePaths = new ArrayList<>();
        currentIndexAdd = 0;
        vbBoxAddStade.setVisible(false);
        pnAddUpdateStade.toFront();
        lbUpdateStade.setVisible(false);
        lbAddStade.setVisible(true);
        btnConfirmUpdateStade.setVisible(false);
        btnConfirmAddStade.setVisible(true);
    }

    @FXML
    private void fnUpdateStade(ActionEvent event) {
        Stade stade = tvStade.getSelectionModel().getSelectedItem();
        vbBoxAddStade.setVisible(true);
        tfCapacityStadeAdd.setText(stade.getCapacity()+"");
        tfPlaceStadeAdd.setText(stade.getNom());
        tfNameStadeAdd.setText(stade.getLieu());
        tfNumeroStadeAdd.setText(stade.getNumero()+"");
        imagePaths =ss.getImages(stade.getId());
        pnAddUpdateStade.toFront();
        lbUpdateStade.setVisible(true);
        lbAddStade.setVisible(false);
        btnConfirmUpdateStade.setVisible(true);
        btnConfirmAddStade.setVisible(false);
        currentIndexAdd= 0;
        updateImageView();
    }

    @FXML
    private void fnDeleteStade(ActionEvent event) {
        Stade stade = new Stade();
        stade.setId(tvStade.getSelectionModel().getSelectedItem().getId());
        ss.delete(stade);
        btnDetailsStade.setVisible(false);
        btnUpdateStade.setVisible(false);
        btnDeleteStade.setVisible(false);
        fnshow();
    }

    @FXML
    private void fnSelectImage(ActionEvent event)  {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Images");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);
        if (selectedFiles != null && !selectedFiles.isEmpty()) {
            imagePaths.clear();
            currentIndexAdd = 0;
            for (File file : selectedFiles) {
                imagePaths.add(file.toURI().toString());
            }
            vbBoxAddStade.setVisible(true);
            updateImageView();
            updateButtonsVisibility();
        }
    }

    @FXML
    private void fnPreviousStadeAdd(ActionEvent event) {
        if (!imagePaths.isEmpty()) {
            currentIndexAdd = (currentIndexAdd - 1 + imagePaths.size()) % imagePaths.size();
            updateImageView();
        }

    }

    @FXML
    private void fnNextAdd(ActionEvent event) {
        if (!imagePaths.isEmpty()) {
            currentIndexAdd = (currentIndexAdd + 1) % imagePaths.size();
            updateImageView();
        }
    }

    @FXML
    private void fnDeleteImageStade(ActionEvent event) {
        if (imagePaths != null && !imagePaths.isEmpty()) {
            String imagePath = imagePaths.get(currentIndexAdd);
            ss.deleteImage(imagePath);
            imagePaths.remove(currentIndexAdd);
            if (!imagePaths.isEmpty()) {
                currentIndexAdd = Math.min(currentIndexAdd, imagePaths.size() - 1);
                updateImageView();

            } else {
                ImgViewStadeAdd.setImage(null);
                vbBoxAddStade.setVisible(false);
            }
            updateButtonsVisibility();
        }
    }


    @FXML
    private void fnConfirmAddStade(ActionEvent event) {
        boolean isCapacityEmpty = tfCapacityStadeAdd.getText().isEmpty();
        boolean isNameEmpty = tfNameStadeAdd.getText().isEmpty();
        boolean isNumeroEmpty = tfNumeroStadeAdd.getText().isEmpty();
        boolean isPlaceEmpty = tfPlaceStadeAdd.getText().isEmpty();
        boolean isImageEmpty = ( ImgViewStadeAdd.getImage()== null);

        if(isCapacityEmpty || isNameEmpty || isNumeroEmpty || isPlaceEmpty || isImageEmpty){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setContentText("please fill in all fiels");
            alert.show();
            return;
        }
        Stade stade= new Stade();
        stade.setLieu(tfPlaceStadeAdd.getText());
        stade.setCapacity(Integer.parseInt(tfCapacityStadeAdd.getText()));
        stade.setNumero(Integer.parseInt(tfNumeroStadeAdd.getText()));
        stade.setNom(tfNameStadeAdd.getText());
        ss.Add(stade);
        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
        alert2.setTitle("succes");
        alert2.setContentText("stade ajoute avec succes");
        alert2.show();
        stade.setId(ss.getLastStadeId());
        for(int i=0;i<imagePaths.size();i++){
            ImagesStade img = new ImagesStade();
            img.setId_stade(stade.getId());
            img.setUrl_image(imagePaths.get(i));
            ss.addImage(img);

        }
        pnGestionStade.toFront();
        ObservableList<String>  list =FXCollections.observableArrayList("All","Tunis","Manouba","Ariana","Ben Arous"); // TODO
        cbLieuStade.setItems(list);
        btnDetailsStade.setVisible(false);
        btnDeleteStade.setVisible(false);
        btnUpdateStade.setVisible(false);
        this.currentIndex = 0;
        btnPreviousStade.setVisible(false);
        btnNextStade.setVisible(false);
        fnshow();
    }

    @FXML
    private void fnConfirmUpdateStade(ActionEvent event) {
        Stade stade= new Stade();
        stade.setId(tvStade.getSelectionModel().getSelectedItem().getId());
        stade.setLieu(tfPlaceStadeAdd.getText());
        stade.setCapacity(Integer.parseInt(tfCapacityStadeAdd.getText()));
        stade.setNumero(Integer.parseInt(tfNumeroStadeAdd.getText()));
        stade.setNom(tfNameStadeAdd.getText());


        ss.Update(stade);
        Alert alert4 = new Alert(Alert.AlertType.INFORMATION);
        alert4.setTitle("succes");
        alert4.setContentText("modidfie avec succes");
        alert4.show();
        stade.setId(ss.getLastStadeId());
        for(int i = 0; i < imagePaths.size(); i++) {
            List<String> imagePathsExisted = ss.getImages(stade.getId());
            String imagePath = imagePaths.get(i);

            if (!imagePathsExisted.contains(imagePath)) {
                ImagesStade img = new ImagesStade();
                img.setId_stade(stade.getId());
                img.setUrl_image(imagePath);
                ss.addImage(img);
            }
        }
        fnshow();
        ObservableList<String>  list =FXCollections.observableArrayList("All","Tunis","Manouba","Ariana","Ben Arous"); // TODO
        cbLieuStade.setItems(list);
        btnDetailsStade.setVisible(false);
        btnDeleteStade.setVisible(false);
        btnUpdateStade.setVisible(false);
        this.currentIndex = 0;
        btnPreviousStade.setVisible(false);
        btnNextStade.setVisible(false);
        pnGestionStade.toFront();

    }

    @FXML
    private void fnStadeList(ActionEvent event) {
        pnGestionStade.toFront();
        ObservableList<String>  list =FXCollections.observableArrayList("All","Tunis","Manouba","Ariana","Ben Arous"); // TODO
        cbLieuStade.setItems(list);
        btnDetailsStade.setVisible(false);
        btnDeleteStade.setVisible(false);
        btnUpdateStade.setVisible(false);
        this.currentIndex = 0;
        fnshow();
    }

    @FXML
    private void fnReservationList(ActionEvent event) {
        fnshowReservation();
        btnDeleteReservation.setVisible(false);
        pnReservation.toFront();

    }
    public void fnshowReservation(){
        ObservableList<Reservation> list = FXCollections.observableList(rs.Get());





        colDateReservation.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colDeuEqReservation.setCellValueFactory(new PropertyValueFactory<>("id_DeuxiemeEquipe"));
        colIdPreEqReservation.setCellValueFactory(new PropertyValueFactory<>("id_PremiereEquipe"));
        colIdOrgaReservation.setCellValueFactory(new PropertyValueFactory<>("id_organisateur"));

        colStadeReservation.setCellValueFactory(new PropertyValueFactory<>("NomStade"));


        tvReservation.setItems(list);


        FilteredList<Reservation> filteredData = new FilteredList<>(list, b -> true);

        tfSearchReservation.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(reservation -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;

                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (reservation.getNomStade().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                    return true;
                }
                else
                    return false;


            });
        });

        SortedList<Reservation> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(tvReservation.comparatorProperty());

        tvReservation.setItems(sortedData);
        tvReservation.setRowFactory(tv -> new TableRow<Reservation>() {

            protected void updateItem(Reservation item, boolean empty){

            }
        });
    }


    @FXML
    void fnReservationSelected(MouseEvent event) {
        btnDeleteReservation.setVisible(true);

    }

    @FXML
    void fnDeleteReservation(ActionEvent event) {
        Reservation r = tvReservation.getSelectionModel().getSelectedItem();
        Alert alert3 = new Alert(Alert.AlertType.CONFIRMATION);
        alert3.setTitle("warning");
        alert3.setContentText("voulez vous supprimer ?");

        Optional<ButtonType> result = alert3.showAndWait();
        if(result.get()==ButtonType.OK){

        rs.delete(r);}

        fnshowReservation();


    }

    @FXML
    private void fnLogOut(ActionEvent event) {
    }

    @FXML
    void fnBackStade(ActionEvent event) {
        pnGestionStade.toFront();
        btnDeleteStade.setVisible(false);
        btnUpdateStade.setVisible(false);
        btnDetailsStade.setVisible(false);
    }

    @FXML
    void fnDetails(ActionEvent event) {
        pnDetails.toFront();
    }
    
}
