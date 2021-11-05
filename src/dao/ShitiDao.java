package dao;

import java.util.List;

import model.Shiti;


public interface ShitiDao  {
	
	
	
	public void insertBean(Shiti bean);
	
	public void deleteBean(Shiti bean);
	
	public void updateBean(Shiti bean);

	public Shiti selectBean(String where);
	
	public List<Shiti> selectBeanList(final int start, final int limit,final String where);
	
	public int selectBeanCount(final String where);
	
	
}
