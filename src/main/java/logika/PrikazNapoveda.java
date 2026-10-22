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
                + "  prozkoumej <vec>           popis predmetu nebo objektu\n"
                + "  vezmi <predmet>            zvedne predmet\n"
                + "  inventar                   co mas u sebe\n"
                + "  napoveda                   tento seznam\n"
                + "  konec                      ukonci hru";
    }

    @Override
    public String getNazev() {
        return NAZEV;
    }
}
