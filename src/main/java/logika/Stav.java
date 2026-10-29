package logika;

// Vsechny stavy hry (co uz hrac splnil)
public enum Stav {
    NAJEDENY,
    KUFR_ODKLIZEN,
    JARDA_DAL,
    ZASUVKA_VOLNA,
    MOBIL_NABITY,
    VLAK_V_PRAZE,
    WC_POUZITO,
    MLUVIL_STEVARDKA,
    MLUVIL_JARDA,
    MLUVIL_MARTA,
    MLUVIL_MANAZER;

    // Prevede text ze souboru (napr. "jarda_dal") na stav, "-" znamena zadny stav
    public static Stav zTextu(String text) {
        if (text.equals("-")) {
            return null;
        }
        return valueOf(text.toUpperCase());
    }
}
