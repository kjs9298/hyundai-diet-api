package com.hyundai.diet.controller;

import com.hyundai.diet.domain.Keyboard;
import com.hyundai.diet.domain.Message;
import com.hyundai.diet.domain.MessageRequest;
import com.hyundai.diet.domain.MessageResponse;
import com.hyundai.diet.enums.DietType;
import com.hyundai.diet.enums.KeyboardType;
import com.hyundai.diet.service.ExcelService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MassageController {
    private Keyboard keyboard;

    @Autowired
    private ExcelService excelService;

    @PostConstruct
    public void init() {
        List<String> buttons = new ArrayList<>();

        for(DietType dietType : DietType.values()) {
            buttons.add(dietType.getDesc());

        }

        keyboard = new Keyboard(KeyboardType.BUTTON.getValue(), buttons);

    }

    @RequestMapping(value = "/keyboard", method = RequestMethod.GET)
    public Keyboard keyboard() {
        return keyboard;

    }

    @RequestMapping(value = "/message", method = RequestMethod.POST, headers = "Accept=application/json")
    public MessageResponse message(@RequestBody MessageRequest request) {
        DietType dietType = DietType.getFromDesc(request.getContent());
        String message = excelService.getDiet(dietType);

        return new MessageResponse(new Message(message), keyboard);

    }

}
