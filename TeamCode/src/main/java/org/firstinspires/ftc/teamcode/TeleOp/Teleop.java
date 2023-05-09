package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Teleop")
public class Teleop extends OpMode {

    DcMotor frontLeft;
    DcMotor backLeft;
    DcMotor frontRight;
    DcMotor backRight;
    DcMotor viper;
    CRServo inTake;
    CRServo rotate;


    @Override
    public void init() {
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");
        viper = hardwareMap.dcMotor.get("viper");
        inTake = hardwareMap.crservo.get("intake");
        rotate = hardwareMap.crservo.get("rotate");

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        viper.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


    }

    @Override
    public void loop() {
        //Front back Left
        if (Math.abs(-gamepad1.left_stick_y) > .2) {
            frontLeft.setPower(-gamepad1.left_stick_y * -1);
            backLeft.setPower(-gamepad1.left_stick_y * -1);
        } else {
            frontLeft.setPower(0);
            backLeft.setPower(0);
        }
        //Front back Right
        if (Math.abs(-gamepad1.right_stick_y) > .2) {
            frontRight.setPower(-gamepad1.right_stick_y * 1);
            backRight.setPower(-gamepad1.right_stick_y * 1);
        } else {
            frontRight.setPower(0);
            backRight.setPower(0);
        }

        //Side speed Right
        if (gamepad1.right_bumper) {
            frontLeft.setPower(-.9);
            backLeft.setPower(.9);
            frontRight.setPower(-.9);
            backRight.setPower(.9);
        }
        else {
            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
        }
        //Side speed Left
        if (gamepad1.left_bumper) {
            frontLeft.setPower(.9);
            backLeft.setPower(-.9);
            frontRight.setPower(.9);
            backRight.setPower(-.9);
        }
        else {
            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
        }
        //Cascade
        if (Math.abs(-gamepad2.left_stick_y) > .1) {
            viper.setPower(-gamepad2.left_stick_y * .5);
        }
        else {
            viper.setPower(0);
        }
        //Intake in
        if (Math.abs(-gamepad2.left_trigger) > .2) {
            inTake.setPower(1);
        }
        else {
            inTake.setPower(0);
        }
        //Intake out
        if (Math.abs(-gamepad2.right_trigger) > .2) {
            inTake.setPower(-1);
        }
        else {
            inTake.setPower(0);
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