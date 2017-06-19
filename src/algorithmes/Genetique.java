/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithmes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javafx.util.Pair;
import utils.AlgoHelper;

/**
 *
 * @author Epulapp
 */
public class Genetique{
   private final int nbSelection = 50;
    private final double probaMutation = 0.10;
    private final int nbIterMax = 100000;
    private final int populationSize = 100;
    

    private int[] bestPlateau;
    private Random random;
    private int plateauSize;

    public Genetique(int plateauSize) {
        this.plateauSize = plateauSize;
        random = new Random();
        bestPlateau = AlgoHelper.CreateNewPlateauOpti(plateauSize);
    }

    private List<int[]> createInitialPopulation(int size) {
        List<int[]> population = new ArrayList<int[]>();
        for (int i = 0; i < size; i++) {
            int[] plateauArray = new int[plateauSize];
            for (int j = 0; j < plateauSize; j++) {
                plateauArray[j] = (j + i) % plateauSize;
            }
            int[] plateau = plateauArray;
            if (AlgoHelper.computeConfict(plateau) < AlgoHelper.computeConfict(bestPlateau)) {
                bestPlateau = plateau;
            }
            population.add(plateau);
        }
        return population;
    }

    public Pair ComputeGenetique() {
        List<int[]> population = createInitialPopulation(populationSize);
        for (int i = 0; i < nbIterMax; i++) {
            population = nextGeneration(population);
            if (AlgoHelper.computeConfict(bestPlateau) == 0) {
                System.out.println("nb iter : " + i);
                return new Pair(AlgoHelper.computeConfict(bestPlateau), bestPlateau);
            }
        }
        return new Pair(AlgoHelper.computeConfict(bestPlateau), bestPlateau);
    }

    private List<int[]> selectPopulation(List<int[]> population, int nb) {
        List<int[]> selectedPopulation = new ArrayList<>();
        Random random = new Random();
        Double finesseInverseTotale = 0.0;
        for (int[] plateau : population) {
            finesseInverseTotale += 1.0 / AlgoHelper.computeConfict(plateau);
        }
        double randomDouble = (random.nextDouble() * finesseInverseTotale) / nb;

        for (int i = 0; i < nb; i++) {
            double randomDoubleMoved = randomDouble + (finesseInverseTotale / nb) * i;
            int[] selectedPlateau = null;

            int j = 0;
            double somme = 0.0;
            while (somme <= randomDoubleMoved) {
                selectedPlateau = population.get(j);
                somme += 1.0 / AlgoHelper.computeConfict(selectedPlateau);
                j++;
            }
            selectedPopulation.add(selectedPlateau);
        }
        for (int[] plateau : selectedPopulation) {
            population.remove(plateau);
        }
        return selectedPopulation;
    }


    private List<int[]> nextGeneration(List<int[]> population) {
        List<int[]> newGeneration = new ArrayList<>();
        List<int[]> selectedPopulation = selectPopulation(population, nbSelection);

        for (int i = 0; i < selectedPopulation.size(); i += 2) {
            int[] plateau1 = selectedPopulation.get(i);
            int[] plateau2 = selectedPopulation.get(i + 1);
            List<int[]> enfants = croiser(plateau1, plateau2);
            for (int[] enfant : enfants) {
                if (AlgoHelper.computeConfict(enfant) < AlgoHelper.computeConfict(bestPlateau)) {
                    bestPlateau = enfant;
                    System.out.println(AlgoHelper.computeConfict(bestPlateau));
                }
                newGeneration.add(enfant);
            }
            newGeneration.add(plateau1);
            newGeneration.add(plateau2);
        }

        for (int[] plateau : population) {
            if (newGeneration.size() >= populationSize) break;
            newGeneration.add(plateau);
        }
        newGeneration = muter(newGeneration);
        return newGeneration;
    }

    private List<int[]> muter(List<int[]> population) {
        for (int i = 0; i < population.size(); i++) {
            //Mutation d'un individu selon la probabilité choisie, si mutation on choisit un voisin
            double randomDouble = random.nextDouble();
            if (randomDouble < probaMutation) {
                int[] plateau = AlgoHelper.getVoisins(population.get(i));
                population.set(i, plateau);
                if (AlgoHelper.computeConfict(plateau) < AlgoHelper.computeConfict(bestPlateau)) {
                    bestPlateau = plateau;
                    System.out.println(AlgoHelper.computeConfict(bestPlateau));
                }
            }
        }
        return population;
    }

    private List<int[]> croiser(int[] plateau1, int[] plateau2) {
        ArrayList<int[]> enfants = new ArrayList<int[]>();
        int[] enfant1, enfant2;
        Random random = new Random();


        int rnd = random.nextInt(plateauSize - 1);
        int[] enfant1Array = new int[plateauSize];
        int[] enfant2Array = new int[plateauSize];

        
        int[] plateau1part1 = Arrays.copyOfRange(plateau1, 0, rnd + 1);
        int[] plateau1part2 = Arrays.copyOfRange(plateau1, rnd + 1, plateauSize);
        int[] plateau2part1 = Arrays.copyOfRange(plateau2, 0, rnd + 1);
        int[] plateau2part2 = Arrays.copyOfRange(plateau2, rnd + 1, plateauSize);

        
        System.arraycopy(plateau1part1, 0, enfant1Array, 0, rnd + 1);
        System.arraycopy(plateau2part2, 0, enfant1Array, rnd + 1, plateauSize - rnd - 1);

        
        System.arraycopy(plateau2part1, 0, enfant2Array, 0, rnd + 1);
        System.arraycopy(plateau1part2, 0, enfant2Array, rnd + 1, plateauSize - rnd - 1);

     

        enfant1 = enfant1Array;
        enfant2 = enfant2Array;
        enfants.add( enfant1)  ;
        enfants.add( enfant2);

        return enfants;
    }



}