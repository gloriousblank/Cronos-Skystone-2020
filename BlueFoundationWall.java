package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;




import java.nio.channels.DatagramChannel;

@Autonomous(name = "Foundation Blue Wall", group = "Autonomous")
//@Disabled
public class FoundationBlueWall extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDriveFront = null;
    private DcMotor leftDriveBack = null;
    private DcMotor rightDriveFront = null;
    private DcMotor rightDriveBack = null;
    private DcMotor FLWheel = null;
    private DcMotor FRWheel = null;
    private Servo rF;
    private Servo lF;

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
        rF = hardwareMap.servo.get("fRight");
        lF = hardwareMap.servo.get("fLeft");


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


        boolean start = true;
        boolean second = true;
        boolean third = true;
        boolean fourth = true;
        boolean fifth = true;
        boolean sixth = true;
        boolean seventh = true;
        boolean eigth = true;
        boolean stop = false;

        while (opModeIsActive() && seventh) {



            if((leftDriveFront.getCurrentPosition() <= -600 && rightDriveFront.getCurrentPosition() <= -600) || !start  ){
                if(start && !stop){
                    // stop motors after step 1
                    start = false;
                    setPower(0);
                    resetEncoders();
                }

                if (!start && !stop){
                    if(leftDriveFront.getCurrentPosition() >= 500 && rightDriveFront.getCurrentPosition() <= -400){
                        third = false;
                        setPower(0);
                        resetEncoders();
                    } else if (third){
                        mecanumTargets(650,-600,-550,600);
                    }
                }

                if(!start && !third && !stop){
                    if(leftDriveFront.getCurrentPosition() <= -100 && rightDriveFront.getCurrentPosition() <= 5){
                        fifth = false;
                        setPower(0);
                        foundation(1);
                        sleep(2000);
                        resetEncoders();
                    } else if (fifth){
                        moveToPos(-250,-300);
                    }
                }

                if(!start && !third && !fifth && !stop){
                    if(leftDriveFront.getCurrentPosition() >= 700 && rightDriveFront.getCurrentPosition() >= 850){
                        second = false;
                        setPower(0);
                        foundation(0);
                        resetEncoders();

                    } else if(second) {
                        foundation(1);
                        moveToPos(900,1200);

                    }
                }



                if (!start && !third && !second && !fifth && !stop){
                    if(leftDriveBack.getCurrentPosition() >= 1700 && rightDriveFront.getCurrentPosition() >= 1650){
                        fourth = false;
                        setPower(0);
                        resetEncoders();
                    } else if(fourth){
                        leftDriveBack.setPower(.9);
                        rightDriveFront.setPower(.75);
                        FLWheel.setPower(1.0);
                        FRWheel.setPower(-1.0);
                        //leftDriveBack.setPower(.75);
                        
                        mecanumTargets(-1900,1900,1900,-1900);
                    }
                }

                if (!start && !third && !second && !fifth && !fourth && !stop){
                    if(leftDriveFront.getCurrentPosition() <= -100  && rightDriveFront.getCurrentPosition() >= 50){
                        sixth = false;
                        setPower(0);
                        //FLWheel.setPower(1);
                    } else if(sixth){
                        //FLWheel.setPower(1.0);
                        moveToPos(200,100);
                        telemetry.addLine("Hello!");

                    }

                }

/*
                if (!start && !third && !second && !fifth && !fourth && !sixth && !stop){
                    if(leftDriveBack.getCurrentPosition() >= 1350 && rightDriveFront.getCurrentPosition() >= 1350){
                        seventh = false;
                        setPower(0);
                        //FLWheel.setPower(0);
                    } else if(seventh){
                        //FLWheel.setPower(1.0);
                        mecanumTargets(-1700,2000,2000,-2000);

                    }
                }
*/




                /*
                if(!start && !third && !second && !fifth && !fourth){
                    if(leftDriveFront.getCurrentPosition() <= -200 && rightDriveFront.getCurrentPosition() <= -200){
                       sixth = false;
                       resetEncoders();
                       setPower(0);
                    } else if(sixth){
                        moveToPos(-300);
                    }
                }
                */
            } else if(start) {
                // First step - Move forward towards foundation
                moveToPos(-750);
            }

            //
            // setPower(0);





            telemetry.addData("Left Front Encoder", (leftDriveFront.getCurrentPosition()));
            telemetry.addData("Right Front Encoder", (rightDriveFront.getCurrentPosition() ));
            telemetry.addData("Left Back Encoder", leftDriveBack.getCurrentPosition());
            telemetry.addData("Right Back Encoder", rightDriveBack.getCurrentPosition());
            telemetry.update();


        }

    }
    
    
    public void foundation (int grab) {
        if(grab == 1) {
            rF.setPosition(0.0);
            lF.setPosition(0.0);
        } else if (grab == 0) {
            rF.setPosition(0.75);
            lF.setPosition(0.75);
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


    /**
     *
     * @param time Time to move forward
     * @param direction // Direction to move (1.0 - Forward; -1.0 - Backward)
     * @param power // Power for the wheels
     */
    public void moveByTime(double time, double direction, double power){
        time = time*1000;
        double endTime = runtime.time()-time;
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

    public void resetEncoders(){
        // Reset Encoders
        leftDriveFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftDriveBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDriveFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDriveBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

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
        leftDriveBack.setPower(.025*leftError);
        leftDriveFront.setPower(.0025*leftError);
        rightDriveBack.setPower(.0015*rightError);
        rightDriveFront.setPower(.0015*rightError);
    }



    /**
     *
     * @param leftTarget Left encoder target
     * @param rightTarget Right encoder target
     *
     */

    public void moveToPos (int leftTarget, int rightTarget){
        double leftPos = leftDriveFront.getCurrentPosition();
        double leftError = leftTarget - leftPos;
        double rightPos = rightDriveFront.getCurrentPosition();
        double rightError = rightTarget - rightPos;
        leftDriveBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftDriveFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDriveBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDriveFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        // Drive to Target
        leftDriveBack.setPower(.0025*leftError);
        leftDriveFront.setPower(.0025*leftError);
        rightDriveBack.setPower(.0015*rightError);
        rightDriveFront.setPower(.0015*rightError);
    }

    public void setPower (int power){
        leftDriveFront.setPower(power);
        leftDriveBack.setPower(power);
        rightDriveFront.setPower(-power);
        rightDriveBack.setPower(-power);
    }

    public void mecanumTargets(double lf, double lr, double rf, double rr){
        double direction = 1.0;
        double lfPos = leftDriveFront.getCurrentPosition();
        double lrPos = leftDriveBack.getCurrentPosition();
        double rfPos = rightDriveFront.getCurrentPosition();
        double rrPos = rightDriveBack.getCurrentPosition();
        double lfError = lf - lfPos;
        double lrError = lr - lrPos;
        double rfError = rf - rfPos;
        double rrError = rr - rrPos;
        leftDriveBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftDriveFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDriveBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDriveFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftDriveBack.setPower(.0025*lrError * direction);
        leftDriveFront.setPower(.0025*lfError * direction);
        rightDriveBack.setPower(.0015*rrError * direction);
        rightDriveFront.setPower(.0015*rfError * direction);

    }

    public void mecanumByTime(double time, double direction){
        time = time*1000;
        double endTime = runtime.time()-time;
        leftDriveBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftDriveFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDriveBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDriveFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftDriveBack.setPower(0.5 * -direction);
        leftDriveFront.setPower(0.5 * direction);
        rightDriveBack.setPower(0.5 * direction);
        rightDriveFront.setPower(0.5 * -direction);
        sleep((long)time);
        leftDriveFront.setPower(0.0);
        leftDriveBack.setPower(0.0);
        rightDriveFront.setPower(0.0);
        rightDriveBack.setPower(0.0);
        sleep((long)endTime);
    }

}
