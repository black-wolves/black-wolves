package com.blackwolves.persistence.dao.impl;

import org.springframework.stereotype.Repository;

import com.blackwolves.persistence.dao.ISeedDao;
import com.blackwolves.persistence.entity.Seed;

/**
 * 
 * @author gastondapice
 *
 */
@Repository
public class SeedDao extends GenericJpaDao<Seed, Long> implements ISeedDao {

}
