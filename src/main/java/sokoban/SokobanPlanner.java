package sokoban;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Locale;

import fr.uga.pddl4j.plan.Plan;
import fr.uga.pddl4j.planners.InvalidConfigurationException;
import fr.uga.pddl4j.planners.LogLevel;
import fr.uga.pddl4j.planners.statespace.HSP;
import fr.uga.pddl4j.problem.operator.Action;

public final class SokobanPlanner {

    private SokobanPlanner() {
    }

    public static String solve(
        Path problemFile
    ) throws IOException {

        Thread.currentThread().setContextClassLoader(
            Agent.class.getClassLoader()
        );

        Path domainFile = extractDomainFile();

        HSP planner = new HSP();

        planner.setDomain(domainFile.toString());
        planner.setProblem(problemFile.toString());
        planner.setTimeout(600);
        planner.setLogLevel(LogLevel.OFF);

        try {
            Plan plan = planner.solve();

            if (plan == null) {
                throw new IllegalStateException(
                    "Le planificateur n'a trouvé aucune solution."
                );
            }

            System.err.println("Plan PDDL trouvé :");
            System.err.println(plan);

            return convertPlanToMoves(plan);

        } catch (InvalidConfigurationException e) {
            throw new IllegalStateException(
                "Impossible de lancer le planificateur : "
                    + e.getMessage(),
                e
            );
        }
    }

    private static Path extractDomainFile()
        throws IOException {

        try (
            InputStream input = SokobanPlanner.class
                .getResourceAsStream("/pddl/domain.pddl")
        ) {
            if (input == null) {
                throw new IllegalStateException(
                    "Le fichier /pddl/domain.pddl "
                        + "est introuvable."
                );
            }

            Path domainFile = Files.createTempFile(
                "sokoban-domain-",
                ".pddl"
            );

            Files.copy(
                input,
                domainFile,
                StandardCopyOption.REPLACE_EXISTING
            );

            domainFile.toFile().deleteOnExit();

            return domainFile;
        }
    }

    private static String convertPlanToMoves(Plan plan) {
        StringBuilder solution = new StringBuilder();

        for (Action action : plan.actions()) {
            solution.append(convertAction(action));
        }

        return solution.toString();
    }

    private static char convertAction(Action action) {
        String actionName = action.getName()
            .toLowerCase(Locale.ROOT);

        int separatorIndex =
            actionName.lastIndexOf('-');

        if (separatorIndex == -1) {
            throw new IllegalStateException(
                "Action PDDL inconnue : " + actionName
            );
        }

        String direction = actionName.substring(
            separatorIndex + 1
        );

        switch (direction) {
            case "up":
                return 'U';

            case "down":
                return 'D';

            case "left":
                return 'L';

            case "right":
                return 'R';

            default:
                throw new IllegalStateException(
                    "Direction inconnue : " + direction
                );
        }
    }
}