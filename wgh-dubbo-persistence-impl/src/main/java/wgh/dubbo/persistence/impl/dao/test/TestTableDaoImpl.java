package wgh.dubbo.persistence.impl.dao.test;

import org.springframework.stereotype.Repository;

import wgh.dubbo.persistence.dao.test.TestTableDao;
import wgh.dubbo.persistence.entity.test.TestTable;
import wgh.dubbo.persistence.impl.dao.BaseDaoImpl;



@Repository
public class TestTableDaoImpl extends BaseDaoImpl<TestTable,Integer> implements TestTableDao<TestTable,Integer>{
   
}
