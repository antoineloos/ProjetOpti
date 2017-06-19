/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threading;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static projet_opti.Projet_Opti.startRecuit;
import static projet_opti.Projet_Opti.startTabou;
import utils.CSVUtils;

/**
 *
 * @author Epulapp
 */
public class TabouRunnable implements Runnable {

    public final int size;
    public final int pas;
    public final String name;
    public final int start;

    public TabouRunnable(int min, int max, int p, String path) {
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
            

            CSVUtils.writeLine(writer, Arrays.asList("", "Tabou"), ';');
            for (int i = start; i <= size; i += pas) {

                String resTabou = startTabou(i);

                CSVUtils.writeLine(writer, Arrays.asList(String.valueOf(i), resTabou), ';');

            }
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }
}
