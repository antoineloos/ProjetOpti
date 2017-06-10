/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithmes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;
import javafx.util.Pair;
import utils.AlgoHelper;

/**
 *
 * @author Epulapp
 */
public class Tabou {

    int size;
    int[] plateau;
    int[] solution;
    int bestFitness;
    int nbIterMax = 2500;
    int lstTabouSize = 50;
    LinkedList<Vector> lstTabou;

    public Tabou() {

        lstTabou = new LinkedList<Vector>();
    }

    public Pair ComputeTabou(int[] plateau) {
        plateau = plateau;
        size = plateau.length;
        solution = plateau.clone();
        for (int k = 0; k < nbIterMax; k++) {
            Pair voisin = getBestVoisin(plateau);
            int res = AlgoHelper.computeConfict((int[]) voisin.getKey());
            if (res < AlgoHelper.computeConfict(solution)) {
                solution = (int[]) voisin.getKey();
            }

            if (res - AlgoHelper.computeConfict(plateau) >= 0) {
                this.addTabou((Vector) voisin.getValue());
            }
            plateau = (int[]) voisin.getKey();

            if (AlgoHelper.computeConfict(solution) == 0) {

                return new Pair(AlgoHelper.computeConfict(solution), solution);
            }
        }
        return new Pair(AlgoHelper.computeConfict(solution), solution);
    }

    private Pair getBestVoisin(int[] plateau) {
        int iBestVoisin = 0, jBestVoisin = 0, nbConflitsBestVoisin = Integer.MAX_VALUE;
        int[] bestVoisinArray = new int[0];

        for (int i = 0; i < plateau.length - 1; i++) {
            for (int j = i + 1; j < plateau.length; j++) {
                boolean stop = false;
                for (Vector vec : lstTabou) {
                    if (vec == new Vector(i, j)) {
                        stop = true;
                        break;
                    }
                }
                if (stop) {
                    continue;
                }

                int[] voisinArray = this.switchColums(i, j, plateau);

                int nbConflitsVoisin = AlgoHelper.computeConfict(voisinArray);
                if (nbConflitsVoisin < nbConflitsBestVoisin) {
                    nbConflitsBestVoisin = nbConflitsVoisin;
                    bestVoisinArray = voisinArray;
                    iBestVoisin = i;
                    jBestVoisin = j;
                }
            }
        }

        return new Pair(bestVoisinArray, new Vector(iBestVoisin, jBestVoisin));
    }

    private int[] switchColums(int i, int j, int[] plateau) {
        int[] voisinArray = plateau.clone();
        int temp = voisinArray[i];
        voisinArray[i] = voisinArray[j];
        voisinArray[j] = temp;
        return voisinArray;
    }

    private void addTabou(Vector vec) {
        lstTabou.add(vec);
        if (lstTabou.size() >= lstTabouSize) {
            lstTabou.remove();
        }
    }
}
