package logika;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;


public class NacitacDat {

    private static final String SLOZKA = "/data/";

    // Nacte vsechny soubory a naplni herni plan
    public static void nacti(HerniPlan plan) {

        // Texty a nastaveni (klic=hodnota)
        for (String radek : nactiRadky("texty.txt")) {
            String[] casti = radek.split("=", 2);
            plan.getTexty().put(casti[0], casti[1].replace("\\n", "\n"));
        }

        // Lokality
        for (String radek : nactiRadky("lokality.txt")) {
            String[] c = radek.split(";");
            plan.getProstory().put(c[0], new Prostor(c[0], c[1], Stav.zTextu(c[2]), c[3], c[4]));
        }

        // Vychody - kazdy se prida do lokality, ze ktere vede
        for (String radek : nactiRadky("vychody.txt")) {
            String[] c = radek.split(";");
            plan.getProstory().get(c[0]).pridejVychod(new Vychod(c[1], c[2], c[3]));
        }

        // Predmety - prida a polozi tam, kde maji byt na zacatku
        for (String radek : nactiRadky("predmety.txt")) {
            String[] c = radek.split(";");
            plan.getPredmety().put(c[0], new Predmet(c[0], c[1], c[2]));
            String umisteni = c[3];
            int pocet = Integer.parseInt(c[4]);
            if (umisteni.equals("inventar")) {
                plan.getBatoh().pridej(c[0], pocet);
            } else if (!umisteni.equals("-")) {
                plan.getProstory().get(umisteni).getVeci().pridej(c[0], pocet);
            }
        }

        // Objekty v lokalitach
        for (String radek : nactiRadky("objekty.txt")) {
            String[] c = radek.split(";");
            plan.getObjekty().put(c[0], new Objekt(c[0], c[1], c[2], Stav.zTextu(c[3]), c[4], c[5]));
        }

        // Postavy
        for (String radek : nactiRadky("postavy.txt")) {
            String[] c = radek.split(";");
                    plan.getPostavy().put(c[0], new Postava(c[0], c[1], c[2], c[3], Stav.zTextu(c[4]), c[5], c[6]));

        }

        // Ukoly
        for (String radek : nactiRadky("ulohy.txt")) {
            String[] c = radek.split(";");
            plan.getUlohy().add(new Uloha(c[0], c[1], c[2], c[3], Stav.zTextu(c[4])));
        }

        // Pravidla pro pouzij a dej
        for (String radek : nactiRadky("pravidla.txt")) {
            String[] c = radek.split(";");
            plan.getPravidla().add(new Pravidlo(c[0], c[1], c[2], c[3], c[4], c[5], c[6]));
        }

        // Startovni lokalita a limit presunu
        plan.setAktualniProstor(plan.getProstory().get(plan.getText("start")));
        plan.setLimit(Integer.parseInt(plan.getText("limit")));
    }

    // Precte soubor a vrati jeho radky bez prazdnych radku a komentaru 
    private static List<String> nactiRadky(String soubor) {
        InputStream vstup = NacitacDat.class.getResourceAsStream(SLOZKA + soubor);

        if (vstup == null) {
            throw new RuntimeException("Nepodarilo sa načítať súbor: " + SLOZKA + soubor);
        }
        try (BufferedReader ctecka = new BufferedReader(
                new InputStreamReader(vstup, StandardCharsets.UTF_8))) {
            return ctecka.lines()
                    .filter(radek -> !radek.isBlank())
                    .filter(radek -> !radek.startsWith("#"))
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException("Chyba pri čítaní súboru: " + SLOZKA + soubor, e);
        }
    }
}