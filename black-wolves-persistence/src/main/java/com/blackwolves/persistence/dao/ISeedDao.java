package com.blackwolves.persistence.dao;

import com.blackwolves.persistence.entity.Seed;
import com.blackwolves.persistence.exception.DaoException;

/**
 * 
 * @author gastondapice
 *
 */
public interface ISeedDao extends IGenericJpaDao<Seed, Long> {

	/**
	 * Searches for a seed in the database by the given email
	 * @param email
	 * @return {@link Seed}
	 * @throws DaoException
	 */
	Seed findByEmail(String email) throws DaoException;

	/**
	 * Inserts the given seed into the database
	 * @param seed
	 * @return {@link Seed}
	 * @throws DaoException 
	 */
	Seed insertSeedInDB(String[] seed) throws DaoException;

}
