import javafx.scene.input.KeyCode;
import javafx.scene.control.Label;
import javafx.scene.paint.*;

import javafx.scene.Group;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
import javafx.scene.media.MediaView;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Toolkit;

import java.util.Timer;
import java.util.TimerTask;
/**
 * Write a description of class Objetos_De_Apollo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Objetos_De_Apollo
{

    private Label bolitasEliminadas;

    /**
     * Constructor for objects of class Objetos_De_Apollo
     */
    public Objetos_De_Apollo()
    {

    }

    /**
     * para crear elementos Label.
     */
    public void crearUnLabel(Label nuevoLabel, Group root, int x, int y ){
        nuevoLabel.setTranslateX(x);
        nuevoLabel.setTranslateY(y);
        nuevoLabel.setTextFill(Color.BLACK);
        nuevoLabel.setStyle("-fx-font-size: 2em;");
        root.getChildren().add( nuevoLabel);
    }
    
    public void sonido(){
        TimerTask tareaSonido = new TimerTask() {
                @Override
                public void run() {
                    Toolkit.getDefaultToolkit().beep();
                }                        
            };

        Timer timerSonido = new Timer();
        timerSonido.schedule(tareaSonido, 0, 100);
    }
}