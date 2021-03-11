package com.thondo.whatsappconnect;

import com.thondo.whatsappconnect.service.MessageProcessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Message;
import com.twilio.twiml.messaging.Redirect;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.TwiMLException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/app")
public class MessageProcessorController {
    @Autowired
    private MessageProcessorService messageProcessorService;

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String processMessage(@RequestParam MultiValueMap<String, String> paramMap) {
        System.out.println(" received the msg >>>>>>>>>>>>>>>>>" + paramMap);
        if (paramMap != null) {
            return messageProcessorService.processMessage(paramMap);
        }else{
            return "Cannot process request";
        }



    }


}
