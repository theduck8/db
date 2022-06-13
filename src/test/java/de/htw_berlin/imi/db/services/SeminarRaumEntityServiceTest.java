package de.htw_berlin.imi.db.services;

import de.htw_berlin.imi.db.entities.SeminarRaum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SeminarRaumEntityServiceTest {

    @Autowired
    SeminarRaumEntityService seminarRaumEntityService;

    @Test
    void findAll() {
        final List<SeminarRaum> all = seminarRaumEntityService.findAll();
        assertThat(all).isNotEmpty();
        assertThat(all).extracting(SeminarRaum::getId).isNotEmpty();
    }

    @Test
    void findById() {
        final Optional<SeminarRaum> seminarRaumOptional = seminarRaumEntityService.findById(12);
        assertThat(seminarRaumOptional).isPresent();
        assertThat(seminarRaumOptional.get().getName()).isEqualTo("Lecture Hall#1");
    }

    @Test
    void cannotfindById() {
        final Optional<SeminarRaum> seminarRaumOptional = seminarRaumEntityService.findById(0);
        assertThat(seminarRaumOptional).isNotPresent();
    }

}
