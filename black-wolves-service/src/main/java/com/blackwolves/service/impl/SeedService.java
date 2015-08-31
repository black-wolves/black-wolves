/**
 * 
 */
package com.blackwolves.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blackwolves.persistence.dao.ISeedDao;
import com.blackwolves.persistence.entity.Seed;
import com.blackwolves.persistence.exception.DaoException;
import com.blackwolves.service.ISeedService;
import com.blackwolves.service.exception.ServiceException;

/**
 * @author gastondapice
 *
 */
@Service
public class SeedService implements ISeedService{

	@Autowired
	private ISeedDao seedDao;
	
	/*
	 * (non-Javadoc)
	 * @see com.blackwolves.service.shared.service.ISeedService#saveOrUpdate(com.blackwolves.persistence.entity.Seed)
	 */
	@Override
	public void saveOrUpdate(Seed seed) throws ServiceException {
		try {
			if(seed.getId()!=null){
				seedDao.merge(seed);
			}else {
				seedDao.persist(seed);
			}
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		
	}

	
}
