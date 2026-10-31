/*TestSemaphore.java*/
public class TestSemaphore {
 
    public static void main(String[] args) {
        /* total produit = total consommé, sinon un thread reste bloqué pour toujours */
        System.out.println("=== Scénario équilibré : 2 producteurs, 3 consommateurs ===");
        lancerScenario(2, 15, 3, 10);
 
        System.out.println();
        System.out.println("=== Scénario déséquilibré : 5 producteurs, 2 consommateurs ===");
        lancerScenario(5, 4, 2, 10);
    }
 
    private static void lancerScenario(int nbProducteurs, int nbParProducteur,
                                       int nbConsommateurs, int nbParConsommateur) {
 
        ProducteurConsommateur pc = new ProducteurConsommateur();
        Thread[] producteurs = new Thread[nbProducteurs];
        Thread[] consommateurs = new Thread[nbConsommateurs];
 
        for (int i = 0; i < nbProducteurs; i++) {
            producteurs[i] = new Thread(new ProducteurConsommateur.Producteur(i, nbParProducteur, pc));
        }
        for (int i = 0; i < nbConsommateurs; i++) {
            consommateurs[i] = new Thread(new ProducteurConsommateur.Consommateur(i, nbParConsommateur, pc));
        }
 
        for (int i = 0; i < nbProducteurs; i++) {
            producteurs[i].start();
        }
        for (int i = 0; i < nbConsommateurs; i++) {
            consommateurs[i].start();
        }
 
        try {
            for (int i = 0; i < nbProducteurs; i++) {
                producteurs[i].join();
            }
            for (int i = 0; i < nbConsommateurs; i++) {
                consommateurs[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
 
        System.out.println("Produits : " + pc.getNbProduits());
        System.out.println("Consommés : " + pc.getNbConsommes());
        System.out.println("Restant dans le buffer : " + pc.getTailleBuffer());
        if (pc.getNbProduits() == pc.getNbConsommes()) {
            System.out.println("Cohérent : tout ce qui a été produit a été consommé");
        } else {
            System.out.println("Incohérent !");
        }
    }
}