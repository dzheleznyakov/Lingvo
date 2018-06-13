package com.zheleznyakov.lingvo.persistence.xml.util

import com.zheleznyakov.lingvo.basic.persistence.Persistable

class TestClasses {
    static class BooleanEntity {
        @Persistable private boolean booleanValue = true
        private boolean booleanValue2 = false
    }
}
