package com.newco.marketplace.translator.dao;

import java.util.List;

/**
 * Interface for ZipGeocodeDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface IZipGeocodeDAO {
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
	 * IZipGeocodeDAO.save(entity);
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
	public void save(ZipGeocode entity);

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
	 * IZipGeocodeDAO.delete(entity);
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
	public void delete(ZipGeocode entity);

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
	 * entity = IZipGeocodeDAO.update(entity);
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
	public ZipGeocode update(ZipGeocode entity);

	public ZipGeocode findById(String id);

	/**
	 * Find all ZipGeocode entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the ZipGeocode property to query
	 * @param value
	 *            the property value to match
	 * @return List<ZipGeocode> found by query
	 */
	public List<ZipGeocode> findByProperty(String propertyName, Object value);

	public List<ZipGeocode> findByCity(Object city);

	public List<ZipGeocode> findByStateCd(Object stateCd);

	public List<ZipGeocode> findByAreaCd(Object areaCd);

	public List<ZipGeocode> findByCountyFips(Object countyFips);

	public List<ZipGeocode> findByCountyName(Object countyName);

	public List<ZipGeocode> findByTimeZone(Object timeZone);

	public List<ZipGeocode> findByDaylightSavingsFlg(Object daylightSavingsFlg);

	public List<ZipGeocode> findByLatitude(Object latitude);

	public List<ZipGeocode> findByLongitude(Object longitude);

	public List<ZipGeocode> findByZipCodeType(Object zipCodeType);

	/**
	 * Find all ZipGeocode entities.
	 * 
	 * @return List<ZipGeocode> all ZipGeocode entities
	 */
	public List<ZipGeocode> findAll();
}