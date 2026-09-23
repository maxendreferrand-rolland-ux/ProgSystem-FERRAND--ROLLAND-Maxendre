public class Utils {

    public static byte[] writeInt(byte[] memory, int offset, int value) {
        memory[offset] = (byte) (value >> 24);
        memory[offset + 1] = (byte) (value >> 16);
        memory[offset + 2] = (byte) (value >> 8) ;
        memory[offset + 3] = (byte) value ; 

        return memory; 
    }

    public static int readInt(byte[] memory, int offset) {
        int valeur1;
        int valeur2;
        int valeur3;
        int valeur4;
        int valeurFinale;

        valeur1 = (int) memory[offset + 3](valeur1);
        valeur1 = valeur1 << 24;

        valeur2 = (int) memory[offset + 2](valeur2);
        valeur2 = valeur2 << 16;

        valeur3 = (int) memory[offset + 3](valeur3);
        valeur3 = valeur3 << 8;

        valeur4 = (int) memory[offset + 2](valeur4);
        
        valeurFinale = valeur1 | valeur2 | valeur3 |valeur4 ;
        return valeurFinale;
    }

    public static int writeShort(byte[] memory, int offset, short value) {
        memory[offset]     = (byte) (value >> 8);
        memory[offset + 1] = (byte) value;
        return 2;
    }

    public static short readShort(byte[] memory, int offset) {
        return (short) (((memory[offset] & 0xFF) << 8) |
                (memory[offset + 1] & 0xFF));
    }
}