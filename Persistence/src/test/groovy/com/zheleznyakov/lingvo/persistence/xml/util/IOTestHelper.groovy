package com.zheleznyakov.lingvo.persistence.xml.util

class IOTestHelper {

    static void makeFolderEmpty(String name) {
        def file = new File(name)
        if (file.isFile())
            return
        file.listFiles().each {
            if (it.isDirectory())
                makeFolderEmpty(it.getAbsolutePath())
            it.delete()
        }
    }

}
