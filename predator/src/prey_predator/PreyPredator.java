package prey_predator;
import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PreyPredator {
    
    // initialise un objet de la classe LinkedList qui implémente l'interface List; liste chainée contenant des objets Prey
    List<Prey> preys = new LinkedList<Prey>();
    // initialise un objet de la classe HashSet qui implémente l'interface Set; ensemble d'objets Predator
    Set<Predator> predators = new HashSet<Predator>();
    int maxPreys = 3000;
    int year = 0;
    
    PreyPredator() {
        for (int i=0; i<2500; i++) {
            preys.add(new Prey());
        }
        for (int i=0; i<500; i++) {
            predators.add(new Predator());
        }
    }   
    
    PreyPredator(int npreys, int npred) {
        for (int i=0; i<npreys; i++) {
            preys.add(new Prey());
        }
        for (int i=0; i<npred; i++) {
            predators.add(new Predator());
        }
    }
    
    PreyPredator(int npreys, int npred, double avrPrey, double sdrPrey, double avlPrey, double sdlPrey, double avePrey, double sdePrey, double avrPred, double sdrPred, double avlPred, double sdlPred, double avaPred, double sdaPred) {
        for (int i=0; i<npreys; i++) {
            preys.add(new Prey(avrPrey, sdrPrey, avlPrey, sdlPrey, avePrey, sdePrey));
        }
        for (int i=0; i<npred; i++) {
            predators.add(new Predator(avrPred, sdrPred, avlPred, sdlPred, avaPred, sdaPred));
        }
    }
    
    public void oneYear() {
        /* birth of animals */
        double babyPreds = 0;
        for (Predator predator : predators) {
            if (predator.isAbleToReproduce()) {
                babyPreds += 0.5;
            }
        }
        for (int i=0; i<babyPreds; i++) {
            Predator b = new Predator();
            predators.add(b);
        }
        
        double babyPreys = 0;
        for (Prey prey : preys) {
            if (prey.isAbleToReproduce() && preys.size() < this.maxPreys) {
                babyPreys += 0.5;
            }
        }
        for (int i=0; i<babyPreys; i++) {
            Prey b = new Prey();
            preys.add(b);
        }
        
        /* lunch time */
        Random r = new Random();
        for (Predator predator : predators) {
            if (predator.canAttack()) {
                int preyIndex = r.nextInt(preys.size());
                Prey prey = preys.get(preyIndex);
                if (prey.isAbleToEscape()) {
                    predator.starvation();
                } else {
                    preys.remove(prey);
                }
            }
        }
        
        /* age increment and death of animals */
        this.year++;
        preys.stream().forEach(Prey::incrementAge); // applique une méthode à tous les objets de la liste
        predators.stream().forEach(Predator::incrementAge);
        predators.removeIf(p -> !p.isAlive()); // on ne peut pas supprimer des éléments de la liste en la parcourant alors on utilise removeIf
        preys.removeIf(p -> !p.isAlive());
    }
    
    public String toString() {
        String s = "Année "+year+"\n";
        s += "Nombre de prédateurs "+predators.size()+"\n";
        s += "Nombre de proies "+preys.size()+"\n";
        return s;
    }
    
    /**
     * Permet générer un fichier csv avec l'évolution du nombre de proies et de prédateur en fonction de l'année (utile pour générer des graphiques)
     */
    public void getData(String filePath) throws IOException {        
        int nyears = 1000;
        int npreys = 2500;
        int npred = 500;
        double avrPrey = 0.9;
        double sdrPrey = 0.1;
        double avlPrey = 8.0;
        double sdlPrey = 1.0;
        double avePrey = 0.7;
        double sdePrey = 0.1;
        double avrPred = 0.9;
        double sdrPred = 0.1;
        double avlPred = 0.4;
        double sdlPred = 0.1;
        double avaPred = 12;
        double sdaPred = 3;
        
        PreyPredator pp = new PreyPredator(npreys, npred, avrPrey, sdrPrey, avlPrey, sdlPrey, avePrey, sdePrey, avrPred, sdrPred, avlPred, sdlPred, avaPred, sdaPred);        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("year,predators,preys");
            for (int y=0; y<nyears; y++) {
                writer.write(y+","+predators.size()+","+preys.size());
                writer.newLine();
                pp.oneYear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        PreyPredator pp = new PreyPredator(2500, 500);
        
        for (int y=0; y<1000; y++) {
            System.out.println(pp);
            pp.oneYear();
        }
    }
}