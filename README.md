Play the game on [CodinGame](https://www.codingame.com/training/hard/sokoban)

Maven is needed.

Install pddl4j (https://github.com/pellierd/pddl4j) in your local maven repo:
```
mvn install:install-file \
   -Dfile=<path-to-file-pddl4j-4.0.0.jar> \
   -DgroupId=fr.uga \
   -DartifactId=pddl4j \
   -Dversion=4.0.0 \
   -Dpackaging=jar \
   -DgeneratePom=true
 ```  
Work with maven: mvn clean, mvn compile, mvn test, mvn package

Run with: 
````
java --add-opens java.base/java.lang=ALL-UNNAMED \
      -server -Xms2048m -Xmx2048m \
      -cp "$(mvn dependency:build-classpath -Dmdep.outputFile=/dev/stdout -q):target/test-classes/:target/classes" \
      sokoban.SokobanMain
````
or (after mvn package)
```
java --add-opens java.base/java.lang=ALL-UNNAMED \
      -server -Xms2048m -Xmx2048m \
      -cp target/sokoban-1.0-SNAPSHOT-jar-with-dependencies.jar \
      sokoban.SokobanMain
```
Sorry ```mvn exec:java``` has still an open issue ("Directory src/main/resources/view/assets not found.")

See planning solutions at http://localhost:8888/test.html


## Fichiers ajoutés

| Fichier | Rôle |
| --- | --- |
| `src/main/java/sokoban/SokobanProblem.java` | Transforme automatiquement le niveau reçu en problème PDDL et l’enregistre dans un fichier temporaire. |
| `src/main/java/sokoban/SokobanPlanner.java` | Charge le domaine, lance HSP, récupère le plan et convertit ses actions en mouvements Sokoban. |
| `src/main/resources/pddl/domain.pddl` | Décrit les déplacements du joueur et les actions permettant de pousser une caisse. |

## Fichiers modifiés

### `src/main/java/sokoban/Agent.java`

Dans le projet principal, l’agent envoyait une solution écrite en dur, par exemple :

```java
String solution = "DUU";
```

L’agent réalise maintenant les opérations suivantes :

1. lecture de la largeur, de la hauteur et du nombre de caisses ;
2. lecture de la carte statique ;
3. lecture de la position du joueur et des caisses ;
4. génération automatique du problème PDDL ;
5. exécution du planificateur ;
6. envoi d’un seul mouvement par tour à l’interface.

Les mouvements sont écrits sur `stdout`, car le moteur Sokoban les interprète comme des commandes. Les informations de diagnostic sont écrites sur `stderr`.

### `pom.xml`

La dépendance suivante a été ajoutée :

```xml
<dependency>
    <groupId>org.javassist</groupId>
    <artifactId>javassist</artifactId>
    <version>3.30.2-GA</version>
</dependency>
```

Elle remplace l’ancienne version transitive de Javassist et évite l’erreur de chargement rencontrée avec les versions modernes de Java :

```text
ConstructorAccessorImpl cannot access MagicAccessorImpl
```

La dépendance PDDL4J était déjà déclarée dans le projet principal.

### `src/main/java/sokoban/SokobanMain.java`

Le niveau utilisé pour les essais locaux peut être changé avec :

```java
gameRunner.setTestCase("test1.json");
```

Le numéro peut être remplacé par celui de n’importe quel fichier `test*.json` du dossier `config`.

### `src/test/java/SokobanMain.java`

Le numéro du niveau de test a également été modifié localement. Cette modification n’est pas nécessaire au fonctionnement du planificateur.