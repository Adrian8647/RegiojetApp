package logika;

/**
 * Prikaz "schovej se" - hrac se schova na WC a ziska presuny navic.
 * Funguje jen na WC a jen jednou za hru.
 */
public class PrikazSchovej implements IPrikaz {

    private static final String NAZEV = "schovej";
    private HerniPlan plan;

    public PrikazSchovej(HerniPlan plan) {
        this.plan = plan;
    }

    @Override
    public String provedPrikaz(String... parametry) {
        // Schovat se jde jen na WC
        if (!plan.getAktualniProstor().getId().equals("wc")) {
            return "Tady se nemas kam schovat.";
        }

        // Podruhe uz to nefunguje
        if (plan.jeStav("wc_pouzito")) {
            return "Stevardka uz vi, kde te hledat.";
        }

        // Limit presunu se zvysi o bonus z texty.txt
        int bonus = Integer.parseInt(plan.getText("bonus_wc"));
        plan.setLimit(plan.getLimit() + bonus);
        plan.pridejStav("wc_pouzito");
        return "Zamkl ses na WC a pockal, az stevardka projde kolem. Ziskal jsi " + bonus + " presuny navic.\n"
                + "Do kontroly zbyva " + plan.getZbyvajiciPresuny() + " presunu.";
    }

    @Override
    public String getNazev() {
        return NAZEV;
    }
}
