package org.usfirst.frc.team5422.utils;
package org.stormgears.StormUtils;

import com.ctre.CANTalon;
//import com.ctre.CanTalonJNI;
import com.ctre.GadgeteerUartClient;
import edu.wpi.first.wpilibj.tables.ITable;

import edu.wpi.first.wpilibj.PIDSourceType;

public class SafeTalon extends CANTalon {
	public final static Object lock = new Object();

	public SafeTalon(int deviceNumber) {
		super(deviceNumber);
	}

	public SafeTalon(int deviceNumber, int controlPeriodMs) {
		super(deviceNumber, controlPeriodMs);
	}

	public SafeTalon(int deviceNumber, int controlPeriodMs, int enablePeriodMs) {
		super(deviceNumber, controlPeriodMs, enablePeriodMs);
	}

	@Override
	public void pidWrite(double output) {
		synchronized (SafeTalon.lock) {
			super.pidWrite(output);
		}
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		synchronized (SafeTalon.lock) {
			super.setPIDSourceType(pidSource);
		}
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		synchronized (SafeTalon.lock) {
			return super.getPIDSourceType();
		}
	}

	@Override
	public double pidGet() {
		synchronized (SafeTalon.lock) {
			return super.pidGet();
		}
	}

	@Override
	public void delete() {
		synchronized (SafeTalon.lock) {
			super.delete();
		}
	}

	@Override
	public void set(double outputValue) {
		synchronized (SafeTalon.lock) {
			super.set(outputValue);
		}
	}

	@Override
	public void setInverted(boolean isInverted) {
		synchronized (SafeTalon.lock) {
			super.setInverted(isInverted);
		}
	}

	@Override
	public boolean getInverted() {
		synchronized (SafeTalon.lock) {
			return super.getInverted();
		}
	}

	@Override
	public void reset() {
		synchronized (SafeTalon.lock) {
			super.reset();
		}
	}

	@Override
	public boolean isEnabled() {
		synchronized (SafeTalon.lock) {
			return super.isEnabled();
		}
	}

	@Override
	public double getError() {
		synchronized (SafeTalon.lock) {
			return super.getError();
		}
	}

	@Override
	public void setSetpoint(double setpoint) {
		synchronized (SafeTalon.lock) {
			super.setSetpoint(setpoint);
		}
	}

	@Override
	public void reverseSensor(boolean flip) {
		synchronized (SafeTalon.lock) {
			super.reverseSensor(flip);
		}
	}

	@Override
	public void reverseOutput(boolean flip) {
		synchronized (SafeTalon.lock) {
			super.reverseOutput(flip);
		}
	}

	@Override
	public double get() {
		synchronized (SafeTalon.lock) {
			return super.get();
		}
	}

	@Override
	public int getEncPosition() {
		synchronized (SafeTalon.lock) {
			return super.getEncPosition();
		}
	}

	@Override
	public void setEncPosition(int newPosition) {
		synchronized (SafeTalon.lock) {
			super.setEncPosition(newPosition);
		}
	}

	@Override
	public int getEncVelocity() {
		synchronized (SafeTalon.lock) {
			return super.getEncVelocity();
		}
	}

	@Override
	public int getPulseWidthPosition() {
		synchronized (SafeTalon.lock) {
			return super.getPulseWidthPosition();
		}
	}

	@Override
	public void setPulseWidthPosition(int newPosition) {
		synchronized (SafeTalon.lock) {
			super.setPulseWidthPosition(newPosition);
		}
	}

	@Override
	public int getPulseWidthVelocity() {
		synchronized (SafeTalon.lock) {
			return super.getPulseWidthVelocity();
		}
	}

	@Override
	public int getPulseWidthRiseToFallUs() {
		synchronized (SafeTalon.lock) {
			return super.getPulseWidthRiseToFallUs();
		}
	}

	@Override
	public int getPulseWidthRiseToRiseUs() {
		synchronized (SafeTalon.lock) {
			return super.getPulseWidthRiseToRiseUs();
		}
	}

	@Override
	public FeedbackDeviceStatus isSensorPresent(FeedbackDevice feedbackDevice) {
		synchronized (SafeTalon.lock) {
			return super.isSensorPresent(feedbackDevice);
		}
	}

	@Override
	public int getNumberOfQuadIdxRises() {
		synchronized (SafeTalon.lock) {
			return super.getNumberOfQuadIdxRises();
		}
	}

