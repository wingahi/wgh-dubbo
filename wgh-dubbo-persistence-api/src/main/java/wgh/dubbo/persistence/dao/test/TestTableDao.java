package wgh.dubbo.persistence.dao.test;

import java.io.Serializable;

import wgh.dubbo.persistence.dao.BaseDao;

public interface TestTableDao<T, ID extends Serializable> extends BaseDao<T,ID> {

}
