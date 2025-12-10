package prey_predator;
import java.util.Random;

public class Predator extends Animal {
    
    private double averageAttackRate = 0.9;
    private double stdDevAttackRate = 0.1;
    private double attackRate;
    
    Predator() {
        super(0.4, 0.1, 12.0, 3.0);
        Random r = new Random();
        do {
            attackRate = r.nextGaussian()*stdDevAttackRate + averageAttackRate;
        } while(attackRate <= 0 || attackRate >= 1);
    }
    
    Predator(double avr, double sdr, double avl, double sdl, double ava, double sda)
           throws IllegalArgumentException {
        super(avr, sdr, avl, sdl);
        Random r = new Random();
        do {
            attackRate = r.nextGaussian()*stdDevAttackRate + averageAttackRate;
        } while(attackRate <= 0 || attackRate >= 1);
    }
    
    public double getAttackRate() {
        return this.attackRate;
    }

    public boolean canAttack() {
        return Math.random() < this.attackRate;
    }
    
    public void starvation() {
        this.lifeExpectancy -= 1;   // ← SEULE MODIFICATION NÉCESSAIRE
    }
    
    public String toString() {
        String s = super.toString(); // appel de la méthode toString de Animal
        s += "Taux d'attaque : " + this.attackRate+"\n";
        return s;
    }
}
