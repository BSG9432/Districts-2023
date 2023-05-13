package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
@TeleOp (name = "testClaw")
public class intake_test extends OpMode {

    CRServo bruh;


    @Override
    public void init() {

        bruh = hardwareMap.crservo.get("inTake");


    }

    @Override
    public void loop() {

        if (gamepad1.a){
            bruh.setPower(-1);

        }
        else{
            bruh.setPower(0);
        }

    }
}
