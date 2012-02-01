package no.kantega.frisktblodtilhodet.web;

import no.kantega.frisktblodtilhodet.model.*;
import no.kantega.frisktblodtilhodet.service.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:testContext.xml"})
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@Transactional
public class HighscoreServiceTest {
    @Autowired
    private PersonRepository personRepository;
    
    @Autowired
    private AktivitetRepository aktivitetRepository;

    @Autowired
    private GruppeRepository gruppeRepository;
    
    @Autowired
    private UtfortAktivitetRepository utfortAktivitetRepository;
    
    @Autowired
    private HighscoreService highscoreService;

    private Gruppe g1;
    private Gruppe g2;
    private Aktivitet aktivitet;

    @Before
    public void populate(){
        g1 = new Gruppe();
        g1.setName("Derp");
        g1 = gruppeRepository.save(g1);

        g2 = new Gruppe();
        g2.setName("Herp");
        g2 = gruppeRepository.save(g2);

        Person p1 = new Person();
        p1.setGruppe(g1);
        p1.setName("abs");
        p1.setUsername("user1");
        p1 = personRepository.save(p1);

        Person p2 = new Person();
        p2.setGruppe(g2);
        p2.setName("sba");
        p2.setUsername("user2");
        p2 = personRepository.save(p2);

        Person p3 = new Person();
        p3.setGruppe(g2);
        p3.setName("sfffba");
        p3.setUsername("user3");
        p3 = personRepository.save(p3);


       g1.setPersons(Arrays.asList(p1));
       g2.setPersons(Arrays.asList(p2, p3));

        gruppeRepository.save(Arrays.asList(g1, g2));

        aktivitet = new Aktivitet();
        aktivitet.setAktivitetsType(AktivitetType.Standard);
        aktivitet.setName("A1");
        aktivitet = aktivitetRepository.saveAndFlush(aktivitet);

        Aktivitet aktivitet2 = new Aktivitet();
        aktivitet2.setAktivitetsType(AktivitetType.Standard);
        aktivitet2.setName("A2");
        aktivitet2 = aktivitetRepository.saveAndFlush(aktivitet2);

        UtfortAktivitet utfortAktivitet = new UtfortAktivitet(aktivitet, p1);

        UtfortAktivitet utfortAktivitet2 = new UtfortAktivitet(aktivitet, p1);

        UtfortAktivitet utfortAktivitet3 = new UtfortAktivitet(aktivitet, p1);

        UtfortAktivitet utfortAktivitet4 = new UtfortAktivitet(aktivitet, p1);

        UtfortAktivitet utfortAktivitet5 = new UtfortAktivitet(aktivitet2, p2);

        UtfortAktivitet utfortAktivitet6 = new UtfortAktivitet(aktivitet, p2);

        UtfortAktivitet utfortAktivitet7 = new UtfortAktivitet(aktivitet2, p3);

        UtfortAktivitet utfortAktivitet8 = new UtfortAktivitet(aktivitet2, p3);

        utfortAktivitetRepository.save(Arrays.asList(utfortAktivitet, utfortAktivitet2, utfortAktivitet3, utfortAktivitet4, utfortAktivitet5, utfortAktivitet6, utfortAktivitet7, utfortAktivitet8));
    }
    
    @Test
    public void averageScoreShouldBeFourAndTwo() throws Exception {
        Map<Gruppe,Double> grupperAndScoreForAlle = highscoreService.getGrupperAndScoreForAlle();
        assertEquals("Wrong score", 4d, grupperAndScoreForAlle.get(g1));
        assertEquals("Wrong score", 2d, grupperAndScoreForAlle.get(g2));
    }

    @Test
    public void scoreForAktivitetShouldBeFourAndPointFive() throws Exception {
        Map<Gruppe,Double> grupperAndScoreForAktivitet = highscoreService.getGrupperAndScoreForAktivitet(aktivitet);
        assertEquals("Wrong score", 4d, grupperAndScoreForAktivitet.get(g1));
        assertEquals("Wrong score", 0.5, grupperAndScoreForAktivitet.get(g2));
    }
}
