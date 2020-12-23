package com.mealwith.GUI.Ingredients;

import com.mealwith.DAO.CategoriesIngredientsDAO;
import com.mealwith.DAO.IngredientsDAO;
import com.mealwith.Entity.Ingredients;
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
import javafx.scene.Cursor;
import javafx.scene.Node;
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
    public HBox Home,hboxPagination;
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
    public Label catAll, catMeat, catFruits, catDairy, catGro,actualPageCount,lastPage,firstPage;
    @FXML
    public FontIcon iconDelete, iconAdd, iconModify, iconDetails, iconTri, catAllIcon, catMeatIcon, catFruitsIcon, catDairyIcon, catGroIcon,previousPage,nextPage;
    @FXML
    public TextField inputSearch;

    // Logo et font du logotype
    private final CustomsFonts customsFonts = new CustomsFonts(); // Service permettant de stocker les Fonts utilisés dans le projet
    public Text textLogo; // Logotype

    // Envoi / Réception de données par ce formulaire
    public static List<Object> dataSend = new ArrayList<>(); // Données qui seront stockées par ce controlleur et utilisé par le controlleur de destination

    // Liste d'objet 'Ingredients' récupéré à partir de la base de données
    public ObservableList<Ingredients> listIngredients = FXCollections.observableArrayList(); // Avec toutes les informations
    public ObservableList<Ingredients> listIngredientsReduced = FXCollections.observableArrayList(); // Seul l'ID,le nom,l'ID de la catégorie et l'URL de l'image de l'ensemble des ingredients

    // Classes DAO utilisées
    public IngredientsDAO repoIngredients = new IngredientsDAO();

    // Gestions des filtres par catégories ou par le texte de la barre de recherche
    public Map<Label, FontIcon> catFilter = new HashMap<>(); // Map associant les noms des catérogies dans le menu de gauche avec leur icône respective
    public FilteredList<Ingredients> listIngredientsFilter = new FilteredList<>(FXCollections.observableList(listIngredientsReduced)); // Liste des ingredients filtrée par catégorie ou texte de la search bar
    public ObjectProperty<Predicate<Ingredients>> categoryNameFilter = new SimpleObjectProperty<>(); // Filtre sur la catégorie sélectionnée dans le menu de gauche
    public ObjectProperty<Predicate<Ingredients>> searchTextFilter = new SimpleObjectProperty<>(); // Critère pour le filtre sur le texte de la barre de recherche
    public String categoryName = ""; // Reccueil la catégorie sélectionnée
    public String searchText = "";  // Reccueil le texte saisit dans l'input de recherche

    // Gestion des erreurs / Informations
    public AlertMessage alert = new AlertMessage();

    // Pagination
    public int limitIngredient = 7 ; // Nombre d'élément par page
    public int offset = 0 ; // Index du premier élèment à retourner, décalage des lignes à obtenir. Initialisation à 0.
    public int pageCount ; // Nombre de pages nécessaires à l'affichage des ingredient
    public int pageNumber = 1; // Numéro de la page en cours. Initialisation à 1.
    public int categoryID = 0 ; // ID de la catégorie pour laquelle on souhaite effectuer la pagination, si égal à 0 alors retourne tous les ingredients
    public int totalNumberIngredient = 0; // Nombre total d'ingredient dans la BDD ou pour une catégorie donnée.

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Changement de la font du Logo
            textLogo.setFont(customsFonts.LogoFont(Double.parseDouble("80")));

        // Vide les précédentes données récupérées et envoyées
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

        // Récupération des ingrédients dans la BDD ou via le controlleur de provenance
            try {
                listIngredients.addAll(repoIngredients.ListPagination(limitIngredient, offset,0 ));
                listIngredientsReduced.addAll(repoIngredients.ListReduced());
                pagination(); // Création automatique de la pagination
            } catch (SQLException throwables) {
                throwables.printStackTrace();
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
                    dataSend.add(repoIngredients.Find(tab_ingredient.getSelectionModel().getSelectedItem().getId())); //Stockage de l'ingredient concerné
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
                    dataSend.add(repoIngredients.Find(tab_ingredient.getSelectionModel().getSelectedItem().getId())); //Stockage de l'ingredient concerné
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
     * Change  l'aspect visuelle de la catégorie sélectionnée en tant que filtre
     * @param event Evenement déclenchant l'action
     */
    public void MenuClick(Event event) throws SQLException {
        Label choiceLabel = (Label) event.getSource();
        categoryName = choiceLabel.getText(); // Stocke la catégorie cliquée
        CategoriesIngredientsDAO repoCatIng = new CategoriesIngredientsDAO();
        String labelID = choiceLabel.getId();

        for (Map.Entry<Label, FontIcon> element : catFilter.entrySet()) {
            // Change le style et affiche l'icone du menu sélectionné
                if (element.getKey().getId().equals(labelID)) {
                    element.getValue().setVisible(true);
                    element.getKey().setStyle("-fx-font-weight: bold");

                    // Filtre les données du tableau
                    if (categoryName.equals("All")) { // Réinitialisation du texte de la catégorie stockée si la catégorie sélectionnée est "All" et réinitilisation du CategoryID
                        categoryName = "";
                        categoryID = 0;
                    }else {
                        categoryID = repoCatIng.GetIDByName(categoryName);
                    }
                    // Réinitilise le numéro de page lors du changement de catégorie
                        pageNumber = 1;

                    // Actualisation de la pagination
                        pagination();
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

    /**
     * Fonction permettrant de générer une pagination pour les ingredients
     */
    private void pagination() throws SQLException {
        // Calcul des nombres d'ingrédients et de pages nécessaires pour les afficher
            if (categoryID == 0){
                totalNumberIngredient = repoIngredients.CountAll(); // Compte le nombre d'ingrédients, toutes catégories confondues
            } else{
                totalNumberIngredient = repoIngredients.CountByCat(categoryID); // Compte le nombre d'ingrédients, pour une catégorie donnée
            }
            pageCount = (totalNumberIngredient % limitIngredient) > 0 ? (totalNumberIngredient / limitIngredient) + 1: (totalNumberIngredient / limitIngredient) ;

        // Affiche le texte du nombre de page total et de la page en cours
            actualPageCount.setText("Page " + pageNumber + " of " + pageCount);

        // Met à jour l'offset selon le numéro de la page en cours de consultation
            offset = (pageNumber * limitIngredient) - limitIngredient;

        // Modification de la liste des ingredients pour n'afficher que ceux de la catégorie
            listIngredients.clear();
            listIngredients.addAll(repoIngredients.ListPagination(limitIngredient, offset, categoryID));
            tab_ingredient.setItems(listIngredients);

        // Génération du menu de pagination en bas de page
            hboxPagination.getChildren().clear(); // On réinitialise le menu de pagination avant de le (ré)alimenter
            int elipsisNumbePrev = 0;
            int elipsisNumberNext = 0;
            System.out.println(
                    "totalNumberIngredient : " + totalNumberIngredient
                    + " | offset : " + offset
                    + " | pageCount : " + pageCount
                    + " | pageNumber : " + pageNumber
                    + " | categoryID : " + categoryID); //TODO supprimer

            for (int i = 1; i < pageCount+1; i++) {
                Label numberPagination = new Label(String.valueOf(i));
                numberPagination.getStyleClass().add("paginationNumber");

                if(i == 1 || i == pageCount || i == (pageNumber-1) || i == (pageNumber+1) || i == pageNumber){ // Limite min et max
                    hboxPagination.getChildren().add(numberPagination);

                }else if(i < (pageNumber - 1) && elipsisNumbePrev < 1){ // Si pas encore d'éllipse, en ajouter une pour les pages précédentes
                    numberPagination.setText("...");
                    hboxPagination.getChildren().add(numberPagination);
                    elipsisNumbePrev ++ ;

                }else if (i > (pageNumber + 1) && elipsisNumberNext < 1){ // Si pas encore d'éllipse, en ajouter une pour les pages suivantes
                    numberPagination.setText("...");
                    hboxPagination.getChildren().add(numberPagination);
                    elipsisNumberNext ++ ;
                }
            }

        // Gestion du comportement de la navigation via les pages de la pagination automatique
            for (Node element: hboxPagination.getChildren()) {

                // Initialisation pour la première ouverture du formulaire, met en avant la première page automatiquement
                if(((Label)element).getText().equals(String.valueOf(pageNumber))){ // Si égal à la page en cours
                    element.getStyleClass().add("selected");
                }

                // Création gestionnaire d'écoute sur click d'un des labels ajoutés lors de la pagination
                if(!((Label)element).getText().equals("...")){ // Si le texte du label n'est pas une elipse

                    element.setCursor(Cursor.HAND); // Informe l'utilisateur qu'il peut cliquer dessus en modifiant le style du curseur

                    // Ajout du style 'selected' pour ne mettre en avant le numéro de la page qui vient d'être sélectionnée
                    element.setOnMouseClicked(event -> {
                            for (Node label: hboxPagination.getChildren()) {
                                label.getStyleClass().remove("selected");
                            }
                            element.getStyleClass().add("selected");

                        // Met à jour le numéro de page
                            pageNumber = Integer.parseInt(((Label)element).getText());

                        // Met à jour la pagination automatique
                            try {
                                pagination();
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                    });
                }
            }

        // Gestion du comportement de la navigation via les flèches de la pagination automatique
            previousPage.setCursor(Cursor.HAND);
            previousPage.setOnMouseClicked(event -> {
                // Met à jour le numéro de page
                pageNumber--;

                // Met à jour la pagination automatique
                try {
                    pagination();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });

            nextPage.setCursor(Cursor.HAND);
            nextPage.setOnMouseClicked(event -> {
                // Met à jour le numéro de page
                pageNumber++;

                // Met à jour la pagination automatique
                try {
                    pagination();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });

            firstPage.setCursor(Cursor.HAND);
            firstPage.setOnMouseClicked(event -> {
                // Met à jour le numéro de page
                pageNumber = 1;

                // Met à jour la pagination automatique
                try {
                    pagination();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });

            lastPage.setCursor(Cursor.HAND);
            lastPage.setOnMouseClicked(event -> {
                // Met à jour le numéro de page
                pageNumber = pageCount;

                // Met à jour la pagination automatique
                try {
                    pagination();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
    }
}