package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;




import java.nio.channels.DatagramChannel;

@Autonomous(name = "Park Line Auto", group = "Autonomous")
//@Disabled
public class ParkLineAuto extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDriveFront = null;
    private DcMotor leftDriveBack = null;
    private DcMotor rightDriveFront = null;
    private DcMotor rightDriveBack = null;
    private DcMotor FLWheel = null;
    private DcMotor FRWheel = null;
    private Servo frontServo;

    @Override
    public void runOpMode()  throws InterruptedException {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        leftDriveFront = hardwareMap.get(DcMotor.class, "FL");
        leftDriveBack = hardwareMap.get(DcMotor.class, "RL");
        rightDriveBack = hardwareMap.get(DcMotor.class, "RR");
        rightDriveFront = hardwareMap.get(DcMotor.class, "FR");

        FLWheel = hardwareMap.get(DcMotor.class, "LI");
        FRWheel = hardwareMap.get(DcMotor.class, "RI");




        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftDriveFront.setDirection(DcMotor.Direction.REVERSE);
        leftDriveBack.setDirection(DcMotor.Direction.REVERSE);
        rightDriveBack.setDirection(DcMotor.Direction.FORWARD);
        rightDriveFront.setDirection(DcMotor.Direction.FORWARD);
        FLWheel.setDirection(DcMotor.Direction.FORWARD);
        FRWheel.setDirection(DcMotor.Direction.REVERSE);


        resetEncoders();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();


        while (opModeIsActive()) {
            
            sleep(6000);
            

            if((leftDriveFront.getCurrentPosition() >= 750 && rightDriveFront.getCurrentPosition() >= 750)){
                setPower(0);
            } else {
                moveToPos(850);
            }


            //turnByTime(1.0,1.0,0.5);
        }

    }


    /**
     *
     * @param time how long to turn in seconds
     * @param direction // What direction to turn (1 - right; -1 - left)
     */

    public void turnByTime(double time, double direction, double power){
        double endTime = 30000-time;
        time = time*1000;
        leftDriveFront.setPower(power *direction);
        leftDriveBack.setPower(power * direction);
        rightDriveFront.setPower(power * -direction);
        rightDriveBack.setPower(power * -direction);
        sleep((long)time);  // Run above code for specified amount of time
        leftDriveFront.setPower(0.0);
        leftDriveBack.setPower(0.0);
        rightDriveFront.setPower(0.0);
        rightDriveBack.setPower(0.0);
        sleep((long)endTime);
    }

    public void resetEncoders(){
        // Reset Encoders
        leftDriveFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftDriveBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDriveFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDriveBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }
    /**
     *
     * @param time Time to move forward
     * @param direction // Direction to move (1.0 - Forward; -1.0 - Backward)
     * @param power // Power for the wheels
     */
    public void moveByTime(double time, double direction, double power){
        time = time*1000;
        double endTime = 30000-time;
        leftDriveFront.setPower(power*direction);
        leftDriveBack.setPower(power*direction);
        rightDriveFront.setPower(power*direction);
        rightDriveBack.setPower(power*direction);
        sleep((long)time);
        leftDriveFront.setPower(0.0);
        leftDriveBack.setPower(0.0);
        rightDriveFront.setPower(0.0);
        rightDriveBack.setPower(0.0);
        sleep((long)endTime);
    }

    public void percentMotorMode(){
        // Set Run mode
        leftDriveFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftDriveBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDriveFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDriveBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void setUpMotorEncoders(){
        // Reset Encoders
        leftDriveFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftDriveBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDriveFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDriveBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Set Run mode
        leftDriveFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftDriveBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDriveFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDriveBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /**
     *
     * @param target Encoder position to drive to
     */

    public void moveToPos (int target){
        double leftPos = leftDriveFront.getCurrentPosition();
        double leftError = target - leftPos;
        double rightPos = rightDriveFront.getCurrentPosition();
        double rightError = target - rightPos;
        leftDriveBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftDriveFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDriveBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDriveFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        // Drive to Target
        leftDriveBack.setPower(.0025*leftError);
        leftDriveFront.setPower(.0025*leftError);
        rightDriveBack.setPower(.0025*rightError);
        rightDriveFront.setPower(.0025*rightError);
    }

    public void setPower (int power){
        leftDriveFront.setPower(power);
        leftDriveBack.setPower(power);
        rightDriveFront.setPower(-power);
        rightDriveBack.setPower(-power);
    }

}
