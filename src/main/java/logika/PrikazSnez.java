package logika;


public class PrikazSnez implements IPrikaz {

    private static final String NAZEV = "snez";
    private HerniPlan plan;

    public PrikazSnez(HerniPlan plan) {
        this.plan = plan;
    }

    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            return plan.getText("co_presne") + " (napr. snez rizek)";
        }

        // Jist se da jen rizek
        if (!parametry[0].equals("rizek")) {
            return "To se jist neda.";
        }
        if (!plan.getBatoh().obsahuje("rizek")) {
            return plan.getText("nemas").replace("{}", "rizek");
        }

        // Druhy rizek uz hrac nesni
        if (plan.jeStav("najedeny")) {
            return "Uz nevladzes, jsi plny.";
        }

        plan.getBatoh().odeber("rizek");
        plan.pridejStav("najedeny");
        return "Snedl jsi rizek. Mama vari nejlip.";
    }

    @Override
    public String getNazev() {
        return NAZEV;
    }
}
