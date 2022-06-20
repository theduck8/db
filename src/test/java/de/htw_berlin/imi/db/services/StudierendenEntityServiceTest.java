package de.htw_berlin.imi.db.services;

import de.htw_berlin.imi.db.entities.Studierenden;
import de.htw_berlin.imi.db.web.StudierendenDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class StudierendenEntityServiceTest {

    @Autowired
    StudierendenEntityService studierendenEntityService;

    @Test
    void findAll() {
        final List<Studierenden> all = studierendenEntityService.findAll();
        assertThat(all).isNotEmpty();
        assertThat(all).extracting(Studierenden::getId).isNotEmpty();
    }

    @Test
    void findById() {
        final Optional<Studierenden> studierendenOptional = studierendenEntityService.findById(1);
        assertThat(studierendenOptional).isPresent();
        assertThat(studierendenOptional.get().getName()).isEqualTo("Xenokrates");
    }

    @Test
    void cannotfindById() {
        final Optional<Studierenden> studierendenOptional = studierendenEntityService.findById(0);
        assertThat(studierendenOptional).isNotPresent();
    }

    @Test
    void createNew() {
        final Studierenden studierenden = studierendenEntityService.create();
        assertThat(studierenden).isNotNull();
        assertThat(studierenden.getId()).isPositive();

        final Studierenden studierenden2 = studierendenEntityService.create();
        assertThat(studierenden.getId()).isLessThan(studierenden2.getId());
    }

    @Test
    void save() {
        final Studierenden studierenden = studierendenEntityService.create();
        studierenden.setId(10);
        studierenden.setMatr_nr(32717);
        studierenden.setName("Mustermann");
        studierenden.setVorname("Max");
        studierenden.setGeburtsdatum("2002-01-02");
        studierenden.setGeburtsort("Burg");
        studierenden.setAnzahl_semester(2);
        studierenden.setStudienbeginn("WS 2019");

        studierendenEntityService.save(studierenden);

        final Optional<Studierenden> studierendenOptional = studierendenEntityService.findById(studierenden.getId());
        assertThat(studierendenOptional).isPresent();
        assertThat(studierendenOptional.get().getName()).isEqualTo("Mustermann");
        assertThat(studierendenOptional.get().getVorname()).isEqualTo("Max");
        assertThat(studierendenOptional.get().getGeburtsdatum()).isEqualTo("2002-01-02");
        assertThat(studierendenOptional.get().getGeburtsort()).isEqualTo("Burg");
        assertThat(studierendenOptional.get().getAnzahl_semester()).isEqualTo(2);
        assertThat(studierendenOptional.get().getStudienbeginn()).isEqualTo("WS 2019");
    }

    @Test
    void createFrom() {
        final StudierendenDto studierenden = new StudierendenDto();
        studierenden.setId(10);
        studierenden.setMatr_nr(32717);
        studierenden.setName("Mustermann");
        studierenden.setVorname("Max");
        studierenden.setGeburtsdatum("2002-01-02");
        studierenden.setGeburtsort("Burg");
        studierenden.setAnzahl_semester(2);
        studierenden.setStudienbeginn("WS 2019");

        final Studierenden from = studierendenEntityService.createFrom(studierenden);

        final Optional<Studierenden> studierendenOptional = studierendenEntityService.findById(from.getId());
        assertThat(studierendenOptional).isPresent();
        assertThat(studierendenOptional.get().getMatr_nr()).isEqualTo(32717);
        assertThat(studierendenOptional.get().getName()).isEqualTo("Mustermann");
        assertThat(studierendenOptional.get().getVorname()).isEqualTo("Max");
        assertThat(studierendenOptional.get().getGeburtsdatum()).isEqualTo("2002-01-02");
        assertThat(studierendenOptional.get().getGeburtsort()).isEqualTo("Burg");
        assertThat(studierendenOptional.get().getAnzahl_semester()).isEqualTo(2);
        assertThat(studierendenOptional.get().getStudienbeginn()).isEqualTo("WS 2019");

    }

}
