package com.mealwith.gui.Login;

import com.mealwith.DAO.UsersDAO;
import com.mealwith.Entity.Users;
import com.mealwith.Service.CustomsFonts;
import com.mealwith.Service.PasswordVerify;
import com.mealwith.Service.SceneHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    public TextField inputEmail;
    @FXML
    public PasswordField inputPassword;
    @FXML
    public Text errorMailIndic,errorMailMessage,errorPasswordIndic,errorPasswordMessage;
    @FXML
    public ImageView ImgLogo;
    @FXML
    public Button btnLogIn;
    @FXML
    public Text textLogo;

    public UsersDAO repoUsers;
    private final CustomsFonts customsFonts = new CustomsFonts(); // Service permettant de stocker les Fonts utilisés dans le projet

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Image du logIn
            Image logInImg = new Image("img/LogIn.png");
            ImgLogo.setImage(logInImg);

        // Changement de la font du Logo
            textLogo.setFont(customsFonts.LogoFont(Double.parseDouble("100")));
    }

    public void btnLogIn_Click(ActionEvent actionEvent)  {
        // Initialisation du repo Users
            repoUsers = new UsersDAO();

        // Vérification que le champ email n'est pas vide en première instance
            if(this.inputEmail.getLength() == 0){
                this.inputEmail.setStyle("-fx-border-color: red;-fx-border-width: 1pt");
                this.errorMailIndic.setVisible(true);
                this.errorMailMessage.setVisible(true);
                this.errorMailMessage.setText("An email is required");
            } else {

                // Vérification de l'existence de l'utilisateurs en BDD
                try {
                    Object testUserExist = repoUsers.FindByMail(this.inputEmail.getText());
                    if (testUserExist == null){
                        this.inputEmail.setStyle("-fx-border-color: red;-fx-border-width: 1pt");
                        this.errorMailIndic.setVisible(true);
                        this.errorMailMessage.setVisible(true);
                        this.errorMailMessage.setText("No user with this email");
                    }else {
                        Users user = (Users) testUserExist;
                        this.inputEmail.setStyle("");
                        this.errorMailIndic.setVisible(false);
                        this.errorMailMessage.setVisible(false);

                        // Vérification de la correspondance du password pour cet utilisateur
                            PasswordVerify passwordVerify = new PasswordVerify();
                            if (passwordVerify.CheckPassword()){
                                //Logged
                                this.errorMailMessage.setVisible(false);
                                this.errorMailIndic.setVisible(false);

                                SceneHandler sceneHandler = new SceneHandler();
                                sceneHandler.setScene(actionEvent,"Home","Home");
                            }
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
    }
}