package com.zheleznyakov.lingvo.dictionary.persistence

import com.zheleznyakov.lingvo.basic.Word
import com.zheleznyakov.lingvo.dictionary.Dictionary
import Language
import com.zheleznyakov.lingvo.util.ZhConfigFactory

class PersistenceTestHelper {
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

    static void ensureDictionaryExistence(Language language, Word... words) {
        String dirName = ZhConfigFactory.get().getString("languageDirRoot") + "/" + language.toLowerCase()
        File dir = new File(dirName)
        if (!dir.exists())
            dir.mkdirs()
        String fileName = dirName + "/" + language.toLowerCase() + PersistenceManager.DIC_EXTENSION
        File file = new File(fileName)
        if (!file.exists())
            new BasicPersistenceManager().persist(createDictionary(language, words))
    }

    private static Dictionary createDictionary(Language language, Word[] words) {
        Dictionary dictionary = new Dictionary(language)
        words.each {
            assert it.language == language
            dictionary.add(it, it.mainForm)
        }
        return dictionary
    }

    static void mockPersistenceManager(PersistenceManager persistenceManager) {
        PersistenceUtil.setPersistenceManager(persistenceManager)
    }
}
