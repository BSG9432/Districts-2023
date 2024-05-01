package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Teleop")
public class Teleop extends OpMode {
    DcMotor fL;
    DcMotor bL;
    DcMotor fR;
    DcMotor bR;
    DcMotor Lift;
    DcMotor wrist;
    Servo LS;
    Servo RS;
    Servo BT;
    CRServo rotate;


    @Override
    public void init() {
        fL = hardwareMap.dcMotor.get("fL");
        bL = hardwareMap.dcMotor.get("bL");
        fR = hardwareMap.dcMotor.get("fR");
        bR = hardwareMap.dcMotor.get("bR");
        Lift = hardwareMap.dcMotor.get("Lift");
        wrist = hardwareMap.dcMotor.get("wrist");
        LS = hardwareMap.servo.get("LS");
        RS = hardwareMap.servo.get("RS");
        BT = hardwareMap.servo.get("BT");
        rotate = hardwareMap.crservo.get("rotate");


        fL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        fR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        bL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        bR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        Lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


    }

    @Override
    public void loop() {
        //Front back Left
        if (Math.abs(gamepad1.left_stick_y) > .2) {
            fL.setPower(-gamepad1.left_stick_y * -1);
            bL.setPower(-gamepad1.left_stick_y * -1);
        } else {
            fL.setPower(0);
            bL.setPower(0);
        }
        //Front back Right
        if (Math.abs(-gamepad1.right_stick_y) > .2) {
            fR.setPower(-gamepad1.right_stick_y * 1);
            bR.setPower(-gamepad1.right_stick_y * 1);
        } else {
            fR.setPower(0);
            bR.setPower(0);
        }

        //Side speed Right
        if (gamepad1.right_bumper) {
            fL.setPower(.9);
            bL.setPower(-.9);
            fR.setPower(-.9);
            bR.setPower(.9);
        } else {
            fL.setPower(0);
            bL.setPower(0);
            fR.setPower(0);
            bR.setPower(0);
        }
        //Side speed Left
        if (gamepad1.left_bumper) {
            fL.setPower(-.9);
            bL.setPower(.9);
            fR.setPower(.9);
            bR.setPower(-.9);
        } else {
            fL.setPower(0);
            bL.setPower(0);
            fR.setPower(0);
            bR.setPower(0);
        }
        //Cascade
        if (Math.abs(-gamepad2.left_stick_y) > .1) {
            Lift.setPower(gamepad2.left_stick_y * -.5);
        } else {
            Lift.setPower(0);
        }
        if (Math.abs(-gamepad2.right_stick_y) > .1) {
            wrist.setPower(gamepad2.right_stick_y * .5);
        } else {
            wrist.setPower(0);
        }
        //Intake in
        if (gamepad2.b) {
            LS.setPosition(.76);
            RS.setPosition(.20);
        }

        //Intake out
        else if (gamepad2.a) {
            LS.setPosition(.90);
            RS.setPosition(0.05);
        }
        //Rotate Left
        if (gamepad2.left_bumper) {
            rotate.setPower(-1);
        } else {
            rotate.setPower(0);
        }
        //Rotate Right
        if (gamepad2.right_bumper) {
            rotate.setPower(2);
        } else {
            rotate.setPower(0);
        }
        //Reset servos position
        if (gamepad2.dpad_left) {
            LS.setPosition(1);
            RS.setPosition(0);
            BT.setPosition(1);
        }
        if (gamepad2.dpad_up) {
            BT.setPosition(.60);
        }
        if (gamepad2.dpad_down) {
            BT.setPosition(1);
        }
    }
}
