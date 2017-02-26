package org.usfirst.frc.team5422.utils;

import org.usfirst.frc.team5422.robot.Robot;

import edu.wpi.first.wpilibj.Notifier;

public class RegisteredNotifier extends Notifier {

	public RegisteredNotifier(Runnable run) {
		super(run);
		synchronized(Robot.notifierRegistry) {
			Robot.notifierRegistry.add(this);
		}
	}
}
