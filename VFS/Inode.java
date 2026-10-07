public class Inode {

    private MemoryManager memoryManager;
    private int inodeNumber;

    public static final int INODE_SIZE = 128;
    public static final int DIRECT_POINTERS = 10;

    public Inode(
            MemoryManager memoryManager,
            int inodeNumber) {

        this.memoryManager = memoryManager;
        this.inodeNumber = inodeNumber;
    }

    public int getInodeOffset() {
        return MemoryManager.INODE_TABLE_OFFSET + inodeNumber * INODE_SIZE;
    }

    public int getFileType() {
        byte[] memory = memoryManager.getFilesystemMemory();
        return Utils.readInt(memory, getInodeOffset() + 4);
    }

    public int getFileSize() {
        byte[] memory = memoryManager.getFilesystemMemory();
        return Utils.readInt(memory, getInodeOffset() + 8);
    }

    public int[] getDirectPointers() {

        byte[] memory =
                memoryManager.getFilesystemMemory();

        int[] pointers =
                new int[DIRECT_POINTERS];

        int offset = getInodeOffset() + 28; //num(4)+type(4)+taille(4)+création(8)+modif(4)

        for (int indice = 0; indice < DIRECT_POINTERS; indice++) {
            pointers[indice] = Utils.readInt(memory, offset + indice * 4);
        }

        return pointers;
    }
    public void writeToMemory(
        int fileType,
        int fileSize,
        long creationTime,
        long modificationTime,
        int[] directPointers,
        int indirectPointer,
        short permissions,
        int linkCount) {
            
        byte[] memory =
        memoryManager.getFilesystemMemory();
        
        int curseur = getInodeOffset();
        // 1. Numéro d'inode
        curseur = curseur + Utils.writeInt(memory, curseur, inodeNumber);
        // 2. Type
        curseur = curseur + Utils.writeInt(memory, curseur, fileType);
        // 3. Taille
        curseur = curseur + Utils.writeInt(memory, curseur, fileSize);
        // 4. Création
        curseur = curseur + Utils.writeLong(memory, curseur, creationTime);
        // 5. Modification
        curseur = curseur + Utils.writeLong(memory, curseur, modificationTime);
        // 6. 10 pointeurs directs
        for (int indice = 0; indice < DIRECT_POINTERS; indice++) {
            curseur += Utils.writeInt(memory, curseur, directPointers[indice]);
        } 
        // 7. Pointeur indirect
        curseur = curseur + Utils.writeInt(memory, curseur, indirectPointer);
        // 8. Permissions
        curseur = curseur + Utils.writeShort(memory, curseur, permissions);
        // 9. Nombre de liens
        curseur = curseur + Utils.writeInt(memory, curseur, linkCount);
    }

}