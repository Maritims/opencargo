package no.clueless.opencargo.applicability;

import no.clueless.opencargo.util.ArgumentExceptionHelper;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public class Rejections implements Iterable<Rejection> {
    private final Set<Rejection> rejections;

    public Rejections(Set<Rejection> rejections) {
        this.rejections = ArgumentExceptionHelper.throwIfNullOrEmpty(rejections, "rejections");
    }

    public Rejections(Rejection... rejections) {
        this(Set.of(rejections));
    }

    @Override
    public Iterator<Rejection> iterator() {
        return rejections.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Rejections that = (Rejections) o;
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
