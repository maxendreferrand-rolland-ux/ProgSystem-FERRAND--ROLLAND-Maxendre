/* Calcule.java */
public class Calcule extends Thread{
 
    private static long somme = 0;
    private static final Object mutex = new Object();
 
    private int valeur;
    private int multiplicateur;
    private int index;
    private long resultat;
 
 
    public Calcule (int valeur, int multiplicateur, int index) {
        this.valeur = valeur ;
        this.multiplicateur = multiplicateur ;
        this.index = index ;
    }
 
    @Override
    public void run() {
        resultat = 0;
        for (int j = 0; j < multiplicateur; j++) {
            resultat += valeur * valeur + valeur;
        }
        synchronized (mutex) {
            /* lock */
            somme += this.resultat;
            /* unlock */
        }
    }
 
    public long getResultat(){
        return resultat;
    }
 
    public static long getSomme(){
        synchronized (mutex) {
            return somme;
        }
    }
 
    public static void main(String[] args) {
        (new Calcule(10,100,10)).start();
    }
}