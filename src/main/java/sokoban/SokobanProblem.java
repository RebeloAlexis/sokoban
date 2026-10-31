package sokoban;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class SokobanProblem {

    private SokobanProblem() {
    }

    public static Path generate(
        int width,
        int height,
        String[] map,
        int playerX,
        int playerY,
        int[][] boxes
    ) throws IOException {

        StringBuilder pddl = new StringBuilder();

        // En-tête
        pddl.append("(define (problem sokoban-generated)\n");
        pddl.append("  (:domain sokoban)\n\n");

        // Objets : toutes les cases sauf les murs
        pddl.append("  (:objects\n");

        for (int y = 0; y < height; y++) {
            pddl.append("    ");

            for (int x = 0; x < width; x++) {
                if (map[y].charAt(x) != '#') {
                    pddl.append(cellName(x, y))
                        .append(" ");
                }
            }

            pddl.append("\n");
        }

        pddl.append("    - cell\n");
        pddl.append("  )\n\n");

        // État initial
        pddl.append("  (:init\n");

        // Position du joueur
        pddl.append("    (player-at ")
            .append(cellName(playerX, playerY))
            .append(")\n");

        // Positions des caisses
        for (int[] box : boxes) {
            pddl.append("    (box-at ")
                .append(cellName(box[0], box[1]))
                .append(")\n");
        }

        // Cases libres
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                boolean isWall =
                    map[y].charAt(x) == '#';

                boolean hasPlayer =
                    x == playerX && y == playerY;

                boolean boxPresent =
                    hasBox(x, y, boxes);

                if (!isWall && !hasPlayer && !boxPresent) {
                    pddl.append("    (free ")
                        .append(cellName(x, y))
                        .append(")\n");
                }
            }
        }

        // Relations directionnelles
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                if (!isAccessible(map, x, y, width, height)) {
                    continue;
                }

                String currentCell = cellName(x, y);

                if (isAccessible(map, x, y - 1, width, height)) {
                    appendRelation(pddl, "up", currentCell, cellName(x, y - 1));
                }

                if (isAccessible(map, x, y + 1,width,height)) {
                    appendRelation(pddl, "down", currentCell, cellName(x, y + 1));
                }

                if (isAccessible(map, x - 1, y, width, height)) {
                    appendRelation(pddl, "left", currentCell, cellName(x - 1, y));
                }

                if (isAccessible(map, x + 1, y, width, height)) {
                    appendRelation(pddl, "right", currentCell,cellName(x + 1, y));
                }
            }
        }

        // Fermeture de l'état initial
        pddl.append("  )\n\n");

        // Objectif : une caisse sur chaque destination
        pddl.append("  (:goal\n");
        pddl.append("    (and\n");

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (map[y].charAt(x) == '*') {
                    pddl.append("      (box-at ")
                        .append(cellName(x, y))
                        .append(")\n");
                }
            }
        }

        pddl.append("    )\n");
        pddl.append("  )\n");
        pddl.append(")\n");

        // Création du fichier PDDL temporaire
        Path problemFile = Files.createTempFile("sokoban-problem-", ".pddl");

        Files.write(problemFile, pddl.toString().getBytes(StandardCharsets.UTF_8));

        problemFile.toFile().deleteOnExit();

        return problemFile;
    }

    private static String cellName(int x, int y) {
        return "c-" + x + "-" + y;
    }

    private static boolean hasBox(int x, int y, int[][] boxes) {
        for (int[] box : boxes) {
            if (box[0] == x && box[1] == y) {
                return true;
            }
        }

        return false;
    }

    private static boolean isAccessible(String[] map, int x, int y, int width, int height) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return false;
        }

        return map[y].charAt(x) != '#';
    }

    private static void appendRelation(StringBuilder pddl, String direction, String from, String to) {
        pddl.append("    (")
            .append(direction)
            .append(" ")
            .append(from)
            .append(" ")
            .append(to)
            .append(")\n");
    }
}