package logika;


public class PrikazRozhledni implements IPrikaz {

    private static final String NAZEV = "rozhledni";
    private HerniPlan plan;

    public PrikazRozhledni(HerniPlan plan) {
        this.plan = plan;
    }

    @Override
    public String provedPrikaz(String... parametry) {
        // Slovo "se" za prikazem se ignoruje
        return plan.popisAktualnihoProstoru();
    }

    @Override
    public String getNazev() {
        return NAZEV;
    }
}
