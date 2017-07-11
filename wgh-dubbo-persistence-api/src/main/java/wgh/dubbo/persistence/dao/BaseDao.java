package wgh.dubbo.persistence.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.NonUniqueResultException;
import org.hibernate.criterion.DetachedCriteria;

import wgh.dubbo.persistence.query.QueryOrderBy;
import wgh.dubbo.persistence.query.QueryParameter;
import wgh.dubbo.persistence.query.QueryResult;

import com.googlecode.genericdao.search.ExampleOptions;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.SearchResult;


public interface BaseDao<T, PK extends Serializable> {
	  // -------------------  hibernate DAO framework start -----------------------
		public int count(ISearch search) ;

		public T find(Serializable id) ;

		public T[] find(Serializable... ids) ;

		public List<T> findAll() ;
		public T getReference(Serializable id) ;

		public T[] getReferences(Serializable... ids) ;
		public boolean isAttached(T entity) ;

		public void refresh(T... entities) ;

		public boolean remove(T entity);

		public void remove(T... entities) ;

		public boolean removeById(Serializable id);

		public void removeByIds(Serializable... ids) ;

		public boolean saveOrUpdateIsNew(T entity) ;

		public boolean[] saveOrUpdateIsNew(T... entities) ;

		public <RT> List<RT> search(ISearch search) ;

		public <RT> SearchResult<RT> searchAndCount(ISearch search) ;

		public <RT> RT searchUnique(ISearch search) ;

		public Filter getFilterFromExample(T example) ;

		public Filter getFilterFromExample(T example, ExampleOptions options) ;
	    // -------------------  hibernate DAO framework end -----------------------
	    

	    // -------------------- 基本检索、增加、修改、删除操作 -------------------- 

	    // 根据主键获取实体。如果没有相应的实体，返回 null。 
	    public T get(PK id) ;

	    // 根据主键获取实体并加锁。如果没有相应的实体，返回 null。 
	    public T getWithLock(PK id, LockMode lock);

	    // 根据主键获取实体。如果没有相应的实体，抛出异常。 
	    public T load(PK id) ;

	    // 根据主键获取实体并加锁。如果没有相应的实体，抛出异常。 
	    public T loadWithLock(PK id, LockMode lock) ;

	    // 获取全部实体。 
	    public List<T> loadAll() ;

	    // loadAllWithLock() ? 

	    // 更新实体 
	    public void update(T entity);

	    // 更新实体并加锁 
	    public void updateWithLock(T entity, LockMode lock) ;

	    // 存储实体到数据库 
	    public void save(T entity);

	    // saveWithLock()？ 

	    // 增加或更新实体 
	    public void saveOrUpdate(T entity) ;

	    // 增加或更新集合中的全部实体 
	    public void saveOrUpdateAll(Collection<T> entities) ;

	    // 删除指定的实体 
	    public void delete(T entity) ;

	    // 加锁并删除指定的实体 
	    public void deleteWithLock(T entity, LockMode lock) ;

	    // 根据主键删除指定实体 
	    public void deleteByKey(PK id) ;

	    // 根据主键加锁并删除指定的实体 
	    public void deleteByKeyWithLock(PK id, LockMode lock) ;

	    // 删除集合中的全部实体 
	    public void deleteAll(Collection<T> entities) ;
	    /**
		 * 根据一个实体中的某一属性名查询实体列表
		 * 
		 * @param fieldName
		 * @param fieldValue
		 * @return
		 */
		public List<T> queryByOneField(String fieldName, Object fieldValue) ;
		/**
		 * 根据一个实体中的某一属性名模糊查询实体列表
		 * 
		 * @param fieldName
		 * @param fieldValue
		 * @return
		 */
		public List<T> queryByOneFieldLike(String fieldName, Object fieldValue) ;
	    
	    // 使用HSQL语句直接增加、更新、删除实体 
	    public int bulkUpdate(String queryString) ;

	    // 使用带参数的HSQL语句增加、更新、删除实体 
	    public int bulkUpdate(String queryString, Object[] values) ;
	    // 使用HSQL语句检索数据 
	    public List<T> find(String queryString) ;

	    // 使用带参数的HSQL语句检索数据 
	    public List<T> find(String queryString, Object[] values);

	    // 使用带命名的参数的HSQL语句检索数据 
	    public List<T> findByNamedParam(String queryString, String[] paramNames, 
	            Object[] values) ;

	    // 使用命名的HSQL语句检索数据 
	    public List<T> findByNamedQuery(String queryName) ;

	    // 使用带参数的命名HSQL语句检索数据 
	    public List<T> findByNamedQuery(String queryName, Object[] values) ;

	    // 使用带命名参数的命名HSQL语句检索数据 
	    public List<T> findByNamedQueryAndNamedParam(String queryName, 
	            String[] paramNames, Object[] values) ;

	    // 使用HSQL语句检索数据，返回 Iterator 
	    public Iterator<T> iterate(String queryString) ;

