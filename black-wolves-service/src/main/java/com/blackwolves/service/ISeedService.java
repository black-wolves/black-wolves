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

	/**
	 * Saves or update the seed according to the id of the object.
	 * @param seed
	 * @throws ServiceException
	 */
	void saveOrUpdate(Seed seed) throws ServiceException ;
}
