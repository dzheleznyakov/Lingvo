package zh.lingvo.utils;

import com.google.common.base.Objects;

public class Id<E> {
    private final E value;

    public Id(E value) {
        this.value = value;
    }

    public E getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Id<?> id = (Id<?>) o;
        return Objects.equal(value, id.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
