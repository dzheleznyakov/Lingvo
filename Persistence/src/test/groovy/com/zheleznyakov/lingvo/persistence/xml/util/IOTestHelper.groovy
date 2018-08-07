package com.zheleznyakov.lingvo.persistence.xml.util

import com.zheleznyakov.lingvo.implementations.FakeEnglish
import com.zheleznyakov.lingvo.util.ZhConfigFactory

class IOTestHelper {
    private static final String PATH_TO_FILE_STORAGE = ZhConfigFactory.get().getString('persistence.xml.root')

    static void clean(name = PATH_TO_FILE_STORAGE) {
        def file = new File(name)
        if (file.isFile())
            return
        file.listFiles().each {
            if (it.isDirectory())
                clean(it.path)
            it.delete()
        }
    }

    static File getDictionaryFileToRead(dictionaryName) {
        getFile(dictionaryName)
    }

    public static File getFile(dictionaryName) {
        new File("${PATH_TO_FILE_STORAGE}/${FakeEnglish.FIXED_LANGUAGE.code()}/xml/${dictionaryName}.xml")
    }

    static File getDictionaryFileToWrite(dictionaryName) {
        File file = getFile(dictionaryName)
        if (!file.parentFile.exists())
            file.parentFile.mkdirs()
        return file
    }

}
