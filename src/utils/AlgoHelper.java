/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.ArrayList;
import java.util.Random;


/**
 *
 * @author Epulapp
 */
public class AlgoHelper {
    public static Random rnd = new Random();
    
    public static int[] CreateNewPlateauOpti(int size)
    {
        ArrayList<Integer> newPlateau = new ArrayList<Integer>();
        newPlateau.add((size / 2) - 1);
        for (int i = 1; i < size; i++) {
            if (!newPlateau.contains((newPlateau.get(i - 1) + 2) % size)) {
                newPlateau.add((newPlateau.get(i - 1) + 2) % size);
            } else {
                newPlateau.add(0);
            }

        }
        
        return newPlateau.stream().mapToInt(Integer::intValue).toArray();
    }
    
    public static int[] CreateNewPlateauStd(int size) {
        int[] plateau = new int[size];
        for (int i = 0; i < size; i++) {
            plateau[i] = i;
        }
        return plateau;
    }
    
     public static int[] CreateNewPlateauRand(int size) {
        int[] plateau = new int[size];
        for (int i = 0; i < size; i++) {
            plateau[i] = i + rnd.nextInt(size-i);
        }
        return plateau;
    }
    
    
    public static int computeConfict(int[] plateau) {
        int fitness = 0;

        for(int i = 0; i < plateau.length-1; i++) {
            for(int j = i+1; j < plateau.length; j++) {
                if (Math.abs(plateau[i] - plateau[j]) == (Math.abs(i - j))) {
                    fitness++;
                }
            }
        }
        return fitness;
    }
    
    public int ComputeConflict(ArrayList<Integer> lst) {
        int fitness = 0;
        for (int i = 0; i < lst.size() - 1; i++) {
            for (int j = i; j < lst.size(); j++) {
                if (lst.get(i + 1) == lst.get(i) + j || lst.get(i + 1) == lst.get(i) - j) {
                    fitness++;
                }
            }

        }

        return fitness;
    }
    
    public int computeConfict(int[] plateau, int index) {
        int fitness = 0;
        if (index < plateau.length) {
            for (int i = index + 1; i < plateau.length-1; i++) {
                if (Math.abs(plateau[index] - plateau[i]) == i - index) {
                    fitness++;
                }
            }
            index++;
            fitness += computeConfict(plateau, index);
        }
        return fitness;
    }
    
    public static int[] getVoisins(int[] plateau) {
        
        int a = rnd.nextInt(plateau.length);
        int b = a;
        int temp;
        while (a == b) {
            b = rnd.nextInt(plateau.length);
        }
        int[] voisins = plateau.clone();
        temp = voisins[a];
        voisins[a] = voisins[b];
        voisins[b] = temp;
        return voisins;
    }
    
    
}
