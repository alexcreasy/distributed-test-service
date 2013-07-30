package org.jboss.as.quickstarts.cmt.jts.ejb;

import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import java.io.Serializable;

/**
 * @Author Alex Creasy &lt;a.r.creasy@newcastle.ac.uk$gt;
 * Date: 24/07/2013
 * Time: 18:36
 */
public class SlowDummyXAResource implements XAResource, Serializable {

    public static final int COMMIT_DELAY = 60000;

    private int timeout = 0;

    @Override
    public void commit(Xid xid, boolean b) throws XAException {
        long endTime = System.currentTimeMillis() + COMMIT_DELAY;
        while (System.currentTimeMillis() < endTime) {
            // Testing
        }
    }

    @Override
    public void end(Xid xid, int i) throws XAException {
    }

    @Override
    public void forget(Xid xid) throws XAException {
    }

    @Override
    public int getTransactionTimeout() throws XAException {
        return timeout;
    }

    @Override
    public boolean isSameRM(XAResource xaResource) throws XAException {
        return false;
    }

    @Override
    public int prepare(Xid xid) throws XAException {
        return XA_OK;
    }

    @Override
    public Xid[] recover(int i) throws XAException {
        return new Xid[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void rollback(Xid xid) throws XAException {
    }

    @Override
    public boolean setTransactionTimeout(int i) throws XAException {
        this.timeout = i;
        return true;
    }

    @Override
    public void start(Xid xid, int i) throws XAException {
    }


    public String toString()
    {
//        return _xaFailureType + ", " + _xaFailureMode + ", " + (_args != null && _args.length != 0 ? _args[0] : "");

        StringBuilder sb = new StringBuilder();

        sb.append("XAResourceWrapperImpl@").append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" pad=").append("false");
        sb.append(" overrideRmValue=").append("false");
        sb.append(" productName=").append("Slow Dummy Product");
        sb.append(" productVersion=").append("0.0.0");
        sb.append(" jndiName=").append("java:/SlowDummyProd4");
        sb.append("]");

        return sb.toString();
    }
}
