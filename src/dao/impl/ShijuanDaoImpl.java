package dao.impl;

import java.sql.SQLException;
import java.util.List;

import model.Shijuan;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import dao.ShijuanDao;









public class ShijuanDaoImpl extends HibernateDaoSupport implements  ShijuanDao{


	public void deleteBean(Shijuan bean) {
		this.getHibernateTemplate().delete(bean);
		
	}

	public void insertBean(Shijuan bean) {
		this.getHibernateTemplate().save(bean);
		
	}

	@SuppressWarnings("unchecked")
	public Shijuan selectBean(String where) {
		List<Shijuan> list = this.getHibernateTemplate().find("from Shijuan " +where);
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}

	public int selectBeanCount(String where) {
		long count = (Long)this.getHibernateTemplate().find("select count(*) from Shijuan "+where).get(0);
		return (int)count;
	}

	@SuppressWarnings("unchecked")
	public List<Shijuan> selectBeanList(final int start,final int limit,final String where) {
		return (List<Shijuan>)this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(final Session session)throws HibernateException, SQLException {				
				List<Shijuan> list = session.createQuery("from Shijuan "+where)
				.setFirstResult(start)
				.setMaxResults(limit)
				.list();
				return list;
			}
		});
	}

	public void updateBean(Shijuan bean) {
		this.getHibernateTemplate().update(bean);
		
	}
	
	
}
