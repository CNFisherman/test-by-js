package model;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="t_Shijuan")
public class Shijuan {


	@Id
	@GeneratedValue
	private int id;
	
	private String juanming;//卷名
	
	@ManyToOne
	@JoinColumn(name="kemuid")
	private Kemu kemu;//科目
	
	@ManyToOne
	@JoinColumn(name="userid")
	private User user;//出卷人
	
	private double zongfen;//总分
	
	private Date createtime;
	
	private String kaoshishijian;//考试时间
	
	private String nandu;//难度
	
	private String zujuan;//组卷状态
	
	private int shijuanlock;

	public int getShijuanlock() {
		return shijuanlock;
	}

	public void setShijuanlock(int shijuanlock) {
		this.shijuanlock = shijuanlock;
	}

	public String getZujuan() {
		return zujuan;
	}

	public void setZujuan(String zujuan) {
		this.zujuan = zujuan;
	}

	public String getNandu() {
		return nandu;
	}

	public void setNandu(String nandu) {
		this.nandu = nandu;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getJuanming() {
		return juanming;
	}

	public void setJuanming(String juanming) {
		this.juanming = juanming;
	}

	

	public Kemu getKemu() {
		return kemu;
	}

	public void setKemu(Kemu kemu) {
		this.kemu = kemu;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public double getZongfen() {
		return zongfen;
	}

	public void setZongfen(double zongfen) {
		this.zongfen = zongfen;
	}

	

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getKaoshishijian() {
		return kaoshishijian;
	}

	public void setKaoshishijian(String kaoshishijian) {
		this.kaoshishijian = kaoshishijian;
	}


	
	

	


	

	
	
	
	
}
