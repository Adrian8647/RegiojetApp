package logika;


public class PrikazProzkoumej implements IPrikaz {

    private static final String NAZEV = "prozkoumej";
    private HerniPlan plan;

    public PrikazProzkoumej(HerniPlan plan) {
        this.plan = plan;
    }

    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            return plan.getText("co_presne") + " (napr. prozkoumej mobil)";
        }
        String nazev = parametry[0];

        // Predmet v inventari nebo v teto lokalite
        Predmet predmet = plan.getPredmety().get(nazev);
        if (predmet != null && (plan.getBatoh().obsahuje(nazev)
                || plan.getAktualniProstor().getVeci().obsahuje(nazev))) {
            // U mobilu se jeste napise, jestli je nabity
            if (nazev.equals("mobil")) {
                return predmet.getPopis() + " Je " + plan.stavMobilu() + ".";
            }
            return predmet.getPopis();
        }

        // Objekt v teto lokalite
        Objekt objekt = plan.najdiObjektTady(nazev);
        if (objekt != null) {
            return objekt.getPopis(plan);
        }

        // Postava v teto lokalite
        Postava postava = plan.najdiPostavuTady(nazev);
        if (postava != null) {
            return postava.getPopis();
        }

        return "Nic takoveho tu nevidis.";
    }

    @Override
    public String getNazev() {
        return NAZEV;
    }
}
