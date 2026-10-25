package logika;

/**
 * Prikaz "napoveda" - vypise seznam vsech prikazu.
 */
public class PrikazNapoveda implements IPrikaz {

    private static final String NAZEV = "napoveda";

    @Override
    public String provedPrikaz(String... parametry) {
        return "Prikazy (pis bez diakritiky):\n"
                + "  jdi <lokalita>             presun (nastupiste, lowcost, standard, wc, business, praha)\n"
                + "  rozhledni se               znovu vypise popis lokality\n"
                + "  prozkoumej <vec>           popis predmetu, objektu nebo postavy\n"
                + "  vezmi <predmet>            zvedne predmet\n"
                + "  pouzij <predmet> <objekt>  pouzije predmet na objekt\n"
                + "  dej <predmet> <postava>    da predmet postave\n"
                + "  mluv <postava>             rozhovor s postavou\n"
                + "  snez rizek                 najis se\n"
                + "  zvedni kufr                zvednes kufr\n"
                + "  sedni                      sednes si na sve misto\n"
                + "  schovej se                 schovas se na WC\n"
                + "  inventar                   co mas u sebe\n"
                + "  ukoly                      prehled ukolu\n"
                + "  napoveda                   tento seznam\n"
                + "  konec                      ukonci hru";
    }

    @Override
    public String getNazev() {
        return NAZEV;
    }
}
