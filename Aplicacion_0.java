import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.paint.*;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.util.Duration;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import javafx.scene.control.Button;
import javafx.animation.Animation.Status;
import java.util.Random;
import javafx.scene.shape.Rectangle;

import javafx.scene.input.KeyCode;
import javafx.scene.control.Label;

//PARA CREAR EL CRONÓMETRO.
import java.util.Timer;
import java.util.TimerTask;

import java.util.ArrayList;
import javafx.scene.shape.Shape;
/**
 * Write a description of class Circulo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Aplicacion_0 extends Application 
{
    private Circle cicle;
    private Rectangle rectangulo;
    //velocidad del círculo.
    private int velocidadX = 1;
    private int velocidadY = 1;
    //velocidad de la barra.
    private int velocidadEnBarraX;
    private int velocidadEnBarraY;

    Color COLOR_ESCENA = Color.WHITE;
    int LARGO_ESCENA = 500;
    int ALTO_ESCENA = 650;
    int LARGO_BOTON = 80;
    int ALTO_BOTON = 5;

    int RADIO = 20;

    int LARGO_CAZADOR = (RADIO *3) +5;
    int ALTO_CAZADOR = (RADIO *3) +5;
    int POSICION_X_CAZADOR = LARGO_ESCENA /4;
    int POSICION_Y_CAZADOR = ALTO_ESCENA /5;

    public static void main(String[] args){
        //Esto se utiliza para ejecutar la aplicación 
        //es como el new Contructor()
        launch(args);
    }

    public void start(Stage ventana){//parámetro que va ha ser la ventan de la aplicación
        Group root = new Group(); //contenedor que colocamos dentro de la escena.

        Scene escena = new Scene(root, LARGO_ESCENA, ALTO_ESCENA, COLOR_ESCENA);//Se crea la escena con el contenedor que contiene los objetos.
        ventana.setScene(escena);//pasamos al parámetro ventana el objeto escena.

        //SE CREA EL CAZADOR
        Cazador cazador = new Cazador(POSICION_X_CAZADOR, POSICION_Y_CAZADOR, LARGO_CAZADOR, ALTO_CAZADOR, COLOR_ESCENA);
        root.getChildren().add(cazador);

        // SE CREA LA PELOTA
        Pelota pelota = new Pelota(LARGO_ESCENA/2, ALTO_ESCENA/2, RADIO);
        root.getChildren().add(pelota);
        /////////////////////////////////////////////////CREACIÓN DE UN BOTÓN
        Button boton = new Button("Stop / Move");
        boton.setDefaultButton(true);
        boton.setLayoutX(15);
        boton.setLayoutY(ALTO_ESCENA - (ALTO_BOTON +10 ));
        boton.setPrefSize(LARGO_BOTON, ALTO_BOTON);
        root.getChildren().add(boton);

        

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        //define un valor de movimiento en los ejes x / y.
        KeyFrame kf = new KeyFrame(Duration.seconds(.002), new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        //PARA QUE SE MUEVA LA POLOTA .
                        pelota.mover(LARGO_ESCENA, ALTO_ESCENA);

                        //PARA QUE SE MUEVA LA BARRA .
                        cazador.mover(LARGO_ESCENA, ALTO_ESCENA);

                    }
                });

        timeline.getKeyFrames().add(kf);
        timeline.play();
        ventana.show();

        //////////////////////  PARA ACTIVAR Y DESACTIVAR EL BOTÓN CUANDO ÉSTE ESTÁ ACTIVADO.
        boton.setOnAction(event2 -> {
                if (timeline.getStatus() == Status.PAUSED){
                    timeline.play();
                }
                else{
                    timeline.pause();
                } 
            });

        ////////////////////  para controlar AL CAZADOR con los botones de izquierda/derecha.

        root.setOnKeyPressed(event ->{
                if(event.getCode() == KeyCode.RIGHT){
                    cazador.cambiarDireccionDerecha(LARGO_ESCENA);
                }
                else if(event.getCode() == KeyCode.LEFT){
                    cazador.cambiarDireccionIzquierda();
                }
                else if(event.getCode() == KeyCode.UP){
                    cazador.cambiarDireccionArriba(ALTO_ESCENA);
                }
                else if(event.getCode() == KeyCode.DOWN){
                    cazador.cambiarDireccionAbajo();
                }
                else if(event.getCode() == KeyCode.ENTER){
                    if( cazador.capturadaPelota(pelota) != false ){
                        root.getChildren().remove(pelota);
                    }
                }
            });

    }

}