	@Override
	public int getPinStateQuadA() {
		synchronized (SafeTalon.lock) {
			return super.getPinStateQuadA();
		}
	}

	@Override
	public int getPinStateQuadB() {
		synchronized (SafeTalon.lock) {
			return super.getPinStateQuadB();
		}
	}

	@Override
	public int getPinStateQuadIdx() {
		synchronized (SafeTalon.lock) {
			return super.getPinStateQuadIdx();
		}
	}

	@Override
	public void setAnalogPosition(int newPosition) {
		synchronized (SafeTalon.lock) {
			super.setAnalogPosition(newPosition);
		}
	}

	@Override
	public int getAnalogInPosition() {
		synchronized (SafeTalon.lock) {
			return super.getAnalogInPosition();
		}
	}

	@Override
	public int getAnalogInRaw() {
		synchronized (SafeTalon.lock) {
			return super.getAnalogInRaw();
		}
	}

	@Override
	public int getAnalogInVelocity() {
		synchronized (SafeTalon.lock) {
			return super.getAnalogInVelocity();
		}
	}

	@Override
	public int getClosedLoopError() {
		synchronized (SafeTalon.lock) {
			return super.getClosedLoopError();
		}
	}

	@Override
	public void setAllowableClosedLoopErr(int allowableCloseLoopError) {
		synchronized (SafeTalon.lock) {
			super.setAllowableClosedLoopErr(allowableCloseLoopError);
		}
	}

	@Override
	public boolean isFwdLimitSwitchClosed() {
		synchronized (SafeTalon.lock) {
			return super.isFwdLimitSwitchClosed();
		}
	}

	@Override
	public boolean isRevLimitSwitchClosed() {
		synchronized (SafeTalon.lock) {
			return super.isRevLimitSwitchClosed();
		}
	}

	@Override
	public boolean isZeroSensorPosOnIndexEnabled() {
		synchronized (SafeTalon.lock) {
			return super.isZeroSensorPosOnIndexEnabled();
		}
	}

	@Override
	public boolean isZeroSensorPosOnRevLimitEnabled() {
		synchronized (SafeTalon.lock) {
			return super.isZeroSensorPosOnRevLimitEnabled();
		}
	}

	@Override
	public boolean isZeroSensorPosOnFwdLimitEnabled() {
		synchronized (SafeTalon.lock) {
			return super.isZeroSensorPosOnFwdLimitEnabled();
		}
	}

	@Override
	public boolean getBrakeEnableDuringNeutral() {
		synchronized (SafeTalon.lock) {
			return super.getBrakeEnableDuringNeutral();
		}
	}

	@Override
	public void configEncoderCodesPerRev(int codesPerRev) {
		synchronized (SafeTalon.lock) {
			super.configEncoderCodesPerRev(codesPerRev);
		}
	}

	@Override
	public void configPotentiometerTurns(int turns) {
		synchronized (SafeTalon.lock) {
			super.configPotentiometerTurns(turns);
		}
	}

	@Override
	public double getTemperature() {
		synchronized (SafeTalon.lock) {
			return super.getTemperature();
		}
	}

	@Override
	public double getOutputCurrent() {
		synchronized (SafeTalon.lock) {
			return super.getOutputCurrent();
		}
	}

	@Override
	public double getOutputVoltage() {
		synchronized (SafeTalon.lock) {
			return super.getOutputVoltage();
		}
	}

	@Override
	public double getBusVoltage() {
		synchronized (SafeTalon.lock) {
			return super.getBusVoltage();
		}
	}

	@Override
	public double getPosition() {
		synchronized (SafeTalon.lock) {
			return super.getPosition();
		}
	}

	@Override
	public void setPosition(double pos) {
		synchronized (SafeTalon.lock) {
			super.setPosition(pos);
		}
	}

	@Override
	public double getSpeed() {
		synchronized (SafeTalon.lock) {
			return super.getSpeed();
		}
	}

	@Override
	public TalonControlMode getControlMode() {
		synchronized (SafeTalon.lock) {
			return super.getControlMode();
		}
	}

	@Override
	public void setControlMode(int mode) {
		synchronized (SafeTalon.lock) {
			super.setControlMode(mode);
		}
	}

