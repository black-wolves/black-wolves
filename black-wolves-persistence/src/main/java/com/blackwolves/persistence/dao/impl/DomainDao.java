package com.blackwolves.persistence.dao.impl;

import org.springframework.stereotype.Repository;

import com.blackwolves.persistence.dao.IDomainDao;
import com.blackwolves.persistence.entity.Domain;

/**
 * 
 * @author gastondapice
 *
 */
@Repository
public class DomainDao extends GenericJpaDao<Domain, Long> implements IDomainDao {

}
