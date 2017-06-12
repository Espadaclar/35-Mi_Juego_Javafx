import javafx.scene.input.KeyCode;
import javafx.scene.control.Label;
import javafx.scene.paint.*;

import javafx.scene.Group;
/**
 * Write a description of class Objetos_De_Apollo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Objetos_De_Apollo
{
    // instance variables - replace the example below with your own
    private Label bolitasEliminadas;

    /**
     * Constructor for objects of class Objetos_De_Apollo
     */
    public Objetos_De_Apollo()
    {
         

    }


    /**
     * el parámetro es el contador de las bolas que se van eliminando.
     */
    public void crearUnLabel(Label nuevoLabel, Group root, int x, int y ){
        nuevoLabel.setTranslateX(x);
        nuevoLabel.setTranslateY(y);
        nuevoLabel.setTextFill(Color.BLACK);
        nuevoLabel.setStyle("-fx-font-size: 2em;");
        root.getChildren().add( nuevoLabel);
    }
}
