package zh.lingvo.persistence.mongodb;

import com.google.common.annotations.VisibleForTesting;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.MongoTimeoutException;

import java.io.Closeable;
import java.net.UnknownHostException;

class MongoWriter implements Closeable {
    private final String dbName;
    private final MongoClient client;

    @VisibleForTesting
    MongoWriter(String url, String dbName) throws UnknownHostException, MongoTimeoutException {
        this.dbName = dbName;
        this.client = new MongoClient(new MongoClientURI(url));
    }

    public void save(DBObject dbObject, String collectionName) throws MongoException {
        DB db = client.getDB(dbName);
        DBCollection collection = db.getCollection(collectionName);
        collection.insert(dbObject);
    }

    @Override
    public void close() {
        if (client != null)
            client.close();
    }
}
