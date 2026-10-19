package logika;

/**
 * Prikaz "konec" - ukonci hru.
 */
public class PrikazKonec implements IPrikaz {

    private static final String NAZEV = "konec";
    private HerniPlan plan;

    public PrikazKonec(HerniPlan plan) {
        this.plan = plan;
    }

    @Override
    public String provedPrikaz(String... parametry) {
        plan.setKonecHry(true);
        return "Hra ukoncena. Ahoj!";
    }

    @Override
    public String getNazev() {
        return NAZEV;
    }
}
