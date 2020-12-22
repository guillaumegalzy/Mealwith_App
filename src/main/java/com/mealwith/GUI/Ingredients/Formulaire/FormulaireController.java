package com.mealwith.GUI.Ingredients.Formulaire;

import com.mealwith.DAO.CategoriesIngredientsDAO;
import com.mealwith.DAO.IngredientsDAO;
import com.mealwith.DAO.OriginDAO;
import com.mealwith.DAO.UnitDAO;
import com.mealwith.Entity.CategoriesIngredients;
import com.mealwith.Entity.Ingredients;
import com.mealwith.Entity.Origin;
import com.mealwith.Entity.Unit;
import com.mealwith.GUI.Ingredients.IngredientsController;
import com.mealwith.Service.AlertMessage;
import com.mealwith.Service.CustomsFonts;
import com.mealwith.Service.DataHolder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.*;

public class FormulaireController implements Initializable {
    @FXML
    public HBox Home,RatingHBox,RatingHBoxStar,hboxBtn;
    @FXML
    public ImageView ImgLogo,ImgIngredient;
    @FXML
    public Button btnAccept,btnCancel;
    @FXML
    public ComboBox<Unit> comboUnit;
    @FXML
    public ComboBox<CategoriesIngredients> comboCategory;
    @FXML
    public ComboBox<Origin> comboOrigin;
    @FXML
    public TextField inputPrice,inputTempMin,inputTempMax,inputName,inputID,inputShelflife;
    @FXML
    public Slider sliderTempMin,sliderTempMax;
    @FXML
    public FontIcon errorCatIcon,errorOriginIcon,errorUnitIcon,errorPriceIcon,errorShelflifeIcon,errorTempMinIcon,errorTempMaxIcon,errorNameIcon,errorImgIcon;

    // Logo et font du logotype
    private final CustomsFonts customsFonts = new CustomsFonts(); // Service permettant de stocker les Fonts utilisés dans le projet
    public Text textLogo; // Logotype

    // Envoi / Réception de données par ce formulaire
    public List<Object> dataReceive = new ArrayList<>(); // Stockage des données récupérées du controlleur de provenance
    public static List<Object> dataSend = new ArrayList<>(); // Données envoyés par ce formulaire
    public Ingredients ingredientSelected; // Ingredient émis par le formulaire de provenance
    public String operation = null; // Opération demandée par le controlleur de provenance

    // Liste d'objet 'Ingredients' récupéré à partir de la base de données
    public List<Ingredients> listIngredients = new ArrayList<>();

    // Classes DAO utilisées
    public OriginDAO repoOrigin = new OriginDAO();
    public UnitDAO repoUnit = new UnitDAO();
    public CategoriesIngredientsDAO repoCatIngr = new CategoriesIngredientsDAO();

    // Combobox
    public ObservableList<Origin> listOrigin = FXCollections.observableArrayList();
    public ObservableList<Unit> listUnit = FXCollections.observableArrayList();
    public ObservableList<CategoriesIngredients> listCatIngr = FXCollections.observableArrayList();
    public List<FontIcon> starRatings = new ArrayList<>();

    // Gestion des erreurs / Informations
    public AlertMessage alertMessage = new AlertMessage(); // Alerte utilisée pour informer l'utilisateur des actions effectuées en BDD
    public Map<Node,FontIcon> inputErrorMap = new HashMap<>(); // Map utilisée pour suivre les erreurs pour chacun des champs lors de la vérification des données du formulaire
    public List<Boolean> errorInput = new ArrayList<>(); // Liste de suivi des erreurs rencontrées lors de la vérification, pour chacun des champs du formulaire
    public Boolean errorTot = true;  // Résultat de l'opération de vérification, si false, pas d'erreur rencontrées. Est initialisée à true par défault.


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Changement de la font du Logo
            textLogo.setFont(customsFonts.LogoFont(Double.parseDouble("80")));

        // Vide les précédentes données récupérées et envoyées
            dataSend.clear();

        // Ajout gestionnaire d'écoute sur le logo pour renvoyer au menu
            Home.setOnMouseClicked(event -> DataHolder.getINSTANCE().ChangeScene((Stage) Home.getScene().getWindow(),Home.getId(),Home.getId()));

