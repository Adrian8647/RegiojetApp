package logika;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


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

        // Nejdriv se vsechno zkontroluje, aby se hra z poskozeneho souboru nenahrala jen napul
        if (!jeSouborPlatny(data)) {
            return "Ulozena hra je poskozena, nahrat ji nejde.";
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

    // Zkontroluje, ze v souboru je vsechno, co hra potrebuje, a ve spravnem tvaru
    private boolean jeSouborPlatny(Map<String, String> data) {
        // Hrac musi stat v existujici lokalite
        if (!plan.getProstory().containsKey(data.get("lokalita"))) {
            return false;
        }

        // Pocitadlo a limit musi byt cisla
        try {
            Integer.parseInt(data.get("pocitadlo"));
            Integer.parseInt(data.get("limit"));
        } catch (NumberFormatException e) {
            return false;
        }

        if (!data.containsKey("stavy") || !jeSeznamVeciPlatny(data.get("batoh"))) {
            return false;
        }

        // Kazda lokalita musi mit seznam veci a kazda postava existujici lokalitu
        boolean prostoryOk = plan.getProstory().keySet().stream()
                .allMatch(id -> jeSeznamVeciPlatny(data.get("prostor_" + id)));
        boolean postavyOk = plan.getPostavy().keySet().stream()
                .allMatch(id -> plan.getProstory().containsKey(data.get("postava_" + id)));
        return prostoryOk && postavyOk;
    }

    // Seznam veci musi vypadat jako "mobil:1,rizek:3" a obsahovat jen predmety, ktere ve hre jsou
    private boolean jeSeznamVeciPlatny(String text) {
        if (text == null) {
            return false;
        }
        if (text.isEmpty()) {
            return true;
        }
        return Arrays.stream(text.split(","))
                .allMatch(polozka -> polozka.matches("[a-z_]+:[0-9]{1,5}")
                        && plan.getPredmety().containsKey(polozka.split(":")[0]));
    }

    @Override
    public String getNazev() {
        return NAZEV;
    }
}
