package com.newco.marketplace.translator.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * ZipGeocode entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.newco.marketplace.translator.dao.ZipGeocode
 * @author MyEclipse Persistence Tools
 */

public class ZipGeocodeDAO extends JpaDaoSupport implements IZipGeocodeDAO {
	// property constants
	public static final String CITY = "city";
	public static final String STATE_CD = "stateCd";
	public static final String AREA_CD = "areaCd";
	public static final String COUNTY_FIPS = "countyFips";
	public static final String COUNTY_NAME = "countyName";
	public static final String TIME_ZONE = "timeZone";
	public static final String DAYLIGHT_SAVINGS_FLG = "daylightSavingsFlg";
	public static final String LATITUDE = "latitude";
	public static final String LONGITUDE = "longitude";
	public static final String ZIP_CODE_TYPE = "zipCodeType";

	/**
	 * Perform an initial save of a previously unsaved ZipGeocode entity. All
	 * subsequent persist actions of this entity should use the #update()
	 * method. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#persist(Object) EntityManager#persist}
	 * operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * ZipGeocodeDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            ZipGeocode entity to persist
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void save(ZipGeocode entity) {
		logger.info("saving ZipGeocode instance");
		try {
			getJpaTemplate().persist(entity);
			logger.info("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	/**
	 * Delete a persistent ZipGeocode entity. This operation must be performed
	 * within the a database transaction context for the entity's data to be
	 * permanently deleted from the persistence store, i.e., database. This
	 * method uses the
	 * {@link javax.persistence.EntityManager#remove(Object) EntityManager#delete}
	 * operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * ZipGeocodeDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            ZipGeocode entity to delete
	 * @throws RuntimeException
	 *             when the operation fails
	 */
	public void delete(ZipGeocode entity) {
		logger.info("deleting ZipGeocode instance");
		try {
			entity = getJpaTemplate().getReference(ZipGeocode.class,
					entity.getZip());
			getJpaTemplate().remove(entity);
			logger.info("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * Persist a previously saved ZipGeocode entity and return it or a copy of
	 * it to the sender. A copy of the ZipGeocode entity parameter is returned
	 * when the JPA persistence mechanism has not previously been tracking the
	 * updated entity. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
	 * operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * entity = ZipGeocodeDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @see <a href =
	 *      "http://www.myeclipseide.com/documentation/quickstarts/jpaspring#containermanaged">Spring
	 *      container-managed transaction examples</a>
	 * @param entity
	 *            ZipGeocode entity to update
	 * @returns ZipGeocode the persisted ZipGeocode entity instance, may not be
	 *          the same
	 * @throws RuntimeException
	 *             if the operation fails
	 */
	public ZipGeocode update(ZipGeocode entity) {
		logger.info("updating ZipGeocode instance");
		try {
			ZipGeocode result = getJpaTemplate().merge(entity);
			logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	public ZipGeocode findById(String id) {
		logger.info("finding ZipGeocode instance with id: " + id);
		try {
			ZipGeocode instance = getJpaTemplate().find(ZipGeocode.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("find failed", re);
			throw re;
		}
	}

	/**
	 * Find all ZipGeocode entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the ZipGeocode property to query
	 * @param value
	 *            the property value to match
	 * @return List<ZipGeocode> found by query
	 */
	@SuppressWarnings("unchecked")
	public List<ZipGeocode> findByProperty(String propertyName,
			final Object value) {
		logger.info("finding ZipGeocode instance with property: "
				+ propertyName + ", value: " + value);
		try {
			final String queryString = "select model from ZipGeocode model where model."
					+ propertyName + "= :propertyValue";
			return getJpaTemplate().executeFind(new JpaCallback() {
				public Object doInJpa(EntityManager em)
						throws PersistenceException {
					Query query = em.createQuery(queryString);
					query.setParameter("propertyValue", value);
					return query.getResultList();
				}
			});
		} catch (RuntimeException re) {
			logger.error("find by property name failed", re);
			throw re;
		}
	}

	public List<ZipGeocode> findByCity(Object city) {
		return findByProperty(CITY, city);
	}

	public List<ZipGeocode> findByStateCd(Object stateCd) {
		return findByProperty(STATE_CD, stateCd);
	}

	public List<ZipGeocode> findByAreaCd(Object areaCd) {
		return findByProperty(AREA_CD, areaCd);
	}

	public List<ZipGeocode> findByCountyFips(Object countyFips) {
		return findByProperty(COUNTY_FIPS, countyFips);
	}

	public List<ZipGeocode> findByCountyName(Object countyName) {
		return findByProperty(COUNTY_NAME, countyName);
	}

	public List<ZipGeocode> findByTimeZone(Object timeZone) {
		return findByProperty(TIME_ZONE, timeZone);
	}

	public List<ZipGeocode> findByDaylightSavingsFlg(Object daylightSavingsFlg) {
		return findByProperty(DAYLIGHT_SAVINGS_FLG, daylightSavingsFlg);
	}

	public List<ZipGeocode> findByLatitude(Object latitude) {
		return findByProperty(LATITUDE, latitude);
	}

	public List<ZipGeocode> findByLongitude(Object longitude) {
		return findByProperty(LONGITUDE, longitude);
	}

	public List<ZipGeocode> findByZipCodeType(Object zipCodeType) {
		return findByProperty(ZIP_CODE_TYPE, zipCodeType);
	}

	/**
	 * Find all ZipGeocode entities.
	 * 
	 * @return List<ZipGeocode> all ZipGeocode entities
	 */
	@SuppressWarnings("unchecked")
	public List<ZipGeocode> findAll() {
		logger.info("finding all ZipGeocode instances");
		try {
			final String queryString = "select model from ZipGeocode model";
			return getJpaTemplate().executeFind(new JpaCallback() {
				public Object doInJpa(EntityManager em)
						throws PersistenceException {
					Query query = em.createQuery(queryString);
					return query.getResultList();
				}
			});
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}
	}

	public static IZipGeocodeDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (IZipGeocodeDAO) ctx.getBean("ZipGeocodeDAO");
	}
}