	@Override
	public void changeControlMode(TalonControlMode controlMode) {
		synchronized (SafeTalon.lock) {
			super.changeControlMode(controlMode);
		}
	}

	@Override
	public void setFeedbackDevice(FeedbackDevice device) {
		synchronized (SafeTalon.lock) {
			super.setFeedbackDevice(device);
		}
	}

	@Override
	public void setStatusFrameRateMs(StatusFrameRate stateFrame, int periodMs) {
		synchronized (SafeTalon.lock) {
			super.setStatusFrameRateMs(stateFrame, periodMs);
		}
	}

	@Override
	public void enableControl() {
		synchronized (SafeTalon.lock) {
			super.enableControl();
		}
	}

	@Override
	public void enable() {
		synchronized (SafeTalon.lock) {
			super.enable();
		}
	}

	@Override
	public void disableControl() {
		synchronized (SafeTalon.lock) {
			super.disableControl();
		}
	}

	@Override
	public boolean isControlEnabled() {
		synchronized (SafeTalon.lock) {
			return super.isControlEnabled();
		}
	}

	@Override
	public double getP() {
		synchronized (SafeTalon.lock) {
			return super.getP();
		}
	}

	@Override
	public double getI() {
		synchronized (SafeTalon.lock) {
			return super.getI();
		}
	}

	@Override
	public double getD() {
		synchronized (SafeTalon.lock) {
			return super.getD();
		}
	}

	@Override
	public double getF() {
		synchronized (SafeTalon.lock) {
			return super.getF();
		}
	}

	@Override
	public double getIZone() {
		synchronized (SafeTalon.lock) {
			return super.getIZone();
		}
	}

	@Override
	public double getCloseLoopRampRate() {
		synchronized (SafeTalon.lock) {
			return super.getCloseLoopRampRate();
		}
	}

	@Override
	public long GetFirmwareVersion() {
		synchronized (SafeTalon.lock) {
			return super.GetFirmwareVersion();
		}
	}

	@Override
	public long GetIaccum() {
		synchronized (SafeTalon.lock) {
			return super.GetIaccum();
		}
	}

	@Override
	public void setP(double p) {
		synchronized (SafeTalon.lock) {
			super.setP(p);
		}
	}

	@Override
	public void setI(double i) {
		synchronized (SafeTalon.lock) {
			super.setI(i);
		}
	}

	@Override
	public void setD(double d) {
		synchronized (SafeTalon.lock) {
			super.setD(d);
		}
	}

	@Override
	public void setF(double f) {
		synchronized (SafeTalon.lock) {
			super.setF(f);
		}
	}

	@Override
	public void setIZone(int izone) {
		synchronized (SafeTalon.lock) {
			super.setIZone(izone);
		}
	}

	@Override
	public void setCloseLoopRampRate(double rampRate) {
		synchronized (SafeTalon.lock) {
			super.setCloseLoopRampRate(rampRate);
		}
	}

	@Override
	public void setVoltageRampRate(double rampRate) {
		synchronized (SafeTalon.lock) {
			super.setVoltageRampRate(rampRate);
		}
	}

	@Override
	public void setVoltageCompensationRampRate(double rampRate) {
		synchronized (SafeTalon.lock) {
			super.setVoltageCompensationRampRate(rampRate);
		}
	}

	@Override
	public void ClearIaccum() {
		synchronized (SafeTalon.lock) {
			super.ClearIaccum();
		}
	}

	@Override
	public void setPID(double p, double i, double d, double f, int izone, double closeLoopRampRate, int profile) {
		synchronized (SafeTalon.lock) {
			super.setPID(p, i, d, f, izone, closeLoopRampRate, profile);
		}
	}

	@Override
	public void setPID(double p, double i, double d) {
		synchronized (SafeTalon.lock) {
			super.setPID(p, i, d);
		}
	}

	@Override
	public double getSetpoint() {
		synchronized (SafeTalon.lock) {
			return super.getSetpoint();
		}
	}

	@Override
	public void setProfile(int profile) {
		synchronized (SafeTalon.lock) {
			super.setProfile(profile);
		}
	}

	/**
	 * @deprecated
	 */
	@Override
	public void stopMotor() {
		synchronized (SafeTalon.lock) {
			super.stopMotor();
		}
	}

	@Override
	public void disable() {
		synchronized (SafeTalon.lock) {
			super.disable();
		}
	}

