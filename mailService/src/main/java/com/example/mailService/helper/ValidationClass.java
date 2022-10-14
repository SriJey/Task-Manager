package com.example.mailService.helper;

import com.example.mailService.exceptions.InvalidEmailIdException;
import com.example.mailService.exceptions.InvalidOtpException;

public class ValidationClass {

    public static boolean validateOtp(int otp) throws InvalidOtpException {
        if (Integer.toString(otp).length() != 4)
            throw new InvalidOtpException("OTP should contain 4 numbers.");
        if (!Integer.toString(otp).matches("[0-9]{3,5}+"))
            throw new InvalidOtpException("OTP must be in numbers.");
        return true;
    }

    public static boolean validateEmail(String emailId) throws InvalidEmailIdException {
        if (((emailId.toLowerCase())
                .matches("[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "quinbay.com$")))
            return true;
        throw new InvalidEmailIdException("Invalid Email");
    }

}
