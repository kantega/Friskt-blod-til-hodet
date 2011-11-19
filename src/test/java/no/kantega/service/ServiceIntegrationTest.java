package no.kantega.service;


import no.kantega.frisktblodtilhodet.model.Aktivitet;
import no.kantega.frisktblodtilhodet.model.Avdeling;
import no.kantega.frisktblodtilhodet.model.Person;
import no.kantega.frisktblodtilhodet.model.UtfortAktivitet;
import no.kantega.frisktblodtilhodet.service.AktivitetRepository;
import no.kantega.frisktblodtilhodet.service.AvdelingRepository;
import no.kantega.frisktblodtilhodet.service.PersonRepository;
import no.kantega.frisktblodtilhodet.service.UtfortAktivitetRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext-jpa.xml"})
@Transactional
public class ServiceIntegrationTest {

    @Autowired
    private AvdelingRepository avdelingRepository;

    @Autowired
    private AktivitetRepository aktivitetRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private UtfortAktivitetRepository utfortAktivitetRepository;

    @Test
    public void testSaveAvdeling(){
        Avdeling avdeling = new Avdeling();
        avdeling.setName("Webgruppa");

        avdeling = avdelingRepository.saveAndFlush(avdeling);
        assertNotNull(avdeling);
        assertNotNull(avdeling.getId());
    }

    @Test
    public void testSaveAndGetAvdeling(){
        testSaveAvdeling();
        List<Avdeling> all = avdelingRepository.findAll();
        assertEquals(1, all.size());
    }
    
    @Test
    public void testSaveAktivitet(){
        Aktivitet aktivitet = new Aktivitet();
        aktivitet.setName("Hangup");

        aktivitet = aktivitetRepository.saveAndFlush(aktivitet);
        assertNotNull(aktivitet);
        assertNotNull(aktivitet.getId());
    }

    @Test
    public void testSaveAndGetAktivitet(){
        testSaveAktivitet();
        List<Aktivitet> all = aktivitetRepository.findAll();
        assertEquals(1, all.size());
    }
    
    @Test
    public void testSavePerson(){
        Person person = new Person();
        person.setName("Person");
        testSaveAvdeling();
        Avdeling avdeling = avdelingRepository.findAll().get(0);
        person.setAvdeling(avdeling);

        person = personRepository.saveAndFlush(person);
        assertNotNull(person);
        assertNotNull(person.getId());
        assertNotNull(person.getAvdeling());
    }

    @Test
    public void testGetPersonsByAvdeling(){
        testSavePerson();
        Avdeling avdeling = avdelingRepository.findAll().get(0);

        List<Person> persons = personRepository.findByAvdeling(avdeling);
        assertEquals(1, persons.size());
    }

    @Test
    public void testSaveAndGetPerson(){
        testSavePerson();
        List<Person> all = personRepository.findAll();
        assertEquals(1, all.size());
    }
    
    @Test(expected = ConstraintViolationException.class)
    public void testSaveUtfortAktivitetFailsWhenNoPersonAndAktivitetSpecified(){
        testSavePerson();
        testSaveAktivitet();

        UtfortAktivitet utfortAktivitet = new UtfortAktivitet();

        utfortAktivitet = utfortAktivitetRepository.saveAndFlush(utfortAktivitet);
    }

    @Test
    public void testSaveUtfortAktivitet(){
        testSavePerson();
        testSaveAktivitet();

        UtfortAktivitet utfortAktivitet = new UtfortAktivitet();
        List<Person> persons = personRepository.findAll();
        List<Aktivitet> aktivitets = aktivitetRepository.findAll();

        utfortAktivitet.setPerson(persons.get(0));
        utfortAktivitet.setAktivitet(aktivitets.get(0));


        utfortAktivitet = utfortAktivitetRepository.saveAndFlush(utfortAktivitet);
        assertNotNull(utfortAktivitet);
        assertNotNull(utfortAktivitet.getId());
        assertNotNull(utfortAktivitet.getPerson());
        assertNotNull(utfortAktivitet.getAktivitet());
        assertNotNull(utfortAktivitet.getTime());
    }

    @Test
    public void testSaveAndGetUtfortAktivitet(){
        testSaveUtfortAktivitet();
        testSaveUtfortAktivitet();

        List<UtfortAktivitet> all = utfortAktivitetRepository.findAll();
        assertEquals(2, all.size());
    }

    @Test
    public void testGetUtfortAktivitetByPerson(){
        testSaveUtfortAktivitet();

        List<Person> persons = personRepository.findAll();

        List<UtfortAktivitet> utfortAktivitets = utfortAktivitetRepository.findByPerson(persons.get(0));
        assertEquals(1, utfortAktivitets.size());
    }


}
