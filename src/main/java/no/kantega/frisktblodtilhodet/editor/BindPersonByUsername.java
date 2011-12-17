package no.kantega.frisktblodtilhodet.editor;

import no.kantega.frisktblodtilhodet.model.Person;
import no.kantega.frisktblodtilhodet.service.PersonRepository;

import java.beans.PropertyEditorSupport;

public class BindPersonByUsername extends PropertyEditorSupport {

    private final PersonRepository personRepository;

    public BindPersonByUsername(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void setAsText(String text) {
        setValue(personRepository.findByUsername(text));
    }

    @Override
    public String getAsText() {
        Person value = (Person) getValue();
        return value.getUsername();
    }
}