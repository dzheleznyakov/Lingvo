package zh.lingvo.persistence;

import zh.lingvo.domain.User;

public interface PersistenceManager {
    boolean persistUser(User user);
}
