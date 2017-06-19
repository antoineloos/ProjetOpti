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
import static projet_opti.Projet_Opti.startGenetique;
import static projet_opti.Projet_Opti.startRecuit;
import static projet_opti.Projet_Opti.startTabou;
import utils.CSVUtils;

/**
 *
 * @author Epulapp
 */
public class RecuitRunnable implements Runnable {

    public final int size;
    public final int pas;
    public final String name;
    public final int start ;

    public RecuitRunnable(int min ,int max, int p, String path) {
        size = max;
        pas = p;
        name = path;
        start = min;
    }

    @Override
    public void run() {

        try {
            projet_opti.Projet_Opti.startRecuit(size);

            String csvFile = name;
            FileWriter writer = new FileWriter(csvFile);
           

            CSVUtils.writeLine(writer, Arrays.asList("", "Recuit"), ';');
            for (int i = start; i <= size; i += pas) {

                String resRecuit = startRecuit(i);
               

                CSVUtils.writeLine(writer, Arrays.asList(String.valueOf(i), resRecuit), ';');

            }
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

}
