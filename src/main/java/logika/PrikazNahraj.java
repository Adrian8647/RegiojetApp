package logika;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Prikaz "nahraj" - nacte ulozenou hru ze souboru a obnovi stav hry.
 */
public class PrikazNahraj implements IPrikaz {

    private static final String NAZEV = "nahraj";
    private HerniPlan plan;

    public PrikazNahraj(HerniPlan plan) {
        this.plan = plan;
    }

    @Override
    public String provedPrikaz(String... parametry) {
        // Precte soubor do mapy klic -> hodnota
        Map<String, String> data = new HashMap<>();
        try {
            for (String radek : Files.readAllLines(Path.of(PrikazUloz.getSoubor()))) {
                String[] casti = radek.split("=", 2);
                if (casti.length == 2) {
                    data.put(casti[0], casti[1]);
                }
            }
        } catch (IOException e) {
            return "Zadna ulozena hra neexistuje.";
        }

        // Hrac
        plan.setAktualniProstor(plan.getProstory().get(data.get("lokalita")));
        plan.getBatoh().zTextu(data.get("batoh"));

        // Cas
        plan.setPocitadlo(Integer.parseInt(data.get("pocitadlo")));
        plan.setLimit(Integer.parseInt(data.get("limit")));

        // Stavy hry
        plan.getStavy().clear();
        if (!data.get("stavy").isEmpty()) {
            for (String stav : data.get("stavy").split(",")) {
                plan.pridejStav(stav);
            }
        }

        // Predmety v lokalitach
        for (Prostor prostor : plan.getProstory().values()) {
            prostor.getVeci().zTextu(data.get("prostor_" + prostor.getId()));
        }

        // Postavy
        for (Postava postava : plan.getPostavy().values()) {
            postava.setLokalita(data.get("postava_" + postava.getId()));
        }

        return "Hra byla nahrana.\n\n" + plan.popisAktualnihoProstoru();
    }

    @Override
    public String getNazev() {
        return NAZEV;
    }
}
