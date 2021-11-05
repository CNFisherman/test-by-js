package dao;

import java.util.List;

import model.Shijuan;


public interface ShijuanDao  {
	
	
	
	public void insertBean(Shijuan bean);
	
	public void deleteBean(Shijuan bean);
	
	public void updateBean(Shijuan bean);

	public Shijuan selectBean(String where);
	
	public List<Shijuan> selectBeanList(final int start, final int limit,final String where);
	
	public int selectBeanCount(final String where);
	
	
}
