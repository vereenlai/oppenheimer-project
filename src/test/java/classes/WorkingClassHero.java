package classes;


public class WorkingClassHero {
	private String natId;
	private String name;
	private String reliefAmount;
	
	public WorkingClassHero(String natId, String name, String reliefAmount) {
		this.natId = natId;
		this.name = name;
		this.reliefAmount = reliefAmount;
	}
	public WorkingClassHero(String natId, String reliefAmount) {
		super();
		this.natId = natId;
		this.reliefAmount = reliefAmount;
	}
	public String getNatId() {
		return natId;
	}
	public void setNatId(String natId) {
		this.natId = natId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getReliefAmount() {
		return reliefAmount;
	}
	public void setReliefAmount(String reliefAmount) {
		this.reliefAmount = reliefAmount;
	}
}
