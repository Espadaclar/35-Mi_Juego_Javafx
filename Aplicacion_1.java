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
import java.awt.Toolkit;

import java.util.ArrayList;
import javafx.scene.shape.Shape;

import java.lang.Object;
import javafx.scene.Node;
import java.util.ArrayList;

/**
 * franciscoJavier
 */
public class Aplicacion_1 extends Application 
{
    private  Pelota pelota;
    private ArrayList<Poligono> arboles = new ArrayList<>(); // ----------- l�nea 126.
    private ArrayList<Pelota> pelotas = new ArrayList<>();   // ----------- l�nea 130 / 136
    //velocidad del c�rculo.
    private int velocidadX = 1;
    private int velocidadY = 1;
    //velocidad de la barra.
    private int velocidadEnBarraX;
    private int velocidadEnBarraY;

    private int contDeTiempo = 1;
    //----- EL CRONOMETRO DEL JUEGO ES DESCENDENTE, EMPIEZA EN 'tiempoEnSegundos'.
    private int tiempoEnSegundos = 966;
    private int numeroBolaEnescena = 0;
    private int eliminados = 0;
    private boolean ponArbol = false;

    // MECANISMO DE CORRECCI�N,( para que aparezca un arbol cada vez que desaparece una bolita.)
    private int corrector_1 = -1;
    private int corrector_0 = -1;

    Color COLOR_ESCENA = Color.WHITE;
    private boolean sonidoDeCapturado = false;
    private boolean seCreaUnArbol = false;
    private int arbolesPlantados = 0;

    int LARGO_ESCENA = 1200;
    int ALTO_ESCENA = 650;
    int LARGO_CAZADOR = 60;
    int ALTO_CAZADOR = 60;
    int LARGO_BOTON = 80;
    int ALTO_BOTON = 5;
    int POSICION_X_CAZADOR = LARGO_ESCENA /4;
    int POSICION_Y_CAZADOR = ALTO_ESCENA /5;

    int NUM_DE_BOLAS = 20;
    int NUM_DE_ARBOLES = 20;
    private int[] conColor = new int[NUM_DE_ARBOLES];

    int RADIO = 15;

    public static void main(String[] args){
        //Esto se utiliza para ejecutar la aplicaci�n 
        //es como el new Contructor()
        launch(args);
    }

