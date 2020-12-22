package com.mealwith.GUI.Ingredients;

import com.mealwith.DAO.IngredientsDAO;
import com.mealwith.Entity.Ingredients;
import com.mealwith.GUI.Ingredients.Formulaire.FormulaireController;
import com.mealwith.Service.AlertMessage;
import com.mealwith.Service.CustomsFonts;
import com.mealwith.Service.DataHolder;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Predicate;

public class IngredientsController implements Initializable {
    @FXML
    public ImageView ImgLogo;
    @FXML
    public HBox Home;
    @FXML
    public VBox catMenu;
    @FXML
    public TableColumn<String, Integer> column_ID;
    @FXML
    public TableColumn<String, String> column_ingredient, column_category;
    @FXML
    public TableColumn<String, Image> column_picture;
    @FXML
    public TableView<Ingredients> tab_ingredient;
    @FXML
    public Button btnDelete, btnDetails, btnModify, btnAdd;
    @FXML
    public Label catAll, catMeat, catFruits, catDairy, catGro;
    @FXML
    public FontIcon iconDelete, iconAdd, iconModify, iconDetails, iconTri, catAllIcon, catMeatIcon, catFruitsIcon, catDairyIcon, catGroIcon;
    @FXML
    public TextField inputSearch;

    private final CustomsFonts customsFonts = new CustomsFonts(); // Service permettant de stocker les Fonts utilisés dans le projet
    public Text textLogo; // Logotype

    public ObservableList<Ingredients> listIngredients = FXCollections.observableArrayList();
    public static List<Object> dataSend = new ArrayList<>(); // Données qui seront stockées par ce controlleur et utilisé par le controlleur de destination
    public static List<Object> dataReceive = new ArrayList<>(); // Stockage des données récupérées du controlleur de provenance
    public Map<Label, FontIcon> catFilter = new HashMap<>(); // Map associant les noms des catérogies dans le menu de gauche avec leur icône respective
    public IngredientsDAO repoIngredients = new IngredientsDAO();
    public AlertMessage alert = new AlertMessage();
    public FilteredList<Ingredients> listIngredientsFilter = new FilteredList<>(FXCollections.observableList(listIngredients)); // Liste des ingredients filtrée par catégorie ou texte de la search bar
    public ObjectProperty<Predicate<Ingredients>> categoryNameFilter = new SimpleObjectProperty<>(); // Filtre sur la catégorie sélectionnée dans le menu de gauche
    public ObjectProperty<Predicate<Ingredients>> searchTextFilter = new SimpleObjectProperty<>(); // Filtre sur le texte de la barre de recherche
    public String categoryName = ""; // Reccueil la catégorie sélectionnée
    public String searchText = "";  // Reccueil le texte saisit dans l'input de recherche

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Changement de la font du Logo
            textLogo.setFont(customsFonts.LogoFont(Double.parseDouble("80")));

        // Vide les précédentes données récupérées et envoyées
            dataReceive.clear();
            dataSend.clear();

        // Récupération des images
            ImgLogo.setImage(new Image("img/Logo Mealwith.png"));

        // Gestionnaire d'écoute sur le logo pour renvoyer au menu
            Home.setOnMouseClicked(event -> DataHolder.getINSTANCE().ChangeScene((Stage) Home.getScene().getWindow(), Home.getId(), Home.getId()));

        // Création des filtres qui pourront être utilisé par le tableview
            // Filtre pour la catégorie
            categoryNameFilter.bind(Bindings.createObjectBinding(() ->
                    ingredients -> ingredients.getCategory_name().equals(categoryName)
            ));

            // Filtre pour la barre de recherche
            searchTextFilter.bind(Bindings.createObjectBinding(() ->
                    ingredients -> ingredients.getName().toLowerCase().contains(searchText.toLowerCase())
            ));

