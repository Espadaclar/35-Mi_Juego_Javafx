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
public class Circulo extends Application 
{
    private Circle cicle;
    private Rectangle rectangulo;
    //velocidad del círculo.
    private int velocidadX = 1;
    private int velocidadY = 1;
    //velocidad de la barra.
    private int velocidadEnBarraX;
    private int velocidadEnBarraY;

    int LARGO_ESCENA = 500;
    int ALTO_ESCENA = 650;
    int LARGO_BOTON = 80;
    int ALTO_BOTON = 5;
    
    int RADIO = 20;
    
    int LARGO_CAZADOR = (RADIO *2) +5;
    int ALTO_CAZADOR = (RADIO *2) +5;
    
    public static void main(String[] args){
        //Esto se utiliza para ejecutar la aplicación 
        //es como el new Contructor()
        launch(args);
    }

    public void start(Stage ventana){//parámetro que va ha ser la ventan de la aplicación
        Group root = new Group(); //contenedor que colocamos dentro de la escena.

        Scene escena = new Scene(root, LARGO_ESCENA, ALTO_ESCENA, Color.WHITE);//Se crea la escena con el contenedor que contiene los objetos.
        ventana.setScene(escena);//pasamos al parámetro ventana el objeto escena.

        //////////////////////////////para pasar coordenadas aleatorias a la situación inicias del círculo:
        Random ale = new Random();
        int coordenadaX = ale.nextInt(430) +10; 
        int coordenadaY = ale.nextInt(430) +10; 

        //se crea un rectángulo
        Rectangle cazador = new Rectangle();
        cazador.setLayoutY(470);
        cazador.setLayoutX(220);
        cazador.setWidth(LARGO_CAZADOR);
        cazador.setHeight(ALTO_CAZADOR);
        cazador.setStroke(Color.BLACK);
        cazador.setFill(Color.WHITE);
        root.getChildren().add(cazador);
        //
        
        //Se crea el círculo
        Circle circle = new Circle();
        circle.setCenterX(coordenadaX);
        circle.setCenterY(coordenadaY);
        circle.setCenterY(250.0f);
        circle.setFill(Color.RED);
        circle.setRadius(RADIO);
        root.getChildren().add(circle);
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

                        circle.setTranslateX(circle.getTranslateX() + velocidadX);
                        circle.setTranslateY(circle.getTranslateY() + velocidadY);
                        if(circle.getBoundsInParent().getMinX() <= 0 || 
                        circle.getBoundsInParent().getMaxX() >= (escena.getWidth()) ){
                            velocidadX = -velocidadX;

                        }
                        if(circle.getBoundsInParent().getMinY() <=  0 || 
                        circle.getBoundsInParent().getMaxY() >= (escena.getHeight()) ){
                            velocidadY = -velocidadY;
                        }

                        //PARA QUE SE MUEVA LA BARRA .
                        cazador.setTranslateX(cazador.getTranslateX() + velocidadEnBarraX);
                        cazador.setTranslateY(cazador.getTranslateY() + velocidadEnBarraY);
                        //para controlar la barra con los botones de izquierda/derecha.
                        root.setOnKeyPressed(event2 ->{
                                if(event2.getCode() == KeyCode.RIGHT){
                                    velocidadEnBarraX = 1;
                                }
                                else if(event2.getCode() == KeyCode.LEFT){
                                    velocidadEnBarraX = -1;
                                }
                                else if(event2.getCode() == KeyCode.UP){
                                    velocidadEnBarraY = -1;
                                }
                                else if(event2.getCode() == KeyCode.DOWN){
                                    velocidadEnBarraY = 1;
                                }
                            });

                        /////para que la barra no se salga de los límites de la escena.
                        if(cazador.getBoundsInParent().getMinX() <= 0 || 
                       cazador.getBoundsInParent().getMaxX() >= (escena.getWidth()) ){
                            velocidadEnBarraX = -velocidadEnBarraX;
                        }
                        else  if( cazador.getBoundsInParent().getMinY() <= 0 || 
                        cazador.getBoundsInParent().getMaxY() >= (escena.getHeight())){
                            velocidadEnBarraY = -velocidadEnBarraY;
                        }
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

        
    }
}