        // Ajout gestionnaires d'écoute sur les sliders pour la température et les inputs réciproquement
            sliderTempMin.valueProperty().addListener((observable, oldValue, newValue) -> inputTempMin.setText(String.valueOf(newValue.intValue())));
            sliderTempMax.valueProperty().addListener((observable, oldValue, newValue) -> inputTempMax.setText(String.valueOf(newValue.intValue())));

            inputTempMin.setOnAction(event -> sliderTempMin.setValue(Double.parseDouble(inputTempMin.getText())));
            inputTempMax.setOnAction(event -> sliderTempMax.setValue(Double.parseDouble(inputTempMax.getText())));

        // Ajout gestionnaires d'écoute sur les sliders pour la température et les inputs réciproquement
            comboUnit.setOnInputMethodTextChanged(event -> comboUnit.cancelEdit());

        // Récupération des images
            ImgLogo.setImage(new Image("img/Logo Mealwith.png"));
            ImgIngredient.setImage(new Image("img/Ingredients.png"));

        // Récupération des étoiles du ratings
            for (Node star: RatingHBoxStar.getChildren()) {
                starRatings.add((FontIcon) star);
            }

        // Remplissage des combobox
            try {
                // Récupération des origines présentes dans la BDD et ajout dans la combobox
                   listOrigin.addAll(repoOrigin.List());
                   comboOrigin.setItems(listOrigin);

                // Récupération des unités présentes dans la BDD et ajout dans la combobox
                    listUnit.addAll(repoUnit.List());
                    comboUnit.setItems(listUnit);

                // Récupération des catégories d'ingrédients présentes dans la BDD et ajout dans la combobox
                    listCatIngr.addAll(repoCatIngr.List());
                    comboCategory.setItems(listCatIngr);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        // Chargement du comportement de la page selon l'opération demandée par le controlleur de provenance
        try {
            // Récupération des données stockées par le controlleur de provenance
                getData();
            // Chargement des valeurs dans les champs si nécessaire
                loadPage();
            // Débloque les saisies, si nécessaire
                unlockField();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //  Liste des contrôles avec leur icone d'erreur associée
            inputErrorMap.put(inputName,errorNameIcon);
            inputErrorMap.put(comboCategory,errorCatIcon);
            inputErrorMap.put(comboOrigin,errorOriginIcon);
            inputErrorMap.put(comboUnit,errorUnitIcon);
            inputErrorMap.put(inputPrice,errorPriceIcon);
            inputErrorMap.put(inputShelflife,errorShelflifeIcon);
            inputErrorMap.put(inputTempMin,errorTempMinIcon);
            inputErrorMap.put(inputTempMax,errorTempMaxIcon);
            inputErrorMap.put(ImgIngredient,errorImgIcon);
    }

    /**
     * Récupère les datas stockées par le controlleur principal et remplis les champs s'il s'agit d'une modification ou d'un détail
     */
    public void getData()  {
        // Réinitialisation des données stockées
            dataReceive.clear();
            ingredientSelected = null;
            operation = null;

        // Récupération des informations émises par le formulaire de provenance
            dataReceive.addAll(IngredientsController.dataSend);
            operation = (String) IngredientsController.dataSend.get(0); // Opération demandée "Modify","Add" ou "Details". Donnée émise par le controlleur de provenance
            listIngredients.addAll((Collection<? extends Ingredients>) IngredientsController.dataSend.get(1));
    }

    /**
     * Fonction récupérant toute les informations nécessaire en cas de modification ou de visualisation d'un ingredient
     * @throws SQLException Exception possible liée à l'usage de la BDD
     */
    public void loadPage() throws SQLException {

        // S'il s'agit d'une modification ou d'un détails
        if (operation.equals("Modify") || operation.equals("Details")) {

            // Remplissage des champs avec les informations du disque
            ingredientSelected = (Ingredients) dataReceive.get(2);
            this.inputID.setText(String.valueOf(ingredientSelected.getId()));
            this.inputName.setText(ingredientSelected.getName());
            this.inputPrice.setText(String.valueOf(ingredientSelected.getPrice()));
            this.inputShelflife.setText(String.valueOf(ingredientSelected.getShelf_life()));
            this.inputTempMin.setText(String.valueOf(ingredientSelected.getTemp_min()));
            this.sliderTempMin.setValue(ingredientSelected.getTemp_min());
            this.inputTempMax.setText(String.valueOf(ingredientSelected.getTemp_max()));
            this.sliderTempMax.setValue(ingredientSelected.getTemp_max());
            this.ImgIngredient.setImage(new Image(ingredientSelected.getPicture()));
            this.comboCategory.getSelectionModel().select(repoCatIngr.FindByID(ingredientSelected.getCategory_id()));
            this.comboUnit.getSelectionModel().select(repoUnit.FindByID(ingredientSelected.getUnit_id()));
            this.comboOrigin.getSelectionModel().select(repoOrigin.FindByID(ingredientSelected.getOrigin_id()));
            this.RatingHBox.setVisible(true);
            for (FontIcon starIcon : starRatings) {
                starIcon.setIconColor(Color.rgb(255, 225, 77));
            }

            if (operation.equals("Details")) { // S'il s'agit de l'affichage du détail, cache le bouton 'btnAccept'
                hboxBtn.getChildren().remove(btnAccept);
            }
        }

        // Changement du texte du btnAccept ainsi que du logo selon opération demandée
        if (operation.equals("Add")) {
            btnAccept.setText("Add");
            FontIcon iconAdd = new FontIcon();
            iconAdd.setIconLiteral("fa-plus");
            btnAccept.setGraphic(iconAdd);
        } else if (operation.equals("Modify")) {
            btnAccept.setText("Update");
            FontIcon iconModify = new FontIcon();
            iconModify.setIconLiteral("fa-pencil");
            btnAccept.setGraphic(iconModify);
        }
    }

    /**
     * Rend éditable l'ensemble des champs et ajout du gestionnaire d'écoute sur l'image pour pouvoir la changer
     */
    public void unlockField() {
        // S'il s'agit d'une modification exclusivement
        if (operation.equals("Modify") || operation.equals("Add")) {

            // rend editable les champs et les sliders
            this.inputID.setDisable(true);
            this.inputName.setEditable(true);
            this.inputPrice.setEditable(true);
            this.inputShelflife.setEditable(true);
            this.inputTempMin.setEditable(true);
            this.inputTempMax.setEditable(true);
            this.sliderTempMin.setDisable(false);
            this.sliderTempMax.setDisable(false);
            this.comboCategory.setDisable(false);
            this.comboUnit.setDisable(false);
            this.comboOrigin.setDisable(false);

            // Ajout gestionnaire d'écoute sur l'image, pour permettre sa modification
            ImgIngredient.setCursor(Cursor.cursor("HAND"));

            // FileChooser on Click
            ImgIngredient.setOnMouseClicked(event -> {
                // Création et paramétrage d'un filechooser
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Sélectionner une image");
                fileChooser.getExtensionFilters().addAll( //Extensions autorisées
                        new FileChooser.ExtensionFilter("Image (.jpg, .jpeg, .png)", "*.jpeg", "*.jpg", "*.png")
                );

                //Chemin d'accès au fichier de resources du projet pour paramétrage du chemin d'accès par défaut
                String directory = System.getProperty("user.dir");
                Path path = Paths.get(directory + "/src/main/resources/img");
                fileChooser.setInitialDirectory(new File(String.valueOf(path)));

                // Ouverture de la fenêtre de dialogue et récupération de l'image choisie
                File selectedFile = fileChooser.showOpenDialog(ImgIngredient.getScene().getWindow());

                // Modification de l'image prévisualisée si elle a été choisie
                if (selectedFile != null) {
                    Image newImage = new Image("/img/" + selectedFile.getName());
                    ImgIngredient.setImage(newImage);
                }
            });

            // Affichage PopUp info on Over
            ImgIngredient.setOnMouseEntered(event -> {
                PopOver popup = new PopOver();
                Label infoPopup = new Label();
                infoPopup.setText("Click on the image to change it");
                popup.setContentNode(infoPopup);
                popup.show(ImgIngredient);
                popup.setAutoHide(true);
            });
        }
    }

    public void btnClick(ActionEvent actionEvent) throws SQLException {
        // Récupère l'ID du bouton dans une string
            String btnText = ((Button) actionEvent.getSource()).getId();

        //Action différente selon le boutton
            if (btnText.equals("btnAccept")){
                Verify(); // Lance la vérification de l'ensemble des champs du formulaire

                if (!errorTot) { // Si la vérification n'a rencontrée aucune erreur
                    IngredientsDAO repoIngredients = new IngredientsDAO();

                    if (operation.equals("Add")) { // Si c'est un ajout
                        // Ajout d'un ingredient à la base de données
                            Ingredients ingredientInsert = new Ingredients(
                                    this.comboCategory.getSelectionModel().getSelectedItem().getId(),
                                    this.comboOrigin.getSelectionModel().getSelectedItem().getId(),
                                    this.comboUnit.getSelectionModel().getSelectedItem().getId(),
                                    this.inputName.getText(),
                                    Double.parseDouble(this.inputPrice.getText()),
                                    Integer.parseInt(this.inputTempMin.getText()),
                                    Integer.parseInt(this.inputTempMax.getText()),
                                    Integer.parseInt(this.inputShelflife.getText()),
                                    this.ImgIngredient.getImage().getUrl()
                            );

                            repoIngredients.Insert(ingredientInsert);

                        // Message de confirmation
                            alertMessage.Alert(Alert.AlertType.INFORMATION, "Ingredient : '" + this.inputName.getText() + "' added to DB.", ButtonType.CLOSE);

                        // Ajout dans la liste des ingrédients
                            ingredientInsert.setId(repoIngredients.FindByName(ingredientInsert.getName()).getId());
                            listIngredients.add(repoIngredients.FindByName(ingredientInsert.getName()));

                    } else if (operation.equals("Modify")){ // Si c'est une modification
                        // Modification de l'ingredient
                            ingredientSelected.setCategory_id(this.comboCategory.getSelectionModel().getSelectedItem().getId());
                            ingredientSelected.setOrigin_id(this.comboOrigin.getSelectionModel().getSelectedItem().getId());
                            ingredientSelected.setUnit_id(this.comboUnit.getSelectionModel().getSelectedItem().getId());
                            ingredientSelected.setName(this.inputName.getText());
                            ingredientSelected.setPrice(Double.parseDouble(this.inputPrice.getText()));
                            ingredientSelected.setTemp_min(Integer.parseInt(this.inputTempMin.getText()));
                            ingredientSelected.setTemp_max(Integer.parseInt(this.inputTempMax.getText()));
                            ingredientSelected.setShelf_life(Integer.parseInt(this.inputShelflife.getText()));
                            ingredientSelected.setPicture(this.ImgIngredient.getImage().getUrl());

                            repoIngredients.Update(ingredientSelected);

                        // Message de confirmation
                            alertMessage.Alert(Alert.AlertType.INFORMATION, "Ingredient : '" + this.inputName.getText() + "' modified into DB.", ButtonType.CLOSE);

                        // Remplace l'ingredient dans la liste des ingredients par le nouveau
                            listIngredients.remove(ingredientSelected);
                            listIngredients.add(repoIngredients.Find(ingredientSelected.getId()));
                    }
                } else {
                    alertMessage.Alert(Alert.AlertType.ERROR, "Please verify all fields in order to insert or update this ingredient into the db", ButtonType.CLOSE);
                }
            }

        if (btnText.equals("btnCancel") || !errorTot) {
            // Action commune, quelle que soit le bouton
            // Retourne le repo des ingredients pour ne pas le fetch de nouveau
            dataSend.add(listIngredients);
            // Redirection vers le formulaire 'Ingredients'
            DataHolder.getINSTANCE().ChangeScene((Stage) btnCancel.getScene().getWindow(), "Ingredients", "Ingredients");
        }
    }

    /**
     * Fonction vérifiant l'ensemble des champs du formulaire en correspondance avec les exigences de la BDD
     */
    private void Verify() {
        // Vide la liste stockant les erreurs potentiellement recontrées lors d'une précédente vérification sur les champs avant toute nouvelle vérification
            errorInput.clear();

        // Vérification de chacun des champs
            for (Node control : inputErrorMap.keySet()) {
                if (control instanceof TextField) { // Si le control est un TextFiel
                    if (((TextField) control).getText().isEmpty()){ // Vérifie si le champ est nul
                        this.errorInput.add(true);
                        control.setStyle("-fx-border-color: red;-fx-border-width: 0.5pt");
                        inputErrorMap.get(control).setVisible(true);
                    }else {
                        switch (control.idProperty().getValue()) {
                            case "inputName":  // Vérifie que le nom de l'ingredient est correctement renseigné
                                if (((TextField) control).getText().matches("[\\D]{1,255}")) {
                                    this.errorInput.add(false);
                                    inputErrorMap.get(control).setVisible(false);
                                    control.setStyle("");
                                } else {
                                    this.errorInput.add(true);
                                    control.setStyle("-fx-border-color: red;-fx-border-width: 0.5pt");
                                    inputErrorMap.get(control).setVisible(true);
                                }
                                break;

                            case "inputPrice": // Vérifie que le prix est correctement renseigné, en DB décimal (6,2)
                                if (((TextField) control).getText().matches("\\d{1,6}(\\.\\d{1,2})?")) {
                                    this.errorInput.add(false);
                                    inputErrorMap.get(control).setVisible(false);
                                    control.setStyle("");
                                } else {
                                    this.errorInput.add(true);
                                    control.setStyle("-fx-border-color: red;-fx-border-width: 0.5pt");
                                    inputErrorMap.get(control).setVisible(true);
                                }
                                break;

                            case "inputShelflife": // Vérifie que la durée de vie est correctement renseignée
                                if (((TextField) control).getText().matches("[\\d]{1,3}")) {
                                    this.errorInput.add(false);
                                    inputErrorMap.get(control).setVisible(false);
                                    control.setStyle("");
                                } else {
                                    this.errorInput.add(true);
                                    control.setStyle("-fx-border-color: red;-fx-border-width: 0.5pt");
                                    inputErrorMap.get(control).setVisible(true);
                                }
                                break;

                            case "inputTempMin": // Vérifie que les températures min et max sont renseignées et que TempMin < TempMax
                                if (((TextField) control).getText().matches("[\\d]{1,2}") && ((Integer.parseInt(inputTempMax.getText())) > (Integer.parseInt(((TextField) control).getText())))) {
                                    this.errorInput.add(false);
                                    inputErrorMap.get(control).setVisible(false);
                                    inputErrorMap.get(inputTempMax).setVisible(false);
                                    control.setStyle("");
                                    inputTempMax.setStyle("");
                                } else {
                                    this.errorInput.add(true);
                                    control.setStyle("-fx-border-color: red;-fx-border-width: 0.5pt");
                                    inputTempMax.setStyle("-fx-border-color: red;-fx-border-width: 0.5pt");
                                    inputErrorMap.get(control).setVisible(true);
                                    inputErrorMap.get(inputTempMax).setVisible(true);
                                }
                                break;

                            default: break;
                        }
                    }

                } else if(control instanceof ComboBox){ // Sinon,si c'est une combobox
                    if(((ComboBox<Object>)control).getSelectionModel().getSelectedItem() != null ){ // Vérifie si un élément à été sélectionné dans la combobox
                        this.errorInput.add(false);
                        inputErrorMap.get(control).setVisible(false);
                        control.setStyle("");
                    }else{
                        this.errorInput.add(true);
                        inputErrorMap.get(control).setVisible(true);
                        control.setStyle("-fx-border-color: red;-fx-border-width: 0.5pt");
                    }

                } else if (control instanceof ImageView){ // Sinon,si c'est une image
                    if(!((ImageView)control).getImage().getUrl().isEmpty() && (((ImageView)control).getImage().getUrl().length() < 255)){ // Vérifie si une image à été sélectionné pour l'ingredient et si l'url ne comprends pas plus de 255 caractère
                        this.errorInput.add(false);
                        inputErrorMap.get(control).setVisible(false);
                    }else{
                        this.errorInput.add(true);
                        inputErrorMap.get(control).setVisible(true);
                    }
                }
            }

        // Si aucune erreur rencontré sur les champs, on attribue à la variable 'errorTot' la valeur false
            if (!errorInput.contains(true)){
                errorTot = false;
            }
    }
}