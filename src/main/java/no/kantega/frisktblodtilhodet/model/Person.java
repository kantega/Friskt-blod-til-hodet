package no.kantega.frisktblodtilhodet.model;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames="username"))
public class Person extends AbstractPersistable<Long> {

    @NotNull
    private String name;
    @NotNull
    private String username;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
