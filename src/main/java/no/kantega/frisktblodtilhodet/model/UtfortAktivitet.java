package no.kantega.frisktblodtilhodet.model;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Calendar;

@Entity
public class UtfortAktivitet extends AbstractPersistable<Long>{

    @ManyToOne(optional = false)
    @NotNull
    private Person person;

    @ManyToOne(optional = false)
    @NotNull
    private Aktivitet aktivitet;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar time = Calendar.getInstance();

    private Boolean isWinner;

    private Integer mengde = 0;

    private Integer poeng = 1;

    public UtfortAktivitet() {}

    public UtfortAktivitet(Aktivitet aktivitet, Person person) {
        this.aktivitet = aktivitet;
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Aktivitet getAktivitet() {
        return aktivitet;
    }

    public void setAktivitet(Aktivitet aktivitet) {
        this.aktivitet = aktivitet;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public Integer getMengde() {
        return mengde;
    }

    public void setMengde(Integer mengde) {
        poeng += mengde;
        this.mengde = mengde;
    }

    public Boolean getWinner() {
        return isWinner;
    }

    public void setWinner(Boolean winner) {
        if(winner) poeng++;
        isWinner = winner;
    }

    public Integer getPoeng() {
        poeng = 1 + mengde;
        if(isWinner) poeng++;
        return poeng;
    }

    public void setPoeng(Integer poeng) {
        this.poeng = poeng;
    }
}
