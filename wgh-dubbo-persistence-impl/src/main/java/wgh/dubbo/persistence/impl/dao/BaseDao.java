package wgh.dubbo.persistence.impl.dao;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wgh.dubbo.common.generic.GenericHibernateDAO;
import wgh.dubbo.persistence.impl.dao.BaseDao;






public class BaseDao<T, ID extends Serializable>  extends GenericHibernateDAO<T, ID>  {
public final Log log = LogFactory.getLog(this.getClass());
	
    @Autowired
    @Override
    public void setMySessionFactory(SessionFactory sessionFactory) {
        super.setMySessionFactory(sessionFactory);
    }
}
