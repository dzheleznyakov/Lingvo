package com.zheleznyakov.lingvo.dictionary.persistence

import com.zheleznyakov.lingvo.dictionary.Dictionary
import com.zheleznyakov.lingvo.language.Language
import com.zheleznyakov.lingvo.util.ZhConfigFactory

import static com.zheleznyakov.lingvo.language.Language.ENGLISH

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

    static void ensureDictionaryExistence(Language language) {
        String dirName = ZhConfigFactory.get().getString("languageDirRoot") + "/" + language.toLowerCase()
        File dir = new File(dirName)
        if (!dir.exists())
            dir.mkdirs()
        String fileName = dirName + "/" + language.toLowerCase() + PersistenceManager.DIC_EXTENSION
        File file = new File(fileName)
        if (!file.exists())
            new BasicPersistenceManager().persist(new Dictionary(ENGLISH))
    }

    static void mockPersistenceManager(PersistenceManager persistenceManager) {
        PersistenceUtil.setPersistenceManager(persistenceManager)
    }
}
