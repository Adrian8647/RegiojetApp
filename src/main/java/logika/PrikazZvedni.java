package logika;


public class PrikazZvedni implements IPrikaz {

    private static final String NAZEV = "zvedni";
    private HerniPlan plan;

    public PrikazZvedni(HerniPlan plan) {
        this.plan = plan;
    }

    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            return plan.getText("co_presne") + " (napr. zvedni kufr)";
        }

        // Kufr je jen ve voze Standard
        if (!parametry[0].equals("kufr") || plan.najdiObjektTady("kufr") == null) {
            return "Nic takoveho tu nezvednes.";
        }
        if (plan.jeStav(Stav.KUFR_ODKLIZEN)) {
            return "Kufr uz lezi na polici.";
        }

        // Hladovy hrac kufr neuzvedne
        if (!plan.jeStav(Stav.NAJEDENY)) {
            return "Nevladzes, od rana jsi nejedl.";
        }

        plan.pridejStav(Stav.KUFR_ODKLIZEN);
        return "Vylozil jsi kufr na polici. Pani Marta dekuje. Cesta do Business je volna.";
    }

    @Override
    public String getNazev() {
        return NAZEV;
    }
}
