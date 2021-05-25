package com.wheretomeet.util;

import java.io.Serializable;
import java.util.Random;

import com.wheretomeet.model.Group;
import com.wheretomeet.model.User;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.slf4j.LoggerFactory;

import org.slf4j.Logger;

public class WhereToMeetIdGenerator implements IdentifierGenerator{
    private int USER_ID_MAX_LENGTH = 4;
    private int USER_ID_MAX_VALUE = 9999;
    private int GROUP_ID_MAX_LENGTH = 9;
    private int GROUP_ID_MAX_VALUE = 999999999;

    final static Logger log = LoggerFactory.getLogger(WhereToMeetIdGenerator.class);

    private String generateRandomTag(Object o) {
        Random rand = new Random();
        log.debug(o.getClass().getName());
        Class<?> entity_type = o.getClass();

        //default uses user id length and value generation specs
        int maxVal = USER_ID_MAX_VALUE;
        int maxLen = USER_ID_MAX_LENGTH;

        if(entity_type == Group.class) {
            maxVal = GROUP_ID_MAX_VALUE;
            maxLen = GROUP_ID_MAX_LENGTH;
        }

        String generatedTag = Integer.toString(1 + rand.nextInt(maxVal-1));
        while(generatedTag.length() != maxLen) {
            generatedTag = '0' + generatedTag;
        }

        //appends username to tag
        if(entity_type == User.class) { 
            generatedTag = ((User)o).getUsername() + "#" + generatedTag;
        }

        return generatedTag;
    }
    
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        String tag = generateRandomTag(object);
        return tag;
    }
}
