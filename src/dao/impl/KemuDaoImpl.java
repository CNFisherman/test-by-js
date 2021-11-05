package dao.impl;

import java.sql.SQLException;
import java.util.List;

import model.Kemu;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import dao.KemuDao;









public class KemuDaoImpl extends HibernateDaoSupport implements  KemuDao{


	public void deleteBean(Kemu bean) {
		this.getHibernateTemplate().delete(bean);
		
	}

	public void insertBean(Kemu bean) {
		this.getHibernateTemplate().save(bean);
		
	}

	@SuppressWarnings("unchecked")
	public Kemu selectBean(String where) {
		List<Kemu> list = this.getHibernateTemplate().find("from Kemu " +where);
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}

	public int selectBeanCount(String where) {
		long count = (Long)this.getHibernateTemplate().find("select count(*) from Kemu "+where).get(0);
		return (int)count;
	}

	@SuppressWarnings("unchecked")
	public List<Kemu> selectBeanList(final int start,final int limit,final String where) {
		return (List<Kemu>)this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(final Session session)throws HibernateException, SQLException {				
				List<Kemu> list = session.createQuery("from Kemu "+where)
				.setFirstResult(start)
				.setMaxResults(limit)
				.list();
				return list;
			}
		});
	}

	public void updateBean(Kemu bean) {
		this.getHibernateTemplate().update(bean);
		
	}
	
	
}
