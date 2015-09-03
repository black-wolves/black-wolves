/**
 * 
 */
package com.blackwolves.service.impl;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blackwolves.persistence.dao.ISeedDao;
import com.blackwolves.persistence.entity.Profile;
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

	/*
	 * (non-Javadoc)
	 * @see com.blackwolves.service.ISeedService#findByEmail(java.lang.String)
	 */
	@Override
	public Seed findByEmail(String email) throws ServiceException {
		try {
			return seedDao.findByEmail(email);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.blackwolves.service.ISeedService#insertSeedInDB(java.lang.String[])
	 */
	@Override
	public Seed insertSeedInDB(String[] seed) throws ServiceException {
		try {
			Seed s = new Seed(seed[0], seed [1]);
			Profile profile = new Profile(getRandomHoursForNextLogin());
			s.setProfile(profile);
			return seedDao.insertSeedInDB(s);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * Generates a random number
	 * @return int
	 */
	private int getRandomHoursForNextLogin() {
		Random rand = new Random();
		int randomNum = rand.nextInt((6 - 1) + 1) + 1;
		switch (randomNum) {
        case 1:
        case 4:
            return 6;
        case 2:
        case 5:
        	return 12;
        case 3:
        case 6:
        	return 24;
        default:
            return 48;
		}
	}
}
