package no.kantega.frisktblodtilhodet.jobs;

import no.kantega.frisktblodtilhodet.model.Person;
import no.kantega.frisktblodtilhodet.service.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import java.util.List;

public class KantegaUserImport {
    private LdapTemplate ldapTemplate;

    private PersonRepository personRepository;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Scheduled(fixedDelay = 10000)
    public void importPersons(){
        List search = ldapTemplate.search("", "cn=*", new AttributesMapper() {
            public Object mapFromAttributes(Attributes attributes) throws NamingException {
                Attribute mailnickname = attributes.get("mailnickname");
                if (mailnickname != null) {
                    String username = (String) mailnickname.get();
                    Person person = personRepository.findByUsername(username);
                    if(person == null){
                        person = new Person();
                        person.setUsername(username);
                        String name = (String) attributes.get("name").get();
                        person.setName(name);
                        logger.info("Imported person: {} ({})", name, username);
                        person = personRepository.save(person);
                    }
                    return person;
                }
                return null;
            }
        });
    }

    public void setLdapTemplate(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    @Autowired
    public void setPersonRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
}
