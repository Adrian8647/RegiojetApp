package logika;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Herni plan drzi cely svet hry (lokality, predmety, postavy...)
 * a taky vsechno, co se behem hrani meni (kde je hrac, co ma u sebe, pocitadlo presunu...).
 */
public class HerniPlan {

    // ---- Data nactena ze souboru ve slozce data (klic je vzdy id) ----
    private Map<String, Prostor> prostory = new LinkedHashMap<>();
    private Map<String, String> texty = new HashMap<>();

    // ---- Stav hry, ktery se meni a uklada ----
    private Prostor aktualniProstor;
    private Set<String> stavy = new LinkedHashSet<>();  // splnene veci, napr. "najedeny", "kufr_odklizen"
    private boolean konecHry = false;

    // Vrati text z texty.txt podle klice
    public String getText(String klic) {
        return texty.get(klic);
    }

    // Zjisti, jestli ve hre plati dany stav
    public boolean jeStav(String stav) {
        return stavy.contains(stav);
    }

    // Zapise novy stav (napr. hrac se najedl)
    public void pridejStav(String stav) {
        stavy.add(stav);
    }

    // Vyhodnoti podminku: "-" plati vzdy, "!stav" plati kdyz stav NENI, jinak musi stav byt
    public boolean platiPodminka(String podminka) {
        if (podminka.equals("-")) {
            return true;
        }
        if (podminka.startsWith("!")) {
            return !jeStav(podminka.substring(1));
        }
        return jeStav(podminka);
    }

    // Sestavi cely popis aktualni lokality (nazev, popis, vychody)
    public String popisAktualnihoProstoru() {
        Prostor p = aktualniProstor;
        String text = p.getNazev().toUpperCase() + "\n" + p.getPopis(this);

        // kam se odsud da jit
        String seznamVychodu = p.getVychody().stream()
                .map(vychod -> vychod.getCil())
                .collect(Collectors.joining(", "));
        text += radekSeznamu("Vychody", seznamVychodu);
        return text;
    }

    // Vrati radek "Nadpis: a, b, c", kdyz je seznam prazdny vrati prazdny text
    private String radekSeznamu(String nadpis, String seznam) {
        if (seznam.isEmpty()) {
            return "";
        }
        return "\n" + nadpis + ": " + seznam;
    }

    // ---- Gettery a settery ----

    public Map<String, Prostor> getProstory() {
        return prostory;
    }

    public Map<String, String> getTexty() {
        return texty;
    }

    public Prostor getAktualniProstor() {
        return aktualniProstor;
    }

    public void setAktualniProstor(Prostor aktualniProstor) {
        this.aktualniProstor = aktualniProstor;
    }

    public boolean isKonecHry() {
        return konecHry;
    }

    public void setKonecHry(boolean konecHry) {
        this.konecHry = konecHry;
    }
}
