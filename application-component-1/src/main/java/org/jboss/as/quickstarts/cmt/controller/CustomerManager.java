/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.cmt.controller;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import org.jboss.as.quickstarts.cmt.jts.ejb.CustomerManagerEJB;
import org.jboss.as.quickstarts.cmt.model.Customer;

@Named("customerManager")
@RequestScoped
public class CustomerManager {
    private Logger logger = Logger.getLogger(CustomerManager.class.getName());

    public static final int DEFAULT_DEMO_NO_OF_CUSTOMERS = 10;
    private static final int DEFAULT_FAILURE_RATE = 1; // No of chances in 10 that a transaction will fail.
    public static final int DEFAULT_DELAY_IN_MILLIS = 500;
    public static final int DEFAULT_NAME_LENGTH = 12;


    @Inject
    private CustomerManagerEJB customerManager;

    public List<Customer> getCustomers() throws SecurityException, IllegalStateException, NamingException,
            NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
        return customerManager.listCustomers();
    }

    public String addCustomer(String name) {

        try {
            if (name.toUpperCase().startsWith("DEMO:"))
                demo(name);
            else
                customerManager.createCustomer(name);
            return "customerAdded";
        } catch (Exception e) {
            logger.warning("Caught a duplicate: " + e.getMessage());
            // Transaction will be marked rollback only anyway utx.rollback();
            return "customerDuplicate";
        }
    }

    private void demo(String name) throws Exception {

        String[] commands = name.split(":");
        int noOfCustomers = commands.length > 1 ? Integer.parseInt(commands[1]) : DEFAULT_DEMO_NO_OF_CUSTOMERS;
        int failureRate = commands.length > 2 ? Integer.parseInt(commands[2]) : DEFAULT_FAILURE_RATE;

        for (int i = 0; i < noOfCustomers; i++) {
            customerManager.createCustomer(randomName(DEFAULT_NAME_LENGTH));
            Thread.sleep(DEFAULT_DELAY_IN_MILLIS);
        }
    }

    private String randomName(int length) {
        final String alpha = "abcdefghijklmnopqrstuvwxyz";
        final Random rand = new Random();
        final StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++)
            sb.append(alpha.charAt(rand.nextInt(alpha.length())));

        return sb.toString();
    }
}
