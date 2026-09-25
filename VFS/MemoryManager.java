public class MemoryManager {

    public static final int BLOCK_SIZE = 512;
    public static final int TOTAL_MEMORY = 1024 * 1024;
    public static final int NUM_BLOCKS = TOTAL_MEMORY / BLOCK_SIZE;

    public static final int SUPERBLOCK_OFFSET = 0;
    public static final int BITMAP_OFFSET = BLOCK_SIZE;
    public static final int INODE_TABLE_OFFSET = 2 * BLOCK_SIZE;
    public static final int DATA_OFFSET = 129 * BLOCK_SIZE;

    public static final int INODE_SIZE = 128;
    public static final int INODE_TABLE_SIZE = DATA_OFFSET - INODE_TABLE_OFFSET;
    public static final int MAX_INODES = INODE_TABLE_SIZE / INODE_SIZE;

    private byte[] memory;

    public MemoryManager() {
        this.memory = new byte[TOTAL_MEMORY];
        initializeFilesystem();
    }

    private void initializeFilesystem() {
        writeSuperblock();

        for (int indice = 0; indice <= 128; indice++) {
            setBlockUsed(indice, true);
        }
    }

    private void writeSuperblock() {
        Utils.writeString(
                memory,
                SUPERBLOCK_OFFSET,
                "MYFS1.0",
                16);
        Utils.writeInt(
                memory,
                SUPERBLOCK_OFFSET + 16,
                BLOCK_SIZE);

        Utils.writeInt(
                memory,
                SUPERBLOCK_OFFSET + 20,
                TOTAL_MEMORY);
        Utils.writeInt(
                memory,
                SUPERBLOCK_OFFSET + 24,
                NUM_BLOCKS);

        Utils.writeInt(
                memory,
                SUPERBLOCK_OFFSET + 28,
                MAX_INODES);
    }
    public byte[] getFilesystemMemory() {

        return memory;
    }

    public boolean setBlockUsed(int blockNumber,
                                boolean used) {

        if (blockNumber < 0 ||
                blockNumber >= NUM_BLOCKS) {

            return false;
        }

        int indiceOctet = blockNumber / 8;
        int positionBit = blockNumber % 8;
        int offset = BITMAP_OFFSET + indiceOctet;

        if (used) {
            memory[offset] |= (byte) (0b00000001 << positionBit);
        } else {
            memory[offset] &= (byte) ~(0b00000001 << positionBit);
        }

        return true;
    }

    public int isBlockUsed(int blockNumber) {
        if (blockNumber < 0 || blockNumber >= NUM_BLOCKS) {
            return -1;
        }

        int indiceOctet = blockNumber / 8;
        int positionBit = blockNumber % 8;
        byte valeurOctet = memory[BITMAP_OFFSET + indiceOctet];

        return ((valeurOctet & 0xFF) >> positionBit) & 0b00000001;
    }

    public int allocateBlock() {

        for (int indice = 129; indice < NUM_BLOCKS; indice++) {
            if (isBlockUsed(indice) == 0) {
                setBlockUsed(indice, true);
                
                return indice;
            }
        }
        return -1;
    }
}