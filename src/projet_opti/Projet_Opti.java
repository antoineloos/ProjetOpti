/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet_opti;

import algorithmes.Recuit;
import algorithmes.Tabou;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import javafx.util.Pair;
import utils.AlgoHelper;

/**
 *
 * @author Epulapp
 */
public class Projet_Opti {

    public static void main(String[] args) throws IOException {

        System.out.println("tapez le numéro correspondant à l'algorithme souhaité : 1-Recuit , 2-Tabou , 3-AlgoGen");
       
        Scanner scanner = new Scanner(System.in);
       
        
        switch (Integer.parseInt(scanner.nextLine())) {
            case 1:
                startRecuit();
                break;
            case 2:
                startTabou();
                break;
            case 3:
                break;
            default:
                System.out.println("votre demande n'est pas correcte");
        }

    }

    public static void startTabou() {
        int GridSize = 200;
        int nbLancement = 5;

        long[] lstElapsedTime = new long[nbLancement];
        long moyenne = 0;
        for (int i = 0; i < nbLancement; i++) {
            Tabou t = new Tabou();
            long startTime = System.currentTimeMillis();
            Pair res = t.ComputeTabou(AlgoHelper.CreateNewPlateauOpti(GridSize));
            long estimatedTime = System.currentTimeMillis() - startTime;
            lstElapsedTime[i] = estimatedTime;
            // System.out.println("fitness : " + res.getKey() + " time ellapsed : " + String.valueOf(estimatedTime / 1000.0) + " solution : " + Arrays.toString((int[]) res.getValue()));
            System.out.println("fitness : " + res.getKey() + " time ellapsed : " + String.valueOf(estimatedTime / 1000.0));

        }

        for (long l : lstElapsedTime) {
            moyenne += l;
        }

        System.out.println("moyenne de temps pour recuit : " + String.valueOf(moyenne / (float) nbLancement / 1000.0));
    }

    public static void startRecuit() {
        int GridSize = 200;
        int nbLancement = 50;

        long[] lstElapsedTime = new long[nbLancement];
        long moyenne = 0;
        for (int i = 0; i < nbLancement; i++) {
            Recuit r = new Recuit(AlgoHelper.CreateNewPlateauOpti(GridSize));
            long startTime = System.currentTimeMillis();
            Pair res = r.ComputeRecuit();
            long estimatedTime = System.currentTimeMillis() - startTime;
            lstElapsedTime[i] = estimatedTime;
            // System.out.println("fitness : " + res.getKey() + " time ellapsed : " + String.valueOf(estimatedTime / 1000.0) + " solution : " + Arrays.toString((int[]) res.getValue()));
            System.out.println("fitness : " + res.getKey() + " time ellapsed : " + String.valueOf(estimatedTime / 1000.0));

        }

        for (long l : lstElapsedTime) {
            moyenne += l;
        }

        System.out.println("moyenne de temps pour recuit : " + String.valueOf(moyenne / (float) nbLancement / 1000.0));
    }

    public static void PrintResult(int[] lst, int size) {
        for (int row = 0; row < size; row++) {
            System.out.println("");

            for (int column = 0; column < size; column++) {
                if (lst[column] == row) {
                    System.out.print("|" + "D");
                } else {
                    System.out.print("|" + "_");
                }
            }
            System.out.print("|");
        }
        System.out.println("");

    }
}
