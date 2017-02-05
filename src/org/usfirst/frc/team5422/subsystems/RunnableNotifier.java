package org.usfirst.frc.team5422.subsystems;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class RunnableNotifier implements Runnable{
	
	private RunnableNotifier instance;
	
	private Notifier thread;
	
	private double period;
	
	protected NetworkTable networkTable;
	
	public RunnableNotifier(String networkKey, double periodS){
		networkTable = NetworkTable.getTable(networkKey);
		thread = new Notifier(this);
		period = periodS;
	}

	public void start(){
		thread.startPeriodic(period);
	}
	
	public void stop(){
		thread.stop();
	}
	
	public RunnableNotifier getInstance() throws Exception{
		if(instance==null){
			throw new Exception("RunnableNotifier not initialized");
		}
		return instance;
	}

	@Override
	public void run() {
		//TODO::This should be overwritten in the child class
		//child classes must post to NetworkTables here
	}
}
