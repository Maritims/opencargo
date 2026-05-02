package no.clueless.opencargo.applicability;

import no.clueless.opencargo.infrastructure.ArgumentExceptionHelper;

import java.util.Objects;

public final class Rejection<T> {
    private final T      subject;
    private final String reason;

    public Rejection(T subject, String reason) {
        this.subject = ArgumentExceptionHelper.throwIfNull(subject, "subject");
        this.reason  = ArgumentExceptionHelper.throwIfNullOrBlank(reason, "reason");
    }

    public T getSubject() {
        return subject;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Rejection<?> rejection = (Rejection<?>) o;
        return Objects.equals(subject, rejection.subject) && Objects.equals(reason, rejection.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, reason);
    }

    @Override
    public String toString() {
        return "Rejection{" +
                "subject=" + subject +
                ", reason='" + reason + '\'' +
                '}';
    }
}
