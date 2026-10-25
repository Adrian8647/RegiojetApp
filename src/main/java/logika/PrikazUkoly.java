package logika;

import java.util.stream.Collectors;

/**
 * Prikaz "ukoly" - vypise hlavni ukol a ukoly od postav i se stavem.
 */
public class PrikazUkoly implements IPrikaz {

    private static final String NAZEV = "ukoly";
    private HerniPlan plan;

    public PrikazUkoly(HerniPlan plan) {
        this.plan = plan;
    }

    @Override
    public String provedPrikaz(String... parametry) {
        // Ukoly od postav se ukazou az po rozhovoru s nimi
        return "Ukoly:" + plan.getUlohy().stream()
                .filter(uloha -> uloha.jeZnama(plan))
                .map(uloha -> popisUlohy(uloha))
                .collect(Collectors.joining());
    }

    // Jeden radek seznamu, napr. "- Vyloz kufr (pani Marta): ... [aktivni]"
    private String popisUlohy(Uloha uloha) {
        String text = "\n- " + uloha.getNazev();
        if (!uloha.getZadavatel().equals("-")) {
            text += " (" + plan.getPostavy().get(uloha.getZadavatel()).getJmeno() + ")";
        }
        text += ": " + uloha.getZadani();
        if (uloha.jeSplnena(plan)) {
            text += " [splneny]";
        } else {
            text += " [aktivni]";
        }
        return text;
    }

    @Override
    public String getNazev() {
        return NAZEV;
    }
}
