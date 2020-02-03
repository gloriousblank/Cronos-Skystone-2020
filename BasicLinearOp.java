package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcontroller.external.samples.SampleRevBlinkinLedDriver;

@TeleOp(name="9785 TELE OP", group="Linear Opmode")
@Disabled
public class BasicLinearOp extends LinearOpMode {
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDriveFront = null;
    private DcMotor leftDriveBack = null;
    private DcMotor rightDriveFront = null;
    private DcMotor rightDriveBack = null;
    private DcMotor FLWheel = null;
    private DcMotor FRWheel = null;
    private Servo frontServo;
    private Servo rightFoundation;
    private Servo leftFoundation;
    private DcMotor elevator = null;

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
        frontServo = hardwareMap.servo.get("Fservo");
        rightFoundation = hardwareMap.servo.get("fRight");
        leftFoundation = hardwareMap.servo.get("fLeft");
        elevator = hardwareMap.get(DcMotor.class, "Elevator");



        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftDriveFront.setDirection(DcMotor.Direction.REVERSE);
        leftDriveBack.setDirection(DcMotor.Direction.REVERSE);
        rightDriveBack.setDirection(DcMotor.Direction.FORWARD);
        rightDriveFront.setDirection(DcMotor.Direction.FORWARD);

        FLWheel.setDirection(DcMotor.Direction.FORWARD);
        FRWheel.setDirection(DcMotor.Direction.REVERSE);


        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Setup a variable for each drive wheel to save power level for telemetry
            double leftPower;
            double rightPower;

            // Choose to drive using either Tank Mode, or POV Mode
            // Comment out the method that's not used.  The default below is POV.

            // POV Mode uses left stick to go forward, and right stick to turn.
            // - This uses basic math to combine motions and is easier to drive straight.
            //double drive = -gamepad1.left_stick_y;
            //double turn = gamepad1.right_stick_x;
            //leftPower = Range.clip(drive + turn, -1.0, 1.0);
            //rightPower = Range.clip(drive - turn, -1.0, 1.0);

            // Tank Mode uses one stick to control each wheel.
            // - This requires no math, but it is hard to drive forward slowly and keep straight.
            leftPower  = -gamepad1.left_stick_y;
            rightPower = -gamepad1.right_stick_y;

            // Send calculated power to wheels
            leftDriveFront.setPower(leftPower);
            leftDriveBack.setPower(leftPower);
            rightDriveFront.setPower(rightPower);
            rightDriveBack.setPower(rightPower);
            boolean toggleServo = true;

            if(gamepad2.a){
                FLWheel.setPower(1.00);
                FRWheel.setPower(1.00);
            } else if(gamepad2.b){
                FLWheel.setPower(-1.00);
                FRWheel.setPower(-1.00);
            } else{
                FLWheel.setPower(0.0);
                FRWheel.setPower(0.0);
            }
            if (gamepad1.left_bumper) {
                leftDriveBack.setPower(1.0);
                leftDriveFront.setPower(-1.0);
                rightDriveFront.setPower(1.0);
                rightDriveBack.setPower(-1.0);
            }
            if(gamepad1.right_bumper) {
                leftDriveBack.setPower(-1.0);
                leftDriveFront.setPower(1.0);
                rightDriveFront.setPower(-1.0);
                rightDriveBack.setPower(1.0);
            }





            if(gamepad2.left_stick_button){
                rightFoundation.setPosition(100);
                leftFoundation.setPosition(50);
            }

            if(gamepad2.right_stick_button){
                leftFoundation.setPosition(0);
                rightFoundation.setPosition(0.0);
            }




            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            //telemetry.addData("Intake Left", FLWheel.getPower());
            telemetry.addData("Intake Right", FRWheel.getPower());
            telemetry.addData("Left Side Encoders", (leftDriveFront.getCurrentPosition() + leftDriveBack.getCurrentPosition())/2);
            telemetry.addData("Right Side Encoders", (rightDriveFront.getCurrentPosition() + rightDriveBack.getCurrentPosition())/2);
            telemetry.addData("Foundation Righ", rightFoundation.getPosition());
            telemetry.addData("Foundation Left", leftFoundation.getPosition());

            telemetry.update();

        }
    }
}
