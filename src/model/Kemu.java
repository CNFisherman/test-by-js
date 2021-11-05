package model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
//科目
@Entity
@Table(name="t_Kemu")
public class Kemu {

	@Id
	@GeneratedValue
	private int id;
	
	private String name;
	
	private int kemulock;
	
	
	public int getKemulock() {
		return kemulock;
	}

	public void setKemulock(int kemulock) {
		this.kemulock = kemulock;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	
	
	
	
}
