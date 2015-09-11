/**
 * 
 */
package com.blackwolves.service;

import com.blackwolves.persistence.entity.Seed;
import com.blackwolves.service.exception.ServiceException;

/**
 * @author gastondapice
 *
 */
public interface ISeedService {
	
	Seed getSeedFromDb(String[] seed, String myIp);

	/**
	 * Saves or update the seed according to the id of the object.
	 * @param seed
	 * @throws ServiceException
	 */
	void saveOrUpdate(Seed seed) throws ServiceException ;

	/**
	 * Searches for a seed in the database by the given email
	 * @param email
	 * @return {@link Seed}
	 * @throws ServiceException
	 */
	Seed findByEmail(String email) throws ServiceException;

	/**
	 * Inserts the given seed into the database
	 * @param seed
	 * @param ip
	 * @return {@link Seed}
	 * @throws ServiceException
	 */
	Seed insertSeedInDB(String[] seed, String ip) throws ServiceException;
	
	/**
	 * Inserts the given seed into the database without any ip
	 * @param seed
	 * @param ip
	 * @return {@link Seed}
	 * @throws ServiceException
	 */
	 Seed insertSeedInDB(Seed seed) throws ServiceException;

	/**
	 * 
	 * @param dbSeed
	 * @return
	 * @throws ServiceException
	 */
	Seed refresh(Seed dbSeed) throws ServiceException;
}
