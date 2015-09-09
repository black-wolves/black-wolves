package com.blackwolves.persistence.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.blackwolves.persistence.dao.ISeedDao;
import com.blackwolves.persistence.entity.Seed;
import com.blackwolves.persistence.exception.DaoException;

/**
 * 
 * @author gastondapice
 *
 */
@Repository
public class SeedDao extends GenericJpaDao<Seed, Long> implements ISeedDao {

	/*
	 * (non-Javadoc)
	 * @see com.blackwolves.persistence.dao.ISeedDao#findByEmail(java.lang.String)
	 */
	@Override
	@Transactional
	public Seed findByEmail(String email) throws DaoException {
		return (Seed) getSession().createCriteria(getClazz())
                .add(Restrictions.eq("email", email))
                .uniqueResult();
	}

	@Override
	@Transactional
	public Seed insertSeedInDB(Seed entity) throws DaoException {
		persist(entity);
		return entity;
	}
}
