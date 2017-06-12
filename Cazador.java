import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import java.util.Random;
import java.util.ArrayList;
/**
 * @author franciscoJavier
 */
public class Cazador extends Rectangle{

    private double x;
    private double y;
    private double width;
    private double height;
    private Color color;

    private int velocidadX;
    private int velocidadY;
    //ladrillos que van siendo eliminados.
    private int eliminados;

    public Cazador(double x, double y, double width, double height, Color color) {
        super();

        this.setTranslateY(y);
        this.setWidth(width);
        this.setHeight(height);
        this.setFill(color);
        this.setStroke(Color.BLACK);

        velocidadX = 1;
        velocidadY = 1;
        eliminados = 0;

    }

    public Cazador() {
        Random ale = new Random();
        Color colorLadrillo = new Color(ale.nextFloat(), ale.nextFloat(), ale.nextFloat(), ale.nextFloat());
        this.setTranslateX(122);
        this.setTranslateY(237);
        this.setWidth(100);
        this.setHeight(20);
        this.setFill(colorLadrillo);
        this.setStroke(Color.BLACK);
    }

    public void mover(int largoEscena, int altoEscena){
        setTranslateX(getTranslateX() + velocidadX);
        setTranslateY(getTranslateY() + velocidadY);

        /////para que la barra no se salga de los límites de la escena.
        if(getBoundsInParent().getMinX() <= 0 || 
        getBoundsInParent().getMaxX() >= (largoEscena) ){
            velocidadX = -velocidadX;
        }
        else  if( getBoundsInParent().getMinY() <= 0 || 
        getBoundsInParent().getMaxY() >= (altoEscena)){
            velocidadY = -velocidadY;
        }

    }

    public void cambiarDireccionDerecha(int largoEscena){
        if(getBoundsInParent().getMaxX() != largoEscena){
            velocidadX = 1;
        }
    }

    public void cambiarDireccionIzquierda(){
        if(getBoundsInParent().getMinY() != 0 ){
            velocidadX =  -1;
        }
    }

    public void cambiarDireccionArriba(int altoEscena){
        if(getBoundsInParent().getMaxY() != altoEscena){
            velocidadY = -1;
        }
    }

    public void cambiarDireccionAbajo(){
        if(getBoundsInParent().getMinX() != 0 ){
            velocidadY =  1;
        }
    }

    public boolean capturadaPelota(Pelota pelota ){
        Shape c = Shape.intersect(this, pelota);
        boolean capturada = false;
        double cap_MaxX = getBoundsInParent().getMaxX();
        double cap_MinX = getBoundsInParent().getMinX();
        double cac_MinY = getBoundsInParent().getMinY();
        double cac_MaxY = getBoundsInParent().getMaxY();

        double pelota_MaxX = pelota.getBoundsInParent().getMaxX();
        double pelota_MinX = pelota.getBoundsInParent().getMinX();
        double pelota_MinY = pelota.getBoundsInParent().getMinY(); 
        double pelota_MaxY = pelota.getBoundsInParent().getMaxY();

        if(c.getBoundsInParent().getWidth() != -1){
            if( pelota_MaxX <= cap_MaxX && pelota_MinX >= cap_MinX &&
            pelota_MinY >= cac_MinY && pelota_MaxY <=  cac_MaxY){

                capturada = true;
            }
        }
        return capturada;
    }

}

  