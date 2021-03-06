package com.mealwith.GUI.Home;

import com.mealwith.Service.CustomsFonts;
import com.mealwith.Service.DataHolder;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    public HBox Recipes,Dashboard,Ingredients,Customers,Orders,Comments;
    @FXML
    public ImageView ImgLogo,ImgRecipes,ImgDashboard,ImgIngredients,ImgCustomers,ImgOrders,ImgComments;

    private final CustomsFonts customsFonts = new CustomsFonts(); // Service permettant de stocker les Fonts utilisés dans le projet
    public Text textLogo; // Logotype

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Changement de la font du Logo
        textLogo.setFont(customsFonts.LogoFont(Double.parseDouble("80")));

        // Récupération des images
            ImgLogo.setImage(new Image("img/Logo Mealwith.png"));
            ImgRecipes.setImage(new Image("img/Recipes icon.png"));
            ImgDashboard.setImage(new Image("img/Dashboard icon.png"));
            ImgIngredients.setImage(new Image("img/Ingredients icon.png"));
            ImgCustomers.setImage(new Image("img/Customers icon.png"));
            ImgOrders.setImage(new Image("img/Orders icon.png"));
            ImgComments.setImage(new Image("img/Comments icon.png"));

        // Ajout des gestionnaires d'écoute sur les menus
            List<HBox> hBoxList = FXCollections.observableArrayList(Recipes,Dashboard,Ingredients,Customers,Orders,Comments);
            for (HBox hbox:hBoxList) {

                // Redirection si clic sur le menu
                hbox.setOnMouseClicked(event -> {
                    DataHolder.getINSTANCE().ChangeScene((Stage) hbox.getScene().getWindow(),hbox.getId(),hbox.getId());
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