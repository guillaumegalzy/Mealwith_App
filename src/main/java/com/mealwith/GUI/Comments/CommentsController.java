package com.mealwith.GUI.Comments;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import com.mealwith.DAO.CommentsDAO;
import com.mealwith.Entity.Comment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import com.mealwith.Service.DataHolder;
import com.mealwith.Service.SceneManager;

import java.util.ArrayList;
import java.util.List;

public class CommentsController {
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
    private List<Comment> comments;

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
        ArrayList<Object> list = new ArrayList<Object>();
        list.add(c);
        DataHolder.getINSTANCE().setList(list);

        SceneManager sm = new SceneManager();
        Stage stage = (Stage) commentsTable.getScene().getWindow();
        sm.ChangeScene(stage, "detail");
    }
}
