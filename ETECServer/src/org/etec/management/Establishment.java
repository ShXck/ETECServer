package org.etec.management;

public class Establishment {
	
	private String type;
	private String category;
	private String name;
	private int id;
	
	public Establishment(String type, String name, String category, int id){
		this.type = type;
		this.name = name;
		this.id = id;
		this.category = category;
	}
	
	
	public Establishment(String type, String name, int id) {
		this(type,name,null,id);
	}
		
	
	public int id(){
		return this.id;
	}
	
	public String type(){
		return this.type;
	}
	
	public String name(){
		return this.name;
	}
	
	public String category() {
		return this.category;
	}
	
	@Override
	public boolean equals(Object obj){
		
		Establishment s = (Establishment)obj;
		
		if(name.equals(s.name()) && id == s.id()){
			return true;
		}
		return false;
	}

}
