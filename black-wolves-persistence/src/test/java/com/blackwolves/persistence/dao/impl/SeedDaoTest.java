package com.blackwolves.persistence.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.blackwolves.persistence.dao.ISeedDao;
import com.blackwolves.persistence.entity.Seed;
import com.blackwolves.persistence.exception.DaoException;

/**
 * 
 * @author gastondapice
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:com/blackwolves/persistence/test-application-context-black-wolves-persistence.xml" })
public class SeedDaoTest {
	
	@Autowired
	private ISeedDao dao;

    @Test
    @Transactional
    public void checkSeed() throws DaoException {
    	List<Seed> seeds = dao.findAll();
    	System.out.println(seeds.size());
    }


}