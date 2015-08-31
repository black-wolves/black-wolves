package com.blackwolves.persistence.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.blackwolves.persistence.exception.DaoException;

/**
 * The Interface IGenericDao.
 *
 * @param <T> the generic type
 * @param <K> the key type
 *
 * @author gastondapice
 *
 */
public interface IGenericJpaDao<T extends Serializable, K extends Serializable> {

	public class PaginationParams {

		int pageSize;
		int pageNumber;

		public PaginationParams(int pageSize, int pageNumber) {

			this.pageSize = pageSize;
			this.pageNumber = pageNumber;
		}

		public int getPageSize() {
			return pageSize;
		}

		public int getPageNumber() {
			return pageNumber;
		}

		public int getFirstResult() {
			return pageSize * (pageNumber - 1);
		}

		public int getMaxResults() {
			return pageNumber * pageSize;
		}
	}

	public enum OrderName {
		ASC
		, DESC
	}

	OrderName DEFAULT_SORT_DIRECTION = OrderName.ASC;

	/**
	 * Persist.
	 * 
	 * @param entity
	 */
	void persist(T entity) throws DaoException;
	
	void saveOrUpdate(T entity) throws DaoException;
	
	/**
	 * Merge.
	 * 
	 * @param entity
	 */
	T merge(T entity) throws DaoException;
	
	public void deleteAll(Collection<?> objects) throws DaoException;

	/**
	 * Delete.
	 *
	 * @param entity the entity
	 */
	void remove(T entity) throws DaoException;
	
	/**
	 * Load by id.
	 *
	 * @param id the id
	 * @return the t
	 */
	T findById(K id) throws DaoException;

	/**
	 * Load all.
	 *
	 * @return the list
	 */
	List<T> findAll() throws DaoException;
	
	/**
	 * Delete by id
	 * 
	 * @param id
	 */
	void deleteById(final K id) throws DaoException;

	/**
	 * Flushes the session
	 */
	void flush() throws DaoException;

	<E extends Serializable>E refresh(E entity) throws DaoException;
	
	public void removeAll() throws DaoException;
}
