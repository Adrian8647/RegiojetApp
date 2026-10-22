package logika;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Testy tridy SeznamVeci (inventar a veci v lokalitach).
 */
public class SeznamVeciTest {

    @Test
    void pridaniZvysiPocet() {
        SeznamVeci veci = new SeznamVeci();
        veci.pridej("rizek", 2);
        veci.pridej("rizek");

        assertEquals(3, veci.pocet("rizek"));
        assertTrue(veci.obsahuje("rizek"));
    }

    @Test
    void odebraniPoslednihoKusuPredmetSmaze() {
        SeznamVeci veci = new SeznamVeci();
        veci.pridej("kelimek");
        veci.odeber("kelimek");

        assertEquals(0, veci.pocet("kelimek"));
        assertFalse(veci.obsahuje("kelimek"));
        assertTrue(veci.getIdVeci().isEmpty());
    }
}
