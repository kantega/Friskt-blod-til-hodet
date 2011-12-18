package no.kantega.frisktblodtilhodet.model;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Gruppe extends AbstractPersistable<Long>{
    @NotNull
    private String name;

    @OneToMany
    private List<Person> persons;

    @ManyToOne
    private Gruppe foreldreGruppe;

    @OneToMany
    private List<Gruppe> undergrupper;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public Gruppe getForeldreGruppe() {
        return foreldreGruppe;
    }

    public void setForeldreGruppe(Gruppe foreldreGruppe) {
        this.foreldreGruppe = foreldreGruppe;
    }

    public List<Gruppe> getUndergrupper() {
        return undergrupper;
    }

    public void setUndergrupper(List<Gruppe> undergrupper) {
        this.undergrupper = undergrupper;
    }
}
