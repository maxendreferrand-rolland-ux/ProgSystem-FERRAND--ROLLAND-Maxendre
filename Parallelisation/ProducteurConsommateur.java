/*ProducteurConsommateur.java*/
import java.util.concurrent.Semaphore;
import java.util.LinkedList;
import java.util.Queue;
 
public class ProducteurConsommateur {
 
    private static final int TAILLE_BUFFER = 5;
    private final Queue<Integer> buffer = new LinkedList<>();
 
    /* placesLibres + elementsDisponibles = TAILLE_BUFFER */
    private final Semaphore placesLibres = new Semaphore(TAILLE_BUFFER);
    private final Semaphore elementsDisponibles = new Semaphore(0);
    /* pas de Semaphore(1) pour l'exclusion mutuelle : on utilise synchronized(buffer) comme dans le cours */
 
    /* monitoring */
    private int nbProduits = 0;
    private int nbConsommes = 0;
 
    static class Producteur implements Runnable {
        private final int numero;
        private final int nbAProduire;
        private final ProducteurConsommateur pc;
 
        public Producteur(int numero, int nbAProduire, ProducteurConsommateur pc) {
            this.numero = numero;
            this.nbAProduire = nbAProduire;
            this.pc = pc;
        }
 
        private int genererProduit(int i) {
            return this.numero * 100 + i;
        }
 
        @Override
        public void run() {
            try {
                for (int i = 0; i < this.nbAProduire; i++) {
                    int produit = this.genererProduit(i);
 
                    /* 1. attendre une place libre */
                    this.pc.placesLibres.acquire();
                    /* 2. accès exclusif au buffer */
                    synchronized (this.pc.buffer) {
                        /* 3. ajout de l'élément */
                        this.pc.buffer.offer(produit);
                        this.pc.nbProduits++;
                        System.out.println("Producteur " + this.numero + " ajoute " + produit
                                + " | buffer : " + this.pc.buffer.size() + "/" + TAILLE_BUFFER);
                    }
                    /* 4. fin du bloc = libération de l'accès au buffer */
                    /* 5. signaler qu'un élément est disponible */
                    this.pc.elementsDisponibles.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
 
    static class Consommateur implements Runnable {
        private final int numero;
        private final int nbAConsommer;
        private final ProducteurConsommateur pc;
 
        public Consommateur(int numero, int nbAConsommer, ProducteurConsommateur pc) {
            this.numero = numero;
            this.nbAConsommer = nbAConsommer;
            this.pc = pc;
        }
 
        @Override
        public void run() {
            try {
                for (int i = 0; i < this.nbAConsommer; i++) {
                    /* 1. attendre qu'un élément soit disponible */
                    this.pc.elementsDisponibles.acquire();
                    /* 2. accès exclusif au buffer */
                    synchronized (this.pc.buffer) {
                        /* 3. retrait de l'élément */
                        Integer produit = this.pc.buffer.poll();
                        this.pc.nbConsommes++;
                        System.out.println("    Consommateur " + this.numero + " retire " + produit
                                + " | buffer : " + this.pc.buffer.size() + "/" + TAILLE_BUFFER);
                    }
                    /* 4. fin du bloc = libération de l'accès au buffer */
                    /* 5. signaler qu'une place est libre */
                    this.pc.placesLibres.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
 
    public int getNbProduits() {
        synchronized (this.buffer) {
            return this.nbProduits;
        }
    }
 
    public int getNbConsommes() {
        synchronized (this.buffer) {
            return this.nbConsommes;
        }
    }
 
    public int getTailleBuffer() {
        synchronized (this.buffer) {
            return this.buffer.size();
        }
    }
}