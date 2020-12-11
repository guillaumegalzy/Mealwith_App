package com.mealwith.GUI.Comments;

import com.mealwith.DAO.CommentsDAO;
import com.mealwith.Entity.Comment;
import com.mealwith.Service.DataHolder;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DetailController implements Initializable {

    private Comment comment;
    public TextField idField;
    public TextField userIDField;
    public TextField recipeIDField;
    public TextField ingredientIDField;
    public TextField creationDateField;
    public TextField contentField;

    public void initialize(URL url, ResourceBundle resourceBundle){
        comment = (Comment) DataHolder.getINSTANCE().getList().get(0);

        idField.setText(String.valueOf(comment.getId()));
        userIDField.setText(String.valueOf(comment.getUserId()));
        recipeIDField.setText(String.valueOf(comment.getRecipeId()));
        ingredientIDField.setText(String.valueOf(comment.getIngredientId()));
        creationDateField.setText(String.valueOf(comment.getCreationDate()));
        contentField.setText(String.valueOf(comment.getContent()));

    }

    public void Back(){
        Stage stage = (Stage) idField.getScene().getWindow();
        DataHolder.getINSTANCE().ChangeScene(stage, "Comments","Comments");
    }

    public void Delete(ActionEvent actionEvent) {
        CommentsDAO.Delete(comment.getId());
        Back();
    }
}