	    // 使用带参数HSQL语句检索数据，返回 Iterator 
	    public Iterator<T> iterate(String queryString, Object[] values) ;

	    // 关闭检索返回的 Iterator 
	    public void closeIterator(Iterator<T> it) ;
	    // -------------------------------- Criteria ------------------------------ 

	    // 创建与会话无关的检索标准 
	    public DetachedCriteria createDetachedCriteria() ;

	    // 创建与会话绑定的检索标准 
	    @SuppressWarnings("deprecation")
		public Criteria createCriteria() ;

	    // 检索满足标准的数据 
	    public List<T> findByCriteria(DetachedCriteria criteria) ;
	    // 检索满足标准的数据，返回指定范围的记录 
	    public List<T> findByCriteria(DetachedCriteria criteria, int firstResult, int maxResults);

	    // 使用指定的实体及属性检索（满足除主键外属性＝实体值）数据 
	    public List<T> findEqualByEntity(T entity, String[] propertyNames) ;
	    
	    // 使用指定的实体及属性检索（满足除主键外属性＝实体值）数据 实现分页 
	    public List<T> findEqualByEntity(T entity, String[] propertyNames,int fromIdx,int fetchCount);

	    public T findUniqueEqualByEntity(T entity, String[] propertyNames);
	    // 使用指定的实体及属性检索（满足属性 like 串实体值）数据 
	    public List<T> findLikeByEntity(T entity, String[] propertyNames) ;

	    // 使用指定的检索标准获取满足标准的记录数 
	    public Integer getRowCount(DetachedCriteria criteria) ;
	    // 使用指定的检索标准检索数据，返回指定统计值(max,min,avg,sum) 
	    public Object getStatValue(DetachedCriteria criteria, String propertyName, String StatName) ;
	    // -------------------------------- Others -------------------------------- 

	    // 加锁指定的实体 
	    public void lock(T entity, LockMode lock) ;

	    // 强制初始化指定的实体 
	    public void initialize(Object proxy) ;
	    // 强制立即更新缓冲数据到数据库（否则仅在事务提交时才更新） 
	    public void flush() ;

	    @SuppressWarnings("rawtypes")
		public List<T> find(final String queryString, final Object[] values, final int start, final int limit) ;

		@SuppressWarnings("rawtypes")
		public int getRow(final String queryString, final Object[] values) ;

		public T findUniqueByCriteria(DetachedCriteria criteria) ;
		
		// By Native Sql
		public SearchResult<T> queryListByNativeSql(String nativeSql,int page,int pageSize,Object ...params);
		
		//根据参数执行sql脚本
		public Object executeUpdateByNativeSql(String nativeSql,Object ... values) ;
		//根据SQL查询列表
		@SuppressWarnings("deprecation")
		public List<T> searchByNativeSql(String nativeSql, int rowStartIdx, int rowCount, Object... values) ;
		//根据SQL查询符合条件的记录
		@SuppressWarnings("deprecation")
		public Integer countByNativeSql(String nativeSql,Object... values ) ;
		
		//根据SQL查询符合条件的记录
		@SuppressWarnings("deprecation")
		public Integer countByNativeSql2(String nativeSql,Object... values ) ;
		//查询符合条件的单一对象
		@SuppressWarnings("deprecation")
		public Object searchUniqueByNativeSql(String nativeSql, Object... values) throws NonUniqueResultException;
		
		//------------------------------ queryByConditions -----------------------------
		public Boolean existsByOneField(String fieldName, Object value);

		public Long countAll() ;

		public Long countByConditions(QueryParameter queryConditions) ;

		public Long countByConditions(String hql, QueryParameter queryConditions) ;
		
		public QueryResult<T> queryByConditions(String countHql, String listHql,
				QueryParameter queryConditions) ;

		public QueryResult<T> queryByConditions(QueryParameter queryConditions);
		
		/**
		 * 
		 * @param criteria
		 * @param orderBy
		 */
		public void setQueryOrderBy(Criteria criteria,
				List<QueryOrderBy> orderByList) ;

		/**
		 * 
		 * @param criteria
		 * @param orderBy
		 */
		public void setQueryOrderBy(Criteria criteria, QueryOrderBy orderBy) ;

		public String setQueryOrderBy(String hql, List<QueryOrderBy> orderByList);

		public String setQueryOrderBy(String hql, QueryOrderBy orderBy);

		public String buildHqlByConditions(StringBuilder hql,
				QueryParameter queryConditions);
		
		
		public void executeUpadteWithQuery(String hql);
		/**
		 *执行HQL更新
		 * @param objects
		 * @param hql
		 */
		public void executeUpdateWithQuery(String hql,Object...objects);
		
		/**
		 * 
		 * //TODO 执行SQL更新
		 * @author yinlinsheng
		 * @param nativeSql
		 * @param values
		 */
		public void executeUpdateWithNativeSql(String nativeSql,Object... values);
		
		/**
		 * 执行查询
		 * @author yinlinsheng
		 * @param nativeSql
		 * @param objects
		 */
		public List executeQuery(String nativeSql,Object...values);
}
