package model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="t_ShijuanItem")
public class Shijuanitem {

	@Id
	@GeneratedValue
	private int id;
	
	@ManyToOne 
	@JoinColumn(name="shijuanid")
	private Shijuan shijuan;//关联的试卷
	
	@ManyToOne 
	@JoinColumn(name="shitiid")
	private Shiti shiti;//关联的试题
	
	private double fenzhi;//分值
	
	private int shijuanitemlock;

	public int getShijuanitemlock() {
		return shijuanitemlock;
	}

	public void setShijuanitemlock(int shijuanitemlock) {
		this.shijuanitemlock = shijuanitemlock;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Shijuan getShijuan() {
		return shijuan;
	}

	public void setShijuan(Shijuan shijuan) {
		this.shijuan = shijuan;
	}

	public Shiti getShiti() {
		return shiti;
	}

	public void setShiti(Shiti shiti) {
		this.shiti = shiti;
	}

	public double getFenzhi() {
		return fenzhi;
	}

	public void setFenzhi(double fenzhi) {
		this.fenzhi = fenzhi;
	}
	
	

	
	

	

	

	
	

	

	

	
	
	
	
}
