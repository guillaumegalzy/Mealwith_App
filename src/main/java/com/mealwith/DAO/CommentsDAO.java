package com.mealwith.DAO;

import com.mealwith.Entity.Comment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentsDAO {

    /**
     * Construct a prepared select request by concatenating the field filter and the id
     * @param filterBy
     * @param id
     * @return a list of comments
     */
    public static List<Comment> getComments(String filterBy, int id) {
        List<Comment> commentList = new ArrayList<Comment>();

        String filter;

        switch (filterBy){
            case "user" : filter = "user_id";

                break;
            case "recipe" : filter = "id_recipe_id";

                break;
            case "ingredient" : filter = "id_ingredient_id";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + filterBy);
        }

        try {
            String url = "jdbc:mysql://localhost:3306/mealwith?serverTimezone=UTC";
            Connection con = DriverManager.getConnection(url, "root", "");

            String statement = "SELECT * FROM Comments WHERE " + filter + " = ?";

            PreparedStatement stm = con.prepareStatement(statement);

            stm.setInt(1, id);

            ResultSet result = stm.executeQuery();

            while (result.next()) {
                //DAO TEST
                Integer i = null;

                Integer recipeId  = result.getInt("id_recipe_id");
                if(result.wasNull())recipeId = null;

                Integer ingredientId  = result.getInt("id_ingredient_id");
                if(result.wasNull())ingredientId = null;

                Comment c = new Comment(result.getInt("id"), result.getInt("user_id"),
                        recipeId, ingredientId,
                        result.getDate("date_creation"), result.getString("content"));

                commentList.add(c);
            }

            stm.close();
            result.close();
            con.close();

        } catch (Exception e) {
            System.out.println("Erreur de lecture 'Comments'");
            System.out.println(e.getMessage());
        }
        return commentList;
    }

    public static void Delete(int id){
        try {
            String url = "jdbc:mysql://localhost:3306/mealwith?serverTimezone=UTC";
            Connection con = DriverManager.getConnection(url, "root", "");

            PreparedStatement stm = con.prepareStatement("DELETE FROM Comments WHERE id = ?");

            stm.setInt(1, id);

            ResultSet result = stm.executeQuery();

            stm.close();
            result.close();
            con.close();

        } catch (Exception e) {
            System.out.println("Erreur de suppression 'Comments'");
            System.out.println(e.getMessage());
        }

    }

}
