package logika;

/**
 * Prikaz "napoveda" - vypise seznam vsech prikazu.
 */
public class PrikazNapoveda implements IPrikaz {

    private static final String NAZEV = "napoveda";

    @Override
    public String provedPrikaz(String... parametry) {
        return "Prikazy (pis bez diakritiky):\n"
                + "  napoveda                   tento seznam\n"
                + "  konec                      ukonci hru";
    }

    @Override
    public String getNazev() {
        return NAZEV;
    }
}
