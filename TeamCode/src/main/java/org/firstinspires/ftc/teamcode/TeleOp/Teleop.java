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
    DcMotor viper;
    CRServo inTake;
    CRServo rotate;


    @Override
    public void init() {
        fL = hardwareMap.dcMotor.get("fL");
        bL = hardwareMap.dcMotor.get("bL");
        fR = hardwareMap.dcMotor.get("fR");
        bR = hardwareMap.dcMotor.get("bR");
        viper = hardwareMap.dcMotor.get("viper");
        inTake = hardwareMap.crservo.get("inTake");
        rotate = hardwareMap.crservo.get("rotate");

        fL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        fR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        bL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        bR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        viper.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


    }

    @Override
    public void loop() {
        //Front back Left
        if (Math.abs(gamepad1.left_stick_y) > .2) {
            fL.setPower(-gamepad1.left_stick_y * 1);
            bL.setPower(-gamepad1.left_stick_y * 1);
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
        }
        else {
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
        }
        else {
            fL.setPower(0);
            bL.setPower(0);
            fR.setPower(0);
            bR.setPower(0);
        }
        //Cascade
        if (Math.abs(-gamepad2.left_stick_y) > .1) {
            viper.setPower(gamepad2.left_stick_y * .5);
        }
        else {
            viper.setPower(0);
        }
        //Intake in
        if (gamepad2.b) {
            inTake.setPower(1);
        }
        //Intake out
        else if (gamepad2.a) {
            inTake.setPower(-1);
        }
        //Rotate Left
        if (gamepad2.left_bumper) {
            rotate.setPower(-.9);
        }
        else {
            rotate.setPower(0);
        }
        //Rotate Right
        if (gamepad2.right_bumper) {
            rotate.setPower(.9);
        }
        else {
            rotate.setPower(0);
        }

    }
}