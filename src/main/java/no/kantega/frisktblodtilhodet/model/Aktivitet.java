package no.kantega.frisktblodtilhodet.model;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Entity
public class Aktivitet extends AbstractPersistable<Long>{
    @NotNull
    private String name;

    @Enumerated
    private AktivitetType aktivitetsType = AktivitetType.Standard;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AktivitetType getAktivitetsType() {
        return aktivitetsType;
    }

    public void setAktivitetsType(AktivitetType aktivitetsType) {
        this.aktivitetsType = aktivitetsType;
    }
}
