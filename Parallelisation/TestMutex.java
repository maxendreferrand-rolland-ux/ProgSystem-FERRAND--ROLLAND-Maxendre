/*TestMutex*/
public class TestMutex {

    private static final int NB_THREADS = 9;

    public static void main(String[] args) {

        Thread[] threads = new Thread[NB_THREADS];
        int[] resultats = new int[50];
        int compteurCorrect = 0;
        int minimum;
        int maximum;

        long debut = System.nanoTime();

        for (int i = 0; i < 50; i++) {
            CompteurSecurise.resetCompteur();
            for (int j = 0; j < NB_THREADS; j++) {
                threads[j] = new Thread(new CompteurSecurise.Incrementeur("inc", 1111111));
            }
            for (int j = 0; j < NB_THREADS; j++) {
                threads[j].start();
            }
            for (int j = 0; j < NB_THREADS; j++) {
                try {
                    threads[j].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            resultats[i] = CompteurSecurise.getCompteur();
        }

        long duree = (System.nanoTime() - debut) / 1_000_000;

        minimum = resultats[0];
        maximum = resultats[0];
        for (int res : resultats) {
            if (res == NB_THREADS * 1111111) {
                compteurCorrect++;
            }
            if (res < minimum) {
                minimum = res;
            }
            if (res > maximum) {
                maximum = res;
            }
        }

        System.out.println("Nombre bonnes réponses : " + compteurCorrect);
        System.out.println("Pourcentage réussite : " + (float)(compteurCorrect)/50*100);
        System.out.println("Minimum obtenu : " + minimum);
        System.out.println("Maximum obtenu : " + maximum);
        System.out.println("Durée : " + duree + " ms");
    }
}