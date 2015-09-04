package com.blackwolves.persistence.dao.impl;

import org.springframework.stereotype.Repository;

import com.blackwolves.persistence.dao.ISessionDao;
import com.blackwolves.persistence.entity.Session;

/**
 * 
 * @author gastondapice
 *
 */
@Repository
public class SessionDao extends GenericJpaDao<Session, Long> implements ISessionDao {

}
