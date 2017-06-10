/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithmes;

import java.util.Random;
import javafx.util.Pair;
import utils.AlgoHelper;

/**
 *
 * @author Epulapp
 */
public class Recuit {

    //Paramètres de l'algo
    private final double probaInit = 0.00001;
    private final double nbIterMax = 20000;
    private final double N2 = 10;
    private final double gamma = 0.99;
    private Random rnd = new Random();
    private int plateauSize;
    private Double temperature;
    private int[] plateau;
    private int[] solution;

    public Recuit(int[] plateau) {
        this.plateauSize = plateau.length;
       
       plateau = AlgoHelper.CreateNewPlateauStd(plateauSize);
        solution = plateau.clone();
        temperature = calculInitialTemperature();
       
    }

    public Pair ComputeRecuit() {
        int delta;

        Double p;

        for (int k = 0; k < nbIterMax; k++) {
            for (int l = 0; l < N2; l++) {
                int[] voisins =  AlgoHelper.getVoisins(plateau);
                delta = AlgoHelper.computeConfict(voisins) - AlgoHelper.computeConfict( plateau );
                if (delta <= 0) {
                    plateau = voisins;
                    if (AlgoHelper.computeConfict(voisins) < AlgoHelper.computeConfict(solution)) {
                        solution = voisins.clone();
                        if (AlgoHelper.computeConfict(solution) == 0) {
                           
                            return new Pair(AlgoHelper.computeConfict(solution),solution);
                        }
                    }
                } else {
                    p = rnd.nextDouble();
                    if (p <= Math.exp(-((double) delta) / temperature)) {
                        plateau = voisins;
                    }
                }
            }
            
            temperature = temperature > 0.0 ? temperature * gamma : temperature;
        }

        return new Pair(AlgoHelper.computeConfict(solution),solution);
    }

    private double calculInitialTemperature() {
        double averageDelta = 0.0;
        for (int i = 0; i < 20; i++) {
            averageDelta += Math.abs(AlgoHelper.computeConfict(AlgoHelper.getVoisins(plateau)) - AlgoHelper.computeConfict(plateau));
        }
        averageDelta = averageDelta / 20;
        return -averageDelta / Math.log(probaInit);
    }

}
