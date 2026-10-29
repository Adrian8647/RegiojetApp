package logika;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Testy hry pres prikazy 
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
    void hladovyHracKufrNezvedne() {
        assertEquals("Nevladzes, od rana jsi nejedl.", hraj("jdi lowcost", "jdi standard", "zvedni kufr"));

        hraj("snez rizek", "zvedni kufr");
        assertTrue(plan.jeStav(Stav.KUFR_ODKLIZEN));
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

    @Test
    void obsazenaZasuvkaMobilNenabije() {
        String odpoved = hraj("jdi lowcost", "dej rizek jarda", "jdi standard", "snez rizek",
                "zvedni kufr", "jdi business", "pouzij nabijecka zasuvka");

        assertEquals("Zasuvka je obsazena.", odpoved);
        assertFalse(plan.jeStav(Stav.MOBIL_NABITY));
    }

    @Test
    void poVycerpaniPresunuHracProhraje() {
        hraj("jdi lowcost");
        // 8 presunu tam a zpet mezi vozy, limit je 8
        for (int i = 0; i < 4; i++) {
            hraj("jdi standard", "jdi lowcost");
        }

        assertTrue(hra.konecHry());
        assertEquals(0, plan.getZbyvajiciPresuny());
    }

    @Test
    void schovaniNaWcPridaPresunyJenJednou() {
        hraj("jdi lowcost", "jdi standard", "jdi wc");
        int predSchovanim = plan.getZbyvajiciPresuny();

        hraj("schovej se");
        assertEquals(predSchovanim + 4, plan.getZbyvajiciPresuny());

        assertEquals("Stevardka uz vi, kde te hledat.", hraj("schovej se"));
        assertEquals(predSchovanim + 4, plan.getZbyvajiciPresuny());
    }

    @Test
    void celaHraAzDoVyhry() {
        String odpoved = hraj(
                "jdi lowcost", "dej rizek jarda",
                "jdi standard", "snez rizek", "zvedni kufr",
                "jdi business", "vezmi kelimek",
                "jdi standard", "pouzij kelimek kavovar",
                "jdi business", "dej kava manazer", "pouzij nabijecka zasuvka",
                "jdi standard", "sedni",
                "jdi lowcost", "jdi praha");

        assertTrue(hra.konecHry());
        assertTrue(odpoved.contains("VYHRA!"));
        assertEquals("praha", plan.getAktualniProstor().getId());
    }
}
