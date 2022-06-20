package de.htw_berlin.imi.db.services;

import de.htw_berlin.imi.db.entities.SeminarRaum;
import de.htw_berlin.imi.db.web.SeminarraumDto;
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
public class SeminarRaumEntityService extends AbstractEntityService<SeminarRaum> {

    private static final String FIND_ALL_QUERY = """
                SELECT
                   id
                   ,name
                   ,raumnummer
                   ,flaeche
                   ,hoehe
                   ,kapazitaet
                   ,stockwerk_id
                FROM uni.v_seminarraeume
            """;

    private static final String INSERT_BASE_QUERY = """
            INSERT INTO uni.Raeume (id, name, raumnummer, flaeche, raumhoehe)
                VALUES (?, ?, ?, ?, ?);
            """;

    private static final String INSERT_WORK_ROOM = """
            INSERT INTO uni.Arbeitsraeume (id, kapazitaet)
                VALUES (?, ?);
            """;

    private static final String FIND_BY_ID_QUERY = FIND_ALL_QUERY + " WHERE ID = ";

    @Override
    public SeminarRaum create() {
        return null;
    }

    @Override
    public void save(final SeminarRaum e) {
        log.debug("insert: {}", INSERT_BASE_QUERY);
        try {
            final Connection connection = getConnection();
            connection.setAutoCommit(false);
            try (final PreparedStatement basePreparedStatement = getPreparedStatement(connection, INSERT_BASE_QUERY);
                 final PreparedStatement workPreparedStatement = getPreparedStatement(connection, INSERT_WORK_ROOM)) {

                createBaseClassPart(e, basePreparedStatement);
                createSeminarRoomPart(e, workPreparedStatement);
                connection.commit();
            } catch (final SQLException ex) {
                log.error("Error creating office, aborting {}", ex.getMessage());
                connection.rollback();
                throw new RuntimeException(ex);
            }
        } catch (final SQLException ex) {
            log.error("Could not get connection.");
            throw new RuntimeException(ex);
        }
    }

    private void createSeminarRoomPart(final SeminarRaum e, final PreparedStatement workPreparedStatement) throws SQLException {
        // TODO set parameters
        workPreparedStatement.setLong(1, e.getId());
        workPreparedStatement.setLong(2, e.getKapazitaet());
        final int update = workPreparedStatement.executeUpdate();
        if (update != 1) {
            throw new SQLException("Could not create (work room) part");
        }
    }

    private void createBaseClassPart(final SeminarRaum e, final PreparedStatement basePreparedStatement) throws SQLException {
        // TODO set parameters
        basePreparedStatement.setLong(1, e.getId());
        basePreparedStatement.setString(2, e.getName());
        basePreparedStatement.setString(3, e.getRaumnummer());
        basePreparedStatement.setDouble(4, e.getFlaeche());
        basePreparedStatement.setDouble(5, e.getHoehe());
        final int update = basePreparedStatement.executeUpdate();
        if (update != 1) {
            throw new SQLException("Could not create (room) part");
        }
    }

    @Override
    public List<SeminarRaum> findAll() {
        final List<SeminarRaum> result = new ArrayList<>();
        try {
            final ResultSet resultSet = query(FIND_ALL_QUERY);
            while (resultSet.next()) {
                result.add(getSeminarRaum(resultSet));
            }
        } catch (final Exception e) {
            log.error("Problem finding seminarraeume {}", e.getMessage());
        }
        return result;
    }

    @Override
    public Optional<SeminarRaum> findById(final long id) {
        try {
            final ResultSet resultSet = query(FIND_BY_ID_QUERY + id);
            if (resultSet.next()) {
                return Optional.of(getSeminarRaum(resultSet));
            }
        } catch (final Exception e) {
            log.error("Problem finding seminarraum by id {}", e.getMessage());
        }
        return Optional.empty();
    }


    private SeminarRaum getSeminarRaum(final ResultSet resultSet) throws SQLException {
        final long id = resultSet.getInt("id");
        final SeminarRaum entity = new SeminarRaum(id);
        entity.setName(resultSet.getString("name"));
        entity.setFlaeche(resultSet.getDouble("flaeche"));
        entity.setHoehe(resultSet.getDouble("hoehe"));
        entity.setKapazitaet(resultSet.getInt("kapazitaet"));
        return entity;
    }

    public SeminarRaum createFrom(final SeminarraumDto template) {
        final SeminarRaum seminarRaum = create();

        seminarRaum.setName(template.getName());
        seminarRaum.setKapazitaet(template.getKapazitaet());
        // TODO initialize missing fields
        seminarRaum.setFlaeche(template.getFlaeche());
        seminarRaum.setHoehe(template.getHoehe());
        save(seminarRaum);
        return seminarRaum;
    }
}

