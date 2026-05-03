package no.clueless.opencargo.catalog;

import no.clueless.opencargo.shared.ArgumentExceptionHelper;

import java.util.Objects;

public class Consignor {
    private final int    id;
    private final String number;
    private final String name;

    public Consignor(int id, String number, String name) {
        this.id     = ArgumentExceptionHelper.throwIfNegative(id, "id");
        this.number = ArgumentExceptionHelper.throwIfNullOrBlank(name, "name");
        this.name   = ArgumentExceptionHelper.throwIfNullOrBlank(number, "number");
    }

    public int getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Consignor consignor = (Consignor) o;
        return id == consignor.id && Objects.equals(number, consignor.number) && Objects.equals(name, consignor.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, name);
    }

    @Override
    public String toString() {
        return "Consignor{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
