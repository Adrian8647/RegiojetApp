package logika;

/**
 * Prikaz "vezmi" - presune predmet z lokality do inventare.
 */
public class PrikazVezmi implements IPrikaz {

    private static final String NAZEV = "vezmi";
    private HerniPlan plan;

    public PrikazVezmi(HerniPlan plan) {
        this.plan = plan;
    }

    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            return plan.getText("co_presne") + " (napr. vezmi kelimek)";
        }
        String nazev = parametry[0];
        SeznamVeci veciTady = plan.getAktualniProstor().getVeci();

        // Predmet tu lezi - vezme se jeden kus
        if (veciTady.obsahuje(nazev)) {
            veciTady.odeber(nazev);
            plan.getBatoh().pridej(nazev);
            return "Vzal jsi " + plan.getPredmety().get(nazev).getNazev() + ".";
        }
        return "Takovy predmet tu neni.";
    }

    @Override
    public String getNazev() {
        return NAZEV;
    }
}
