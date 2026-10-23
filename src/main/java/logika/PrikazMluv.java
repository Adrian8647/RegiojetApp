package logika;

/**
 * Prikaz "mluv" - rozhovor s postavou. Postava rekne jednu repliku podle stavu hry.
 */
public class PrikazMluv implements IPrikaz {

    private static final String NAZEV = "mluv";
    private HerniPlan plan;

    public PrikazMluv(HerniPlan plan) {
        this.plan = plan;
    }

    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            return plan.getText("co_presne") + " (napr. mluv jarda)";
        }

        // Postava musi byt v teto lokalite
        Postava postava = plan.najdiPostavuTady(parametry[0]);
        if (postava == null) {
            return "Takova postava tu neni.";
        }

        return postava.getJmeno() + ": \"" + postava.getReplika(plan) + "\"";
    }

    @Override
    public String getNazev() {
        return NAZEV;
    }
}
