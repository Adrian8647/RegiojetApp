package logika;

/**
 * Prikaz "dej" - da predmet postave (napr. rizek Jardovi).
 * Co se stane, je napsane v souboru pravidla.txt.
 */
public class PrikazDej implements IPrikaz {

    private static final String NAZEV = "dej";
    private HerniPlan plan;

    public PrikazDej(HerniPlan plan) {
        this.plan = plan;
    }

    @Override
    public String provedPrikaz(String... parametry) {
        // Potrebujeme predmet i postavu
        if (parametry.length < 2) {
            return plan.getText("co_presne") + " (napr. dej rizek jarda)";
        }
        String predmet = parametry[0];

        // Hrac musi predmet mit
        if (!plan.getBatoh().obsahuje(predmet)) {
            return plan.getText("nemas").replace("{}", predmet);
        }

        // Postava musi byt v teto lokalite
        Postava postava = plan.najdiPostavuTady(parametry[1]);
        if (postava == null) {
            return "Takova postava tu neni.";
        }

        // Jinou kombinaci postava odmitne a predmet zustane hraci
        Pravidlo pravidlo = plan.najdiPravidlo("dej", predmet, postava.getId());
        if (pravidlo == null) {
            return postava.getJmeno() + " o to nestoji.";
        }

        // Napr. Jarda prijme jen jeden rizek
        if (!plan.platiPodminka(pravidlo.getPodminka())) {
            return pravidlo.getTextNesplneno();
        }

        // Predmet hrac odevzda a provedou se efekty
        plan.getBatoh().odeber(predmet);
        plan.provedEfekty(pravidlo.getEfekty(), predmet);
        return pravidlo.getText();
    }

    @Override
    public String getNazev() {
        return NAZEV;
    }
}