    public void start(Stage ventana){//par�metro que va ha ser la ventan de la aplicaci�n

        Group root = new Group(); //contenedor que colocamos dentro de la escena.

        Scene escena = new Scene(root, LARGO_ESCENA, ALTO_ESCENA, COLOR_ESCENA);//Se crea la escena con el contenedor que contiene los objetos.
        ventana.setScene(escena);//pasamos al par�metro ventana el objeto escena.

        ////////////////////////////////////////////////////////////////////////////////***********************************      SE CREA EL POLIGONO CAZADOR DE BOLITAS.

        Poligono cazador = new Poligono(POSICION_X_CAZADOR, POSICION_Y_CAZADOR, LARGO_CAZADOR, ALTO_CAZADOR, COLOR_ESCENA);
        root.getChildren().add(cazador);

        ///////////////////////////////////////////////////////////////// *******************     COLECCioN DE POLIGONOS ARBOL.
        int coorDeX = LARGO_ESCENA /10 ;//------variable para determinar la coordenada de X.
        int aux = coorDeX;
        int aux2 = coorDeX;
        int acumCoorDeX = 0;  //--------------- variable final para coordenada X de todos los arboles.
        int coorDeY = (ALTO_ESCENA - 150);
        for(int i = 0; i < NUM_DE_ARBOLES ; i ++){            
            if(i < 9 ){
                acumCoorDeX += coorDeX;
            }
            else if(i >= 18){
                coorDeY = ALTO_ESCENA - 450;
                acumCoorDeX = aux2 ;
                aux2 += coorDeX;
            }
            else if(i >= 9 && i < 18){    
                coorDeY = ALTO_ESCENA - 300;
                acumCoorDeX = aux ;
                aux += coorDeX;
            }
            Poligono arbol = new Poligono(acumCoorDeX, coorDeY, 10, 100, Color.BLACK);
            arboles.add(arbol);
        }
        /////////////////////////////////////////////////////////////////////////////////////********* COLECCioN DE PELOTAS.

        pelota =  new  Pelota( LARGO_ESCENA/2,ALTO_ESCENA/2, RADIO);
        for(int i = 0; i < NUM_DE_BOLAS ; i ++){
            Random ale = new Random();
            pelota = new Pelota(ale.nextInt( (LARGO_ESCENA/2) -(RADIO)) +RADIO , ale.nextInt( (ALTO_ESCENA/2) -RADIO ) +RADIO , 10);
            //pelota = new Pelota(ale.nextInt(LARGO_ESCENA/2), ale.nextInt(ALTO_ESCENA/2), ale.nextInt(RADIO) +10);
            pelotas.add(pelota);
        }
        for(int i = 0; i < NUM_DE_BOLAS; i ++ ){// a�ade pelotas a la escena.
            root.getChildren().add(pelotas.get(i));
        }

        /////////////////////////////////////////////////////////////////////////////////////////SE CREA UN  CRON�METRO

        Label tiempoPasado = new Label("0");  //-------------objeto Label para pasar como parametro.
        Objetos_De_Apollo miLabelCronometro = new Objetos_De_Apollo();//-Objetos_De_Apollo, hecha en este proyecto.
        miLabelCronometro.crearUnLabel(tiempoPasado, root,12, 25);//-------muestra cronometro  de descuento.

        ////////////////////////////////////////////////////SE CREA EL  CONTADOR DEL N� DE  BOLITAS QUE SE VAN ELIMINANDO, ////     

        Label bolitasEliminadas = new Label();  //-------------objeto Label para pasar como parametro.
        Objetos_De_Apollo miLabelContador = new Objetos_De_Apollo();//-Objetos_De_Apollo, hecha en este proyecto.
        miLabelContador.crearUnLabel(bolitasEliminadas, root, 12, 60);//-------muestra el n� de bolitas eliminadas.

        ////////////////////////////////////////////////////////////////////////////////// SE CREA UN BOT�N

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
        KeyFrame kf = new KeyFrame(Duration.seconds(.005), new EventHandler<ActionEvent>() {

                    ///////////////// ---------VARIABLE PARA PERMITIR EL RECUENTO DE LAS BARRITAS QUE SE VAN ELIMINADO.
                    int val = root.getChildren().size();
                    boolean sonidoDeCapturados = false;
                    public void handle(ActionEvent event) {
                        //DAR MOVIMIENTO A LAS PELOTAS .
                        for(int i = 0; i < NUM_DE_BOLAS; i ++){
                            pelotas.get(i).mover(LARGO_ESCENA, ALTO_ESCENA);                           
                        }

                        ///////////////////////////////////////////////// cada vez que se elimina una bolita aparece un arbol.
                        //                         if(eliminados > corrector_0){
                        //                             corrector_1 ++;
                        //                             corrector_0 = eliminados;
                        //                         }
                        eliminados = ( val - (root.getChildren().size()) );///---- n� de bolitas eliminadas.
                        bolitasEliminadas.setText("Eliminadas  " +arbolesPlantados);
                        //bolitasEliminadas.setText("Eliminadas  " +eliminados);
                        if(ponArbol == true){
                            root.getChildren().add(arboles.get(arbolesPlantados));
                            arbolesPlantados ++;
                            ponArbol = false;
                        }

                        ///////////////////////////////////////////////// Actualizamos la etiqueta del tiempo
                        int minutos = tiempoEnSegundos / 60;
                        int segundos = tiempoEnSegundos % 60;
                        tiempoPasado.setText(minutos + ":" + segundos);  
                        if(tiempoEnSegundos == 0){
                            timeline.stop();
                            root.getChildren().removeAll(pelotas);
                            Label finDelJuego = new Label("0");  //-------------objeto Label para pasar como parametro.
                            Objetos_De_Apollo miLabelCronometro = new Objetos_De_Apollo();//-Objetos_De_Apollo, hecha en este proyecto.
                            miLabelCronometro.crearUnLabel(finDelJuego, root,(LARGO_ESCENA /2) -100, ALTO_ESCENA /2);//-------muestra cronometro  de descuento.
                            finDelJuego.setText(" -- GANE  OVER -- ");
                        }

                        ////////////////////////////////////////////////////PARA QUE SE MUEVA EL CUADRADO CAZADOR .
                        cazador.mover(LARGO_ESCENA, ALTO_ESCENA);

                        for(Pelota pelota: pelotas){
                            if( cazador.capturadaPelota(pelota) == true ){
                                sonidoDeCapturado = true;
                            }
                            else if(cazador.capturadaPelota(pelota) == false){
                                sonidoDeCapturado = false;
                            }
                        }

                        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                        //PARA QUE REBOTE el cuadrado CAZADOR AL COLISIONAR CON LAS BARRITAS.
                        //for(int i = 0; i < pelotas.size(); i ++ ){
                        for(int a = 0; a < arboles.size(); a ++ ){

                            double MinX_Cazador =  cazador.getBoundsInParent().getMinX() -0.5;
                            double MinY_Cazador = cazador.getBoundsInParent().getMinY() ;
                            double MaxX_Cazador = cazador.getBoundsInParent().getMaxX() -0.5;
                            double MaxY_Cazador = cazador.getBoundsInParent().getMaxY() ;

                            double MinX_Arbol =  arboles.get(a).getBoundsInParent().getMinX();
                            double MinY_Arbol = arboles.get(a).getBoundsInParent().getMinY();
                            double MaxX_Arbol = arboles.get(a).getBoundsInParent().getMaxX();                                
                            double MaxY_Arbol =  arboles.get(a).getBoundsInParent().getMaxY();

                            //// EL CAZADOR REBOTA AL CHOCAR EN LA COPA DE LOS ARBOLES Y CAMBIAN DE COLOR.
                            if( MaxY_Cazador == MinY_Arbol &&  MaxX_Cazador  > MinX_Arbol &&
                            (MinX_Cazador  < MaxX_Arbol ) ) {  

                                Random aleatorio = new Random();
                                Color color = new Color(aleatorio.nextDouble(), aleatorio.nextDouble(), aleatorio.nextDouble(), aleatorio.nextDouble());
                                if(color == COLOR_ESCENA ){
                                    color = Color.GREEN;
                                }
                                else if(color == Color.BLACK){
                                    color = Color.GREEN;
                                }
                                cazador.cambiarDireccionArriba(ALTO_ESCENA);
                                arboles.get(a).setFill(color); 

                                //se almacena en un Array le n� veces que ha sido golpeada cada barrita, a la 2� se pone roja
                                // a la 3� desaparece.
                                conColor[a] = conColor[a] +1; 
                                for(int i = 0; i < NUM_DE_ARBOLES; i ++){
                                    if(conColor[i] == 2){
                                        arboles.get(i).setFill(Color.RED);
                                    }
                                    else if(conColor[i] == 3){
                                        root.getChildren().remove(arboles.get(i));
                                        arboles.get(i).setStroke(COLOR_ESCENA);
                                    }
                                }

                            }

                        }
                        // }

                    }
                });

        timeline.getKeyFrames().add(kf);
        timeline.play();
        ventana.show();
        ////////////////////////////////////////////////////////////  PARA ACTIVAR Y DESACTIVAR EL BOT�N CUANDO ESTE ACTIVADO.
        boton.setOnAction(event2 -> {
                if (timeline.getStatus() == Status.PAUSED){
                    timeline.play();
                }
                else{
                    timeline.pause();
                } 
            });

        /////////////////////////////////////////////////////////  DA DIRECCION  AL CAZADOR CON LOS BOTONES  de izquierda/derecha.
        root.setOnKeyPressed(event2 ->{
                if(event2.getCode() == KeyCode.RIGHT){
                    cazador.cambiarDireccionDerecha(LARGO_ESCENA);
                }
                else if(event2.getCode() == KeyCode.LEFT){
                    cazador.cambiarDireccionIzquierda();
                }

                else if(event2.getCode() == KeyCode.UP){
                    cazador.cambiarDireccionArriba(ALTO_ESCENA);
                }
                else if(event2.getCode() == KeyCode.DOWN){
                    cazador.cambiarDireccionAbajo();
                }
                else if(event2.getCode() == KeyCode.ENTER){

                    for(int i = 0; i < pelotas.size(); i ++){
                        //for(Pelota pelota: pelotas){
                        if( cazador.capturadaPelota(pelotas.get(i)) == true ){
                            root.getChildren().remove(pelotas.get(i));

                            ponArbol = true;//---------- se muestra un arbol en pantalla.
                            //root.getChildren().remove(pelota);

                        }
                    }

                }

            });
        //---------- REALIZA UNA ACCION CADA UN N� DETERMINADO DE MILISEGUNDOS.
        TimerTask tarea = new TimerTask() {
                @Override
                public void run() {
                    tiempoEnSegundos--;
                    contDeTiempo ++;
                }                        
            };

        Timer timer = new Timer();
        timer.schedule(tarea, 0, 1000);

        //-----PARA QUE SUENE LA ALARMA CUANDO UNA PELOTA ES ATRAPADA POR EL CAZADOR.

        TimerTask tareaSonido = new TimerTask() {
                @Override
                public void run() {

                    if( sonidoDeCapturado == true ){
                        Toolkit.getDefaultToolkit().beep();    
                    }

                }                        
            };

        Timer timerSonido = new Timer();
        timer.schedule(tareaSonido, 0, 5000);
    }

}