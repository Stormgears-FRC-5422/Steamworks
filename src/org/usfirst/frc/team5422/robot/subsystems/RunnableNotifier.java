package org.usfirst.frc.team5422.robot.subsystems;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class RunnableNotifier implements Runnable{
	
	private RunnableNotifier instance;
	
	private Notifier thread;
	
	private double period;
	
	private NetworkTable networkTable;
	
	protected void networkPublish(String string, double number){
		networkTable.putNumber(string, number);
	}
	
	private String networkKey;
	
	public RunnableNotifier(String NetworkKey, double periodS){
		networkTable = NetworkTable.getTable(networkKey);
		networkKey = NetworkKey;
		thread = new Notifier(this);
		period = periodS;
	}

	public void start(){
		thread.startPeriodic(period);
	}
	
	public void stop(){
		thread.stop();
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
