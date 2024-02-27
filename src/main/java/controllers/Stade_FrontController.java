package controllers;


import entities.Reservation;
import entities.Stade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import services.BlockedDateCell;
import services.Reservation_Service;
import services.Stade_Service;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class Stade_FrontController implements Initializable {
    Stade_Service ss = new Stade_Service();
    Reservation_Service rs = new Reservation_Service();
    @FXML
    private ImageView ImgViewStade;

    @FXML
    private ImageView ImgViewStadeUpdate;

    @FXML
    private Button btnModifierConfirmer;

    @FXML
    private Button btnModifierReservation;

    @FXML
    private Button btnNextStade;

    @FXML
    private Button btnNextStadeUpdate;

    @FXML
    private Button btnPreviousStade;

    @FXML
    private Button btnPreviousStadeUpdate;

    @FXML
    private Button btnReservationList;

    @FXML
    private Button btnReserver;

    @FXML
    private Button btnStadeList;

    @FXML
    private Button btnSupprimerReservation;

    @FXML
    private Button btn_logOut;

    @FXML
    private ComboBox<String> cbStade;

    @FXML
    private TableColumn<Reservation, Date> colDateReservation;

    @FXML
    private TableColumn<Reservation, Integer> colDeuEqReservation;

    @FXML
    private TableColumn<Reservation, Integer> colIdPreEqReservation;

    @FXML
    private TableColumn<Reservation, Integer> colStadeReservation;

    @FXML
    private VBox hbDetailsStade;

    @FXML
    private VBox hbDetailsStadeUpdate;

    @FXML
    private Label lbCapacityStade;

    @FXML
    private Label lbCapacityStadeUpdate;

    @FXML
    private Label lbLieuStade;

    @FXML
    private Label lbLieuStadeUpdate;

    @FXML
    private Label lbNomStade;

    @FXML
    private Label lbNomStadeUpdate;

    @FXML
    private Label lbNumeroStade;

    @FXML
    private Label lbNumeroStadeUpdate;

    @FXML
    private ScrollPane scrollPaneStade;

    @FXML
    private DatePicker tfDateModifier;

    @FXML
    private DatePicker tfDateReservation;

    @FXML
    private TextField tfDeusiemeEquipe;

    @FXML
    private TextField tfPremierEquipe;

    @FXML
    private TableView<Reservation> tvReservation;

    @FXML
    private VBox vbReserverStade;
    @FXML
    private Pane pnMesReservation;

    @FXML
    private Pane pnModifierReservation;

    @FXML
    private Pane pnStade;

    @FXML
    private Label lbIdstade;

    private int currentIndex = 0;


    private List<String> imagePaths = new ArrayList<>();
    private int currentIndexUpdate = 0;
    private void updateImageView() {
        String imagePath = imagePaths.get(currentIndexUpdate);
        Image img = new Image(imagePath);
        ImgViewStadeUpdate.setImage(img);
    }

    private void updateButtonsVisibility() {
        if (imagePaths.size() > 1) {
            btnNextStadeUpdate.setVisible(true);
            btnPreviousStadeUpdate.setVisible(true);
        } else {
            btnNextStadeUpdate.setVisible(false);
            btnPreviousStadeUpdate.setVisible(false);

        }

    }


    @FXML
    void fnChangeStade(ActionEvent event) {
        currentIndexUpdate = 0;
        Stade stade= ss.GetStadeByName(cbStade.getValue());
        if (ss.getImages(stade.getId()).size() > 1) {
            btnNextStadeUpdate.setVisible(true);
            btnPreviousStadeUpdate.setVisible(true);
        } else {
            btnNextStadeUpdate.setVisible(false);
            btnPreviousStadeUpdate.setVisible(false);

        }
        lbNomStadeUpdate.setText(stade.getNom());
        lbCapacityStadeUpdate.setText(stade.getCapacity()+"");
        lbLieuStadeUpdate.setText(stade.getLieu());
        lbNumeroStadeUpdate.setText(stade.getNumero()+"");
        String name = ss.getImages(stade.getId()).get(0);
        Image img = new Image(name);
        ImgViewStadeUpdate.setImage(img);
        if(ss.getImages(stade.getId()).size()>1){
            btnNextStadeUpdate.setVisible(true);
            btnPreviousStadeUpdate.setVisible(true);
        }
    }

    @FXML
    void fnConfirmerUpdate(ActionEvent event) {
        Stade stade = ss.GetStadeByName(cbStade.getValue());
        Reservation reservation = tvReservation.getSelectionModel().getSelectedItem();
        reservation.setDate(Date.valueOf(tfDateModifier.getValue()));
        reservation.setId_stade(stade.getId());
        rs.Update(reservation);
        fnshowReservation();
        pnMesReservation.toFront();
        hbDetailsStade.setVisible(false);

        btnModifierReservation.setVisible(false);
        btnSupprimerReservation.setVisible(false);

    }

    @FXML
    void fnLogOut(ActionEvent event) {

    }

    @FXML
    void fnModifier(ActionEvent event) {
        pnModifierReservation.toFront();
        cbStade.setItems(FXCollections.observableArrayList(ss.GetNomStade()));
        cbStade.setValue(tvReservation.getSelectionModel().getSelectedItem().NomStade);
        tfDateModifier.setValue(tvReservation.getSelectionModel().getSelectedItem().date.toLocalDate());
        currentIndexUpdate = 0;
        Stade stade= ss.GetStadeById(tvReservation.getSelectionModel().getSelectedItem().getId_stade());
        if (ss.getImages(stade.getId()).size() > 1) {
            btnNextStadeUpdate.setVisible(true);
            btnPreviousStadeUpdate.setVisible(true);
        } else {
            btnNextStadeUpdate.setVisible(false);
            btnPreviousStadeUpdate.setVisible(false);

        }
        lbNomStadeUpdate.setText(stade.getNom());
        lbCapacityStadeUpdate.setText(stade.getCapacity()+"");
        lbLieuStadeUpdate.setText(stade.getLieu());
        lbNumeroStadeUpdate.setText(stade.getNumero()+"");
        String name = ss.getImages(stade.getId()).get(0);
        Image img = new Image(name);
        ImgViewStadeUpdate.setImage(img);
        if(ss.getImages(stade.getId()).size()>1){
            btnNextStadeUpdate.setVisible(true);
            btnPreviousStadeUpdate.setVisible(true);
        }


    }

    @FXML
    void fnNext(ActionEvent event) {
        currentIndex++;
        if (currentIndex >= ss.getImages(tvReservation.getSelectionModel().getSelectedItem().getId_stade()).size()) {
            currentIndex = 0;
        }
        updateImage();

    }

    @FXML
    void fnNextUpdate(ActionEvent event) {
        if (!imagePaths.isEmpty()) {
            currentIndexUpdate = (currentIndexUpdate + 1) % imagePaths.size();
            updateImageView();
        }
    }

    @FXML
    void fnPreviousStade(ActionEvent event) {
        currentIndex--;
        if (currentIndex < 0) {
            currentIndex = ss.getImages(tvReservation.getSelectionModel().getSelectedItem().getId_stade()).size() - 1;
        }
        updateImage();

    }
    private void updateImage() {
        String imageName = ss.getImages(tvReservation.getSelectionModel().getSelectedItem().getId_stade()).get(currentIndex);
        Image img = new Image(imageName);
        ImgViewStade.setImage(img);
    }

    @FXML
    void fnPreviousStadeUpdate(ActionEvent event) {
        if (!imagePaths.isEmpty()) {
            currentIndexUpdate = (currentIndexUpdate - 1 + imagePaths.size()) % imagePaths.size();
            updateImageView();
        }

    }
    public void fnshowReservation(){
        ObservableList<Reservation> list = FXCollections.observableList(rs.Get());


        colDateReservation.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colDeuEqReservation.setCellValueFactory(new PropertyValueFactory<>("id_DeuxiemeEquipe"));
        colIdPreEqReservation.setCellValueFactory(new PropertyValueFactory<>("id_PremiereEquipe"));
        colStadeReservation.setCellValueFactory(new PropertyValueFactory<>("id_organisateur"));


        tvReservation.setItems(list);

    }
    @FXML
    void fnReservationList(ActionEvent event) {
        pnMesReservation.toFront();
        fnshowReservation();
        hbDetailsStade.setVisible(false);

        btnModifierReservation.setVisible(false);
        btnSupprimerReservation.setVisible(false);

    }

    @FXML
    void fnReservationSelected(MouseEvent event) {
        Stade stade= ss.GetStadeById(tvReservation.getSelectionModel().getSelectedItem().getId_stade());
        hbDetailsStade.setVisible(true);
        hbDetailsStade.setVisible(true);
        if (ss.getImages(stade.getId()).size() > 1) {
            btnNextStade.setVisible(true);
            btnPreviousStade.setVisible(true);
        } else {
            btnNextStade.setVisible(false);
            btnPreviousStade.setVisible(false);

        }
        lbNomStade.setText(stade.getNom());
        lbCapacityStade.setText(stade.getCapacity()+"");
        lbLieuStade.setText(stade.getLieu());
        lbNumeroStade.setText(stade.getNumero()+"");
        System.out.println(stade.getId()+"++++++++++++++++++++++++++++++++++");
        System.out.println(ss.getImages(stade.getId()).toString());
        String name = ss.getImages(stade.getId()).get(0);
        Image img = new Image(name);
        ImgViewStade.setImage(img);
        if(ss.getImages(stade.getId()).size()>1){
            btnNextStade.setVisible(true);
            btnPreviousStade.setVisible(true);
        }
        btnModifierReservation.setVisible(true);
        btnSupprimerReservation.setVisible(true);

    }



    @FXML
    void fnReserver(ActionEvent event) {
        boolean isPremEquipeEmpty =tfPremierEquipe.getText().isEmpty();
        boolean isDeuxEquipeEmpty = tfDeusiemeEquipe.getText().isEmpty();

        boolean isDateEmpty = ( tfDateReservation.getValue()== null);

        if( isPremEquipeEmpty|| isDeuxEquipeEmpty  || isDateEmpty){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setContentText("please fill in all fiels");
            alert.show();
            return;
        }
        Reservation reservation = new Reservation();
        reservation.setId_stade(Integer.parseInt(lbIdstade.getText()));
        reservation.setId_organisateur(1);
        reservation.setId_PremiereEquipe(Integer.parseInt(tfPremierEquipe.getText()));
        reservation.setId_DeuxiemeEquipe(Integer.parseInt(tfDeusiemeEquipe.getText()));
        reservation.setDate(Date.valueOf(tfDateReservation.getValue()));

        rs.Add(reservation);
        Alert alert5 = new Alert(Alert.AlertType.INFORMATION);
        alert5.setTitle("succes");
        alert5.setContentText("reservation ajoute avec succes");
        alert5.show();
        vbReserverStade.setVisible(false);
        pnMesReservation.toFront();
        btnModifierReservation.setVisible(false);
        fnshowReservation();

    }

    @FXML
    void fnStadeList(ActionEvent event) {
        pnStade.toFront();
        vbReserverStade.setVisible(true);
        tfDateReservation.setValue(null);
        tfDeusiemeEquipe.setText("");
        tfPremierEquipe.setText("");
        fnshow();
    }

    @FXML
    void fnSupprimer(ActionEvent event) {
        Reservation reservation = tvReservation.getSelectionModel().getSelectedItem();
        Alert alert4= new Alert(Alert.AlertType.CONFIRMATION);
        alert4.setTitle("warning");
        alert4.setContentText("voulez vous supprimer ?");

        Optional<ButtonType> result = alert4.showAndWait();
        if(result.get()==ButtonType.OK){

            rs.delete(reservation);}

        fnshowReservation();
        hbDetailsStade.setVisible(false);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfPremierEquipe.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tfPremierEquipe.setText(oldValue);
            }
        });
        tfDeusiemeEquipe.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tfDeusiemeEquipe.setText(oldValue);
            }
        });
        fnshow();
        pnStade.toFront();
    }

    public void fnshow() {
        ObservableList<Stade> list = FXCollections.observableList(ss.Get());

        scrollPaneStade.setContent(null);

        HBox hbox = new HBox();
        hbox.setSpacing(10);

        for (Stade stade : list) {
            VBox stadeBox = new VBox();
            stadeBox.setSpacing(10);

            Label nameLabel = new Label("Nom: " + stade.getNom());
            Label lieuLabel = new Label("Lieu: " + stade.getLieu());
            Label CpacityLabel = new Label("Capacité: " + stade.getCapacity());
            Label NumeroLabel = new Label("Numéro: " + stade.getNumero());

            stadeBox.getChildren().addAll(nameLabel, lieuLabel, CpacityLabel, NumeroLabel);

            List<String> images = ss.getImages(stade.getId());
            for (String imageUrl : images) {
                ImageView imageView = new ImageView(new Image(imageUrl));
                imageView.setFitWidth(200);
                imageView.setPreserveRatio(true);
                stadeBox.getChildren().add(imageView);
            }

            Separator separator = new Separator();
            separator.setOrientation(Orientation.VERTICAL);
            separator.setPrefHeight(200);
            hbox.getChildren().addAll(stadeBox, separator);

            stadeBox.setOnMouseClicked(e -> {
                lbIdstade.setText(stade.getId()+"");
                vbReserverStade.setVisible(true);
                btnModifierReservation.setVisible(true);
                // Define your list of blocked dates
                List<LocalDate> blockedDates = rs.getReservedDaysByStade(stade.getId());

                // Set the DayCellFactory
                tfDateReservation.setDayCellFactory(param -> new BlockedDateCell(blockedDates));

                // Optional: Add custom validation for additional checks (e.g., date range)
                tfDateReservation.valueProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null && !blockedDates.contains(newValue)) {
                        // Date is valid (not blocked)
                    } else {
                        // Date is blocked, handle error (e.g., display message, reset date)
                        tfDateReservation.setValue(null); // Reset date to avoid invalid selection
                        // Show an error message or perform other actions
                    }
                });
            });
        }

        scrollPaneStade.setContent(hbox);
    }


}
