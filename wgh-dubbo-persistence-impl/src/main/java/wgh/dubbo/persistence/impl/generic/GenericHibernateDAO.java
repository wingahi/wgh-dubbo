package wgh.dubbo.persistence.impl.generic;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;

import wgh.dubbo.common.utils.Utils;
import wgh.dubbo.persistence.dao.BaseDao;
import wgh.dubbo.persistence.query.QueryCondition;
import wgh.dubbo.persistence.query.QueryOrderBy;
import wgh.dubbo.persistence.query.QueryPage;
import wgh.dubbo.persistence.query.QueryParameter;
import wgh.dubbo.persistence.query.QueryResult;

import com.googlecode.genericdao.dao.DAOUtil;
import com.googlecode.genericdao.search.ExampleOptions;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

/**
 * GenericHibernateDao 继承 HibernateDao，简单封装 HibernateTemplate 各项功能，
 * 简化基于Hibernate Dao 的编写。
 * 
 */
@SuppressWarnings("unchecked")
public class GenericHibernateDAO<T, PK extends Serializable> extends
		HibernateBaseDAO implements BaseDao<T, PK> {

	private static final String QUERY_BY_ONE_FIELD = "from %s eo where eo.%s=?";

	// 实体类类型(由构造方法自动赋值)
	// private Class<T> entityClass;
	private final Class<T> entityClass = (Class<T>) DAOUtil.getTypeArguments(
			GenericHibernateDAO.class, this.getClass()).get(0);

	private final String entityName = entityClass == null ? null : entityClass
			.getName();

	private final String entitySimpleName = entityClass == null ? null
			: entityClass.getSimpleName();

	// ------------------- hibernate DAO framework start -----------------------
	@Override
	public int count(ISearch search) {
		if (search == null)
			search = new Search();
		return _count(entityClass, search);
	}

	@Override
	public T find(Serializable id) {
		return _get(entityClass, id);
	}

	@Override
	public T[] find(Serializable... ids) {
		return _get(entityClass, ids);
	}

	@Override
	public List<T> findAll() {
		return _all(entityClass);
	}

	@Override
	public T getReference(Serializable id) {
		return _load(entityClass, id);
	}

	@Override
	public T[] getReferences(Serializable... ids) {
		return _load(entityClass, ids);
	}

	@Override
	public boolean isAttached(T entity) {
		return _sessionContains(entity);
	}

	@Override
	public void refresh(T... entities) {
		_refresh(entities);
	}

	@Override
	public boolean remove(T entity) {
		return _deleteEntity(entity);
	}

	@Override
	public void remove(T... entities) {
		_deleteEntities(entities);
	}

	@Override
	public boolean removeById(Serializable id) {
		return _deleteById(entityClass, id);
	}

	@Override
	public void removeByIds(Serializable... ids) {
		_deleteById(entityClass, ids);
	}

	@Override
	public boolean saveOrUpdateIsNew(T entity) {
		return _saveOrUpdateIsNew(entity);
	}

	@Override
	public boolean[] saveOrUpdateIsNew(T... entities) {
		return _saveOrUpdateIsNew(entities);
	}

	@Override
	public <RT> List<RT> search(ISearch search) {
		if (search == null)
			return (List<RT>) findAll();
		return _search(entityClass, search);
	}

	@Override
	public <RT> SearchResult<RT> searchAndCount(ISearch search) {
		if (search == null) {
			SearchResult<RT> result = new SearchResult<RT>();
			result.setResult((List<RT>) findAll());
			result.setTotalCount(result.getResult().size());
			return result;
		}
		return _searchAndCount(entityClass, search);
	}

	@Override
	public <RT> RT searchUnique(ISearch search) {
		return (RT) _searchUnique(entityClass, search);
	}

	@Override
	public Filter getFilterFromExample(T example) {
		return _getFilterFromExample(example);
	}

	@Override
	public Filter getFilterFromExample(T example, ExampleOptions options) {
		return _getFilterFromExample(example, options);
	}

	// ------------------- hibernate DAO framework end -----------------------

	// -------------------- 基本检索、增加、修改、删除操作 --------------------

	// 根据主键获取实体。如果没有相应的实体，返回 null。
	@Override
	public T get(PK id) {
		return getHibernateTemplate().get(entityClass, id);
	}

	// 根据主键获取实体并加锁。如果没有相应的实体，返回 null。
	@Override
	public T getWithLock(PK id, LockMode lock) {
		T t = getHibernateTemplate().get(entityClass, id, lock);
		if (t != null) {
			this.flush(); // 立即刷新，否则锁不会生效。
		}
		return t;
	}

	// 根据主键获取实体。如果没有相应的实体，抛出异常。
	@Override
	public T load(PK id) {
		return getHibernateTemplate().load(entityClass, id);
	}

	// 根据主键获取实体并加锁。如果没有相应的实体，抛出异常。
	@Override
	public T loadWithLock(PK id, LockMode lock) {
		T t = getHibernateTemplate().load(entityClass, id, lock);
		if (t != null) {
			this.flush(); // 立即刷新，否则锁不会生效。
		}
		return t;
	}

	// 获取全部实体。
	@Override
	public List<T> loadAll() {
		return getHibernateTemplate().loadAll(entityClass);
	}

	// loadAllWithLock() ?

	// 更新实体
	@Override
	public void update(T entity) {
		getHibernateTemplate().update(entity);
	}

	// 更新实体并加锁
	@Override
	public void updateWithLock(T entity, LockMode lock) {
		getHibernateTemplate().update(entity, lock);
		this.flush(); // 立即刷新，否则锁不会生效。
	}

	// 存储实体到数据库
	@Override
	public void save(T entity) {
		getHibernateTemplate().save(entity);
	}

	// saveWithLock()？

	// 增加或更新实体
	@Override
	public void saveOrUpdate(T entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}

	// 增加或更新集合中的全部实体
	@Override
	public void saveOrUpdateAll(Collection<T> entities) {
		getHibernateTemplate().saveOrUpdateAll(entities);
	}

	// 删除指定的实体
	@Override
	public void delete(T entity) {
		getHibernateTemplate().delete(entity);
	}

	// 加锁并删除指定的实体
	@Override
	public void deleteWithLock(T entity, LockMode lock) {
		getHibernateTemplate().delete(entity, lock);
		this.flush(); // 立即刷新，否则锁不会生效。
	}

	// 根据主键删除指定实体
	@Override
	public void deleteByKey(PK id) {
		this.delete(this.load(id));
	}

	// 根据主键加锁并删除指定的实体
	@Override
	public void deleteByKeyWithLock(PK id, LockMode lock) {
		this.deleteWithLock(this.load(id), lock);
	}

	// 删除集合中的全部实体
	@Override
	public void deleteAll(Collection<T> entities) {
		getHibernateTemplate().deleteAll(entities);
	}

	// -------------------- HSQL ----------------------------------------------

	protected Long countByHql(String hql) {
		final Query query = getSession().createQuery(hql);

		Long count = (Long) query.list().get(0);
		if (count == null) {
			return 0L;
		}
		return count;
	}

	protected Long countByHql(String hql, Object... values) {
		final Query query = getSession().createQuery(hql);
		setParameters(query, values);

		Long count = (Long) query.list().get(0);
		if (count == null) {
			return 0L;
		}
		return count;
	}

	protected void setParameters(Query query, Object... values) {
		if (values != null) {
			int cur = 0;
			for (int i = 0; i < values.length; i++) {
				// query.setParameter(i, values[i]);
				if (values[i] != null && values[i] instanceof List) {
					List list = (List) values[i];
					for (int j = 0; list != null && j < list.size(); j++) {
						query.setParameter(cur++, list.get(j));
					}
				} else {
					query.setParameter(cur++, values[i]);
				}
			}
		}
	}

	protected List<T> queryByHql(String hql, Object... values) {

		List<T> entites = getHibernateTemplate().find(hql, values);
		if (Utils.isNull(entites)) {
			entites = Collections.EMPTY_LIST;
		}

		return entites;
	}

	/**
	 * 根据一个实体中的某一属性名查询实体列表
	 * 
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
	@Override
	public List<T> queryByOneField(String fieldName, Object fieldValue) {
		final String hql = String.format(QUERY_BY_ONE_FIELD, entitySimpleName,
				fieldName);

		return queryByHql(hql, fieldValue);
	}

	/**
	 * 根据一个实体中的某一属性名模糊查询实体列表
	 * 
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
	@Override
	public List<T> queryByOneFieldLike(String fieldName, Object fieldValue) {
		return queryByOneField(fieldName, "%" + fieldValue + "%");
	}

	// 使用HSQL语句直接增加、更新、删除实体
	@Override
	public int bulkUpdate(String queryString) {
		return getHibernateTemplate().bulkUpdate(queryString);
	}

	// 使用带参数的HSQL语句增加、更新、删除实体
	@Override
	public int bulkUpdate(String queryString, Object[] values) {
		return getHibernateTemplate().bulkUpdate(queryString, values);
	}

	// 使用HSQL语句检索数据
	@Override
	public List<T> find(String queryString) {
		return getHibernateTemplate().find(queryString);
	}

	// 使用带参数的HSQL语句检索数据
	@Override
	public List<T> find(String queryString, Object[] values) {
		return getHibernateTemplate().find(queryString, values);
	}

	// 使用带命名的参数的HSQL语句检索数据
	@Override
	public List<T> findByNamedParam(String queryString, String[] paramNames,
			Object[] values) {
		return getHibernateTemplate().findByNamedParam(queryString, paramNames,
				values);
	}

	// 使用命名的HSQL语句检索数据
	@Override
	public List<T> findByNamedQuery(String queryName) {
		return getHibernateTemplate().findByNamedQuery(queryName);
	}

	// 使用带参数的命名HSQL语句检索数据
	@Override
	public List<T> findByNamedQuery(String queryName, Object[] values) {
		return getHibernateTemplate().findByNamedQuery(queryName, values);
	}

	// 使用带命名参数的命名HSQL语句检索数据
	@Override
	public List<T> findByNamedQueryAndNamedParam(String queryName,
			String[] paramNames, Object[] values) {
		return getHibernateTemplate().findByNamedQueryAndNamedParam(queryName,
				paramNames, values);
	}

	// 使用HSQL语句检索数据，返回 Iterator
	@Override
	public Iterator<T> iterate(String queryString) {
		return getHibernateTemplate().iterate(queryString);
	}

	// 使用带参数HSQL语句检索数据，返回 Iterator
	@Override
	public Iterator<T> iterate(String queryString, Object[] values) {
		return getHibernateTemplate().iterate(queryString, values);
	}

	// 关闭检索返回的 Iterator
	@Override
	public void closeIterator(Iterator<T> it) {
		getHibernateTemplate().closeIterator(it);
	}

	// -------------------------------- Criteria ------------------------------

	// 创建与会话无关的检索标准
	@Override
	public DetachedCriteria createDetachedCriteria() {
		return DetachedCriteria.forClass(this.entityClass);
	}

	// 创建与会话绑定的检索标准
	@Override
	@SuppressWarnings("deprecation")
	public Criteria createCriteria() {
		return this.createDetachedCriteria().getExecutableCriteria(
				this.getSession());
	}

	// 检索满足标准的数据
	@Override
	public List<T> findByCriteria(DetachedCriteria criteria) {
		return getHibernateTemplate().findByCriteria(criteria);
	}

	// 检索满足标准的数据，返回指定范围的记录
	@Override
	public List<T> findByCriteria(DetachedCriteria criteria, int firstResult,
			int maxResults) {
		return getHibernateTemplate().findByCriteria(criteria, firstResult,
				maxResults);
	}

	// 使用指定的实体及属性检索（满足除主键外属性＝实体值）数据
	@Override
	public List<T> findEqualByEntity(T entity, String[] propertyNames) {
		return findEqualByEntity(entity, propertyNames, -1, -1);
	}

	// 使用指定的实体及属性检索（满足除主键外属性＝实体值）数据 实现分页
	@Override
	public List<T> findEqualByEntity(T entity, String[] propertyNames,
			int fromIdx, int fetchCount) {
		Criteria criteria = this.createCriteria();
		Example exam = Example.create(entity);
		exam.excludeZeroes();
		String[] defPropertys = getSessionFactory().getClassMetadata(
				entityClass).getPropertyNames();
		for (String defProperty : defPropertys) {
			int ii = 0;
			for (ii = 0; ii < propertyNames.length; ++ii) {
				if (defProperty.equals(propertyNames[ii])) {
					criteria.addOrder(Order.asc(defProperty));
					break;
				}
			}
			if (ii == propertyNames.length) {
				exam.excludeProperty(defProperty);
			}
		}
		if (fromIdx > -1) {
			criteria.setFirstResult(fromIdx);
		}
		if (fetchCount > -1) {
			criteria.setMaxResults(fetchCount);
		}
		criteria.add(exam);
		return criteria.list();
	}

	@Override
	public T findUniqueEqualByEntity(T entity, String[] propertyNames) {
		List<T> result = findEqualByEntity(entity, propertyNames, 0, 1);
		if (result == null || result.isEmpty())
			return null;
		return result.get(0);
	}

	// 使用指定的实体及属性检索（满足属性 like 串实体值）数据
	@Override
	public List<T> findLikeByEntity(T entity, String[] propertyNames) {
		Criteria criteria = this.createCriteria();
		for (String property : propertyNames) {
			try {
				Object value = PropertyUtils.getProperty(entity, property);
				if (value instanceof String) {
					criteria.add(Restrictions.like(property, (String) value,
							MatchMode.ANYWHERE));
					criteria.addOrder(Order.asc(property));
				} else {
					criteria.add(Restrictions.eq(property, value));
					criteria.addOrder(Order.asc(property));
				}
			} catch (Exception ex) {
				// 忽略无效的检索参考数据。
			}
		}
		return criteria.list();
	}

	// 使用指定的检索标准获取满足标准的记录数
	@Override
	public Integer getRowCount(DetachedCriteria criteria) {
		criteria.setProjection(Projections.rowCount());
		List<T> list = this.findByCriteria(criteria, 0, 1);
		return (Integer) list.get(0);
	}

	// 使用指定的检索标准检索数据，返回指定统计值(max,min,avg,sum)
	@Override
	public Object getStatValue(DetachedCriteria criteria, String propertyName,
			String StatName) {
		if (StatName.toLowerCase().equals("max")) {
			criteria.setProjection(Projections.max(propertyName));
		} else if (StatName.toLowerCase().equals("min")) {
			criteria.setProjection(Projections.min(propertyName));
		} else if (StatName.toLowerCase().equals("avg")) {
			criteria.setProjection(Projections.avg(propertyName));
		} else if (StatName.toLowerCase().equals("sum")) {
			criteria.setProjection(Projections.sum(propertyName));
		} else {
			return null;
		}
		List<T> list = this.findByCriteria(criteria, 0, 1);
		return list.get(0);
	}

	// -------------------------------- Others --------------------------------

	// 加锁指定的实体
	@Override
	public void lock(T entity, LockMode lock) {
		getHibernateTemplate().lock(entity, lock);
	}

	// 强制初始化指定的实体
	@Override
	public void initialize(Object proxy) {
		getHibernateTemplate().initialize(proxy);
	}

	// 强制立即更新缓冲数据到数据库（否则仅在事务提交时才更新）
	@Override
	public void flush() {
		getHibernateTemplate().flush();
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List<T> find(final String queryString, final Object[] values,
			final int start, final int limit) {
		return (List<T>) getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session arg0)
							throws HibernateException, SQLException {
						Query query = arg0.createQuery(queryString);
						if (values != null) {
							for (int i = 0; i < values.length; i++) {
								query.setParameter(i, values[i]);
							}
						}
						if (start > -1)
							query.setFirstResult(start);
						if (limit > -1)
							query.setMaxResults(limit);
						return query.list();
					}

				});
	}

	@Override
	@SuppressWarnings("rawtypes")
	public int getRow(final String queryString, final Object[] values) {
		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session arg0)
							throws HibernateException, SQLException {
						Query query = arg0.createQuery(queryString);
						if (values != null) {
							for (int i = 0; i < values.length; i++) {
								query.setParameter(i, values[i]);
							}
						}
						return query.uniqueResult();
					}
				});
	}

	@Override
	public T findUniqueByCriteria(DetachedCriteria criteria) {
		List<T> list = this.findByCriteria(criteria, 0, 1);
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}

	// By Native Sql
	@Override
	public SearchResult<T> queryListByNativeSql(String nativeSql, int page,
			int pageSize, Object... params) {
		SearchResult<T> res = new SearchResult<T>();
		int rowStartIdx = (page - 1) * pageSize;
		List<T> results = searchByNativeSql(nativeSql, rowStartIdx, pageSize,
				params);
		Integer totalCount = countByNativeSql(nativeSql, params);
		res.setTotalCount(totalCount);
		res.setResult(results);
		return res;
	}

	// 根据参数执行sql脚本
	@Override
	public Object executeUpdateByNativeSql(String nativeSql, Object... values) {
		final String tmpSql = nativeSql;
		final Object[] tmpValues = values;
		return this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			@SuppressWarnings("deprecation")
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Connection con = session.connection();
				PreparedStatement ps = con.prepareStatement(tmpSql);
				if (tmpValues != null && tmpValues.length > 0) {
					int col = 1;
					for (int i = 0; i < tmpValues.length; i++) {
						if (tmpValues[i] != null
								&& tmpValues[i] instanceof List) {
							List list = (List) tmpValues[i];
							for (int j = 0; list != null && j < list.size(); j++) {
								// query.setParameter(cur++, list.get(j));
								ps.setObject(col++, list.get(j));
							}
						} else {
							ps.setObject(col++, tmpValues[i]);
						}
					}
				}
				int affectedCount = ps.executeUpdate();
				ps.close();
				session.flush();
				session.close();
				return affectedCount;
			}
		});
	}

	// 根据SQL查询列表
	@Override
	@SuppressWarnings("deprecation")
	public List<T> searchByNativeSql(String nativeSql, int rowStartIdx,
			int rowCount, Object... values) {
		Session session = getSession();
		// if (values.length > 0) {
		// queryString = String.format(queryString, values);
		// }
		Query query = session.createSQLQuery(nativeSql).addEntity(entityClass);
		setParameters(query, values);
		if (rowStartIdx > 0)
			query.setFirstResult(rowStartIdx);
		if (rowCount > 0)
			query.setMaxResults(rowCount);
		List<T> list = query.list();
		releaseSession(session);
		return list;
	}

	// 根据SQL查询符合条件的记录
	@Override
	@SuppressWarnings("deprecation")
	public Integer countByNativeSql(String nativeSql, Object... values) {
		Session session = getSession();
		Query query = session.createSQLQuery("select count(1) from ("
				+ nativeSql + ") a");
		setParameters(query, values);
		Integer result = ((BigInteger) query.uniqueResult()).intValue();
		releaseSession(session);
		return result;
	}

	// 根据SQL查询符合条件的记录
	@Override
	@SuppressWarnings("deprecation")
	public Integer countByNativeSql2(String nativeSql, Object... values) {
		Session session = getSession();
		Query query = session.createSQLQuery(nativeSql);
		setParameters(query, values);
		Integer result = ((BigInteger) query.uniqueResult()).intValue();
		releaseSession(session);
		return result;
	}

	// 查询符合条件的单一对象
	@Override
	@SuppressWarnings("deprecation")
	public Object searchUniqueByNativeSql(String nativeSql, Object... values)
			throws NonUniqueResultException {
		Session session = getSession();
		Query query = session.createSQLQuery(nativeSql);// .addEntity(entityClass);
		setParameters(query, values);
		// addResultMode(query,transFormat);
		Object res = query.uniqueResult();
		releaseSession(session);
		return res;
	}

	private void addResultMode(Query query, int resultMode) {
		// int resultMode = search.getResultMode();
		// if (resultMode == ISearch.RESULT_AUTO) {
		// int count = 0;
		// Iterator<Field> fieldItr = search.getFields().iterator();
		// while (fieldItr.hasNext()) {
		// Field field = fieldItr.next();
		// if (field.getKey() != null && !field.getKey().equals("")) {
		// resultMode = ISearch.RESULT_MAP;
		// break;
		// }
		// count++;
		// }
		// if (resultMode == ISearch.RESULT_AUTO) {
		// if (count > 1)
		// resultMode = ISearch.RESULT_ARRAY;
		// else
		// resultMode = ISearch.RESULT_SINGLE;
		// }
		// }

		switch (resultMode) {
		case ISearch.RESULT_ARRAY:
			query.setResultTransformer(ARRAY_RESULT_TRANSFORMER);
			break;
		case ISearch.RESULT_LIST:
			query.setResultTransformer(Transformers.TO_LIST);
			break;
		// case ISearch.RESULT_MAP:
		// List<String> keyList = new ArrayList<String>();
		// Iterator<Field> fieldItr = search.getFields().iterator();
		// while (fieldItr.hasNext()) {
		// Field field = fieldItr.next();
		// if (field.getKey() != null && !field.getKey().equals("")) {
		// keyList.add(field.getKey());
		// } else {
		// keyList.add(field.getProperty());
		// }
		// }
		// query.setResultTransformer(new
		// MapResultTransformer(keyList.toArray(new String[0])));
		// break;
		default: // ISearch.RESULT_SINGLE
			break;
		}
	}

	@SuppressWarnings("unchecked")
	private static final ResultTransformer ARRAY_RESULT_TRANSFORMER = new ResultTransformer() {
		private static final long serialVersionUID = 1L;

		@Override
		public List transformList(List collection) {
			return collection;
		}

		@Override
		public Object transformTuple(Object[] tuple, String[] aliases) {
			return tuple;
		}
	};

	@SuppressWarnings("unchecked")
	private static class MapResultTransformer implements ResultTransformer {
		private static final long serialVersionUID = 1L;

		private final String[] keys;

		public MapResultTransformer(String[] keys) {
			this.keys = keys;
		}

		@Override
		public List transformList(List collection) {
			return collection;
		}

		@Override
		public Object transformTuple(Object[] tuple, String[] aliases) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < keys.length; i++) {
				String key = keys[i];
				if (key != null) {
					map.put(key, tuple[i]);
				}
			}

			return map;
		}
	}

	// ------------------------------ queryByConditions
	// -----------------------------
	@Override
	public Boolean existsByOneField(String fieldName, Object value) {
		final String hql = "select count(*) from " + entityName
				+ " eo where eo." + fieldName + " = ? ";
		Long count = countByHql(hql, value);
		return count > 0;
	}

	@Override
	public Long countAll() {
		final String hql = "select count(*) from " + entityName + " eo";

		return countByHql(hql);
	}

	@Override
	public Long countByConditions(QueryParameter queryConditions) {
		final StringBuilder hqlBuilder = new StringBuilder(
				"select count(*) from " + entitySimpleName + " eo");
		final String hql = buildHqlByConditions(hqlBuilder, queryConditions);
		return countByConditions(hql, queryConditions);
	}

	@Override
	public Long countByConditions(String hql, QueryParameter queryConditions) {
		// LOG.debug(String.format("HQL builded by conditions: " + hql));
		final Query query = getSession().createQuery(hql);
		setParameter(query, queryConditions);
		// LOG.debug(String.format("HQL of conditions: " +
		// queryConditions.toString()));

		Long count = (Long) query.list().get(0);
		if (count == null) {
			return 0L;
		}
		return count;
	}

	@Override
	public QueryResult<T> queryByConditions(String countHql, String listHql,
			QueryParameter queryConditions) {

		listHql = buildHqlByConditions(new StringBuilder(listHql),
				queryConditions);
		if (Utils.isNotEmpty(queryConditions.getOrderByList())) {
			listHql = setQueryOrderBy(listHql, queryConditions.getOrderByList());
		} else {
			listHql = setQueryOrderBy(listHql, queryConditions.getOrderBy());
		}
		// LOG.debug(String.format("HQL builded by conditions: " + listHql));
		Query queryList = getSession().createQuery(listHql);
		setQueryPage(queryList, queryConditions.getQueryPage());
		setParameter(queryList, queryConditions);

		@SuppressWarnings("unchecked")
		List<T> results = queryList.list();
		if (Utils.isEmpty(results)) {
			results = new ArrayList<T>(0);
		}
		if (Utils.isNull(queryConditions.getQueryPage())) {
			return new QueryResult<T>(results.size(), results);
		}

		countHql = buildHqlByConditions(new StringBuilder(countHql),
				queryConditions);
		return new QueryResult<T>(countByConditions(countHql, queryConditions)
				.intValue(), queryConditions.getQueryPage(), results);
	}

	@Override
	public QueryResult<T> queryByConditions(QueryParameter queryConditions) {
		final Criteria criteria = getSession().createCriteria(entityClass);

		setQueryPage(criteria, queryConditions.getQueryPage());
		setQueryConditions(criteria, queryConditions.getQueryConditions());
		if (Utils.isNotEmpty(queryConditions.getOrderByList())) {
			setQueryOrderBy(criteria, queryConditions.getOrderByList());
		} else {
			setQueryOrderBy(criteria, queryConditions.getOrderBy());
		}
		@SuppressWarnings("unchecked")
		List<T> results = criteria.list();
		if (Utils.isEmpty(results)) {
			results = new ArrayList<T>(0);
		}

		return new QueryResult<T>(
				countByConditions(queryConditions).intValue(),
				queryConditions.getQueryPage(), results);
	}

	/**
	 * 
	 * @param criteria
	 * @param orderBy
	 */
	@Override
	public void setQueryOrderBy(Criteria criteria,
			List<QueryOrderBy> orderByList) {
		if (Utils.isNull(orderByList) || Utils.isEmpty(orderByList)) {
			return;
		}

		for (QueryOrderBy queryOrderBy : orderByList) {

			if (QueryOrderBy.ASC.equals(queryOrderBy.getOrderBy())) {
				criteria.addOrder(Order.asc(queryOrderBy.getFieldName()));
			}
			if (QueryOrderBy.DESC.equals(queryOrderBy.getOrderBy())) {
				criteria.addOrder(Order.desc(queryOrderBy.getFieldName()));
			}
		}
	}

	/**
	 * 
	 * @param criteria
	 * @param orderBy
	 */
	@Override
	public void setQueryOrderBy(Criteria criteria, QueryOrderBy orderBy) {
		if (Utils.isNull(orderBy)) {
			return;
		}
		if (QueryOrderBy.ASC.equals(orderBy.getOrderBy())) {
			criteria.addOrder(Order.asc(orderBy.getFieldName()));
		}
		if (QueryOrderBy.DESC.equals(orderBy.getOrderBy())) {
			criteria.addOrder(Order.desc(orderBy.getFieldName()));
		}
	}

	@Override
	public String setQueryOrderBy(String hql, List<QueryOrderBy> orderByList) {
		if (Utils.isNull(orderByList)) {
			return hql;
		}
		for (QueryOrderBy queryOrderBy2 : orderByList) {
			if (QueryOrderBy.ASC.equals(queryOrderBy2.getOrderBy())) {
				hql += " order by " + queryOrderBy2.getFieldName();
			}
			if (QueryOrderBy.DESC.equals(queryOrderBy2.getOrderBy())) {
				hql += " order by " + queryOrderBy2.getFieldName() + " desc";
			}
		}

		return hql;
	}

	@Override
	public String setQueryOrderBy(String hql, QueryOrderBy orderBy) {
		if (Utils.isNull(orderBy)) {
			return hql;
		}
		if (QueryOrderBy.ASC.equals(orderBy.getOrderBy())) {
			hql += " order by " + orderBy.getFieldName();
		}
		if (QueryOrderBy.DESC.equals(orderBy.getOrderBy())) {
			hql += " order by " + orderBy.getFieldName() + " desc";
		}

		return hql;
	}

	@Override
	public String buildHqlByConditions(StringBuilder hql,
			QueryParameter queryConditions) {
		final List<QueryCondition> conditions = queryConditions
				.getQueryConditions();
		if (Utils.isEmpty(conditions)) {
			return hql.toString();
		}

		boolean bWhere = false;
		if (hql.indexOf(" where ") > 0) {
			bWhere = true;
		}

		boolean moreThanOne = bWhere;
		for (int i = 0; i < conditions.size(); i++) {
			if (i == 0 && bWhere == false) {
				hql.append(" where ");
			}
			QueryCondition condition = conditions.get(i);

			if (moreThanOne) {
				hql.append(" and ");
			}

			if ("=".equals(condition.getOperator())) {
				moreThanOne = true;

				// hql.append(" eo.");
				hql.append(condition.getField());
				hql.append(" = ?");
			} else if (">".equals(condition.getOperator())) {
				moreThanOne = true;

				// hql.append(" eo.");
				hql.append(condition.getField());
				hql.append(" > ?");
			} else if (">=".equals(condition.getOperator())) {
				moreThanOne = true;

				// hql.append(" eo.");
				hql.append(condition.getField());
				hql.append(" >= ?");
			} else if ("<".equals(condition.getOperator())) {
				moreThanOne = true;

				// hql.append(" eo.");
				hql.append(condition.getField());
				hql.append(" < ?");
			} else if ("<=".equals(condition.getOperator())) {
				moreThanOne = true;

				// hql.append(" eo.");
				hql.append(condition.getField());
				hql.append(" <= ?");
			} else if ("like".equals(condition.getOperator())) {
				moreThanOne = true;

				// hql.append(" eo.");
				hql.append(condition.getField());
				hql.append(" like ?");
			} else if ("between".equals(condition.getOperator())) {
				moreThanOne = true;

				// hql.append(" eo.");
				hql.append(condition.getField());
				hql.append(" between ? and ? ");
			} else if ("in".equals(condition.getOperator())) {
				moreThanOne = true;

				// hql.append(" eo.");
				List<Object> source = Arrays.asList(condition.getValue());
				Integer size = 500;
				if (source != null && source.size() > 0) {
					if (source.size() > size) {
						int k = source.size() / size
								+ (source.size() % size > 0 ? 1 : 0);
						for (int j = 0; j < k; j++) {
							if (j == 0) {
								List sublist = source.subList(0, size > source
										.size() ? source.size() : size);

								hql.append(condition.getField());
								hql.append(" in (");
								int n = sublist == null ? 0 : sublist.size();
								for (int c = 0; c < n; c++) {
									if (c > 0) {
										hql.append(",");
									}
									hql.append("?");
								}
								hql.append(")");
							} else {
								List sublist = source.subList(j * size, (j + 1)
										* size > source.size() ? source.size()
										: (j + 1) * size);
								hql.append(" or ");
								hql.append(condition.getField());
								hql.append(" in (");
								int n = sublist == null ? 0 : sublist.size();
								for (int c = 0; c < n; c++) {
									if (c > 0) {
										hql.append(",");
									}
									hql.append("?");
								}
								hql.append(")");
							}
						}
					} else {
						hql.append(condition.getField());
						hql.append(" in (");
						int n = condition.getValue() == null ? 0 : condition
								.getValue().length;
						for (int j = 0; j < n; j++) {
							if (j > 0) {
								hql.append(",");
							}
							hql.append("?");
						}
						hql.append(")");
					}
				}
			} else if ("not in".equals(condition.getOperator())) {
				moreThanOne = true;

				// hql.append(" eo.");
				hql.append(condition.getField());
				hql.append(" not in (");
				int n = condition.getValue() == null ? 0
						: condition.getValue().length;
				for (int j = 0; j < n; j++) {
					if (j > 0) {
						hql.append(",");
					}
					hql.append("?");
				}
				hql.append(")");
			} else if ("is null".equals(condition.getOperator())) {
				moreThanOne = true;

				hql.append(condition.getField());
				hql.append(" is null");
			} else if ("is not null".equals(condition.getOperator())) {
				moreThanOne = true;

				hql.append(condition.getField());
				hql.append(" is not null");
			}
		}

		return hql.toString();
	}

	protected void setQueryCondition(Criteria criteria,
			QueryCondition queryCondition) {
		final Object value1 = queryCondition.getValue()[0];

		if ("=".equals(queryCondition.getOperator())) {
			criteria.add(Restrictions.eq(queryCondition.getField(), value1));
			return;
		} else if (">".equals(queryCondition.getOperator())) {
			criteria.add(Restrictions.gt(queryCondition.getField(), value1));
			return;
		} else if (">=".equals(queryCondition.getOperator())) {
			criteria.add(Restrictions.ge(queryCondition.getField(), value1));
			return;
		} else if ("<".equals(queryCondition.getOperator())) {
			criteria.add(Restrictions.lt(queryCondition.getField(), value1));
			return;
		} else if ("<=".equals(queryCondition.getOperator())) {
			criteria.add(Restrictions.le(queryCondition.getField(), value1));
			return;
		}
		if ("like".equals(queryCondition.getOperator())) {
			criteria.add(Restrictions.like(queryCondition.getField(), "%"
					+ value1 + "%"));
			return;
		}
		if ("between".equals(queryCondition.getOperator())) {
			final Object value2 = queryCondition.getValue()[1];

			criteria.add(Restrictions.between(queryCondition.getField(),
					value1, value2));
			return;
		}
		if ("not in".equals(queryCondition.getOperator())) {
			criteria.add(Restrictions.not(Restrictions.in(
					queryCondition.getField(), queryCondition.getValue())));
			return;
		}
		if ("in".equals(queryCondition.getOperator())) {
			List<Object> source = Arrays.asList(queryCondition.getValue());
			Integer size = 500;
			String field = queryCondition.getField();
			if (source != null && source.size() > 0) {
				if (source.size() > size) {
					int k = source.size() / size
							+ (source.size() % size > 0 ? 1 : 0);
					Criterion criterion = null;
					for (int i = 0; i < k; i++) {
						if (i == 0) {
							List sublist = source
									.subList(
											0,
											size > source.size() ? source
													.size() : size);
							criterion = Restrictions.in(field, sublist);
						} else {
							List sublist = source.subList(i * size, (i + 1)
									* size > source.size() ? source.size()
									: (i + 1) * size);
							criterion = Restrictions.or(criterion,
									Restrictions.in(field, sublist));
						}
					}
					criteria.add(criterion);
				} else {
					criteria.add(Restrictions.in(field, source));
				}
			}

			// criteria.add(Restrictions.in(queryCondition.getField(),
			// queryCondition.getValue()));
			return;
		}
		if ("is null".equals(queryCondition.getOperator())) {
			criteria.add(Restrictions.isNull(queryCondition.getField()));
			return;
		} else if ("is not null".equals(queryCondition.getOperator())) {
			criteria.add(Restrictions.isNotNull(queryCondition.getField()));
			return;
		}

		//
	}

	protected void setQueryConditions(Criteria criteria,
			List<QueryCondition> queryConditions) {
		if (Utils.isEmpty(queryConditions)) {
			return;
		}

		for (QueryCondition queryCondition : queryConditions) {
			setQueryCondition(criteria, queryCondition);
		}
	}

	protected void setQueryPage(Criteria criteria, QueryPage queryPage) {
		if (Utils.isNotNull(queryPage)) {
			if (queryPage.getPageNumber() == null
					|| queryPage.getPageNumber() < 1) {
				queryPage.setPageNumber(1);
			}

			int pageNum = queryPage.getPageNumber() - 1;

			criteria.setFirstResult(pageNum * queryPage.getPerPageSize());
			criteria.setMaxResults(queryPage.getPerPageSize());
		}
	}

	protected void setQueryPage(Query query, QueryPage queryPage) {
		if (Utils.isNotNull(queryPage)) {
			if (queryPage.getPageNumber() == null
					|| queryPage.getPageNumber() < 1) {
				queryPage.setPageNumber(1);
			}

			int pageNum = queryPage.getPageNumber() - 1;

			query.setFirstResult(pageNum * queryPage.getPerPageSize());
			query.setMaxResults(queryPage.getPerPageSize());
		}
	}

	/**
	 * 设置查询参数
	 * 
	 * @param query
	 * @param queryConditions
	 */
	protected void setParameter(Query query, QueryParameter queryConditions) {
		final List<QueryCondition> conditions = queryConditions
				.getQueryConditions();
		if (Utils.isEmpty(conditions)) {
			return;
		}

		int identity = 0;
		for (int i = 0; i < conditions.size(); i++) {

			QueryCondition condition = conditions.get(i);
			Object[] values = condition.getValue();
			Object value1 = values[0];
			Integer len = values.length;

			if (len > 1) {
				int t = 0;
				if (identity != 0) {
					t = identity;
				}
				for (int j = 0; j < values.length; j++) {
					if (value1 instanceof Integer) {
						query.setInteger(t++, (Integer) values[j]);
					} else if (value1 instanceof String) {
						query.setString(t++, (String) values[j]);
					} else if (value1 instanceof Date) {
						query.setTimestamp(t++, new Timestamp(
								((Date) values[j]).getTime()));
					} else if (value1 instanceof Timestamp) {
						query.setTimestamp(t++, (Timestamp) values[j]);
					}
				}
				identity = t;
			} else {
				if ("is null".equals(condition.getOperator())
						|| "is not null".equals(condition.getOperator())) {
					continue;
				}

				if (value1 instanceof Integer) {
					query.setInteger(identity++, (Integer) value1);
				} else if (value1 instanceof String) {
					if ("like".equals(condition.getOperator())) {
						query.setString(identity++, "%" + (String) value1 + "%");
					} else {
						query.setString(identity++, (String) value1);
					}
				} else if (value1 instanceof Date) {
					query.setDate(identity++, (Date) value1);
				}
			}
		}
	}

	@Override
	public void executeUpadteWithQuery(String hql) {
		executeUpdateWithQuery(hql, new Object[0]);
	}

	/**
	 * 执行HQL更新
	 * 
	 * @param objects
	 * @param hql
	 */
	@Override
	public void executeUpdateWithQuery(String hql, Object... objects) {
		Query query = this.getSession().createQuery(hql);
		if (objects != null && objects.length > 0) {
			for (int i = 0; i < objects.length; i++) {
				query.setParameter(i, objects[i]);
			}
		}
		query.executeUpdate();
	}

	/**
	 * 
	 * //TODO 执行SQL更新
	 * 
	 * @author yinlinsheng
	 * @param nativeSql
	 * @param values
	 */
	@Override
	public void executeUpdateWithNativeSql(String nativeSql, Object... values) {
		Session session = getSession();
		Query query = session.createSQLQuery(nativeSql).addEntity(entityClass);
		setParameters(query, values);
		query.executeUpdate();
	}

	/**
	 * 执行查询
	 * 
	 * @author yinlinsheng
	 * @param nativeSql
	 * @param objects
	 */
	@Override
	public List executeQuery(String nativeSql, Object... values) {
		Query query = this.getSession().createSQLQuery(nativeSql);
		setParameters(query, values);
		List li = query.list();
		return li;
	}

}