package com.blackwolves.persistence.dao.impl;

import org.springframework.stereotype.Repository;

import com.blackwolves.persistence.dao.ISubscriptionDao;
import com.blackwolves.persistence.entity.Subscription;

/**
 * 
 * @author gastondapice
 *
 */
@Repository
public class SubscriptionDao extends GenericJpaDao<Subscription, Long> implements ISubscriptionDao {

}