	@Override
	public int getDeviceID() {
		synchronized (SafeTalon.lock) {
			return super.getDeviceID();
		}
	}

	@Override
	public void clearIAccum() {
		synchronized (SafeTalon.lock) {
			super.clearIAccum();
		}
	}

	@Override
	public void setForwardSoftLimit(double forwardLimit) {
		synchronized (SafeTalon.lock) {
			super.setForwardSoftLimit(forwardLimit);
		}
	}

	@Override
	public int getForwardSoftLimit() {
		synchronized (SafeTalon.lock) {
			return super.getForwardSoftLimit();
		}
	}

	@Override
	public void enableForwardSoftLimit(boolean enable) {
		synchronized (SafeTalon.lock) {
			super.enableForwardSoftLimit(enable);
		}
	}

	@Override
	public boolean isForwardSoftLimitEnabled() {
		synchronized (SafeTalon.lock) {
			return super.isForwardSoftLimitEnabled();
		}
	}

	@Override
	public void setReverseSoftLimit(double reverseLimit) {
		synchronized (SafeTalon.lock) {
			super.setReverseSoftLimit(reverseLimit);
		}
	}

	@Override
	public int getReverseSoftLimit() {
		synchronized (SafeTalon.lock) {
			return super.getReverseSoftLimit();
		}
	}

	@Override
	public void enableReverseSoftLimit(boolean enable) {
		synchronized (SafeTalon.lock) {
			super.enableReverseSoftLimit(enable);
		}
	}

	@Override
	public boolean isReverseSoftLimitEnabled() {
		synchronized (SafeTalon.lock) {
			return super.isReverseSoftLimitEnabled();
		}
	}

	@Override
	public void configMaxOutputVoltage(double voltage) {
		synchronized (SafeTalon.lock) {
			super.configMaxOutputVoltage(voltage);
		}
	}

	@Override
	public void configPeakOutputVoltage(double forwardVoltage, double reverseVoltage) {
		synchronized (SafeTalon.lock) {
			super.configPeakOutputVoltage(forwardVoltage, reverseVoltage);
		}
	}

	@Override
	public void configNominalOutputVoltage(double forwardVoltage, double reverseVoltage) {
		synchronized (SafeTalon.lock) {
			super.configNominalOutputVoltage(forwardVoltage, reverseVoltage);
		}
	}

//	@Override
//	public void setParameter(CanTalonJNI.param_t paramEnum, double value) {
//		super.setParameter(paramEnum, value);
//	}

//	@Override
//	public double getParameter(CanTalonJNI.param_t paramEnum) {
//		return super.getParameter(paramEnum);
//	}

	@Override
	public void clearStickyFaults() {
		synchronized (SafeTalon.lock) {
			super.clearStickyFaults();
		}
	}

	@Override
	public void enableLimitSwitch(boolean forward, boolean reverse) {
		synchronized (SafeTalon.lock) {
			super.enableLimitSwitch(forward, reverse);
		}
	}

	@Override
	public void ConfigFwdLimitSwitchNormallyOpen(boolean normallyOpen) {
		synchronized (SafeTalon.lock) {
			super.ConfigFwdLimitSwitchNormallyOpen(normallyOpen);
		}
	}

	@Override
	public void ConfigRevLimitSwitchNormallyOpen(boolean normallyOpen) {
		synchronized (SafeTalon.lock) {
			super.ConfigRevLimitSwitchNormallyOpen(normallyOpen);
		}
	}

	@Override
	public void enableBrakeMode(boolean brake) {
		synchronized (SafeTalon.lock) {
			super.enableBrakeMode(brake);
		}
	}

	@Override
	public int getFaultOverTemp() {
		synchronized (SafeTalon.lock) {
			return super.getFaultOverTemp();
		}
	}

	@Override
	public int getFaultUnderVoltage() {
		synchronized (SafeTalon.lock) {
			return super.getFaultUnderVoltage();
		}
	}

	@Override
	public int getFaultForLim() {
		synchronized (SafeTalon.lock) {
			return super.getFaultForLim();
		}
	}

	@Override
	public int getFaultRevLim() {
		synchronized (SafeTalon.lock) {
			return super.getFaultRevLim();
		}
	}

	@Override
	public int getFaultHardwareFailure() {
		synchronized (SafeTalon.lock) {
			return super.getFaultHardwareFailure();
		}
	}

