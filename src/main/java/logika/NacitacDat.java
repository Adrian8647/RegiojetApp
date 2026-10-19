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
