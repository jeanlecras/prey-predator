package prey_predator;
import java.util.Random;

public class Prey extends Animal {
    
    private double averageEscapeRate = 0.55;   
    private double stdDevEscapeRate = 0.1;
    private double escapeRate;
    
    Prey() {
        super(0.9, 0.1, 10.0, 1.0);
        Random r = new Random();
        do {
            escapeRate = r.nextGaussian()*stdDevEscapeRate + averageEscapeRate;
        } while(escapeRate <= 0 || escapeRate >= 1);
    }
    
    Prey(double avr, double sdr, double avl, double sdl, double ave, double sde)
           throws IllegalArgumentException {
        super(avr, sdr, avl, sdl);
        if (ave <= 0 || ave >= 1 || sde < 0) {
            throw(new IllegalArgumentException("La moyenne de la capacité d'échappement doit être comprise entre 0 et 1"));
        }
        Random r = new Random();
        do {
            escapeRate = r.nextGaussian()*stdDevEscapeRate + averageEscapeRate;
        } while(escapeRate <= 0 || escapeRate >= 1);
    }
    
    public double getEscapeRate() {
        return this.escapeRate;
    }
    
    public boolean isAbleToEscape() {
        return Math.random() < this.escapeRate;
    }
    
    public String toString() {
        String s = super.toString(); // appel de la méthode toString de la classe Animal
        s += "Capacité d'échappement : " + this.escapeRate + "\n";
        return s;
    }
    
    public static void main(String args[]) {
        Prey p1 = new Prey();
        System.out.println(p1);
        
        Prey p2 = new Prey(0.9, 0.1, 10.0, 1.0, 0.5, 0.1);
        System.out.println(p2);
        
        System.out.println("Test isAbleToEscape :\n");
        System.out.println(
            p2.isAbleToEscape()+" "+p2.isAbleToEscape()+" "+p2.isAbleToEscape()+" "+
            p2.isAbleToEscape()+" "+p2.isAbleToEscape()+" "+p2.isAbleToEscape()
        );
        
        System.out.println("\nTest isAlive :");
        System.out.println(p2.isAlive());
    }
}
