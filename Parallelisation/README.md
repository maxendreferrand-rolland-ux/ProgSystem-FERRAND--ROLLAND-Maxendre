# Parallélisation — TD Thread et synchronisation (R3.05)

Objectif : comprendre la concurrence, les races conditions et la synchronisation en Java.

- `CalculSequentiel` / `CalculParallele` + `Calcule` : même calcul, en séquentiel puis avec un thread par valeur (`start()` puis `join()`). Chaque thread ajoute son résultat à une somme partagée protégée par `synchronized`.
- Le parallèle n'est plus rapide que si les calculs sont longs : sinon, la création des threads coûte plus cher que le gain.
- `CompteurDangereux` / `TestCompteur` : 9 threads incrémentent le même compteur sans protection. `compteur++` n'est pas atomique (lecture, ajout, écriture), donc des incréments se perdent et le résultat est souvent inférieur à 9 999 999.
