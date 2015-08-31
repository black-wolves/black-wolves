package com.blackwolves.persistence.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import com.blackwolves.persistence.dao.IGenericJpaDao;
import com.blackwolves.persistence.exception.DaoException;

/**
 * 
 * @author gaston.dapice
 *
 * @param <T>
 * @param <K>
 */
public class GenericJpaDao<T extends Serializable, K extends Serializable> implements IGenericJpaDao<T, K> {

	private Class<T> clazz;

	private EntityManager entityManager;
	
	/*
	 * (non-Javadoc)
	 * @see com.blackwolves.persistence.dao.IGenericJpaDao#deleteAll(java.util.Collection)
	 */
	@Override
	public void deleteAll(Collection<?> objects) throws DaoException {
		
		try {
			for (Object object : objects) {
				entityManager.remove(object);
			}
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GenericJpaDao(){
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		clazz = (Class) pt.getActualTypeArguments()[0];
	}

	/**
	 * @return the clazz
	 */
	public Class<T> getClazz() {
		return clazz;
	}

	/**
	 * @param clazz the clazz to set
	 */
	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}

	/**
	 * @return the entityManager
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * @param entityManager the entityManager to set
	 */
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * @return the session
	 */
	public Session getSession() {
		return (Session) getEntityManager().getDelegate();
	}

	/*
	 * (non-Javadoc)
	 * @see com.blackwolves.persistence.dao.IGenericJpaDao#persist(java.io.Serializable)
	 */
	@Override
	@Transactional
	public void persist(T entity) throws DaoException {
		try {
//			if(entityManager.contains(entity)) {
//				entityManager.merge(entity);
//			}else {
				entityManager.persist(entity);
//			}
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.blackwolves.persistence.dao.IGenericJpaDao#merge(java.io.Serializable)
	 */
	@Override
	@Transactional
	public T merge(T entity) throws DaoException {
		
		try {
			return entityManager.merge(entity);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.blackwolves.persistence.dao.IGenericJpaDao#remove(java.io.Serializable)
	 */
	@Override
	@Transactional
	public void remove(final T entity) throws DaoException {
		
		try {
			entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	
	@Override
	public <E extends Serializable>E refresh(E entity) throws DaoException {
		
		try {
			entityManager.refresh(entity);
			return entity;
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.blackwolves.persistence.dao.IGenericJpaDao#findById(java.io.Serializable)
	 */
	@Override
	@Transactional
	public T findById(K id) throws DaoException {
		
		try {
			return entityManager.find(clazz, id);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.blackwolves.persistence.dao.IGenericJpaDao#findAll()
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<T> findAll() throws DaoException {
		try {
			return entityManager.createQuery("from " + clazz.getName()).getResultList();
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.blackwolves.persistence.dao.IGenericJpaDao#deleteById(java.io.Serializable)
	 */
	@Override
	@Transactional
	public void deleteById(final K id) throws DaoException {
		try {
			final T entity = findById(id);
			remove(entity);
		} catch (Exception e) {
			throw new DaoException(e);
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.blackwolves.persistence.dao.IGenericJpaDao#flush()
	 */
	@Override
	@Transactional
	public void flush() throws DaoException {
		try {
			entityManager.flush();
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.blackwolves.persistence.dao.IGenericJpaDao#removeAll()
	 */
	@Override
	@Transactional
	public void removeAll() throws DaoException {
		
		try {
			for (T entity : findAll()) {
				remove(entity);
			}
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.blackwolves.persistence.dao.IGenericJpaDao#saveOrUpdate(java.io.Serializable)
	 */
	@Override
	public void saveOrUpdate(T entity) throws DaoException {
		
//		try {
//			ClassMetadata classMetadata = getSession().getSessionFactory().getClassMetadata(clazz);
//			if (classMetadata.getIdentifier(entity, (SessionImplementor) getSession()) != null) {
//				
//			}
//		}
	}
}
