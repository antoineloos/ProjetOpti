/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet_opti;

import algorithmes.Genetique;
import algorithmes.Recuit;
import algorithmes.Tabou;
import java.io.FileWriter;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import utils.AlgoHelper;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sun.print.resources.serviceui;
import threading.GeneticRunnable;
import threading.RecuitRunnable;
import threading.TabouRunnable;
import utils.CSVUtils;

/**
 *
 * @author Epulapp
 */
public class Projet_Opti extends Application {

    public static void main(String[] args) throws IOException {
        if (args.length > 0 && args[0] == "-console") {
            System.out.println("tapez le numéro correspondant à l'algorithme souhaité : 1-Recuit , 2-Tabou , 3-AlgoGen");

            Scanner scanner = new Scanner(System.in);

            switch (Integer.parseInt(scanner.nextLine())) {
                case 1:
                    startRecuit(500);
                    break;
                case 2:
                    startTabou(100);
                    break;
                case 3:
                    startGenetique(50);

                    break;
                default:
                    System.out.println("votre demande n'est pas correcte");
            }
        } else {
            launch(args);
        }

    }

    public static String startGenetique(int size) {
        int GridSize = size;
        int nbLancement = 1;

        long[] lstElapsedTime = new long[nbLancement];
        long moyenne = 0;
        for (int i = 0; i < nbLancement; i++) {
            Genetique g = new Genetique(GridSize);
            long startTime = System.currentTimeMillis();
            Pair res = g.ComputeGenetique();
            long estimatedTime = System.currentTimeMillis() - startTime;
            lstElapsedTime[i] = estimatedTime;
            // System.out.println("fitness : " + res.getKey() + " time ellapsed : " + String.valueOf(estimatedTime / 1000.0) + " solution : " + Arrays.toString((int[]) res.getValue()));
            System.out.println("fitness : " + res.getKey() + " time ellapsed : " + String.valueOf(estimatedTime / 1000.0));

        }

        for (long l : lstElapsedTime) {
            moyenne += l;
        }
        BigDecimal bd = new BigDecimal((moyenne / (float) nbLancement / 1000.0));
        bd = bd.setScale(10, BigDecimal.ROUND_DOWN);
        System.out.println("moyenne de temps pour Algo gen : " + String.valueOf(bd));
        return String.valueOf(bd);
    }

    public static String startTabou(int size) {
        int GridSize = size;
        int nbLancement = 1;

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
        BigDecimal bd = new BigDecimal((moyenne / (float) nbLancement / 1000.0));
        bd = bd.setScale(10, BigDecimal.ROUND_DOWN);
        System.out.println("moyenne de temps pour Tabou : " + String.valueOf(bd));
        return String.valueOf(bd);
    }

    public static String startRecuit(int size) {
        int GridSize = size;
        int nbLancement = 1;

        long[] lstElapsedTime = new long[nbLancement];
        long moyenne = 0;
        for (int i = 0; i < nbLancement; i++) {

            int[] initialtab = AlgoHelper.CreateNewPlateauOpti(GridSize);
            Recuit r = new Recuit(initialtab);
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
        BigDecimal bd = new BigDecimal((moyenne / (float) nbLancement / 1000.0));
        bd = bd.setScale(10, BigDecimal.ROUND_DOWN);
        System.out.println("moyenne de temps pour recuit : " + String.valueOf(bd));
        return String.valueOf(bd);
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

    public void LaunchTestProtocole(int max, int pas) throws IOException {
        // pour 6 coeurs 
        //    ExecutorService service = Executors.newFixedThreadPool(6);

//        service.execute(new RecuitRunnable(pas, max / 2, pas, "recuit1.csv"));
//        service.execute(new RecuitRunnable(max / 2 + 1, max, pas, "recuit2.csv"));
//        service.execute(new TabouRunnable(pas, max / 2, pas, "tabou1.csv"));
//        service.execute(new TabouRunnable(max / 2 + 1, max, pas, "tabou2.csv"));
//        service.execute(new GeneticRunnable(pas, max / 2, pas, "Genetic1.csv"));
//        service.execute(new TabouRunnable( max / 2+1,max, pas, "Genetic2.csv"));
//pour 4 coeurs
        //            service.execute(new GeneticRunnable(150, 150 , 10, "Genetic1.csv"));
//            service.execute(new GeneticRunnable(150, 150 , 10, "Genetic2.csv"));
//            service.execute(new GeneticRunnable(150, 150 , 10, "Genetic3.csv"));
//            service.execute(new GeneticRunnable(150, 150 , 10, "Genetic4.csv"));
//              service.execute(new TabouRunnable(140, 140 , 10, "Tabou1.csv"));
//            service.execute(new TabouRunnable(140, 140 , 10, "Tabou2.csv"));
//            service.execute(new TabouRunnable(140, 140 , 10, "Tabou3.csv"));
//            service.execute(new TabouRunnable(140, 140 , 10, "Tabou4.csv"));
// service.execute(new RecuitRunnable(pas, max, pas, "Recuit1.csv"));
// service.execute(new RecuitRunnable(pas, max, pas, "Recuit2.csv"));
// service.execute(new RecuitRunnable(pas, max, pas, "Recuit3.csv"));
// service.execute(new RecuitRunnable(pas, max, pas, "Recuit4.csv"));
        ExecutorService service = Executors.newFixedThreadPool(3);
        try {

            service.execute(new RecuitRunnable(pas, max, pas, "Recuit.csv"));
            service.execute(new TabouRunnable(pas, max, pas, "Tabou.csv"));
            service.execute(new GeneticRunnable(pas, max, pas, "Genetic.csv"));
           

        } finally {
            service.shutdown();
        }

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Projet Opti");
        Label maxL = new Label("max de la taille de grille");
        TextField maxTB = new TextField();
        Label pasL = new Label("pas pour les itérations");
        TextField pasTB = new TextField();
        Button btn = new Button();
        Label erreur = new Label("");
        btn.setText("Lancer le protocole");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                try {
                    LaunchTestProtocole(Integer.parseInt(maxTB.getText()), Integer.parseInt(pasTB.getText()));

                } catch (IOException ex) {
                    Logger.getLogger(Projet_Opti.class.getName()).log(Level.SEVERE, null, ex);
                    erreur.setText(ex.toString());
                }
            }
        });

        FlowPane root = new FlowPane();
        root.getChildren().add(maxL);
        root.getChildren().add(maxTB);
        root.getChildren().add(pasL);
        root.getChildren().add(pasTB);
        root.getChildren().add(btn);
        root.getChildren().add(erreur);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
}
