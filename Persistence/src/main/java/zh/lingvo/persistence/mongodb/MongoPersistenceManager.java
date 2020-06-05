package zh.lingvo.persistence.mongodb;

import com.google.common.annotations.VisibleForTesting;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.mongodb.MongoTimeoutException;
import zh.lingvo.domain.User;
import zh.lingvo.persistence.PersistenceManager;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.UnknownHostException;

public class MongoPersistenceManager implements PersistenceManager, Closeable {
    private static final String DB_URL = "mongodb://localhost:27017";

    private static final String USER_COLLECTION = "user";

    private final MongoWriter writer;
    private final DBObjectFactory dbObjectFactory;

    @VisibleForTesting
    MongoPersistenceManager(String dbName) throws UnknownHostException {
        writer = new MongoWriter(DB_URL, dbName);
        dbObjectFactory = new DBObjectFactory();
    }

    @Override
    public boolean persistUser(User user) throws MongoTimeoutException {
        try {
            DBObject dbObject = dbObjectFactory.convert(user);
            writer.save(dbObject, USER_COLLECTION);
            return true;
        } catch (IllegalAccessException | InvocationTargetException | MongoException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void close() throws IOException {
        try {
            if (writer != null)
                writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
