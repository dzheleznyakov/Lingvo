package zh.lingvo.domain;

import com.google.common.base.Objects;
import zh.lingvo.utils.Id;

public class User implements DomainEntity {
    private Id<String> id;
    private String name;

    @Override
    public Id<String> getId() {
        return id;
    }

    public void setId(Id<String> id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equal(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
