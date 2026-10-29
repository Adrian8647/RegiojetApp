package logika;


public class Postava {

    private String id;             // tohle pise hrac (napr. "jarda")
    private String jmeno;
    private String popis;
    private String lokalita;       // id lokality, kde postava je
    private Stav stavZmeny;        // po splneni tohoto stavu rika postava druhou repliku
    private String replikaPred;
    private String replikaPo;

    public Postava(String id, String jmeno, String popis, String lokalita,
                   Stav stavZmeny, String replikaPred, String replikaPo) {
        this.id = id;
        this.jmeno = jmeno;
        this.popis = popis;
        this.lokalita = lokalita;
        this.stavZmeny = stavZmeny;
        this.replikaPred = replikaPred;
        this.replikaPo = replikaPo;
    }

    // Vrati, co postava rekne, podle aktualniho stavu hry
    public String getReplika(HerniPlan plan) {
        if (plan.jeStav(stavZmeny)) {

            return replikaPo;
        }
        return replikaPred;
    }

    public String getId() {
        return id;
    }

    public String getJmeno() {
        return jmeno;
    }

    public String getPopis() {
        return popis;
    }

    public String getLokalita() {
        return lokalita;
    }

    // Presunuti postavy (stevardka prijde na konci do vozu Standard)
    public void setLokalita(String lokalita) {
        this.lokalita = lokalita;
    }
}
