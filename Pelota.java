
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import java.util.Random;
/**
 * @author franciscoJavier
 */
public class Pelota extends Circle{
    private Circle pelota; 
    private double centerX;
    private double centerY;
    private double radius;
    private int velocidadEnX;
    private int velocidadEnY;

    private Color color;
    private Color bordePelota;
    public Pelota(double centerX, double centerY, double radius){
        super(centerX, centerY, radius);
        Random ale = new Random();
        Color colorPelota = new Color(ale.nextFloat(), ale.nextFloat(), ale.nextFloat(), ale.nextFloat());
        color = colorPelota;
        this.setCenterX(centerX);
        this.setCenterY( centerY);
        this.setRadius(radius);
        this.setFill(color);
        this.setStroke(Color.RED);
        velocidadEnX = 1;
        velocidadEnY = 1;
    }

    public Pelota() {
        super();
        Random ale = new Random();
        Color colorPelota = new Color(ale.nextFloat(), ale.nextFloat(), ale.nextFloat(), ale.nextFloat());
        this.setCenterX(250);
        this.setCenterY( 250);
        this.setRadius(20);
        this.setFill(colorPelota);
        this.setStroke(Color.RED);
        velocidadEnX = 1;
        velocidadEnY = 1;
    }

    public void mover(int largoEscena, int altoEscena){
        setTranslateX(getTranslateX() + velocidadEnX);
        setTranslateY(getTranslateY() + velocidadEnY);
        if(getBoundsInParent().getMinX() <= 0 || 
       getBoundsInParent().getMaxX() >= (largoEscena) ){
            velocidadEnX = -velocidadEnX;

        }
        if(getBoundsInParent().getMinY() <=  0 || 
        getBoundsInParent().getMaxY() >= (altoEscena) ){
            velocidadEnY = -velocidadEnY;
        }

    }

    /**
     * la pelota se queda en velocidad 0 cuando ha sobrepasado la raqueta. Este valor es utilizado para mostrar 'GANE OVER'
     */
    public int getVelocidadPelota(){
        int valor = 1;
        if(velocidadEnY == 0 && velocidadEnX == 0){
            valor = 0;
        }
        return valor;
    }

    public int moverHorizontal(){
        int val = 1;
        if( velocidadEnX == -1){
            val = -1;
        }
        return val;
    }
    public int moverVertical(){
        int val = 1;
        if( velocidadEnY == -1){
            val = -1;
        }
        return val;
    }
    public int getDireccionX(){
        return velocidadEnX;
    }
    public int getDireccionY(){
        return velocidadEnY;
    }
    
    /**
     * imprescindible para variar la velocidad de la bola cuando sea necesario en la clase Rectangulo
     */
    public void setVelocidad_X_APelota(int velocidad){
        velocidadEnX = velocidad;
    }

    public void setVelocidad_Y_APelota(int velocidad){
        velocidadEnY = velocidad;
    }
    
    public void ponerColorNegro(){
        color = Color.BLACK;
    }
    
    public void setColor(Color color7){
        color = color7;
    }
}

