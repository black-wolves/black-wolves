package com.blackwolves.persistence.dao.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.blackwolves.persistence.exception.DaoException;

/**
 * 
 * @author gastondapice
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:com/blackwolves/persistence/test-application-context-black-wolves-persistence.xml" })
public class SeedDaoTest {

    @Test
    public void checkSeed() throws DaoException {

    }


}