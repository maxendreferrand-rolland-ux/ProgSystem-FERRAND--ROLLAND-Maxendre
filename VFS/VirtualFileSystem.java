import java.util.*;

public class VirtualFileSystem {

    private MemoryManager memoryManager;

    public VirtualFileSystem() {

        this.memoryManager = 
                new MemoryManager();

    }
    private int allocateInode() {
        byte[] memory = 
                memoryManager.getFilesystemMemory();

        // Parcourir les inodes de 0 à MAX_INODES - 1.
        for (int indice = 0; indice < MemoryManager.MAX_INODES; indice++) {
            Inode inode = new Inode(memoryManager, indice);
        // Identifier le premier inode libre.
            if (inode.getFileType() == 0) {
        // Retourner son numéro.
            return indice;
            }
        }
        return -1; // si rien trouvé 
    }

    public boolean createFile(
            String directory,
            String filename) {
        int inodeNum = allocateInode();
        if (inodeNum == -1) {
        return false;
        }

        // Construire l'inode.
        // L'initialiser comme fichier vide.
        Inode inode = new Inode(memoryManager, inodeNum);
        
        long maintenant = System.currentTimeMillis();

        inode.writeToMemory(1, 0, maintenant, maintenant, 
                new int[Inode.DIRECT_POINTERS], 0, (short) 0, 1);
        return true;
    }

    public MemoryManager getMemoryManager() {
        return memoryManager;
    }

    public boolean writeFile(
            int inodeNum,
            byte[] data) {

        int blocksNeeded =
                (data.length
                + MemoryManager.BLOCK_SIZE - 1)
                / MemoryManager.BLOCK_SIZE;

        if (blocksNeeded > Inode.DIRECT_POINTERS) {
            return false;
        }

        int[] blockPointers =
                new int[Inode.DIRECT_POINTERS];
        // TODO:
        // Allouer blocksNeeded blocs.

        byte[] memory =
                memoryManager.getFilesystemMemory();

        int bytesRemaining =
                data.length;

        int dataSrcOffset = 0;

        // TODO:
        // Pour chaque bloc :
        // - calculer la quantité à copier ;
        // - récupérer le numéro du bloc ;
        // - calculer son offset physique ;
        // - copier les données.
        // TODO:
        // Mettre à jour l'inode.

        return true;
    }
    
    public byte[] readFile(int inodeNum) {

        Inode inode =
                new Inode(memoryManager, inodeNum);

        int fileSize =
                inode.getFileSize();

        if (fileSize == 0) {
            return new byte[0];
        }
        byte[] fileData =
                new byte[fileSize];

        byte[] memory =
                memoryManager.getFilesystemMemory();

        int[] blockPointers =
                inode.getDirectPointers();
        // TODO:
        // Parcourir les blocs utilisés.
        // Copier chaque fragment vers fileData.

        return fileData;
    }
}