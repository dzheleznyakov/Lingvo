package com.zheleznyakov.lingvo.persistence.xml

class PersistenceException extends Exception {
    PersistenceException(String message) {
        super(message)
    }

    PersistenceException(Throwable cause) {
        super(cause.message, cause)
    }
}
