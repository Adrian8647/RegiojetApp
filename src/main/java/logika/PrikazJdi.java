package logika;

/**
 * Prikaz "jdi" - presune hrace do sousedni lokality.
 * Hlida zavrene cesty.
 */
public class PrikazJdi implements IPrikaz {

    private static final String NAZEV = "jdi";
    private HerniPlan plan;

    public PrikazJdi(HerniPlan plan) {
        this.plan = plan;
    }

    @Override
    public String provedPrikaz(String... parametry) {
        // Hrac nenapsal, kam chce jit
        if (parametry.length == 0) {
            return plan.getText("co_presne") + " (napr. jdi standard)";
        }

        // Lokalita musi sousedit s tou aktualni
        Prostor odkud = plan.getAktualniProstor();
        Vychod vychod = odkud.najdiVychod(parametry[0]);
        if (vychod == null) {
            return plan.getText("nedostanes");
        }

        // Cesta muze byt zavrena (kufr, vlak jeste neni v Praze)
        if (!plan.platiPodminka(vychod.getPodminka())) {
            return vychod.getTextZavreno();
        }

        // Samotny presun
        plan.setAktualniProstor(plan.getProstory().get(vychod.getCil()));
        return plan.popisAktualnihoProstoru();
    }

    @Override
    public String getNazev() {
        return NAZEV;
    }
}