	@Override
	public int getFaultForSoftLim() {
		synchronized (SafeTalon.lock) {
			return super.getFaultForSoftLim();
		}
	}

	@Override
	public int getFaultRevSoftLim() {
		synchronized (SafeTalon.lock) {
			return super.getFaultRevSoftLim();
		}
	}

	@Override
	public int getStickyFaultOverTemp() {
		synchronized (SafeTalon.lock) {
			return super.getStickyFaultOverTemp();
		}
	}

	@Override
	public int getStickyFaultUnderVoltage() {
		synchronized (SafeTalon.lock) {
			return super.getStickyFaultUnderVoltage();
		}
	}

	@Override
	public int getStickyFaultForLim() {
		synchronized (SafeTalon.lock) {
			return super.getStickyFaultForLim();
		}
	}

	@Override
	public int getStickyFaultRevLim() {
		synchronized (SafeTalon.lock) {
			return super.getStickyFaultRevLim();
		}
	}

	@Override
	public int getStickyFaultForSoftLim() {
		synchronized (SafeTalon.lock) {
			return super.getStickyFaultForSoftLim();
		}
	}

	@Override
	public int getStickyFaultRevSoftLim() {
		synchronized (SafeTalon.lock) {
			return super.getStickyFaultRevSoftLim();
		}
	}

	@Override
	public void enableZeroSensorPositionOnIndex(boolean enable, boolean risingEdge) {
		synchronized (SafeTalon.lock) {
			super.enableZeroSensorPositionOnIndex(enable, risingEdge);
		}
	}

	@Override
	public void enableZeroSensorPositionOnForwardLimit(boolean enable) {
		synchronized (SafeTalon.lock) {
			super.enableZeroSensorPositionOnForwardLimit(enable);
		}
	}

	@Override
	public void enableZeroSensorPositionOnReverseLimit(boolean enable) {
		synchronized (SafeTalon.lock) {
			super.enableZeroSensorPositionOnReverseLimit(enable);
		}
	}

	@Override
	public void setNominalClosedLoopVoltage(double voltage) {
		synchronized (SafeTalon.lock) {
			super.setNominalClosedLoopVoltage(voltage);
		}
	}

	@Override
	public void DisableNominalClosedLoopVoltage() {
		synchronized (SafeTalon.lock) {
			super.DisableNominalClosedLoopVoltage();
		}
	}

	@Override
	public double GetNominalClosedLoopVoltage() {
		synchronized (SafeTalon.lock) {
			return super.GetNominalClosedLoopVoltage();
		}
	}

	@Override
	public void SetVelocityMeasurementPeriod(VelocityMeasurementPeriod period) {
		synchronized (SafeTalon.lock) {
			super.SetVelocityMeasurementPeriod(period);
		}
	}

	@Override
	public void SetVelocityMeasurementWindow(int windowSize) {
		synchronized (SafeTalon.lock) {
			super.SetVelocityMeasurementWindow(windowSize);
		}
	}

	@Override
	public VelocityMeasurementPeriod GetVelocityMeasurementPeriod() {
		synchronized (SafeTalon.lock) {
			return super.GetVelocityMeasurementPeriod();
		}
	}

	@Override
	public int GetVelocityMeasurementWindow() {
		synchronized (SafeTalon.lock) {
			return super.GetVelocityMeasurementWindow();
		}
	}

	@Override
	public void changeMotionControlFramePeriod(int periodMs) {
		synchronized (SafeTalon.lock) {
			super.changeMotionControlFramePeriod(periodMs);
		}
	}

	@Override
	public void clearMotionProfileTrajectories() {
		synchronized (SafeTalon.lock) {
			super.clearMotionProfileTrajectories();
		}
	}

	@Override
	public int getMotionProfileTopLevelBufferCount() {
		synchronized (SafeTalon.lock) {
			return super.getMotionProfileTopLevelBufferCount();
		}
	}

	@Override
	public boolean pushMotionProfileTrajectory(TrajectoryPoint trajPt) {
		synchronized (SafeTalon.lock) {
			return super.pushMotionProfileTrajectory(trajPt);
		}
	}

	@Override
	public boolean isMotionProfileTopLevelBufferFull() {
		synchronized (SafeTalon.lock) {
			return super.isMotionProfileTopLevelBufferFull();
		}
	}

