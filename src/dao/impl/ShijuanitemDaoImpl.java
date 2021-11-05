package dao.impl;

import java.sql.SQLException;
import java.util.List;

import model.Shijuanitem;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import dao.ShijuanitemDao;









public class ShijuanitemDaoImpl extends HibernateDaoSupport implements  ShijuanitemDao{


	public void deleteBean(Shijuanitem bean) {
		this.getHibernateTemplate().delete(bean);
		
	}

	public void insertBean(Shijuanitem bean) {
		this.getHibernateTemplate().save(bean);
		
	}

	@SuppressWarnings("unchecked")
	public Shijuanitem selectBean(String where) {
		List<Shijuanitem> list = this.getHibernateTemplate().find("from Shijuanitem " +where);
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}

	public int selectBeanCount(String where) {
		long count = (Long)this.getHibernateTemplate().find("select count(*) from Shijuanitem "+where).get(0);
		return (int)count;
	}

	@SuppressWarnings("unchecked")
	public List<Shijuanitem> selectBeanList(final int start,final int limit,final String where) {
		return (List<Shijuanitem>)this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(final Session session)throws HibernateException, SQLException {				
				List<Shijuanitem> list = session.createQuery("from Shijuanitem "+where)
				.setFirstResult(start)
				.setMaxResults(limit)
				.list();
				return list;
			}
		});
	}

	public void updateBean(Shijuanitem bean) {
		this.getHibernateTemplate().update(bean);
		
	}
	
	
}
