package com.bboehnert.studipmensa.responses;

import com.bboehnert.studipmensa.models.mensa.MensaLocation;

public class MensaResponse implements Response<MensaLocation>{

    private MensaLocation menu;

    public MensaLocation getResponse() {
        return menu;
    }
}