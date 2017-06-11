
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

    public Pelota(double centerX, double centerY, double radius){
        super(centerX, centerY, radius);
        Random ale = new Random();
        Color colorPelota = new Color(ale.nextFloat(), ale.nextFloat(), ale.nextFloat(), ale.nextFloat());
        this.setCenterX(centerX);
        this.setCenterY( centerY);
        this.setRadius(radius);
        this.setFill(colorPelota);
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

    public void mover(int num){
        setTranslateX(getTranslateX() + velocidadEnX);
        setTranslateY(getTranslateY() + velocidadEnY);
        if(getBoundsInParent().getMaxY() <= num  ){
            velocidadEnX = +6;
            velocidadEnY = +6;
        }
        //         else if(getBoundsInParent().getMinY() <=  0)        {
        //             velocidadEnY = -velocidadEnY;
        //         }
        //         else if(getBoundsInParent().getMaxY() >= minimoYRaqueta && 
        //                         (getBoundsInParent().getMaxX() - radius) <= maximoXRaqueta &&
        //                           getBoundsInParent().getMinX() >= minimoXRaqueta ){
        //             velocidadEnY = -velocidadEnY;
        //         }
        //         else if(getBoundsInParent().getMaxY() >= altoEscena){
        //             velocidadEnY = 0;
        //             velocidadEnX = 0;
        //         }
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

    /**
     * imprescindible para variar la velocidad de la bola cuando sea necesario en la clase Rectangulo
     */
    public void setVelocidad_X_APelota(int velocidad){
        velocidadEnX = velocidad;
    }

    public void setVelocidad_Y_APelota(int velocidad){
        velocidadEnY = velocidad;
    }
}

