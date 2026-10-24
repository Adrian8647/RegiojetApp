package logika;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
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
    private Map<String, Predmet> predmety = new LinkedHashMap<>();
    private Map<String, Objekt> objekty = new LinkedHashMap<>();
    private Map<String, Postava> postavy = new LinkedHashMap<>();
    private List<Pravidlo> pravidla = new ArrayList<>();
    private Map<String, String> texty = new HashMap<>();

    // ---- Stav hry, ktery se meni a uklada ----
    private Prostor aktualniProstor;
    private SeznamVeci batoh = new SeznamVeci();        // inventar hrace
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

    // Vrati "nabity" nebo "vybity" podle stavu mobilu
    public String stavMobilu() {
        if (jeStav("mobil_nabity")) {
            return "nabity";
        }
        return "vybity";
    }

    // Najde objekt podle nazvu, ale jen v aktualni lokalite
    public Objekt najdiObjektTady(String nazev) {
        return objekty.values().stream()
                .filter(objekt -> objekt.getNazev().equals(nazev))
                .filter(objekt -> objekt.getLokalita().equals(aktualniProstor.getId()))
                .findFirst()
                .orElse(null);
    }

    // Najde postavu podle id, ale jen v aktualni lokalite
    public Postava najdiPostavuTady(String id) {
        Postava postava = postavy.get(id);
        if (postava != null && postava.getLokalita().equals(aktualniProstor.getId())) {
            return postava;
        }
        return null;
    }

    // Najde pravidlo pro pouzij/dej podle predmetu a cile, kdyz neexistuje vrati null
    public Pravidlo najdiPravidlo(String typ, String predmet, String cil) {
        return pravidla.stream()
                .filter(pravidlo -> pravidlo.getTyp().equals(typ))
                .filter(pravidlo -> pravidlo.getPredmet().equals(predmet))
                .filter(pravidlo -> pravidlo.getCil().equals(cil))
                .findFirst()
                .orElse(null);
    }

    // Provede efekty z pravidla, napr. "ziskej:nabijecka,stav:jarda_dal"
    public void provedEfekty(String efekty, String pouzityPredmet) {
        if (efekty.equals("-")) {
            return;
        }
        for (String efekt : efekty.split(",")) {
            String[] casti = efekt.split(":");
            String druh = casti[0];
            String hodnota = casti[1];
            if (druh.equals("ziskej")) {
                // hrac dostane novy predmet
                batoh.pridej(hodnota);
            } else if (druh.equals("zmen")) {
                // pouzity predmet se zmeni na jiny (kelimek -> kava)
                batoh.odeber(pouzityPredmet);
                batoh.pridej(hodnota);
            } else if (druh.equals("stav")) {
                // zapise se novy stav hry
                pridejStav(hodnota);
            }
        }
    }

    // Sestavi cely popis aktualni lokality (nazev, popis, veci, objekty, postavy, vychody)
    public String popisAktualnihoProstoru() {
        Prostor p = aktualniProstor;
        String text = p.getNazev().toUpperCase() + "\n" + p.getPopis(this);

        // predmety, ktere tu lezi (u vic kusu i s poctem)
        String seznamVeci = p.getVeci().getIdVeci().stream()
                .map(id -> nazevSPoctem(p.getVeci(), id))
                .collect(Collectors.joining(", "));
        text += radekSeznamu("Predmety", seznamVeci);

        // objekty v teto lokalite
        String seznamObjektu = objekty.values().stream()
                .filter(objekt -> objekt.getLokalita().equals(p.getId()))
                .map(objekt -> objekt.getNazev())
                .collect(Collectors.joining(", "));
        text += radekSeznamu("Objekty", seznamObjektu);

        // postavy v teto lokalite
        String seznamPostav = postavy.values().stream()
                .filter(postava -> postava.getLokalita().equals(p.getId()))
                .map(postava -> postava.getId())
                .collect(Collectors.joining(", "));
        text += radekSeznamu("Postavy", seznamPostav);

        // kam se odsud da jit
        String seznamVychodu = p.getVychody().stream()
                .map(vychod -> vychod.getCil())
                .collect(Collectors.joining(", "));
        text += radekSeznamu("Vychody", seznamVychodu);
        return text;
    }

    // Vrati nazev predmetu, u vic kusu i s poctem, napr. "rizek (3x)"
    private String nazevSPoctem(SeznamVeci veci, String id) {
        String nazev = predmety.get(id).getNazev();
        if (veci.pocet(id) > 1) {
            nazev += " (" + veci.pocet(id) + "x)";
        }
        return nazev;
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

    public Map<String, Predmet> getPredmety() {
        return predmety;
    }

    public Map<String, Objekt> getObjekty() {
        return objekty;
    }

    public Map<String, Postava> getPostavy() {
        return postavy;
    }

    public List<Pravidlo> getPravidla() {
        return pravidla;
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

    public SeznamVeci getBatoh() {
        return batoh;
    }

    public boolean isKonecHry() {
        return konecHry;
    }

    public void setKonecHry(boolean konecHry) {
        this.konecHry = konecHry;
    }
}
