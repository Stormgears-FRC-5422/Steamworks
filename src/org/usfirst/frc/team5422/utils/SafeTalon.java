package org.usfirst.frc.team5422.utils;

import com.ctre.CANTalon;
import com.ctre.GadgeteerUartClient;
import edu.wpi.first.wpilibj.tables.ITable;

import edu.wpi.first.wpilibj.PIDSourceType;

public class SafeTalon extends CANTalon {

	public SafeTalon(int deviceNumber) {
		super(deviceNumber);
	}

	public SafeTalon(int deviceNumber, int controlPeriodMs) {
		super(deviceNumber, controlPeriodMs);
	}

	public SafeTalon(int deviceNumber, int controlPeriodMs, int enablePeriodMs) {
		super(deviceNumber, controlPeriodMs, enablePeriodMs);
	}
	
    public synchronized void pidWrite(double output) { super.pidWrite(output); }

    public synchronized void setPIDSourceType(PIDSourceType pidSource) { super.setPIDSourceType(pidSource); }

    public synchronized PIDSourceType getPIDSourceType() { return super.getPIDSourceType(); }

    public synchronized double pidGet() { return super.pidGet(); }

    public synchronized void delete() { super.delete(); }

    public synchronized void set(double outputValue) { super.set(outputValue); }

    public synchronized void setInverted(boolean isInverted) { super.setInverted(isInverted); }

    public synchronized boolean getInverted() { return super.getInverted(); }

    public synchronized void reset() { super.reset(); }

    public synchronized boolean isEnabled() { return super.isEnabled(); }

    public synchronized double getError() { return super.getError(); }

    public synchronized void setSetpoint(double setpoint) { super.setSetpoint(setpoint); }

    public synchronized void reverseSensor(boolean flip) { super.reverseSensor(flip); }

    public synchronized void reverseOutput(boolean flip) { super.reverseOutput(flip); }

    public synchronized double get() { return super.get(); }

    public synchronized int getEncPosition() { return super.getEncPosition(); }

    public synchronized void setEncPosition(int newPosition) { super.setEncPosition(newPosition); }

    public synchronized int getEncVelocity() { return super.getEncVelocity(); }

    public synchronized int getPulseWidthPosition() { return super.getPulseWidthPosition(); }

    public synchronized void setPulseWidthPosition(int newPosition) { super.setPulseWidthPosition(newPosition); }

    public synchronized int getPulseWidthVelocity() { return super.getPulseWidthVelocity(); }

    public synchronized int getPulseWidthRiseToFallUs() { return super.getPulseWidthRiseToFallUs(); }

    public synchronized int getPulseWidthRiseToRiseUs() { return super.getPulseWidthRiseToRiseUs(); }

    public synchronized FeedbackDeviceStatus isSensorPresent(FeedbackDevice feedbackDevice) { return super.isSensorPresent(feedbackDevice); }

    public synchronized int getNumberOfQuadIdxRises() { return super.getNumberOfQuadIdxRises(); }

    public synchronized int getPinStateQuadA() { return super.getPinStateQuadA(); }

    public synchronized int getPinStateQuadB() { return super.getPinStateQuadB(); }

    public synchronized int getPinStateQuadIdx() { return super.getPinStateQuadIdx(); }

    public synchronized void setAnalogPosition(int newPosition) { super.setAnalogPosition(newPosition); }

    public synchronized int getAnalogInPosition() { return super.getAnalogInPosition(); }

    public synchronized int getAnalogInRaw() { return super.getAnalogInRaw(); }

    public synchronized int getAnalogInVelocity() { return super.getAnalogInVelocity(); }

    public synchronized int getClosedLoopError() { return super.getClosedLoopError(); }

    public synchronized void setAllowableClosedLoopErr(int allowableCloseLoopError) { super.setAllowableClosedLoopErr(allowableCloseLoopError); }

    public synchronized boolean isFwdLimitSwitchClosed() { return super.isFwdLimitSwitchClosed(); }

    public synchronized boolean isRevLimitSwitchClosed() { return super.isRevLimitSwitchClosed(); }

    public synchronized boolean isZeroSensorPosOnIndexEnabled() { return super.isZeroSensorPosOnIndexEnabled(); }

    public synchronized boolean isZeroSensorPosOnRevLimitEnabled() { return super.isZeroSensorPosOnRevLimitEnabled(); }

    public synchronized boolean isZeroSensorPosOnFwdLimitEnabled() { return super.isZeroSensorPosOnFwdLimitEnabled(); }

    public synchronized boolean getBrakeEnableDuringNeutral() { return super.getBrakeEnableDuringNeutral(); }

    public synchronized void configEncoderCodesPerRev(int codesPerRev) { super.configEncoderCodesPerRev(codesPerRev); }

    public synchronized void configPotentiometerTurns(int turns) { super.configPotentiometerTurns(turns); }

