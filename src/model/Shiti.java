package model;


import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
//试题库
@Entity
@Table(name="t_Shiti")
public class Shiti {

	@Id
	@GeneratedValue
	private int id;
	
	@ManyToOne 
	@JoinColumn(name="kemuid")
	private Kemu kemu;//科目
	
	private String leixing;//试题类型
	
	private String nandu;//难度

	private String wenti;//问题
	
	private String daan;//答案
	
	private Date createtime;
	
	private String shizhidian;//知识点
	
	
	private String a;
	
	private String b;
	
	private String c;
	
	private String d;
	
	private int shitilock;


	public int getShitilock() {
		return shitilock;
	}

	public void setShitilock(int shitilock) {
		this.shitilock = shitilock;
	}

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}

	

	

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getShizhidian() {
		return shizhidian;
	}

	public void setShizhidian(String shizhidian) {
		this.shizhidian = shizhidian;
	}

	

	public String getLeixing() {
		return leixing;
	}

	public void setLeixing(String leixing) {
		this.leixing = leixing;
	}

	
	

	public String getWenti() {
		return wenti;
	}

	public void setWenti(String wenti) {
		this.wenti = wenti;
	}

	public String getDaan() {
		return daan;
	}

	public void setDaan(String daan) {
		this.daan = daan;
	}

	public Kemu getKemu() {
		return kemu;
	}

	public void setKemu(Kemu kemu) {
		this.kemu = kemu;
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

	


	
	

	
	
	
	
}
