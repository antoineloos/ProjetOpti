/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threading;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import projet_opti.Projet_Opti;
import static projet_opti.Projet_Opti.startGenetique;
import static projet_opti.Projet_Opti.startTabou;
import utils.CSVUtils;

/**
 *
 * @author Epulapp
 */
public class GeneticRunnable implements Runnable {

    public final int size;
    public final int pas;
    public final String name;
    public final int start;

    public GeneticRunnable(int min, int max, int p, String path) {
        size = max;
        pas = p;
        name = path;
        start = min;
    }

    @Override
    public void run() {
        try {
      

            String csvFile = name;
            FileWriter writer = new FileWriter(csvFile);

            CSVUtils.writeLine(writer, Arrays.asList("", "genetic"), ';');
            for (int i = start; i <= size; i += pas) {

                String resGenetic = startGenetique(i);

                CSVUtils.writeLine(writer, Arrays.asList(String.valueOf(i), resGenetic), ';');

            }
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }
}
