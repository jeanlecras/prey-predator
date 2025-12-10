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
    
    PreyPredator(int npreys, int npred, double avrPrey, double sdrPrey, double avlPrey, double sdlPrey,
                 double avePrey, double sdePrey, double avrPred, double sdrPred, double avlPred,
                 double sdlPred, double avaPred, double sdaPred) {

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
        final int MAX_ATTACKS = 2; // ou 1 si tu veux exactement l’ancien comportement

        for (Predator predator : predators) {

            for (int k = 0; k < MAX_ATTACKS; k++) {

                // sécurité : plus aucune proie
                if (preys.isEmpty()) {
                    predator.starvation();
                    break;
                }

                if (predator.canAttack()) {

                    int preyIndex = r.nextInt(preys.size());  // ← plus jamais d’erreur ici
                    Prey prey = preys.get(preyIndex);

                    if (prey.isAbleToEscape()) {
                        predator.starvation();
                    } else {
                        preys.remove(preyIndex);

                        // re-sécurité après suppression
                        if (preys.isEmpty()) break;

                        break; // il a mangé → stop
                    }
                }
            }
        }




        /* age increment and death of animals */
        this.year++;
        preys.stream().forEach(Prey::incrementAge);
        predators.stream().forEach(Predator::incrementAge);
        predators.removeIf(p -> !p.isAlive());
        preys.removeIf(p -> !p.isAlive());
    }
    
    public String toString() {
        String s = "*****" + "Année "+year+"*****"+"\n";
        s += "Nombre de prédateurs "+predators.size()+"\n";
        s += "Nombre de proies "+preys.size()+"\n";
        return s;
    }
   
    public List<Prey> getPreys() {
        return this.preys;
    }
    
    public Set<Predator> getPreds() {
        return this.predators;
    }

    /**
     * Permet générer un fichier csv avec l'évolution du nombre de proies et de prédateur
     * en fonction de l'année (utile pour générer des graphiques)
     */
    public static void getData(String filePath) throws IOException {        
        int nyears = 1000;
        int npreys = 3000;
        int npred = 800;
        double avrPrey = 0.9;
        double sdrPrey = 0.1;
        double avlPrey = 8.0;
        double sdlPrey = 1.0;
        double avePrey = 0.7;
        double sdePrey = 0.1;
        double avrPred = 0.9;
        double sdrPred = 0.1;
        double avlPred = 12;
        double sdlPred = 3;
        double avaPred = 0.4;
        double sdaPred = 0.1;
        
        PreyPredator pp = new PreyPredator(npreys, npred, avrPrey, sdrPrey, avlPrey, sdlPrey,
                                           avePrey, sdePrey, avrPred, sdrPred, avlPred,
                                           sdlPred, avaPred, sdaPred);        
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("year,predators,preys");
            writer.newLine();

            for (int y=0; y<nyears; y++) {

                writer.write(y + "," + pp.getPreds().size() + "," + pp.getPreys().size());
                writer.newLine();

                // 🔥 on NE S'ARRÊTE PLUS JAMAIS : simulation jusqu'à 1000 ans garantie
                // même si predators = 0 ou preys = 0
                
                pp.oneYear();
            }
        }
    }
    
    public static void main(String[] args) {
        try {
            getData("simulation3.csv");
            System.out.println("\nCSV généré : simulation.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