	@Override
	public void processMotionProfileBuffer() {
		synchronized (SafeTalon.lock) {
			super.processMotionProfileBuffer();
		}
	}

	@Override
	public void getMotionProfileStatus(MotionProfileStatus motionProfileStatus) {
		synchronized (SafeTalon.lock) {
			super.getMotionProfileStatus(motionProfileStatus);
		}
	}

	@Override
	protected void setMotionProfileStatusFromJNI(MotionProfileStatus motionProfileStatus, int flags, int profileSlotSelect, int targPos, int targVel, int topBufferRem, int topBufferCnt, int btmBufferCnt, int outputEnable) {
		synchronized (SafeTalon.lock) {
			super.setMotionProfileStatusFromJNI(motionProfileStatus, flags, profileSlotSelect, targPos, targVel, topBufferRem, topBufferCnt, btmBufferCnt, outputEnable);
		}
	}

	@Override
	public void clearMotionProfileHasUnderrun() {
		synchronized (SafeTalon.lock) {
			super.clearMotionProfileHasUnderrun();
		}
	}

	@Override
	public void setMotionMagicCruiseVelocity(double motMagicCruiseVeloc) {
		synchronized (SafeTalon.lock) {
			super.setMotionMagicCruiseVelocity(motMagicCruiseVeloc);
		}
	}

	@Override
	public void setMotionMagicAcceleration(double motMagicAccel) {
		synchronized (SafeTalon.lock) {
			super.setMotionMagicAcceleration(motMagicAccel);
		}
	}

	@Override
	public double getMotionMagicCruiseVelocity() {
		synchronized (SafeTalon.lock) {
			return super.getMotionMagicCruiseVelocity();
		}
	}

	@Override
	public double getMotionMagicAcceleration() {
		synchronized (SafeTalon.lock) {
			return super.getMotionMagicAcceleration();
		}
	}

	@Override
	public double getMotionMagicActTrajVelocity() {
		synchronized (SafeTalon.lock) {
			return super.getMotionMagicActTrajVelocity();
		}
	}

	@Override
	public double getMotionMagicActTrajPosition() {
		synchronized (SafeTalon.lock) {
			return super.getMotionMagicActTrajPosition();
		}
	}

	@Override
	public void setCurrentLimit(int amps) {
		synchronized (SafeTalon.lock) {
			super.setCurrentLimit(amps);
		}
	}

	@Override
	public void EnableCurrentLimit(boolean enable) {
		synchronized (SafeTalon.lock) {
			super.EnableCurrentLimit(enable);
		}
	}

	@Override
	public int GetGadgeteerStatus(GadgeteerUartStatus status) {
		synchronized (SafeTalon.lock) {
			return super.GetGadgeteerStatus(status);
		}
	}

	@Override
	public String getLastError() {
		synchronized (SafeTalon.lock) {
			return super.getLastError();
		}
	}

	@Override
	public void setExpiration(double timeout) {
		synchronized (SafeTalon.lock) {
			super.setExpiration(timeout);
		}
	}

	@Override
	public double getExpiration() {
		synchronized (SafeTalon.lock) {
			return super.getExpiration();
		}
	}

	@Override
	public boolean isAlive() {
		synchronized (SafeTalon.lock) {
			return super.isAlive();
		}
	}

	@Override
	public boolean isSafetyEnabled() {
		synchronized (SafeTalon.lock) {
			return super.isSafetyEnabled();
		}
	}

	@Override
	public void setSafetyEnabled(boolean enabled) {
		synchronized (SafeTalon.lock) {
			super.setSafetyEnabled(enabled);
		}
	}

	@Override
	public String getDescription() {
		synchronized (SafeTalon.lock) {
			return super.getDescription();
		}
	}

	@Override
	public void initTable(ITable subtable) {
		synchronized (SafeTalon.lock) {
			super.initTable(subtable);
		}
	}

	@Override
	public void updateTable() {
		synchronized (SafeTalon.lock) {
			super.updateTable();
		}
	}

	@Override
	public ITable getTable() {
		synchronized (SafeTalon.lock) {
			return super.getTable();
		}
	}

	@Override
	public void startLiveWindowMode() {
		synchronized (SafeTalon.lock) {
			super.startLiveWindowMode();
		}
	}

	@Override
	public void stopLiveWindowMode() {
		synchronized (SafeTalon.lock) {
			super.stopLiveWindowMode();
		}
	}
}
