package org.usfirst.frc.team5422.utils;

import org.usfirst.frc.team5422.robot.Robot;

import edu.wpi.first.wpilibj.Notifier;

public class RegisteredNotifier extends Notifier {
	
	private String name;
	
	public RegisteredNotifier(Runnable run) {
		super(run);
		synchronized(Robot.notifierRegistry) {
			Robot.notifierRegistry.add(this);
		}

		this.name = "Unnamed";
		
		if(run==null){
			System.out.println(name + " IS NULL");
		}else{
			System.out.println(name + " IS NOT NULL");
		}
	}

	public RegisteredNotifier(Runnable run, String name) {
		super(run);
		synchronized(Robot.notifierRegistry) {
			Robot.notifierRegistry.add(this);
		}

		this.name = name;
		
		if(run==null){
			System.out.println(name + " IS NULL");
		}else{
			System.out.println(name + " IS NOT NULL");
		}
		
	}
	
	public String getName(){
		return name;
	} 

}
