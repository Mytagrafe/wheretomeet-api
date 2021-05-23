package com.wheretomeet.util;

import java.io.Serializable;
import java.util.Random;

import org.hibernate.id.IdentifierGenerator;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

public class WhereToMeetIdGenerator implements IdentifierGenerator {
    private int ID_MAX_LENGTH = 4;
    private int ID_MAX_VALUE = 9999;

    private String generateRandomTag() {
        Random rand = new Random();
        String generatedTag = Integer.toString(1 + rand.nextInt(ID_MAX_VALUE-1));
        while(generatedTag.length() != ID_MAX_LENGTH) {
            generatedTag = '0' + generatedTag;
        }   
        return generatedTag;
    }
    
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        String tag = generateRandomTag();
        return tag;
    }
}
