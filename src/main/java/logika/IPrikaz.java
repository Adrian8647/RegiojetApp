package logika;

/**
 * Rozhrani, ktere musi splnit kazdy prikaz ve hre.
 */
public interface IPrikaz {

    // Provede prikaz a vrati text, ktery se vypise hraci
    String provedPrikaz(String... parametry);

    // Vrati nazev prikazu, kterym ho hrac vola (napr. "jdi")
    String getNazev();
}
