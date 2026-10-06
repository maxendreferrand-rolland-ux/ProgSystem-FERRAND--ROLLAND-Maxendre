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
}