    public synchronized double getTemperature() { return super.getTemperature(); }

    public synchronized double getOutputCurrent() { return super.getOutputCurrent(); }

    public synchronized double getOutputVoltage() { return super.getOutputVoltage(); }

    public synchronized double getBusVoltage() { return super.getBusVoltage(); }

    public synchronized double getPosition() { return super.getPosition(); }

    public synchronized void setPosition(double pos) { super.setPosition(pos); }

    public synchronized double getSpeed() { return super.getSpeed(); }

    public synchronized TalonControlMode getControlMode() { return super.getControlMode(); }

    public synchronized void setControlMode(int mode) { super.setControlMode(mode); }

    public synchronized void changeControlMode(TalonControlMode controlMode) { super.changeControlMode(controlMode); }

    public synchronized void setFeedbackDevice(FeedbackDevice device) { super.setFeedbackDevice(device); }

    public synchronized void setStatusFrameRateMs(StatusFrameRate stateFrame, int periodMs) { super.setStatusFrameRateMs(stateFrame, periodMs); }

    public synchronized void enableControl() { super.enableControl(); }

    public synchronized void enable() { super.enable(); }

    public synchronized void disableControl() { super.disableControl(); }

    public synchronized boolean isControlEnabled() { return super.isControlEnabled(); }

    public synchronized double getP() { return super.getP(); }

    public synchronized double getI() { return super.getI(); }

    public synchronized double getD() { return super.getD(); }

    public synchronized double getF() { return super.getF(); }

    public synchronized double getIZone() { return super.getIZone(); }

    public synchronized double getCloseLoopRampRate() { return super.getCloseLoopRampRate(); }

    public synchronized long GetFirmwareVersion() { return super.GetFirmwareVersion(); }

    public synchronized long GetIaccum() { return super.GetIaccum(); }

    public synchronized void setP(double p) { super.setP(p); }

    public synchronized void setI(double i) { super.setI(i); }

    public synchronized void setD(double d) { super.setD(d); }

    public synchronized void setF(double f) { super.setF(f); }

    public synchronized void setIZone(int izone) { super.setIZone(izone); }

    public synchronized void setCloseLoopRampRate(double rampRate) { super.setCloseLoopRampRate(rampRate); }

    public synchronized void setVoltageRampRate(double rampRate) { super.setVoltageRampRate(rampRate); }

    public synchronized void setVoltageCompensationRampRate(double rampRate) { super.setVoltageCompensationRampRate(rampRate); }

    public synchronized void ClearIaccum() { super.ClearIaccum(); }

    public synchronized void setPID(double p, double i, double d, double f, int izone, double closeLoopRampRate, int profile) { super.setPID(p, i, d, f, izone, closeLoopRampRate, profile); }

    public synchronized void setPID(double p, double i, double d) { super.setPID(p, i, d); }

    public synchronized double getSetpoint() { return super.getSetpoint(); }

    public synchronized void setProfile(int profile) { super.setProfile(profile); }

    @SuppressWarnings( "deprecation" )
    public synchronized void stopMotor() { super.stopMotor(); } 

    public synchronized void disable() { super.disable(); }

    public synchronized int getDeviceID() { return super.getDeviceID(); }

    public synchronized void clearIAccum() { super.clearIAccum(); }

    public synchronized void setForwardSoftLimit(double forwardLimit) { super.setForwardSoftLimit(forwardLimit); }

    public synchronized int getForwardSoftLimit() { return super.getForwardSoftLimit(); }

    public synchronized void enableForwardSoftLimit(boolean enable) { super.enableForwardSoftLimit(enable); }

    public synchronized boolean isForwardSoftLimitEnabled() { return super.isForwardSoftLimitEnabled(); }

    public synchronized void setReverseSoftLimit(double reverseLimit) { super.setReverseSoftLimit(reverseLimit); }

    public synchronized int getReverseSoftLimit() { return super.getReverseSoftLimit(); }

    public synchronized void enableReverseSoftLimit(boolean enable) { super.enableReverseSoftLimit(enable); }

    public synchronized boolean isReverseSoftLimitEnabled() { return super.isReverseSoftLimitEnabled(); }

    public synchronized void configMaxOutputVoltage(double voltage) { super.configMaxOutputVoltage(voltage); }

    public synchronized void configPeakOutputVoltage(double forwardVoltage, double reverseVoltage) { super.configPeakOutputVoltage(forwardVoltage, reverseVoltage); }

    public synchronized void configNominalOutputVoltage(double forwardVoltage, double reverseVoltage) { super.configNominalOutputVoltage(forwardVoltage, reverseVoltage); }

    public synchronized void clearStickyFaults() { super.clearStickyFaults(); }

    public synchronized void enableLimitSwitch(boolean forward, boolean reverse) { super.enableLimitSwitch(forward, reverse); }

    public synchronized void ConfigFwdLimitSwitchNormallyOpen(boolean normallyOpen) { super.ConfigFwdLimitSwitchNormallyOpen(normallyOpen); }

