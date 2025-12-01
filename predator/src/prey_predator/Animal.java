package prey_predator;
import java.util.Random;

public class Animal {
    
    private double averageReproductionRate = 0.3;
    private double stdDevReproductionRate = 0.1;
    
    private double averageLifeExpectancy = 10;
    private double stdDevLifeExpectancy = 4;
    
    private int age;
    private double reproductionRate;
    protected double lifeExpectancy; //doit être en protected pour implémenter la méthode starvation dans la classe Predator
    
    Animal() {
        Random r = new Random();
        
        do {
            reproductionRate = r.nextGaussian()*stdDevReproductionRate + averageReproductionRate;
            lifeExpectancy = r.nextGaussian()*stdDevLifeExpectancy + averageLifeExpectancy;
            age = (int)(r.nextDouble() * this.lifeExpectancy);
        } while (reproductionRate <= 0 || reproductionRate >= 1  || lifeExpectancy <= 0);
    }
    
    Animal(double averageReproductionRate, double stdDevReproductionRate, double averageLifeExpectancy, double stdDevLifeExpectancy) throws IllegalArgumentException{
        if (averageReproductionRate <= 0 || averageReproductionRate >= 1  || averageLifeExpectancy <= 0) {
            throw(new IllegalArgumentException("La moyenne du taux de reproduction doit être comprise entre 0 et 1, la moyenne de l'espérance de vie doit être supérieur à 0"));
        }
        this.averageLifeExpectancy = averageLifeExpectancy;
        this.stdDevLifeExpectancy = stdDevLifeExpectancy;
        this.averageReproductionRate = averageReproductionRate;
        this.stdDevReproductionRate = stdDevReproductionRate;
        
        Random r = new Random();
        
        do {
            reproductionRate = r.nextGaussian()*stdDevReproductionRate + averageReproductionRate;
            lifeExpectancy = r.nextGaussian()*stdDevLifeExpectancy + averageLifeExpectancy;
            age = (int)(r.nextDouble() * this.lifeExpectancy);
        } while (reproductionRate <= 0 || reproductionRate >= 1  || lifeExpectancy <= 0);
    }
    
    /* accesseurs */
    public double getReproRate(){
        return reproductionRate;
    }
    
    public int getAge() {
        return this.age;
    }
    
    public double getLifeExpectancy() {
        return this.lifeExpectancy;
    }
    
    /*methodes pour tous les animaux*/
    public boolean isAbleToReproduce(){
       return Math.random()< this.reproductionRate ;
    }
    
    public boolean isAlive() {
        return this.age < this.lifeExpectancy;
    }
    
    public void incrementAge() {
        this.age++;
    }
    
    /* surchage de la methode toString*/
    public String toString(){
        String s ="**** Animal ****\n" ;
        s += "Age : " + this.age + " pour une esperance de vie de " + this.lifeExpectancy +"\n";
        s += "Taux de reproduction de : " + this.reproductionRate+"\n" ;
        return s ;
    }
    
    public static void main(String args[]) {
        Animal an1 = new Animal();
        System.out.println(an1);
        
        Animal an2 = new Animal(0.95, 0.2, 10, 2);
        System.out.println(an2);
        
        System.out.println(an1.getReproRate());
        System.out.println(an2.getReproRate());

        System.out.println(an1.getLifeExpectancy());
        System.out.println(an2.getLifeExpectancy());

        System.out.println(an1.isAbleToReproduce());
        System.out.println(an2.isAbleToReproduce());

        System.out.println(an1.isAlive());
        System.out.println(an2.isAlive());

        System.out.println(an1.getAge());
        an1.incrementAge();
        System.out.println(an1.getAge());
        System.out.println(an2.getAge());
        an2.incrementAge();
        System.out.println(an2.getAge());
    }
}