package ps.emperor.easy_water.utils;

import org.xutils.x;

public class BlueDevice {
	
	private String name;
	private String address;
	public BlueDevice() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BlueDevice(String name, String address) {
		super();
		this.name = name;
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "name=" + name + ", address=" + address +"\n";
	}
	
	
	
	

}
