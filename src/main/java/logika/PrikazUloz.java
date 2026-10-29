package logika;

import java.io.IOException;
import java.io.PrintWriter;


public class PrikazUloz implements IPrikaz {

    private static final String NAZEV = "uloz";
    private static String soubor = "ulozena_hra.txt";   // hra ma jednu ulozenou pozici
    private HerniPlan plan;

    public PrikazUloz(HerniPlan plan) {
        this.plan = plan;
    }

    @Override
    public String provedPrikaz(String... parametry) {
        try (PrintWriter zapis = new PrintWriter(soubor)) {
            // Hrac: kde je a co ma u sebe
            zapis.println("lokalita=" + plan.getAktualniProstor().getId());
            zapis.println("batoh=" + plan.getBatoh().doTextu());

            // Cas: pocitadlo presunu a aktualni limit
            zapis.println("pocitadlo=" + plan.getPocitadlo());
            zapis.println("limit=" + plan.getLimit());

            // Vsechny splnene stavy (najedeny, kufr_odklizen, jarda_dal, wc_pouzito...)
            zapis.println("stavy=" + String.join(",", plan.getStavy().stream().map(stav -> stav.name().toLowerCase()).toList()));

            // Predmety, ktere lezi v jednotlivych lokalitach
            for (Prostor prostor : plan.getProstory().values()) {
                zapis.println("prostor_" + prostor.getId() + "=" + prostor.getVeci().doTextu());
            }

            // Kde jsou postavy (stevardka se na konci presune)
            for (Postava postava : plan.getPostavy().values()) {
                zapis.println("postava_" + postava.getId() + "=" + postava.getLokalita());
            }
        } catch (IOException e) {
            return "Hru se nepodarilo ulozit.";
        }
        return "Hra byla ulozena.";
    }

    @Override
    public String getNazev() {
        return NAZEV;
    }

    public static String getSoubor() {
        return soubor;
    }

    // Testy si nastavi jiny soubor, aby neprepsaly ulozenou hru hrace
    static void setSoubor(String novySoubor) {
        soubor = novySoubor;
    }
}
