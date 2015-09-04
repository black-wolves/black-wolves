package com.blackwolves.persistence.dao.impl;

import org.springframework.stereotype.Repository;

import com.blackwolves.persistence.dao.IActionDao;
import com.blackwolves.persistence.entity.Action;

/**
 * 
 * @author gastondapice
 *
 */
@Repository
public class ActionDao extends GenericJpaDao<Action, Long> implements IActionDao {

}