        // Gestionnaire d'écoute sur l'input de recherche
            inputSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                searchText = newValue; //Stocke le texte de la barre de recherche
                searchRecipe();
            });

        // Récupération des données stockées par le formulaire
            getData();

        // Récupération des ingrédients dans la BDD ou via le controlleur de provenance
            if (!dataReceive.isEmpty()) {
                listIngredients.addAll((Collection<? extends Ingredients>) dataReceive.get(0));
            }else{
                try {
                    listIngredients.addAll(repoIngredients.List());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

        // Lie le membre approprié de chaque ingrédients à la colonne du tableau
            column_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
            column_ingredient.setCellValueFactory(new PropertyValueFactory<>("name"));
            column_category.setCellValueFactory(new PropertyValueFactory<>("category_name"));
            column_picture.setCellValueFactory(new PropertyValueFactory<>("picture_Img"));

        // Ajout les éléments du repo en tant que données du tableau
            tab_ingredient.setItems(listIngredients);

        // Stocke les labels et leur icône respective dans la Map 'catFilter
            catFilter.put(catAll, catAllIcon);
            catFilter.put(catMeat, catMeatIcon);
            catFilter.put(catFruits, catFruitsIcon);
            catFilter.put(catDairy, catDairyIcon);
            catFilter.put(catGro, catGroIcon);

            for (Map.Entry<Label, FontIcon> element : catFilter.entrySet()) {
                element.getKey().setStyle("");
            }
    }

    public void btnClick(ActionEvent actionEvent) throws SQLException {
        // Récupère l'ID du bouton dans une string
        String btnText = ((Button) actionEvent.getSource()).getId();

        //Action différente selon le boutton
        switch (btnText) {
            // Ajout d'un ingredient à la base de données
            case "btnDetails":
                if (tab_ingredient.getSelectionModel().isEmpty()) {
                    alert.Alert(AlertType.WARNING,"Please select an ingredient before.",ButtonType.CLOSE);
                } else {
                    // Informe le controlleur du formulaire que l'on veut effectuer un ajout
                    dataSend.add("Details"); //Stockage de l'opération demandée
                    dataSend.add(listIngredients); //Stockage de l'ensemble des ingredients
                    dataSend.add(tab_ingredient.getSelectionModel().getSelectedItem()); //Stockage du ingredient concerné
                    // Redirection vers le formulaire d'ajout
                    DataHolder.getINSTANCE().ChangeScene((Stage) btnAdd.getScene().getWindow(), "Ingredients/Formulaire", "Formulaire");
                }
                break;

            // Ajout d'un ingredient à la base de données
            case "btnAdd":
                // Informe le controlleur du formulaire que l'on veut effectuer un ajout
                dataSend.add("Add"); //Stockage de l'opération demandée
                dataSend.add(listIngredients); //Stockage de l'ensemble des ingredients
                // Redirection vers le formulaire d'ajout
                DataHolder.getINSTANCE().ChangeScene((Stage) btnAdd.getScene().getWindow(), "Ingredients/Formulaire", "Formulaire");
                break;

            // Modification d'un ingredient existant
            case "btnModify":
                if (tab_ingredient.getSelectionModel().isEmpty()) {
                    alert.Alert(AlertType.WARNING,"Please select an ingredient before.",ButtonType.CLOSE);
                } else {
                    // Informe le controlleur du formulaire que l'on veut effectuer une modification
                    dataSend.add("Modify"); //Stockage de l'opération demandée
                    dataSend.add(listIngredients); //Stockage de l'ensemble des ingredients
                    dataSend.add(tab_ingredient.getSelectionModel().getSelectedItem()); //Stockage du ingredient concerné
                    // Redirection vers le formulaire de modification
                    DataHolder.getINSTANCE().ChangeScene((Stage) btnAdd.getScene().getWindow(), "Ingredients/Formulaire", "Formulaire");
                }
                break;

            // Suppression d'un ingredient à la base de données
            case "btnDelete":
                // Récupération de l'ID du ingredient à supprimer
                Ingredients deletedIngredients = tab_ingredient.getSelectionModel().getSelectedItem();

                // Le supprime de la BDD
                repoIngredients.Delete(deletedIngredients);

                // Le supprime de la filteredList d'abord, puis de l'observableList et raffraichit l'affichage du tableau
                listIngredientsFilter.remove(deletedIngredients);
                listIngredients.remove(deletedIngredients);
                tab_ingredient.refresh();

                alert.Alert(AlertType.INFORMATION,"Ingredient " + deletedIngredients.getName() + " deleted from DB.",ButtonType.CLOSE);

                break;

            default:
                break;
        }
    }

    /**
     * Récupère les datas stockées par le formulaire d'ingredients
     */
    public void getData()  {
        dataReceive.addAll(FormulaireController.dataSend);
    }

    /**
     * Change  l'aspect visuelle de la catégorie sélectionnée en tant que filtre
     * @param event Evenement déclenchant l'action
     */
    public void MenuClick(Event event) {
        Label choiceLabel = (Label) event.getSource();
        categoryName = choiceLabel.getText(); // Stocke la catégorie cliquée
        String labelID = choiceLabel.getId();

        for (Map.Entry<Label, FontIcon> element : catFilter.entrySet()) {
            // Change le style et affiche l'icone du menu sélectionné
                if (element.getKey().getId().equals(labelID)) {
                    element.getValue().setVisible(true);
                    element.getKey().setStyle("-fx-font-weight: bold");

                    // Filtre les données du tableau
                    if (categoryName.equals("All")) {categoryName = "";} // Réinitialisation du texte de la catégorie stockée
                    searchRecipe(); // Filtre les recettes affichées selon leur catégorie

                } else {
                    element.getValue().setVisible(false);
                    element.getKey().setStyle("");
                }
        }
    }

    /**
     * Fonction permettrant de filtrer la liste des recettes en utilisant le texte saisit dans l'input de recherche ou la catégorie sélectionnée dans le menu de gauche
     */
        public void searchRecipe() {
            // Utilise la liste filtrée en tant que jeu de données pour le tableau
            tab_ingredient.setItems(listIngredientsFilter);

            if (!searchText.isBlank() && !categoryName.isBlank()){ // Si une catégorie sélectionnée et un texte saisit
                if (!categoryName.equals("All")){
                    // Applique un filtre à la liste grâce aux deux filtres paramétrés
                    listIngredientsFilter.predicateProperty().bind(Bindings.createObjectBinding(()->
                            categoryNameFilter.get().and(searchTextFilter.get()),categoryNameFilter,searchTextFilter));
                }

            }else if (!searchText.isBlank()){ // Si seulement du texte saisit dans la barre de recherche {
                // Applique un filtre à la liste exclusivement selon le texte de la barre de recherche
                listIngredientsFilter.predicateProperty().bind(Bindings.createObjectBinding(()->
                        searchTextFilter.get()));

            }else if (!categoryName.isBlank() && !categoryName.equals("All")) { // Si seulement une catégorie sélectionnée
                // Filtre la liste exclusivement selon la catégorie sélectionnée
                listIngredientsFilter.predicateProperty().bind(Bindings.createObjectBinding(()->
                        categoryNameFilter.get()));

            } else{
                // Aucun filtre employé, réutilisation de l'ensemble du jeu de données
                tab_ingredient.setItems(listIngredients);
            }
        }
}