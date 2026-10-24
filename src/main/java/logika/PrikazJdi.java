package logika;

/**
 * Prikaz "jdi" - presune hrace do sousedni lokality.
 * Hlida zavrene cesty, pocita presuny a resi vyhru a prohru.
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

        // Presun se pocita jen ve vlaku (ne z nastupiste) a jen dokud neni mobil nabity
        boolean pocitaSe = !odkud.getId().equals(plan.getText("start")) && !plan.jeStav("mobil_nabity");

        // Samotny presun
        plan.setAktualniProstor(plan.getProstory().get(vychod.getCil()));
        String vystup = plan.popisAktualnihoProstoru();

        // Hrac dorazil do Prahy = vyhra
        if (vychod.getCil().equals(plan.getText("cil"))) {
            plan.setKonecHry(true);
            return vystup + "\n\n" + plan.getText("vyhra");
        }

        // Pripocita presun a zkontroluje, jestli uz neprisla kontrola
        if (pocitaSe) {
            plan.pridejPresun();
            if (plan.getZbyvajiciPresuny() <= 0) {
                plan.setKonecHry(true);
                return vystup + "\n\n" + plan.getText("prehra");
            }
            vystup += "\nDo kontroly zbyva " + plan.getZbyvajiciPresuny() + " presunu.";
        }
        return vystup;
    }

    @Override
    public String getNazev() {
        return NAZEV;
    }
}
