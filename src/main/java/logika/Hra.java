package logika;

import java.util.Arrays;


public class Hra {

    private final HerniPlan plan;
    private SeznamPrikazu prikazy;

    public Hra() {
        // Vytvori svet z datovych souboru
        plan = new HerniPlan();
        try {
            NacitacDat.nacti(plan);
        }
        catch(RuntimeException e){
            System.err.println(e.getMessage());
            return;
        }
        // Vytvori vsechny prikazy (kazdy prikaz je vlastni trida)
        prikazy = new SeznamPrikazu();
        prikazy.vlozPrikaz(new PrikazJdi(plan));
        prikazy.vlozPrikaz(new PrikazRozhledni(plan));
        prikazy.vlozPrikaz(new PrikazProzkoumej(plan));
        prikazy.vlozPrikaz(new PrikazVezmi(plan));
        prikazy.vlozPrikaz(new PrikazPouzij(plan));
        prikazy.vlozPrikaz(new PrikazDej(plan));
        prikazy.vlozPrikaz(new PrikazMluv(plan));
        prikazy.vlozPrikaz(new PrikazSnez(plan));
        prikazy.vlozPrikaz(new PrikazZvedni(plan));
        prikazy.vlozPrikaz(new PrikazSedni(plan));
        prikazy.vlozPrikaz(new PrikazSchovej(plan));
        prikazy.vlozPrikaz(new PrikazInventar(plan));
        prikazy.vlozPrikaz(new PrikazUkoly(plan));
        prikazy.vlozPrikaz(new PrikazNapoveda());
        prikazy.vlozPrikaz(new PrikazUloz(plan));
        prikazy.vlozPrikaz(new PrikazNahraj(plan));
        prikazy.vlozPrikaz(new PrikazKonec(plan));
    }

    // Uvodni text, ktery se vypise pri spusteni hry
    public String vratUvod() {
        return plan.getText("uvod") + "\n\n" + plan.popisAktualnihoProstoru();
    }

    // Zpracuje jeden radek, ktery hrac napsal, a vrati odpoved hry
    public String zpracujPrikaz(String radek) {
        // Velikost pismen nehraje roli, slova se rozdeli podle mezer
        String[] slova = radek.trim().toLowerCase().split("\\s+");
        if (slova[0].isEmpty()) {
            return "";
        }

        // Prvni slovo je nazev prikazu
        IPrikaz prikaz = prikazy.vratPrikaz(slova[0]);
        if (prikaz == null) {
            return plan.getText("nerozumim");
        }

        // Zbytek slov jsou parametry prikazu
        String[] parametry = Arrays.copyOfRange(slova, 1, slova.length);
        return prikaz.provedPrikaz(parametry);
    }

    // Vrati true, kdyz hra skoncila
    public boolean konecHry() {
        return plan.isKonecHry();
    }

    public HerniPlan getPlan() {
        return plan;
    }
}
