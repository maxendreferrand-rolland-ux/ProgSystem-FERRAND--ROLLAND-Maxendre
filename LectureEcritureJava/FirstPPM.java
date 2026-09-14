import java.io.FileWriter;
import java.io.IOException;

public class FirstPPM {
    public static void main(String[] args) {
        try {
            FileWriter writer = new FileWriter("FirstPPM.ppm");

            writer.write("P3\n");
            writer.write("3 2\n");       // largeur hauteur
            writer.write("255\n");       // valeur max

            // Première ligne : rouge, vert, bleu
            writer.write("255 0 0 0 255 0 0 0 255\n");
            // Deuxième ligne : jaune, blanc, noir
            writer.write("255 255 0 255 255 255 0 0 0\n");

            writer.close();

            System.out.println("Image PPM créée avec succès !");
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture du fichier : " + e.getMessage());
        }
    }
}