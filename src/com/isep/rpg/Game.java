package com.isep.rpg;

import com.isep.rpg.enemy.*;
import com.isep.rpg.heros.*;
import com.isep.rpg.item.Weapon;
import com.isep.utils.InputParser;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Math.abs;

public class Game {

    public static final String RESET = "\u001B[0m";
    public static final String YELLOW = "\u001B[33m";
    public static final String RED = "\u001B[31m";
    public static final String BLUE = "\u001B[34m";
    public static final String GREEN = "\u001B[32m";
    public static final String PURPLE = "\u001B[35m";
    public static final String FOND_NOIR = "\u001B[40m";
    public static final String FOND_VERT = "\u001B[42m";
    public static final String FOND_ROUGE = "\u001B[41m";



    public Game() {

        System.out.println(FOND_NOIR);
        System.out.println(GREEN + "\t\tB I E N V E N U E   D A N S   L A   P A R T I E  !" + RESET + FOND_NOIR);
        System.out.println(FOND_NOIR + YELLOW + "\r\n\tLes règles du jeu sont simples. Tout d'abord, tu auras la possibilité de choisir le nombre de héros que tu voudras. ");
        System.out.println("\r\tEnsuite tu auras le choix de la classe de tes héros. Tu pourras choisir pour qu'ils soient des Warriors, des Hunters, des Mages ou des Healers. ");
        System.out.println("\r\tTu leurs donneras un nom, tu leur accorderas leurs points de vies, leurs armes, ainsi que les dégats que feront leurs armes. ");
        System.out.println("\r\n\tMaintenant, c'est l'heure de jouer !");
        System.out.println(FOND_NOIR);
        System.out.println(RESET);





       this.inputParser = inputParser;

        heros = new ArrayList<>();

        String classe = "";
        String n;
        int h;
        String name_arme;
        int damagePoints;

        Scanner scanner0 = new Scanner(System.in);
        System.out.print(RED + "\r\n\tCombien de héros veux-tu ? ");
        int nb_combatant = scanner0.nextInt();

        for (int k = 1; k <= nb_combatant; k++){
            System.out.println("\r\n\t=====================\r\n");

            while (!classe.equals("Warrior") & !classe.equals("Hunter") & !classe.equals("Healer") & !classe.equals("Mage")){
                Scanner scanner1 = new Scanner(System.in);
                System.out.print("\tDe quel type sera le héros ");
                System.out.print(k);
                System.out.print(" ? (Warrior, Hunter, Healer, Mage) ");
                classe = scanner1.nextLine();
            }

            Scanner scanner2 = new Scanner(System.in);
            System.out.print("\tQuel est le nom du ");
            System.out.print(classe);
            System.out.print(" ? ");
            n = scanner2.nextLine();

            Scanner scanner3 = new Scanner(System.in);
            System.out.print("\tCombien de points de vie aura le ");
            System.out.print(classe);
            System.out.print(" ");
            System.out.print(n);
            System.out.print(" ? ");
            h = scanner3.nextInt();

            Scanner scanner4 = new Scanner(System.in);
            System.out.print("\tQuel sera l'arme du ");
            System.out.print(classe);
            System.out.print(" ");
            System.out.print(n);
            System.out.print(" ? ");
            name_arme = scanner4.nextLine();

            Scanner scanner5 = new Scanner(System.in);
            System.out.print("\tCombien de dégats fera l'arme de ");
            System.out.print(classe);
            System.out.print(" ");
            System.out.print(n);
            System.out.print(" ? ");
            damagePoints = scanner5.nextInt();

            this.createHero(classe, n, abs(h), name_arme, abs(damagePoints));
            classe = "";
        }

        String Jouer;

        System.out.println(RESET);
        Scanner scannerJeu = new Scanner(System.in);
        System.out.println(FOND_NOIR);
        System.out.println(RESET);
        System.out.println("\r\n\t" + YELLOW + "Pour jouer, appuyez sur entrer ! \r\n");
        Jouer = scannerJeu.nextLine();
        System.out.println(FOND_NOIR);
        System.out.println(RESET);

        if (Jouer.equals("")) {
            System.out.println();
        }

        enemies = new ArrayList<>();

        for (int j = 1; j <= nb_combatant; j++){

            Random random = new Random();
            int nb;
            nb = random.nextInt(5)+1;

            this.createEnemy(nb);
        }
    }

    public void createHero(String classe, String n, int h, String name_arme, int damagePoints) {
        Hero hero = switch (classe) {
            case "Warrior" -> new Warrior(n, h);
            case "Hunter" -> new Hunter(n, h);
            case "Healer" -> new Healer(n, h);
            case "Mage" -> new Mage(n, h);
            default -> new Warrior(n, h);
        };
        if (hero instanceof Healer) {
            damagePoints = -damagePoints;
        }
        hero.setWeapon(new Weapon(name_arme, damagePoints));
        heros.add(hero);
    }

