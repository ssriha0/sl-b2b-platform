package com.servicelive.bus.event.log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.servicelive.bus.EventBusException;

public class ServiceLiveBusEventLogDao implements IServiceLiveBusEventLogDao {
	private static final Log log = LogFactory.getLog(ServiceLiveBusEventLogDao.class);
	
	private static final String INSERT_SERVICE_LIVE_BUS_EVENT_LOG_SQL =
		" INSERT INTO servicelive_bus_event_log (serviceliveBusEventLogId, eventId, clientId, eventType, serviceOrderId, orderEventType)\n" +
		" VALUES (0, :eventId, :clientId, :eventType, :serviceOrderId, :orderEventType)";
	
	private static final String GET_SERVICE_LIVE_BUS_EVENT_LOG_SQL =
		" SELECT * FROM servicelive_bus_event_log\n"+
		" WHERE serviceliveBusEventLogId = :serviceliveBusEventLogId";
	
	private static final String FIND_SERVICE_LIVE_BUS_EVENT_LOG_BY_EVENT_AND_CLIENT_IDS_SQL =
		" SELECT * FROM servicelive_bus_event_log\n"+
		" WHERE eventId = :eventId\n" +
		"   AND clientId = :clientId";
	
	private static final String FIND_SERVICE_LIVE_BUS_EVENT_LOG_BY_EVENT_ID_SQL =
		" SELECT * FROM servicelive_bus_event_log\n"+
		" WHERE eventId = :eventId";
	
    private NamedParameterJdbcTemplate jdbcTemplate;

    public ServiceLiveBusEventLogDao() {
    	
    }

	public boolean save(ServiceLiveBusEventLog serviceLiveBusEventLog) throws EventBusException {
		final String methodName = "save";
		log.info(String.format("entered %s", methodName));

		Map<String, String> namedParameters = new HashMap<String, String>();
		namedParameters.put("eventId", serviceLiveBusEventLog.getEventId());
		namedParameters.put("clientId", serviceLiveBusEventLog.getClientId());
		namedParameters.put("eventType", serviceLiveBusEventLog.getEventType());
		namedParameters.put("serviceOrderId", serviceLiveBusEventLog.getServiceOrderId());
		namedParameters.put("orderEventType", serviceLiveBusEventLog.getOrderEventType());
		KeyHolder generatedKeys = new GeneratedKeyHolder();
		
		long serviceLiveBusEventLogId;
		boolean completedSuccessfully = false;
		String failureReason = "";
		try {
			this.jdbcTemplate.update(INSERT_SERVICE_LIVE_BUS_EVENT_LOG_SQL,
					new MapSqlParameterSource(namedParameters), generatedKeys);
		
			serviceLiveBusEventLogId = generatedKeys.getKey().longValue();
			completedSuccessfully = true;
		}
		catch (DataIntegrityViolationException e) {
			serviceLiveBusEventLogId = 0;
			failureReason = e.getMessage();
			if (failureReason == null || !failureReason.contains("Duplicate entry")) {
				throw new EventBusException(String.format("Unable to create serviceLiveBusEventLog: %s.", serviceLiveBusEventLog.toString()), e);
			}
		}
		catch (DataAccessException e) {
			throw new EventBusException(String.format("Unable to create serviceLiveBusEventLog: %s.", serviceLiveBusEventLog.toString()), e);
		}

		if (completedSuccessfully) {
			log.info(String.format("created serviceLiveBusEventLog with id %d", serviceLiveBusEventLogId));
		serviceLiveBusEventLog.setId(serviceLiveBusEventLogId);
		}
		else {
			log.info(String.format("did not create serviceLiveBusEventLog record due to '%s'", failureReason));
		}
		
		log.info(String.format("exiting %s", methodName));
		return completedSuccessfully;
	}

