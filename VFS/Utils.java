public class Utils {

    public static byte[] writeInt(byte[] memory, int offset, int value) {
        memory[offset] = (byte) (value >> 24);
        memory[offset + 1] = (byte) (value >> 16);
        memory[offset + 2] = (byte) (value >> 8) ;
        memory[offset + 3] = (byte) value ; 

        return 4; 
    }

    public static int readInt(byte[] memory, int offset) {
        int valeur1;
        int valeur2;
        int valeur3;
        int valeur4;

        valeur1 = (int) (memory[offset] & 0xFF)<< 24;
        valeur2 = (int) (memory[offset] & 0xFF)<< 24;
        valeur3 = (int) (memory[offset] & 0xFF)<< 24;
        valeur4 = (int) (memory[offset] & 0xFF)<< 24;
        
        return valeur1 | valeur2 | valeur3 | valeur4 ;
    }

    public static int writeShort(byte[] memory, int offset, short value) {

        memory[offset] = (byte) ((value >> 8)& 0xFF);
        memory[offset + 1] = (byte) (value & & 0xFF);

        return 2;
    }

    public static short readShort(byte[] memory, int offset) {

        int valeur1 = (memory[offset] & 0xFF) << 8;
        int valeur2 = memory[offset + 1] & 0xFF;

        return (short) (valeur1 | valeur2);
    }

    public static int writeLong(byte[] memory, int offset, long value) {

    memory[offset] = (byte) (value >> 56);
    memory[offset + 1] = (byte) (value >> 48);
    memory[offset + 2] = (byte) (value >> 40);
    memory[offset + 3] = (byte) (value >> 32);
    memory[offset + 4] = (byte) (value >> 24);
    memory[offset + 5] = (byte) (value >> 16);
    memory[offset + 6] = (byte) (value >> 8);
    memory[offset + 7] = (byte) value;

    return 8;

    }
    public static long readLong(byte[] memory, int offset) {

        long valeur1 = (memory[offset] & 0xFFL) << 56;
        long valeur2 = (memory[offset + 1] & 0xFFL) << 48;
        long valeur3 = (memory[offset + 2] & 0xFFL) << 40;
        long valeur4 = (memory[offset + 3] & 0xFFL) << 32;
        long valeur5 = (memory[offset + 4] & 0xFFL) << 24;
        long valeur6 = (memory[offset + 5] & 0xFFL) << 16;
        long valeur7 = (memory[offset + 6] & 0xFFL) << 8;
        long valeur8 = (memory[offset + 7] & 0xFFL);

        return valeur1 | valeur2 | valeur3 | valeur4 | valeur5 | valeur6 | valeur7 | valeur8;
    }

    public static int writeString(byte[] memory, int offset, String str, int maxLength) {
        byte[] valeurOctets = str.getBytes();
        int longueurACopier = Math.min(valeurOctets.length, maxLength);

        for (int i = 0; i < longueurACopier; i++) {
            memory[offset + i] = valeurOctets[i];
        }
        for (int i = longueurACopier; i < maxLength; i++) {
            memory[offset + i] = 0;
        }

        return maxLength;
    }

        public static String readString(byte[] memory, int offset, int maxLength) {
        int longueur = 0;
        while (longueur < maxLength && memory[offset + longueur] != 0) {
            longueur++;
        }
        return new String(memory, offset, longueur);
    }
}