    public void createEnemy(Integer nb){

        Enemy enemy = switch (nb) {
            case 1 -> new Dragon("Dragon");
            case 2 -> new Gobelin("Gobelin");
            case 3 -> new Golem("Golem");
            case 4 -> new Lama("Lama");
            case 5 -> new Minotaure("Minotaure");
            default -> new Dragon("Dragon");
        };

        enemies.add(enemy);
    }
private Combatant goodOne;
private Combatant badOne;
    public void start() {

        int ixHero = 0;

        // Boucle de jeu
        while (true) {

            displayStatus(heros, enemies);

            goodOne = heros.get(ixHero);
            badOne = enemies.get(0);

            // Attaque de l'ennemi
            displayMessage("\r\nLe vilain " + badOne.getName()
                    + " attaque le gentil " + goodOne.getName() + "...");
            badOne.fight(goodOne);
            if (goodOne.getHealthPoint() <= 0) {
                displayMessage
                        ("\r\n" + FOND_ROUGE + "Oh nan ! Le pauvre " + goodOne.getName() + " a été vaincu..." + RESET);
                heros.remove(ixHero);
                ixHero--; // Correction: évite que le suivant perde son tour
            } else {

                this.playerTurn();

                if (badOne.getHealthPoint() <= 0) {
                    displayMessage("\r\n" + FOND_VERT + "Bien joué, " + goodOne.getName()
                            + " a vaincu " + badOne.getName() + " !!!" + RESET );
                    enemies.remove(0);
                }

            }

               // Tests de fin du jeu
            if (heros.size() == 0) {
                displayMessage(FOND_ROUGE + "Malheureusement, les héros ont perdu, c'est la fin du monde..." + RESET);
                break;
            }
            if (enemies.size() == 0) {
                displayMessage(FOND_VERT + "BRAVO, les héros ont gagné, le monde est sauvé !!!" + RESET);
                System.out.println(RESET);
                break;
            }

            // Au tour du héro suivant
            ixHero = (ixHero + 1) % heros.size();
        }
    }

    private void playerTurn() {
    /*
    * Demander valeur à l'utilisateur
        * if attaquer
            * mettre les 3 lignes du bas
        * if utiliser un consomable
            * afficher actions non implémentées, vous avez perdu votre tour
        * if se défendre
            * afficher actions non implémentées, vous avez perdu votre tour
     */

        String nb;
        Scanner scannerZ = new Scanner(System.in);
        System.out.print(BLUE + "\r\nQue voulez vous faire ? (Attaquer, Consommer, Se défendre) " + RESET);
        nb = scannerZ.nextLine();


        /*
        * if !healer
        *   cicle = badOne
        * else if healer
        *   cicle = chooeAGoodOne() -> retur le heros selectionner
        * goodOne.fight(cible)
         */




        ((Hero) goodOne).looseProtection();
        switch (nb) {
             case "Attaquer" : // Riposte du gentil, s'il n'est pas vaincu

                 Combatant cible;

                 if (!(goodOne instanceof Healer)) {
                     Scanner scannerx = new Scanner(System.in);
                     System.out.print(RED + "\r\nQuel méchant veux-tu attaquer ? " + RESET);
                     System.out.println(enemies);
                     System.out.println("choisir un méchant entre 1 et " + enemies.size());
                     int  choosegoodOne = scannerx.nextInt();
                     cible = enemies.get(choosegoodOne-1);
                 }

                 else {
                     Scanner scannerx = new Scanner(System.in);
                     System.out.print(PURPLE + "\r\nQuel héros veux-tu soigner ? " + RESET);
                     System.out.println(heros);
                     System.out.println("choisir un héro entre 1 et " + heros.size());
                     int  choosegoodOne = scannerx.nextInt();
                     cible = heros.get(choosegoodOne-1);
                 }

                 displayMessage("Le gentil " + goodOne.getName()
                         + " attaque le vilain " + cible.getName() + "...");
                 goodOne.fight(cible);


                 break;

             case "Consommer" :
                break;


             case "Se défendre" : // Divise les dégats subies par 2 pendant 1 tour
                 ((Hero) goodOne).addProtection();
                 break;

        }

    }

    private InputParser inputParser;

    private final List<Combatant> heros;
    private final List<Combatant> enemies;


    // Méthodes d'affichage
    // (STATIQUES pour pouvoir les appeler depuis n'importe où dans le programme)
    //
    // => pourraient éventuellement être déplacées dans le package
    //    "com.isep.utils", en s'inspirant de "InputParser" (méthodes de saisie)

    private boolean firstTour = true;
    public void displayStatus(List<Combatant> h, List<Combatant> e) {

        if (!this.firstTour) {
            System.out.println("\r\n===== TOUR SUIVANT =====\r\n");
        }
        firstTour = false;

        for (Combatant c : h) {
            System.out.print(c.getName() + " (" + c.getHealthPoint() + " ♥) ");
        }
        System.out.println("\r\n" + FOND_NOIR + RED + "VS" + RESET);
        for (Combatant c : e) {
            System.out.print(c.getName() + " (" + c.getHealthPoint() + " ♥) ");
        }
        System.out.println();

    }

    public static void displayMessage(String message) {
        System.out.println(message);
    }

}