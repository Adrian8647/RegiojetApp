package logika;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Testy ukladani a nahravani hry. Ukladaji se do docasne slozky,
 * aby neprepsaly skutecnou ulozenou hru hrace.
 */
public class UlozeniTest {

    @TempDir
    Path docasnaSlozka;

    private String puvodniSoubor;
    private Path soubor;

    @BeforeEach
    void nastavSoubor() {
        puvodniSoubor = PrikazUloz.getSoubor();
        soubor = docasnaSlozka.resolve("test_ulozeni.txt");
        PrikazUloz.setSoubor(soubor.toString());
    }

    @AfterEach
    void vratSoubor() {
        PrikazUloz.setSoubor(puvodniSoubor);
    }

    @Test
    void nahranimSeVratiUlozenyStav() {
        Hra hra = new Hra();
        hra.zpracujPrikaz("jdi lowcost");
        hra.zpracujPrikaz("dej rizek jarda");
        hra.zpracujPrikaz("jdi standard");
        hra.zpracujPrikaz("snez rizek");
        assertEquals("Hra byla ulozena.", hra.zpracujPrikaz("uloz"));

        // Nova hra (jako po vypnuti a zapnuti) a nahrani
        Hra novaHra = new Hra();
        assertTrue(novaHra.zpracujPrikaz("nahraj").startsWith("Hra byla nahrana."));

        HerniPlan plan = novaHra.getPlan();
        assertEquals("standard", plan.getAktualniProstor().getId());
        assertTrue(plan.getBatoh().obsahuje("nabijecka"));
        assertEquals(1, plan.getBatoh().pocet("rizek"));
        assertTrue(plan.jeStav("jarda_dal"));
        assertTrue(plan.jeStav("najedeny"));
        assertEquals(1, plan.getPocitadlo());
    }

    @Test
    void nahraniBezUlozeneHry() {
        Hra hra = new Hra();
        assertEquals("Zadna ulozena hra neexistuje.", hra.zpracujPrikaz("nahraj"));
    }

    @Test
    void poskozenySouborHruNezmeni() throws IOException {
        Hra hra = new Hra();
        hra.zpracujPrikaz("jdi lowcost");
        hra.zpracujPrikaz("uloz");

        // Pokazi cislo v souboru
        String obsah = Files.readString(soubor).replace("pocitadlo=0", "pocitadlo=hodne");
        Files.writeString(soubor, obsah);

        Hra novaHra = new Hra();
        assertEquals("Ulozena hra je poskozena, nahrat ji nejde.", novaHra.zpracujPrikaz("nahraj"));
        // Hrac zustal na zacatku, nic se nenahralo napul
        assertEquals("nastupiste", novaHra.getPlan().getAktualniProstor().getId());
    }

    @Test
    void souborSNeznamymPredmetemJePoskozeny() throws IOException {
        Hra hra = new Hra();
        hra.zpracujPrikaz("uloz");

        String obsah = Files.readString(soubor).replace("batoh=mobil:1", "batoh=raketa:1");
        Files.writeString(soubor, obsah);

        Hra novaHra = new Hra();
        assertEquals("Ulozena hra je poskozena, nahrat ji nejde.", novaHra.zpracujPrikaz("nahraj"));
        assertFalse(novaHra.getPlan().getBatoh().obsahuje("raketa"));
    }
}
