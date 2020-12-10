package com.mealwith.Service;

import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class CustomsFonts {
    /**
     * Font 'DancingScript' de Google Fonts utilisés pour le logotype 'MealWith'
     * @param size Taille de la font demandée
     * @return Font 'DancingScript' à la taille demandée
     */
    public Font LogoFont(Double size) {
        try {
            return Font.loadFont(new FileInputStream("src/main/resources/fonts/DancingScript-VariableFont_wght.ttf"), size);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}