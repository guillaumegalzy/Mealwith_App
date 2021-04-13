package com.mealwith.GUI.Login;

import com.mealwith.DAO.UsersDAO;
import com.mealwith.Entity.Users;
import com.mealwith.Service.CustomsFonts;
import com.mealwith.Service.DataHolder;
import com.mealwith.Service.PasswordVerify;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
            ImgLogo.setImage(new Image("img/LogIn.png"));

        // Changement de la font du Logo
            textLogo.setFont(customsFonts.LogoFont(Double.parseDouble("100")));
    }

    public void btnLogIn_Click(ActionEvent actionEvent)  {

// PERMETTRAIT LA VERIFICATION DE LEXISTENCE DUN UTILISATEUR VIA REQUETE API SYMFONY
//        String userLoginMail = inputEmail.getText();
//        String plainPassword = inputPassword.getText();
//
//        // Get the instance of CheckCredentialsService
//        CheckCredentialsService APIService = CheckCredentialsUtils.getAPIService();
//
//        Call<CheckCredentialsApiResponse> callAPIService = APIService.checkCredentials(userLoginMail, plainPassword);
//
//        callAPIService.enqueue(new Callback<>() {
//            @Override
//            public void onResponse(Call<CheckCredentialsApiResponse> call, Response<CheckCredentialsApiResponse> response) {
//                if(response.isSuccessful()) {
//                    System.out.println("OK");
//                } else {
//                    System.out.println("KO");
//                    System.out.println("code : " + response.code());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<CheckCredentialsApiResponse> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
//
//        try {
//            Response<CheckCredentialsApiResponse> response = callAPIService.execute();
//            CheckCredentialsApiResponse credentialsApiResponse = response.body();
//            System.out.println(credentialsApiResponse.toString());
//            System.out.println(callAPIService.request());
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }

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
                                DataHolder.getINSTANCE().ChangeScene((Stage) btnLogIn.getScene().getWindow(),"Home","Home");
                            }
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
    }
}