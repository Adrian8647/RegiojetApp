package main;

import logika.Hra;

import java.util.Scanner;

/**
 * Spousteci trida. Cte prikazy z klavesnice a vypisuje odpovedi hry.
 */
public class Start {

    public static void main(String[] args) {
        Hra hra = new Hra();
        Scanner scanner = new Scanner(System.in);

        // Vypise uvod a popis startovni lokality
        System.out.println(hra.vratUvod());

        // Opakuje se, dokud hra neskonci
        while (!hra.konecHry()) {
            System.out.print("\n> ");
            if (!scanner.hasNextLine()) {
                break;
            }
            String radek = scanner.nextLine();
            System.out.println(hra.zpracujPrikaz(radek));
        }
    }
}
