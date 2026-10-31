# Parallélisation — TD Thread et synchronisation (R3.05)

Objectif : comprendre la concurrence, les races conditions et la synchronisation en Java.

- `CalculSequentiel` / `CalculParallele` + `Calcule` : même calcul, en séquentiel puis avec un thread par valeur (`start()` puis `join()`). Chaque thread ajoute son résultat à une somme partagée protégée par `synchronized`.
- Le parallèle n'est plus rapide que si les calculs sont longs : sinon, la création des threads coûte plus cher que le gain.
- `CompteurDangereux` / `TestCompteur` : 9 threads incrémentent le même compteur sans protection.

## Resultat de `TestCompteur` : 
    ### 1 Thread : 
            Nombre de threads : 1
            Nombre bonnes réponses : 50
            Pourcentage réussite : 100.0
            Minimum obtenu : 1111111
            Maximum obtenu : 1111111

    ### 2 Threads : 
            Nombre de threads : 2
            Nombre bonnes réponses : 49
            Pourcentage réussite : 98.0
            Minimum obtenu : 1153595
            Maximum obtenu : 2222222

    ### 5 Threads : 
            Nombre de threads : 5
            Nombre bonnes réponses : 48
            Pourcentage réussite : 96.0
            Minimum obtenu : 1373053
            Maximum obtenu : 5555555

    ### 10 Threads : 
            Nombre de threads : 10
            Nombre bonnes réponses : 49
            Pourcentage réussite : 98.0
            Minimum obtenu : 3814357
            Maximum obtenu : 11111110

- `ProducteurConsommateur` / `TestSemaphore` : buffer de 5 places, `placesLibres` (5) et `elementsDisponibles` (0) gèrent le plein/vide, `synchronized(buffer)` protège l'accès. Produits = consommés, à condition que les totaux soient égaux, sinon un thread reste bloqué.

## Réponses aux questions d’analyse du TD :

        1. Si on inverse placesLibres.acquire() et le mutex, un producteur peut prendre le verrou alors que le buffer est plein. Il se bloque en gardant le verrou, et aucun consommateur ne peut plus entrer pour libérer une place : c’est un interblocage (deadlock).

        2. Cohérence : oui, produits = consommés à la fin, et le buffer reste à 0 tant que les totaux sont égaux.

        3. Blocages : le système se bloque si le total à consommer dépasse le total produit (un consommateur attend), ou si le total produit dépasse le total consommé de plus de 5, la taille du buffer (un producteur attend).