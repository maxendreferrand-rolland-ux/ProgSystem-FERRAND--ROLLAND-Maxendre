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

        // Allouer blocksNeeded blocs.

        for (int indice = 0; indice < blocksNeeded; indice++) {
                blockPointers[indice] = memoryManager.allocateBlock();
        }

        byte[] memory =
                memoryManager.getFilesystemMemory();

        int bytesRemaining =
                data.length;

        int dataSrcOffset = 0;

        // Pour chaque bloc : calculer la quantité à copier,
        // récupérer son offset physique, copier les données.

        for (int indice = 0; indice < blocksNeeded; indice++) {
                int quantiteACopier = Math.min(bytesRemaining, MemoryManager.BLOCK_SIZE);
                int blockOffset = blockPointers[indice] * MemoryManager.BLOCK_SIZE;

                System.arraycopy(data, dataSrcOffset, memory, blockOffset, quantiteACopier);

                dataSrcOffset += quantiteACopier;
                bytesRemaining -= quantiteACopier;
        }

        // Mettre à jour l'inode.
        Inode inode = new Inode(memoryManager, inodeNum);
        long maintenant = System.currentTimeMillis();

        inode.writeToMemory(
                1, data.length, maintenant, maintenant,
                blockPointers, 0, (short) 0, 1);

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
        
        int blocksUsed =
                (fileSize + MemoryManager.BLOCK_SIZE - 1)
                / MemoryManager.BLOCK_SIZE;

        int bytesRemaining = fileSize;
        int destOffset = 0;

        // Parcourir les blocs utilisés.
        // Copier chaque fragment vers fileData.


        for (int indice = 0; indice < blocksUsed; indice++) {
            int quantiteACopier = Math.min(bytesRemaining, MemoryManager.BLOCK_SIZE);
            int blockOffset = blockPointers[indice] * MemoryManager.BLOCK_SIZE;

            System.arraycopy(memory, blockOffset, fileData, destOffset, quantiteACopier);

            destOffset += quantiteACopier;
            bytesRemaining -= quantiteACopier;
        }

        return fileData;
    }
    public boolean deleteFile(int inodeNum) {
        
        Inode inode =
                new Inode(memoryManager, inodeNum);

        int fileSize = inode.getFileSize();

        if (fileSize == 0) {
                return false; // rien à supprimer
        }

        int blocksUsed =
                (fileSize + MemoryManager.BLOCK_SIZE - 1)
                        / MemoryManager.BLOCK_SIZE;

        int[] blockPointers = inode.getDirectPointers();

        // Libérer les blocs qu'occupait le fichier dans le bitmap.
        for (int indice = 0; indice < blocksUsed; indice++) {
                memoryManager.setBlockUsed(blockPointers[indice], false);
        }

        // Réinitialiser l'inode : type 0 = libre, taille 0.
        inode.writeToMemory(
                0, 0, 0L, 0L,new int[Inode.DIRECT_POINTERS],
                0, (short) 0, 0);

        return true;
    }
}