package de.htw_berlin.imi.db.services;

import de.htw_berlin.imi.db.entities.Studierenden;
import de.htw_berlin.imi.db.web.StudierendenDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
                   ,matr_nr
                   ,name
                   ,vorname
                   ,geburtsdatum
                   ,geburtsort
                   ,anzahl_semester
                   ,studienbeginn
                FROM uni.v_studierenden
            """;

    private static final String INSERT_BASE_QUERY = """
            INSERT INTO uni.Studierende (id, matr_nr, name, vorname, geburtsdatum, geburtsort, anzahl_semester, studienbeginn)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?);
            """;


    private static final String FIND_BY_ID_QUERY = FIND_ALL_QUERY + " WHERE ID = ";

    @Override
    public Studierenden create() {
        return null;
    }

    @Override
    public void save(final Studierenden e) {
        log.debug("insert: {}", INSERT_BASE_QUERY);
        try {
            final Connection connection = getConnection();
            connection.setAutoCommit(false);
            try (final PreparedStatement basePreparedStatement = getPreparedStatement(connection, INSERT_BASE_QUERY)) {

                createBaseClassPart(e, basePreparedStatement);
                connection.commit();
            } catch (final SQLException ex) {
                log.error("Error creating studierenden, aborting {}", ex.getMessage());
                connection.rollback();
                throw new RuntimeException(ex);
            }
        } catch (final SQLException ex) {
            log.error("Could not get connection.");
            throw new RuntimeException(ex);
        }
    }

    private void createBaseClassPart(final Studierenden e, final PreparedStatement basePreparedStatement) throws SQLException {
        // TODO set parameters
        basePreparedStatement.setLong(1, e.getId());
        basePreparedStatement.setLong(2, e.getMatr_nr());
        basePreparedStatement.setString(3, e.getName());
        basePreparedStatement.setString(4, e.getVorname());
        basePreparedStatement.setString(5, e.getGeburtsdatum());
        basePreparedStatement.setString(6, e.getGeburtsort());
        basePreparedStatement.setInt(7, e.getAnzahl_semester());
        basePreparedStatement.setString(8, e.getStudienbeginn());
        final int update = basePreparedStatement.executeUpdate();
        if (update != 1) {
            throw new SQLException("Could not create (studierenden) part");
        }
    }

    public Studierenden createFrom(final StudierendenDto template) {
        final Studierenden studierenden = create();

        studierenden.setMatr_nr(template.getMatr_nr());
        studierenden.setName(template.getName());
        studierenden.setVorname(template.getVorname());
        studierenden.setGeburtsdatum(template.getGeburtsdatum());
        studierenden.setGeburtsort(template.getGeburtsort());
        studierenden.setAnzahl_semester(template.getAnzahl_semester());
        studierenden.setStudienbeginn(template.getStudienbeginn());
        save(studierenden);
        return studierenden;
    }

    @Override
    public List<Studierenden> findAll() {
        final List<Studierenden> result = new ArrayList<>();
        try {
            final ResultSet resultSet = query(FIND_ALL_QUERY);
            while (resultSet.next()) {
                result.add(getStudierenden(resultSet));
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
                return Optional.of(getStudierenden(resultSet));
            }
        } catch (final Exception e) {
            log.error("Problem finding studierenden by id {}", e.getMessage());
        }
        return Optional.empty();
    }


    private Studierenden getStudierenden(final ResultSet resultSet) throws SQLException {
        final long id = resultSet.getLong("id");
        final Studierenden entity = new Studierenden(id);
        entity.setMatr_nr(resultSet.getLong("matr_nr"));
        entity.setName(resultSet.getString("name"));
        entity.setVorname(resultSet.getString("vorname"));
        entity.setGeburtsdatum(resultSet.getString("geburtsdatum"));
        entity.setGeburtsort(resultSet.getString("geburtsort"));
        entity.setAnzahl_semester(resultSet.getInt("anzahl_semester"));
        entity.setStudienbeginn(resultSet.getString("studienbeginn"));
        return entity;
    }
}

