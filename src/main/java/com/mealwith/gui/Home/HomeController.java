package com.mealwith.gui.Home;

import com.mealwith.Service.SceneHandler;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    public HBox Recipes,Dashboard,Ingredients,Customers,Orders,Comments;
    @FXML
    public ImageView ImgLogo,ImgRecipes,ImgDashboard,ImgIngredients,ImgCustomers,ImgOrders,ImgComments;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Récupération des images
            Image Img = new Image("img/Logo Mealwith.png");
            ImgLogo.setImage(Img);

            Img = new Image("img/Recipes icon.png");
            ImgRecipes.setImage(Img);

            Img = new Image("img/Dashboard icon.png");
            ImgDashboard.setImage(Img);

            Img = new Image("img/Ingredients icon.png");
            ImgIngredients.setImage(Img);

            Img = new Image("img/Customers icon.png");
            ImgCustomers.setImage(Img);

            Img = new Image("img/Orders icon.png");
            ImgOrders.setImage(Img);

            Img = new Image("img/Comments icon.png");
            ImgComments.setImage(Img);

        // Ajout des gestionnaires d'écoute sur les menus
            List<HBox> hBoxList = FXCollections.observableArrayList(Recipes,Dashboard,Ingredients,Customers,Orders,Comments);
            for (HBox hbox:hBoxList) {

                // Redirection si clic sur le menu
                hbox.setOnMouseClicked(event -> {
                    SceneHandler sceneHandler = new SceneHandler();
                    sceneHandler.setScene(event,hbox.getId());
                });

                // Hover effect si survol du menu
                    hbox.setOnMouseEntered(event -> {
                        DropShadow dropShadow = new DropShadow();
                        dropShadow.setRadius(8.0);
                        dropShadow.setOffsetX(4.0);
                        dropShadow.setOffsetY(4.0);
                        dropShadow.setColor(Color.rgb(206, 119, 95));
                        hbox.setEffect(dropShadow);
                        hbox.setScaleX(1.1);
                        hbox.setScaleY(1.1);
                        hbox.setScaleZ(1.1);
                    });

                    hbox.setOnMouseExited(event -> {
                        hbox.setEffect(null);
                        hbox.setScaleX(1);
                        hbox.setScaleY(1);
                        hbox.setScaleZ(1);
                    });
            }
    }
}
