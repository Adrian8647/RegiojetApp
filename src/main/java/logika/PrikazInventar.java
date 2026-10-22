package logika;

/**
 * Prikaz "inventar" - vypise, co ma hrac u sebe (vcetne poctu rizku a stavu mobilu).
 */
public class PrikazInventar implements IPrikaz {

    private static final String NAZEV = "inventar";
    private HerniPlan plan;

    public PrikazInventar(HerniPlan plan) {
        this.plan = plan;
    }

    @Override
    public String provedPrikaz(String... parametry) {
        SeznamVeci batoh = plan.getBatoh();
        if (batoh.getIdVeci().isEmpty()) {
            return "Nemas u sebe nic.";
        }

        String text = "U sebe mas:";
        for (String id : batoh.getIdVeci()) {
            text += "\n- " + plan.getPredmety().get(id).getNazev();
            // U mobilu se ukaze, jestli je nabity
            if (id.equals("mobil")) {
                text += " (" + plan.stavMobilu() + ")";
            }
            // Kdyz je kusu vic, ukaze se pocet
            if (batoh.pocet(id) > 1) {
                text += " (" + batoh.pocet(id) + "x)";
            }
        }
        return text;
    }

    @Override
    public String getNazev() {
        return NAZEV;
    }
}