    public synchronized void ConfigRevLimitSwitchNormallyOpen(boolean normallyOpen) { super.ConfigRevLimitSwitchNormallyOpen(normallyOpen); }

    public synchronized void enableBrakeMode(boolean brake) { super.enableBrakeMode(brake); }

    public synchronized int getFaultOverTemp() { return super.getFaultOverTemp(); }

    public synchronized int getFaultUnderVoltage() { return super.getFaultUnderVoltage(); }

    public synchronized int getFaultForLim() { return super.getFaultForLim(); }

    public synchronized int getFaultRevLim() { return super.getFaultRevLim(); }

    public synchronized int getFaultHardwareFailure() { return super.getFaultHardwareFailure(); }

    public synchronized int getFaultForSoftLim() { return super.getFaultForSoftLim(); }

    public synchronized int getFaultRevSoftLim() { return super.getFaultRevSoftLim(); }

    public synchronized int getStickyFaultOverTemp() { return super.getStickyFaultOverTemp(); }

    public synchronized int getStickyFaultUnderVoltage() { return super.getStickyFaultUnderVoltage(); }

    public synchronized int getStickyFaultForLim() { return super.getStickyFaultForLim(); }

    public synchronized int getStickyFaultRevLim() { return super.getStickyFaultRevLim(); }

    public synchronized int getStickyFaultForSoftLim() { return super.getStickyFaultForSoftLim(); }

    public synchronized int getStickyFaultRevSoftLim() { return super.getStickyFaultRevSoftLim(); }

    public synchronized void enableZeroSensorPositionOnIndex(boolean enable, boolean risingEdge) { super.enableZeroSensorPositionOnIndex(enable, risingEdge); }

    public synchronized void enableZeroSensorPositionOnForwardLimit(boolean enable) { super.enableZeroSensorPositionOnForwardLimit(enable); }

    public synchronized void enableZeroSensorPositionOnReverseLimit(boolean enable) { super.enableZeroSensorPositionOnReverseLimit(enable); }

    public synchronized void changeMotionControlFramePeriod(int periodMs) { super.changeMotionControlFramePeriod(periodMs); }

    public synchronized void clearMotionProfileTrajectories() { super.clearMotionProfileTrajectories(); }

    public synchronized int getMotionProfileTopLevelBufferCount() { return super.getMotionProfileTopLevelBufferCount(); }

    public synchronized boolean pushMotionProfileTrajectory(TrajectoryPoint trajPt) { return super.pushMotionProfileTrajectory(trajPt); }

    public synchronized boolean isMotionProfileTopLevelBufferFull() { return super.isMotionProfileTopLevelBufferFull(); }

    public synchronized void processMotionProfileBuffer() { super.processMotionProfileBuffer(); }

    public synchronized void getMotionProfileStatus(MotionProfileStatus motionProfileStatus) { super.getMotionProfileStatus(motionProfileStatus); }

    public synchronized void clearMotionProfileHasUnderrun() { super.clearMotionProfileHasUnderrun(); }

    public synchronized void setMotionMagicCruiseVelocity(double motMagicCruiseVeloc) { super.setMotionMagicCruiseVelocity(motMagicCruiseVeloc); }

    public synchronized void setMotionMagicAcceleration(double motMagicAccel) { super.setMotionMagicAcceleration(motMagicAccel); }

    public synchronized double getMotionMagicCruiseVelocity() { return super.getMotionMagicCruiseVelocity(); }

    public synchronized double getMotionMagicAcceleration() { return super.getMotionMagicAcceleration(); }

    public synchronized void setCurrentLimit(int amps) { super.setCurrentLimit(amps); }

    public synchronized void EnableCurrentLimit(boolean enable) { super.EnableCurrentLimit(enable); }

    public synchronized int GetGadgeteerStatus(GadgeteerUartClient.GadgeteerUartStatus status) { return super.GetGadgeteerStatus(status); }

    public synchronized String getLastError() { return super.getLastError(); }

    public synchronized void setExpiration(double timeout) { super.setExpiration(timeout); }

    public synchronized double getExpiration() { return super.getExpiration(); }

    public synchronized boolean isAlive() { return super.isAlive(); }

    public synchronized boolean isSafetyEnabled() { return super.isSafetyEnabled(); }

    public synchronized void setSafetyEnabled(boolean enabled) { super.setSafetyEnabled(enabled); }

    public synchronized String getDescription() { return super.getDescription(); }

    public synchronized void initTable(ITable subtable) { super.initTable(subtable); }

    public synchronized void updateTable() { super.updateTable(); }

    public synchronized ITable getTable() { return super.getTable(); }

    public synchronized void startLiveWindowMode() { super.startLiveWindowMode(); }

    public synchronized void stopLiveWindowMode() { super.stopLiveWindowMode(); }
    
}
