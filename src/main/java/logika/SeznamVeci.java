package logika;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Seznam veci pouziva inventar i lokality.
 */
public class SeznamVeci {

    // Klic je id predmetu, hodnota je pocet kusu
    private Map<String, Integer> veci = new LinkedHashMap<>();

    // Prida jeden kus predmetu
    public void pridej(String id) {
        pridej(id, 1);
    }

    // Prida vic kusu predmetu najednou
    public void pridej(String id, int kolik) {
        veci.put(id, pocet(id) + kolik);
    }

    // Odebere jeden kus predmetu, kdyz uz zadny nezbyde, smaze ho uplne
    public void odeber(String id) {
        int zbyva = pocet(id) - 1;
        if (zbyva <= 0) {
            veci.remove(id);
        } else {
            veci.put(id, zbyva);
        }
    }

    // Vrati kolik kusu predmetu tu je (0 kdyz zadny)
    public int pocet(String id) {
        return veci.getOrDefault(id, 0);
    }

    // Zjisti, jestli tu predmet je
    public boolean obsahuje(String id) {
        return pocet(id) > 0;
    }

    // Vrati id vsech predmetu v seznamu
    public Set<String> getIdVeci() {
        return veci.keySet();
    }

    // Prevede seznam na text pro ulozeni, napr. "mobil:1,rizek:3"
    public String doTextu() {
        return veci.keySet().stream()
                .map(id -> id + ":" + veci.get(id))
                .collect(Collectors.joining(","));
    }

    // Smaze vsechno a naplni seznam z ulozeneho textu (opak metody doTextu)
    public void zTextu(String text) {
        veci.clear();
        if (text == null || text.isEmpty()) {
            return;
        }
        for (String polozka : text.split(",")) {
            String[] casti = polozka.split(":");
            pridej(casti[0], Integer.parseInt(casti[1]));
        }
    }
}
