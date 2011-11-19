package no.kantega.frisktblodtilhodet.model;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Person extends AbstractPersistable<Long> {

    private String name;

    @ManyToOne
    private Avdeling avdeling;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Avdeling getAvdeling() {
        return avdeling;
    }

    public void setAvdeling(Avdeling avdeling) {
        this.avdeling = avdeling;
    }
}
