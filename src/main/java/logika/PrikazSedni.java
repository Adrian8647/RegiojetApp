package logika;


public class PrikazSedni implements IPrikaz {

    private static final String NAZEV = "sedni";
    private HerniPlan plan;

    public PrikazSedni(HerniPlan plan) {
        this.plan = plan;
    }

    @Override
    public String provedPrikaz(String... parametry) {
        // Sedadlo hrace je jen ve voze Standard
        if (plan.najdiObjektTady("sedadlo") == null) {
            return "Tady nemas kde sedet. Tvoje misto je ve voze Standard.";
        }
        if (plan.jeStav(Stav.VLAK_V_PRAZE)) {
            return "Vlak uz stoji v Praze. Vystoupit muzes z vozu Low cost.";
        }

        // Bez nabiteho mobilu nema hrac listek
        if (!plan.jeStav(Stav.MOBIL_NABITY)) {
            return "Bez listku si radsi nesedej.";
        }

        // Prijde stevardka (presune se sem) a vlak dojede do Prahy
        plan.getPostavy().get("stevardka").setLokalita(plan.getAktualniProstor().getId());
        plan.pridejStav(Stav.VLAK_V_PRAZE);
        return "Sedl sis na sve misto 45. Prisla stevardka: \"Listky, prosim.\"\n"
                + "Ukazal jsi listek v nabitem mobilu. Stevardka se usmala a sla dal.\n"
                + "Cesta ubiha a za par hodin vlak zastavuje na hlavnim nadrazi v Praze.\n"
                + "Vystoupit muzes z vozu Low cost (jdi praha).";
    }

    @Override
    public String getNazev() {
        return NAZEV;
    }
}
