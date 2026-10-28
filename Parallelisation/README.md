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

- `CompteurSecurise` / `TestMutex` : même test, avec `compteur++` protégé par `synchronized(verrou)`. Résultat correct (50/50), mais beaucoup plus lent.