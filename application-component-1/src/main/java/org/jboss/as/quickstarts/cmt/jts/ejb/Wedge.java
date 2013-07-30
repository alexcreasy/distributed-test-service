package org.jboss.as.quickstarts.cmt.jts.ejb;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

/**
 * @Author Alex Creasy &lt;a.r.creasy@newcastle.ac.uk$gt;
 * Date: 25/07/2013
 * Time: 15:47
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class Wedge {

    @Resource
    private EJBContext ctx;

    @Resource(lookup = "java:jboss/TransactionManager")
    private TransactionManager transactionManager;


    @Asynchronous
    public void wedgeTx() throws Exception {
        UserTransaction utx = ctx.getUserTransaction();
        utx.setTransactionTimeout(1800000);
        utx.begin();


        try {
            transactionManager.getTransaction().enlistResource(new DummyXAResource());
        } catch (RollbackException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(17900000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
