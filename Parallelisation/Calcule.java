/* Calcule.java */
public class Calcule extends Thread{

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
    }

    public long getResultat(){
        return resultat; 
    }

    public static void main(String[] args) {
        (new Calcule(10,100,10)).start();
    }
}