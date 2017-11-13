package com.zheleznyakov.lingvo.dictionary.persistence

class PersistenceHelper {
    static void removeFolder(String folderName) {
        File rootDir = new File(folderName)
        Queue<File> filesToDelete = new LinkedList<>([rootDir])
        while (!filesToDelete.isEmpty()) {
            File fileOrDirectory = filesToDelete.poll()
            if (!fileOrDirectory.exists())
                continue
            else if (fileOrDirectory.isFile() || fileOrDirectory.listFiles().length == 0)
                fileOrDirectory.delete()
            else {
                offerAll(filesToDelete, fileOrDirectory.listFiles())
                filesToDelete.offer(fileOrDirectory)
            }
        }
    }

    private static void offerAll(Queue<File> queue, File[] files) {
        for (File file : files)
            queue.offer(file)
    }
}
