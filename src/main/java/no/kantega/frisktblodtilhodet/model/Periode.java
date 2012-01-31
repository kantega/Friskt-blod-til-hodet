package no.kantega.frisktblodtilhodet.model;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Periode extends AbstractPersistable<Long> {

    @Temporal(TemporalType.DATE)
    @NotNull
    private Date startdato;

    @Temporal(TemporalType.DATE)
    @NotNull
    private Date stopdato;

    public Date getStopdato() {
        return stopdato;
    }

    public void setStopdato(Date stopdato) {
        this.stopdato = stopdato;
    }

    public Date getStartdato() {
        return startdato;
    }

    public void setStartdato(Date startdato) {
        this.startdato = startdato;
    }
}
