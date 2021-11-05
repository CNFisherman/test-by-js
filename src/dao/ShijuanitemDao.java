package dao;

import java.util.List;

import model.Shijuanitem;


public interface ShijuanitemDao  {
	
	
	
	public void insertBean(Shijuanitem bean);
	
	public void deleteBean(Shijuanitem bean);
	
	public void updateBean(Shijuanitem bean);

	public Shijuanitem selectBean(String where);
	
	public List<Shijuanitem> selectBeanList(final int start, final int limit,final String where);
	
	public int selectBeanCount(final String where);
	
	
}
