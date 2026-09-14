import java.io.FileWriter;
import java.io.IOException;

public class Image {
    private int width;
    private int height;
    private int[][][] pixels; // pixels[y][x][0=R,1=G,2=B]

    public Image(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[height][width][3];
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public void setPixel(int x, int y, int r, int g, int b) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            pixels[y][x][0] = r;
            pixels[y][x][1] = g;
            pixels[y][x][2] = b;
        }
    }

    public void save_txt(String filename) throws IOException {
        FileWriter writer = new FileWriter(filename);
        writer.write("P3\n");
        writer.write(width + " " + height + "\n");
        writer.write("255\n");

        for (int y = 0; y < height; y++) {
            StringBuilder ligne = new StringBuilder();
            for (int x = 0; x < width; x++) {
                ligne.append(pixels[y][x][0]).append(" ");
                ligne.append(pixels[y][x][1]).append(" ");
                ligne.append(pixels[y][x][2]).append(" ");
            }
            writer.write(ligne.toString().trim() + "\n");
        }

        writer.close();
    }
}