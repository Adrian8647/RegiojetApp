package logika;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Nacte vsechna herni data ze slozky "data" a postavi z nich svet hry.
 * Novou lokalitu nebo predmet tak pridame jen upravou textovych souboru.
 */
public class NacitacDat {

    private static final String SLOZKA = "data/";

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
            plan.getProstory().put(c[0], new Prostor(c[0], c[1], c[2], c[3], c[4]));
        }

        // Vychody - kazdy se prida do lokality, ze ktere vede
        for (String radek : nactiRadky("vychody.txt")) {
            String[] c = radek.split(";");
            plan.getProstory().get(c[0]).pridejVychod(new Vychod(c[1], c[2], c[3]));
        }

        // Predmety - a rovnou se polozi tam, kde maji byt na zacatku
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
            plan.getObjekty().put(c[0], new Objekt(c[0], c[1], c[2], c[3], c[4], c[5]));
        }

        // Postavy
        for (String radek : nactiRadky("postavy.txt")) {
            String[] c = radek.split(";");
            plan.getPostavy().put(c[0], new Postava(c[0], c[1], c[2], c[3], c[4], c[5], c[6]));
        }

        // Pravidla pro pouzij a dej
        for (String radek : nactiRadky("pravidla.txt")) {
            String[] c = radek.split(";");
            plan.getPravidla().add(new Pravidlo(c[0], c[1], c[2], c[3], c[4], c[5], c[6]));
        }

        // Startovni lokalita
        plan.setAktualniProstor(plan.getProstory().get(plan.getText("start")));
    }

    // Precte soubor a vrati jeho radky bez prazdnych radku a komentaru (#)
    private static List<String> nactiRadky(String soubor) {
        try {
            return Files.readAllLines(Path.of(SLOZKA + soubor)).stream()
                    .filter(radek -> !radek.isBlank())
                    .filter(radek -> !radek.startsWith("#"))
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException("Nepodarilo se nacist soubor " + SLOZKA + soubor);
        }
    }
}
