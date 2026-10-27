package logika;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class HerniPlanTest {

    private HerniPlan plan;

    @BeforeEach
    void pripravPlan() {
        // Pred kazdym testem nova hra nactena ze slozky data
        plan = new Hra().getPlan();
    }

    @Test
    void podminkaPomlckaPlatiVzdy() {
        assertTrue(plan.platiPodminka("-"));
    }

    @Test
    void podminkaSeStavem() {
        assertFalse(plan.platiPodminka("najedeny"));
        assertTrue(plan.platiPodminka("!najedeny"));

        plan.pridejStav("najedeny");
        assertTrue(plan.platiPodminka("najedeny"));
        assertFalse(plan.platiPodminka("!najedeny"));
    }

    @Test
    void efektZiskejAStav() {
        plan.provedEfekty("ziskej:nabijecka,stav:jarda_dal", "rizek");

        assertTrue(plan.getBatoh().obsahuje("nabijecka"));
        assertTrue(plan.jeStav("jarda_dal"));
    }

    @Test
    void efektZmenVymeniPredmet() {
        plan.getBatoh().pridej("kelimek");
        plan.provedEfekty("zmen:kava", "kelimek");

        assertFalse(plan.getBatoh().obsahuje("kelimek"));
        assertTrue(plan.getBatoh().obsahuje("kava"));
    }

    @Test
    void najdePravidloJenProSpravnouKombinaci() {
        assertEquals("kavovar", plan.najdiPravidlo("pouzij", "kelimek", "kavovar").getCil());
        assertNull(plan.najdiPravidlo("pouzij", "rizek", "kavovar"));
        assertNull(plan.najdiPravidlo("dej", "kelimek", "kavovar"));
    }

    @Test
    void objektSeHledaJenVAktualniLokalite() {
        // Na nastupisti kavovar neni, ve voze Standard ano
        assertNull(plan.najdiObjektTady("kavovar"));

        plan.setAktualniProstor(plan.getProstory().get("standard"));
        assertEquals("kavovar", plan.najdiObjektTady("kavovar").getId());
    }

    @Test
    void popisLokalityVypiseSeznamyOddeleneCarkou() {
        plan.setAktualniProstor(plan.getProstory().get("standard"));
        String popis = plan.popisAktualnihoProstoru();

        assertTrue(popis.contains("Objekty: kufr, zasuvka, kavovar, sedadlo"));
        assertTrue(popis.contains("Vychody: lowcost, wc, business"));
        // Ve voze Standard zadne predmety nelezi, radek se nevypise
        assertFalse(popis.contains("Predmety:"));
    }
}
