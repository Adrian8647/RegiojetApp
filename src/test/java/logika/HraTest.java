package logika;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Testy hry pres prikazy - stejne, jako by je psal hrac.
 */
public class HraTest {

    private Hra hra;
    private HerniPlan plan;

    @BeforeEach
    void novaHra() {
        hra = new Hra();
        plan = hra.getPlan();
    }

    // Provede vic prikazu za sebou a vrati odpoved na posledni
    private String hraj(String... prikazy) {
        String odpoved = "";
        for (String prikaz : prikazy) {
            odpoved = hra.zpracujPrikaz(prikaz);
        }
        return odpoved;
    }

    @Test
    void hraZacinaNaNastupisti() {
        assertEquals("nastupiste", plan.getAktualniProstor().getId());
        assertTrue(plan.getBatoh().obsahuje("mobil"));
        assertEquals(3, plan.getBatoh().pocet("rizek"));
        assertFalse(hra.konecHry());
    }

    @Test
    void neznamyPrikaz() {
        assertEquals("Tomu nerozumim. Napis napoveda.", hraj("letej"));
    }

    @Test
    void velkaPismenaAMezeryNevadi() {
        hraj("  JDI   LowCost ");
        assertEquals("lowcost", plan.getAktualniProstor().getId());
    }

    @Test
    void nelzeJitKamNevedeVychod() {
        assertEquals("Tam se odsud nedostanes.", hraj("jdi business"));
        assertEquals("nastupiste", plan.getAktualniProstor().getId());
    }

    @Test
    void kufrBlokujeCestuDoBusiness() {
        String odpoved = hraj("jdi lowcost", "jdi standard", "jdi business");

        assertEquals("Ulicku blokuje obrovsky kufr.", odpoved);
        assertEquals("standard", plan.getAktualniProstor().getId());
    }

    @Test
    void jardaDaNabijeckuZaRizekJenJednou() {
        hraj("jdi lowcost", "dej rizek jarda");
        assertTrue(plan.getBatoh().obsahuje("nabijecka"));
        assertEquals(2, plan.getBatoh().pocet("rizek"));

        // Druhy rizek uz Jarda nechce a hraci zustane
        hraj("dej rizek jarda");
        assertEquals(2, plan.getBatoh().pocet("rizek"));
    }
}