	public ServiceLiveBusEventLog getById(long serviceLiveBusEventLogId) throws EventBusException {
		final String methodName = "getById";
		log.info(String.format("entered %s", methodName));

		Map<String, Long> namedParameters = new HashMap<String, Long>();
		namedParameters.put("serviceliveBusEventLogId", serviceLiveBusEventLogId);

		ServiceLiveBusEventLog serviceLiveBusEventLog;
		try {
			serviceLiveBusEventLog = (ServiceLiveBusEventLog)jdbcTemplate.queryForObject(
					GET_SERVICE_LIVE_BUS_EVENT_LOG_SQL, namedParameters, 
					new ServiceLiveBusEventLogRowMapper());
		}
		catch (EmptyResultDataAccessException e) {
			if (e.getActualSize() > 1) {
				throw new EventBusException(String.format("Found %d serviceLiveBusEventLogs with serviceLiveBusEventLogId %d while expecting at most 1.", e.getActualSize(), serviceLiveBusEventLogId), e);
			}
			serviceLiveBusEventLog = null;
		}
		catch (DataAccessException e) {
			throw new EventBusException(String.format("Unable to find serviceLiveBusEventLog with serviceLiveBusEventLogId %d.", serviceLiveBusEventLogId), e);
		}
		
		log.info(String.format("exiting %s", methodName));
		return serviceLiveBusEventLog;
	}
    
	public ServiceLiveBusEventLog findByEventAndClientIds(String eventId, String clientId) 
			throws EventBusException {

		final String methodName = "findByEventAndClientIds";
		log.info(String.format("entered %s", methodName));

		Map<String, String> namedParameters = new HashMap<String, String>();
		namedParameters.put("eventId", eventId);
		namedParameters.put("clientId", clientId);

		ServiceLiveBusEventLog serviceLiveBusEventLog;
		try {
			serviceLiveBusEventLog = (ServiceLiveBusEventLog)jdbcTemplate.queryForObject(
					FIND_SERVICE_LIVE_BUS_EVENT_LOG_BY_EVENT_AND_CLIENT_IDS_SQL,
					namedParameters, new ServiceLiveBusEventLogRowMapper());
		}
		catch (EmptyResultDataAccessException e) {
			if (e.getActualSize() > 1) {
				throw new EventBusException(String.format("Found %d serviceLiveBusEventLogs with eventId '%s' and clientId '%s' while expecting at most 1.", e.getActualSize(), eventId, clientId), e);
			}
			serviceLiveBusEventLog = null;
		}
		catch (DataAccessException e) {
			throw new EventBusException(String.format("Unable to find serviceLiveBusEventLog with eventId '%s' and clientId '%s'.", eventId, clientId), e);
		}
		
		log.info(String.format("exiting %s", methodName));
		return serviceLiveBusEventLog;
	}
	
	public ServiceLiveBusEventLog findByEventId(String eventId) 
	throws EventBusException {

		final String methodName = "findByEventId";
		log.info(String.format("entered %s", methodName));
		
		Map<String, String> namedParameters = new HashMap<String, String>();
		namedParameters.put("eventId", eventId);

		ServiceLiveBusEventLog serviceLiveBusEventLog;
		try {
			serviceLiveBusEventLog = (ServiceLiveBusEventLog)jdbcTemplate.queryForObject(
					FIND_SERVICE_LIVE_BUS_EVENT_LOG_BY_EVENT_ID_SQL,
			namedParameters, new ServiceLiveBusEventLogRowMapper());
		}
		catch (EmptyResultDataAccessException e) {
			if (e.getActualSize() > 1) {
				throw new EventBusException(String.format("Found %d serviceLiveBusEventLogs with eventId '%s' while expecting at most 1.", e.getActualSize(), eventId), e);
			}
			serviceLiveBusEventLog = null;
		}
		catch (DataAccessException e) {
			throw new EventBusException(String.format("Unable to find serviceLiveBusEventLog with eventId '%s'.", eventId), e);
		}
		
		log.info(String.format("exiting %s", methodName));
		return serviceLiveBusEventLog;
	}
	
	
	
    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private final class ServiceLiveBusEventLogRowMapper implements RowMapper {
		public ServiceLiveBusEventLog mapRow(ResultSet rs, int rowNumber) 
				throws SQLException {
			ServiceLiveBusEventLog returnVal = new ServiceLiveBusEventLog(
		    		rs.getLong("serviceliveBusEventLogId"),
		    		rs.getString("eventId"),
		    		rs.getString("clientId"),
		    		rs.getString("eventType"),
		    		rs.getString("serviceOrderId"),
		    		rs.getString("orderEventType"));
			return returnVal;
		}
	}
}
