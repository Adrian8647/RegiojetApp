package logika;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Testy herniho planu: podminky.
 */
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
}
