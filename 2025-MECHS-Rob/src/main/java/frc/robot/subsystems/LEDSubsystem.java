package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.Command;
import static edu.wpi.first.units.Units.Second;

public class LEDSubsystem extends SubsystemBase {
    private static final int kPort = 9;
    private static final int kLength = 60;
    
    private AddressableLED mLed;
    private AddressableLEDBuffer mLedBuffer;

    public LEDSubsystem() {
        this.mLed = new AddressableLED(kPort);
        mLedBuffer = new AddressableLEDBuffer(kLength);
        mLed.setLength(kLength);
        mLed.start();
    }

    @Override
    public void periodic() {
        mLed.setData(mLedBuffer);
    }

    public Command idlePulse() {
        LEDPattern baseColor = LEDPattern.solid(Color.kOrange);
        LEDPattern pulse = baseColor.breathe(Second.of(10));
        return run(() -> pulse.applyTo(mLedBuffer));
    }

    public Command runPattern(LEDPattern pattern) {
        return run(() -> pattern.applyTo(mLedBuffer));
    }
}