package org.firstinspires.ftc.teamcode.Auto;

import static org.firstinspires.ftc.teamcode.Vision.SleeveDetection.ParkingPosition.ONE;
import static org.firstinspires.ftc.teamcode.Vision.SleeveDetection.ParkingPosition.THREE;
import static org.firstinspires.ftc.teamcode.Vision.SleeveDetection.ParkingPosition.TWO;
import static org.openftc.easyopencv.OpenCvCameraRotation.UPRIGHT;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Vision.SleeveDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous (name = "SleeveParkBlue")
public class SignalSleevePark extends LinearOpMode {
    //declare motors & miscellaneous
    //Motors
    DcMotor fR;
    DcMotor fL;
    DcMotor bR;
    DcMotor bL;
    SleeveDetection sleeve;
    OpenCvCamera camera;

    private ElapsedTime runtime = new ElapsedTime();

    static final double COUNTS_PER_MOTOR_REV = 312;
    static final double DRIVE_GEAR_REDUCTION = 1.0;     // No External Gearing.
    static final double WHEEL_DIAMETER_INCHES = 3.7;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double DRIVE_SPEED = 0.6;

    static final double COUNTS_PER_SPOOL_MOTOR_REV = 2786.2;
    static final double DRIVE_SPOOL_GEAR_REDUCTION = 1;     // This is < 1.0 if geared UP
    static final double SPOOL_DIAMETER_INCHES = 2.2;     // For figuring circumference
    static final double ROTATION_PER_INCH = (COUNTS_PER_SPOOL_MOTOR_REV * DRIVE_SPOOL_GEAR_REDUCTION) /
            (SPOOL_DIAMETER_INCHES * 3.1415);

    SleeveDetection pipeline = new SleeveDetection();

    Integer cpr = 28;
    Integer gearratio = (((1 + (46 / 17))) * (1 + (46 / 11)));
    Double diameter = 4.0;
    Double cpi = (cpr * gearratio) / (Math.PI * diameter);
    Double bias = 0.8;
    Double meccyBias = 0.9;


    private String webcamName = "Webcam 1";

    @Override
    public void runOpMode() throws InterruptedException {
        //names lol
        fR = hardwareMap.get(DcMotor.class, "frontRight");
        fL = hardwareMap.get(DcMotor.class, "frontLeft");
        bR = hardwareMap.get(DcMotor.class, "backRight");
        bL = hardwareMap.get(DcMotor.class, "backLeft");

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, webcamName), cameraMonitorViewId);
        sleeve = new SleeveDetection();
        camera.setPipeline(sleeve);

        //configure directions (test bevel gear affecting)
        fR.setDirection(DcMotor.Direction.REVERSE);
        fL.setDirection(DcMotor.Direction.FORWARD);
        bR.setDirection(DcMotor.Direction.REVERSE);
        bL.setDirection(DcMotor.Direction.FORWARD);

        //encoder shmuck
        fR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        fR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //telemetry shmuck pt.2
        telemetry.addData("Starting at", "%7d :%7d :%7d :%7d :%7d",
                fR.getCurrentPosition(),
                fL.getCurrentPosition(),
                bR.getCurrentPosition(),
                bL.getCurrentPosition());
        telemetry.update();

        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(320, 240, UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
            }
        });

        while (!isStarted()) {
            telemetry.addData("ROTATION: ", sleeve.getPosition());
            telemetry.update();
        }

        //hold up wing ding
        waitForStart();

        SleeveDetection.ParkingPosition P = sleeve.getPosition();

        if (P == THREE) {
            strafeToPosition(-38, DRIVE_SPEED, 1);
        }
        else if (P == TWO){
            strafeToPosition(-38, DRIVE_SPEED, 1);

            encoderDrive(DRIVE_SPEED, -5,-5,1);

            encoderDrive(DRIVE_SPEED, 12, -12, 1);
        }
        else if (P == ONE){
            encoderDrive(DRIVE_SPEED, -12,-12, 1);

            encoderDrive(DRIVE_SPEED, 24,-24,1);
        }

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(3000);
    }


        public void strafeToPosition ( double inches, double speed, double timeoutS){

            int move = (int) (Math.round(inches * cpi * meccyBias * 1.265));

            fL.setTargetPosition(fL.getCurrentPosition() + move);
            bL.setTargetPosition(bL.getCurrentPosition() - move);
            fR.setTargetPosition(fR.getCurrentPosition() + move);
            bR.setTargetPosition(bR.getCurrentPosition() - move);

            fL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            fR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            fL.setPower(speed);
            bL.setPower(speed);
            fR.setPower(speed);
            bR.setPower(speed);

            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (fL.isBusy() && bL.isBusy() && fR.isBusy() && bR.isBusy())) {

                telemetry.addData("Running to", "%7d :%7d",
                        fL.getCurrentPosition(), bL.getCurrentPosition(), fR.getCurrentPosition(), bR.getCurrentPosition());
                telemetry.update();
            }

            while (fL.isBusy() && bL.isBusy() && fR.isBusy() && bR.isBusy()) {
            }
            fL.setPower(0);
            bL.setPower(0);
            fR.setPower(0);
            bR.setPower(0);
            return;
        }

    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newfLTarget;
        int newfRTarget;
        int newbLTarget;
        int newbRTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newfLTarget = fL.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
            newfRTarget = fR.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);
            newbLTarget = bL.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
            newbRTarget = bR.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);
            fL.setTargetPosition(newfLTarget);
            fR.setTargetPosition(newfRTarget);
            bL.setTargetPosition(newbLTarget);
            bR.setTargetPosition(newbRTarget);

            // Turn On RUN_TO_POSITION
            fR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            fL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bL.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            resetRuntime();
            fR.setPower(Math.abs(speed));
            fL.setPower(Math.abs(speed));
            bR.setPower(Math.abs(speed));
            bL.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (fR.isBusy() && bR.isBusy() && bL.isBusy() && fL.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Running to", " %7d :%7d :%7d :%7d", newfRTarget, newfLTarget, newbRTarget, newbLTarget);
                telemetry.addData("Currently at", " at %7d :%7d :%7d :%7d", newfRTarget, newfLTarget, newbRTarget, newbLTarget,
                        fR.getCurrentPosition(), fL.getCurrentPosition(), bR.getCurrentPosition(), bL.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            fR.setPower(0);
            fL.setPower(0);
            bR.setPower(0);
            bL.setPower(0);

            // Turn off RUN_TO_POSITION
            fR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            fL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            bR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            bL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(250);
        }
    }
}

