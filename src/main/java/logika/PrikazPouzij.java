package logika;

/**
 * Prikaz "pouzij" - pouzije predmet na objekt (napr. kelimek na kavovar).
 * Co se stane, je napsane v souboru pravidla.txt.
 */
public class PrikazPouzij implements IPrikaz {

    private static final String NAZEV = "pouzij";
    private HerniPlan plan;

    public PrikazPouzij(HerniPlan plan) {
        this.plan = plan;
    }

    @Override
    public String provedPrikaz(String... parametry) {
        // Potrebujeme predmet i objekt
        if (parametry.length < 2) {
            return plan.getText("co_presne") + " (napr. pouzij kelimek kavovar)";
        }
        String predmet = parametry[0];
        String nazevObjektu = parametry[1];

        // Hrac musi predmet mit
        if (!plan.getBatoh().obsahuje(predmet)) {
            return plan.getText("nemas").replace("{}", predmet);
        }

        // Objekt musi byt v teto lokalite
        Objekt objekt = plan.najdiObjektTady(nazevObjektu);
        if (objekt == null) {
            return "Nic takoveho tu neni.";
        }

        // Najde pravidlo pro tuto kombinaci
        Pravidlo pravidlo = plan.najdiPravidlo("pouzij", predmet, objekt.getId());
        if (pravidlo == null) {
            return "To nejde.";
        }

        // Pravidlo muze mit podminku (napr. zasuvka musi byt volna)
        if (!plan.platiPodminka(pravidlo.getPodminka())) {
            return pravidlo.getTextNesplneno();
        }

        plan.provedEfekty(pravidlo.getEfekty(), predmet);
        return pravidlo.getText();
    }

    @Override
    public String getNazev() {
        return NAZEV;
    }
}
