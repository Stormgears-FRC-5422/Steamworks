package org.usfirst.frc.team5422.robot.subsystems;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class RunnableNotifier implements Runnable{
	
	private RunnableNotifier instance;
	
	private double period;
	
	private NetworkTable networkTable;
	
	public RunnableNotifier(String networkKey, double periodS){
		networkTable = NetworkTable.getTable(networkKey);
		this.networkKey = networkKey;
		period = periodS;
	}
	
	protected void networkPublish(String string, double number){
		networkTable.putNumber(string, number);
	}
	
	protected void networkPublish(String string, String value){
		networkTable.putString(string, value);
	}
	
	private String networkKey;
	
	public String getNetworkKey(){
		return this.networkKey;
	}
	
	RunnableNotifier getInstance() throws Exception{
		if(instance==null){
			throw new Exception("RunnableNotifier not initialized\nPlease Initialize in SensorManager.java");
		}
		return instance;
	}

	@Override
	public void run() {
		//TODO::This should be overwritten in the child class
		//child classes must post to NetworkTables here
	}
}
