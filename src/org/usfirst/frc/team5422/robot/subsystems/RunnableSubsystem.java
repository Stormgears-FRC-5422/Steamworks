package org.usfirst.frc.team5422.robot.subsystems;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class RunnableSubsystem implements Runnable {
	
	private RunnableSubsystem instance;
	
	private NetworkTable networkTable;
	
	public RunnableSubsystem(String networkKey){
		networkTable = NetworkTable.getTable(networkKey);
		this.networkKey = networkKey;
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
	
	RunnableSubsystem getInstance() throws Exception{
		if(instance==null){
			throw new Exception("RunnableSubsystem not initialized\nPlease Initialize in child class");
		}
		return instance;
	}

	@Override
	public void run() {
		//TODO::This should be overwritten in the child class
		//child classes must post to NetworkTables here
	}
}
