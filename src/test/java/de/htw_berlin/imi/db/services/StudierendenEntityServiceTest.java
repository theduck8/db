package de.htw_berlin.imi.db.services;

import de.htw_berlin.imi.db.entities.Studierenden;
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
        final Optional<Studierenden> studierendenOptional = studierendenEntityService.findById(24002);
        assertThat(studierendenOptional).isPresent();
        assertThat(studierendenOptional.get().getName()).isEqualTo("Xenokrates");
    }

    @Test
    void cannotfindById() {
        final Optional<Studierenden> studierendenOptional = studierendenEntityService.findById(0);
        assertThat(studierendenOptional).isNotPresent();
    }

}
