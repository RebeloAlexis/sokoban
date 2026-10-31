package sokoban;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class Agent {

    public static void main(String[] args)
        throws IOException {

        Scanner scanner = new Scanner(System.in);

        // Informations fixes du niveau
        int width = scanner.nextInt();
        int height = scanner.nextInt();
        int boxCount = scanner.nextInt();
        scanner.nextLine();

        // Carte statique
        String[] map = new String[height];

        for (int y = 0; y < height; y++) {
            map[y] = scanner.nextLine();
        }

        // État initial
        int playerX = scanner.nextInt();
        int playerY = scanner.nextInt();

        int[][] boxes = new int[boxCount][2];

        for (int i = 0; i < boxCount; i++) {
            boxes[i][0] = scanner.nextInt();
            boxes[i][1] = scanner.nextInt();
        }

        // Génération automatique du problème PDDL
        Path problemFile = SokobanProblem.generate(
            width,
            height,
            map,
            playerX,
            playerY,
            boxes
        );

        System.err.println(
            "Problème PDDL généré : "
                + problemFile.toAbsolutePath()
        );

        // Recherche de la solution
        String solution = SokobanPlanner.solve(
            problemFile
        );

        System.err.println(
            "Solution calculée : " + solution
        );

        for (int step = 0; step < solution.length(); step++) {
            if (step > 0
                && !readNextState(scanner, boxes)) {

                break;
            }

            // Seul le mouvement est envoyé sur stdout
            System.out.println(solution.charAt(step));
            System.out.flush();
        }

        scanner.close();
    }

    private static boolean readNextState(
        Scanner scanner,
        int[][] boxes
    ) {
        if (!scanner.hasNextInt()) {
            return false;
        }

        // Nouvelle position du joueur
        scanner.nextInt();
        scanner.nextInt();

        // Nouvelles positions des caisses
        for (int i = 0; i < boxes.length; i++) {
            boxes[i][0] = scanner.nextInt();
            boxes[i][1] = scanner.nextInt();
        }

        return true;
    }
}