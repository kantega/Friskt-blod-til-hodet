package no.kantega.service;

import no.kantega.frisktblodtilhodet.model.Periode;
import no.kantega.frisktblodtilhodet.service.PeriodeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static junit.framework.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:testContext.xml"})
@Transactional
public class PeriodeServiceTest {

    @Autowired
    private PeriodeRepository periodeRepository;
    private Date stopdato;

    @Before
    public void populate(){
        Periode p1 = new Periode();
        p1.setStartdato(new Date(2010, 10, 10));
        p1.setStopdato(new Date(2010, 10, 11));

        Periode p2 = new Periode();
        p2.setStartdato(new Date(2010, 10, 11));
        stopdato = new Date(2010, 10, 12);
        p2.setStopdato(stopdato);

        periodeRepository.saveAndFlush(p1);
        periodeRepository.saveAndFlush(p2);
    }

    @Test
    public void findLatestPeriodeShouldfindLatestPeriode(){
        Periode latestPeriode = periodeRepository.findLatestPeriode();
        assertEquals("Wrong periode found", stopdato, latestPeriode.getStopdato());
    }
}
