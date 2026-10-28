/*TestCompteur*/
public class TestCompteur {
    public static void main(String[] args) {
         
        final int NB_THREADS = 9;
        Thread[] threads = new Thread[NB_THREADS];
        int[] resultats = new int[50];
        int compteurCorrect = 0;
        int minimum;
        int maximum ;

        for (int i = 0; i < 50; i++) {
            CompteurDangereux.resetCompteur();
            for (int j = 0; j < NB_THREADS; j++) {
                threads[j] = new Thread(new CompteurDangereux.Incrementeur("inc", 1111111));
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
            resultats[i] = CompteurDangereux.getCompteur();
        }

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
    }
}