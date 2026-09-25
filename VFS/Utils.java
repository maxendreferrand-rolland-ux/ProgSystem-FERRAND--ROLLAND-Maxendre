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
        memory[offset]     = (byte) ((value >> 8)& 0xFF);
        memory[offset + 1] = (byte) (value & & 0xFF);
        return 2;
    }

    public static short readShort(byte[] memory, int offset) {
        int valeur1 = (memory[offset] & 0xFF) << 8;
        int valeur2 = memory[offset + 1] & 0xFF;
        return (short) (valeur1 | valeur2);
    }
}