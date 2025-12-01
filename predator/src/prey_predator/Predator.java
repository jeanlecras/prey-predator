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
    
    Predator(double avr, double sdr, double avl, double sdl, double ava, double sda) throws IllegalArgumentException {
        super(avr, sdr, avl, sdl);
        if (ava <= 0 || ava >= 1) {
            throw(new IllegalArgumentException("La moyenne du taux d'attaque doit être comprise entre 0 et 1"));
        }
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
        this.lifeExpectancy -= 2;
    }
    
    public String toString() {
        String s = super.toString();
        s += "Taux d'attaque : " + this.attackRate+"\n" ;
        return s;
    }
    
    public static void main(String args[]) {
        Predator p1 = new Predator();
        System.out.println(p1);
        
        Predator p2 = new Predator(0.4, 0.1, 12.0, 3.0, 0.9, 0.1);
        System.out.println(p2);
        
        System.out.println("Test canAttack :\n");
        System.out.println(p2.canAttack()+" "+p2.canAttack()+" "+p2.canAttack()+" "+p2.canAttack()+" "+p2.canAttack()+" "+p2.canAttack()+" ");
        
        p2.starvation();
        System.out.println("Après starvation :\n"+p2);
        
        System.out.println("Test isAlive :\n");
        System.out.println(p2.isAlive());
    }
}