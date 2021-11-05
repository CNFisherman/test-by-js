package dao;

import java.util.List;

import model.Kemu;


public interface KemuDao  {
	
	
	
	public void insertBean(Kemu bean);
	
	public void deleteBean(Kemu bean);
	
	public void updateBean(Kemu bean);

	public Kemu selectBean(String where);
	
	public List<Kemu> selectBeanList(final int start, final int limit,final String where);
	
	public int selectBeanCount(final String where);
	
	
}
