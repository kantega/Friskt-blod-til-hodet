package no.kantega.frisktblodtilhodet.editor;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.beans.PropertyEditorSupport;

public class BindByIdEditor extends PropertyEditorSupport {
    private final JpaRepository<? extends AbstractPersistable<Long>, Long> repository;

    public BindByIdEditor(JpaRepository<? extends AbstractPersistable<Long>, Long> repository) {
        this.repository = repository;
    }


    @Override
    public void setAsText(String text) {
        Long id = Long.parseLong(text);
        setValue(repository.findOne(id));
    }

    @Override
    public String getAsText() {
        AbstractPersistable value = (AbstractPersistable) getValue();
        return value.getId().toString();
    }
}
