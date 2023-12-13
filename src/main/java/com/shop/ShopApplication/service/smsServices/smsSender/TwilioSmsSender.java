package com.shop.ShopApplication.service.smsServices.smsSender;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("twilio")
public class TwilioSmsSender implements SmsSender {
    private final TwilioConfig twilioConfig;

    private final static Logger LOGGER = LoggerFactory.getLogger(TwilioSmsSender.class);

    @Autowired
    public TwilioSmsSender(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
    }

    @Override
    public void sendSms(SmsRequest smsRequest) {
        if (isPhoneNumberValid(smsRequest.getPhoneNumber())) {
            PhoneNumber to = new PhoneNumber(smsRequest.getPhoneNumber());
            PhoneNumber from = new PhoneNumber(twilioConfig.getPhoneNumber());
            String message = smsRequest.getMessage();

            Message.creator(to, from, message).create();

            LOGGER.info("Sent SMS: {}", smsRequest);
        } else {
            LOGGER.error("Invalid phone number: {}", smsRequest.getPhoneNumber());
            throw new IllegalArgumentException("Не правильный номер телефона");
        }
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        // TODO: Implement phone number validation logic here
        return true;
    }
}
