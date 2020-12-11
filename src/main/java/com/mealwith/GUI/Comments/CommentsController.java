package com.mealwith.GUI.Comments;

import com.mealwith.DAO.CommentsDAO;
import com.mealwith.Entity.Comment;
import com.mealwith.Service.CustomsFonts;
import com.mealwith.Service.DataHolder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CommentsController implements Initializable {
    @FXML
    public TableView<Comment> commentsTable;
    @FXML
    public TableColumn<Comment, Integer> colID;
    @FXML
    public TableColumn<Comment, Integer> colUserID;
    @FXML
    public TableColumn<Comment, Integer> colRecipeID;
    @FXML
    public TableColumn<Comment, Integer> colIngredientID;
    @FXML
    public TableColumn<Comment, java.sql.Date> colCreationDate;
    @FXML
    public TableColumn<Comment, String> colContent;
    public TextField userIDText;
    public TextField recipeIDText;
    public TextField ingredientIDText;

    //private CommentsDAO commentsDAO= new CommentsDAO();
    public Button btnGetList;
    public ImageView ImgLogo;
    public Text textLogo;
    public HBox Home;
    private List<Comment> comments;
    private final CustomsFonts customsFonts = new CustomsFonts(); // Service permettant de stocker les Fonts utilisés dans le projet

    @FXML
    private ObservableList<Comment> model = FXCollections.observableArrayList();

    /**
     * Retrieves a list of comments from the db according to the parameters set in userData.
     */
    @FXML
    public void GetListByUser(ActionEvent e){
        int id = Integer.parseInt(userIDText.getText());
        comments = CommentsDAO.getComments("user", id);
        SetList();
    }
    public void GetListByRecipe(ActionEvent e){
        int id = Integer.parseInt(recipeIDText.getText());
        comments = CommentsDAO.getComments("recipe", id);
        SetList();
    }
    public void GetListByIngredient(ActionEvent e){
        int id = Integer.parseInt(ingredientIDText.getText());
        comments = CommentsDAO.getComments("ingredient", id);
        SetList();
    }

    public void SetList() {
        model.clear();
        //add the comments to the tableView
        model.addAll(comments);

        //associate the comments members to the tableview columns.
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUserID.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colRecipeID.setCellValueFactory(new PropertyValueFactory<>("recipeId"));
        colIngredientID.setCellValueFactory(new PropertyValueFactory<>("ingredientId"));
        colCreationDate.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        colContent.setCellValueFactory(new PropertyValueFactory<>("content"));

        commentsTable.setItems(model);
    }

    public void AccessDetail(MouseEvent e){
        Comment c = commentsTable.getSelectionModel().getSelectedItem();
        if(c == null || e.getClickCount() < 2){return;}
        ArrayList<Object> list = new ArrayList<>();
        list.add(c);
        DataHolder dataHolder = DataHolder.getINSTANCE();
        dataHolder.setList(list);
        Stage stage = (Stage) commentsTable.getScene().getWindow();
        dataHolder.ChangeScene(stage, "Comments","detail");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Changement de la font du Logo
        textLogo.setFont(customsFonts.LogoFont(Double.parseDouble("80")));

        // Récupération des images
        Image Img = new Image("img/Logo Mealwith.png");
        ImgLogo.setImage(Img);

        // Gestionnaire d'écoute sur le logo pour renvoyer au menu
        Home.setOnMouseClicked(event -> DataHolder.getINSTANCE().ChangeScene((Stage) Home.getScene().getWindow(), Home.getId(), Home.getId()));
    }
}
