package de.htw_berlin.imi.db.services;

import de.htw_berlin.imi.db.entities.Studierenden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implements the DAO (data access object) pattern for SeminarRaum.
 * <p>
 * Classes annotated with @Service can be injected using @Autowired in other Spring components.
 * <p>
 * Classes annotated with @Slf4j have access to loggers.
 */
@Service
@Slf4j
public class StudierendenEntityService extends AbstractEntityService<Studierenden> {

    private static final String FIND_ALL_QUERY = """
                SELECT
                   id
                   ,name
                   ,vorname
                   ,geburtsdatum
                   ,geburtsort
                   ,anzahl_semester
                   ,studienbeginn
                FROM uni.v_studierenden
            """;

    private static final String FIND_BY_ID_QUERY = FIND_ALL_QUERY + " WHERE ID = ";

    @Override
    public List<Studierenden> findAll() {
        final List<Studierenden> result = new ArrayList<>();
        try {
            final ResultSet resultSet = query(FIND_ALL_QUERY);
            while (resultSet.next()) {
                result.add(createStudierenden(resultSet));
            }
        } catch (final Exception e) {
            log.error("Problem finding studierenden {}", e.getMessage());
        }
        return result;
    }

    @Override
    public Optional<Studierenden> findById(final long id) {
        try {
            final ResultSet resultSet = query(FIND_BY_ID_QUERY + id);
            if (resultSet.next()) {
                return Optional.of(createStudierenden(resultSet));
            }
        } catch (final Exception e) {
            log.error("Problem finding studierenden by id {}", e.getMessage());
        }
        return Optional.empty();
    }


    private Studierenden createStudierenden(final ResultSet resultSet) throws SQLException {
        final long id = resultSet.getInt("id");
        final Studierenden entity = new Studierenden(id);
        entity.setName(resultSet.getString("name"));
        entity.setVorname(resultSet.getString("vorname"));
        entity.setGeburtsdatum(resultSet.getString("geburtsdatum"));
        entity.setGeburtsort(resultSet.getString("geburtsort"));
        entity.setAnzahl_semester(resultSet.getInt("anzahl_semester"));
        entity.setStudienbeginn(resultSet.getString("studienbeginn"));
        return entity;
    }
}

