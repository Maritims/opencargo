package no.clueless.opencargo.applicability;

import no.clueless.opencargo.util.ArgumentExceptionHelper;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public class Rejections<T> implements Iterable<Rejection<T>> {
    private final Set<Rejection<T>> rejections;

    public Rejections(Set<Rejection<T>> rejections) {
        this.rejections = ArgumentExceptionHelper.throwIfNullOrEmpty(rejections, "rejections");
    }

    @Override
    public Iterator<Rejection<T>> iterator() {
        return rejections.iterator();
    }

    public Stream<Rejection<T>> stream() {
        return rejections.stream();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Rejections<?> that = (Rejections<?>) o;
        return Objects.equals(rejections, that.rejections);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(rejections);
    }

    @Override
    public String toString() {
        return "Rejections{" +
                "rejections=" + rejections +
                '}';
    }
}
