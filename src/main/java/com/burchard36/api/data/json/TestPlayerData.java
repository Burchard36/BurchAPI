package com.burchard36.api.data.json;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TestPlayerData extends JsonPlayerDataFile {

    public String myFunString;

    protected TestPlayerData() {
        this.myFunString = "SUPER FUN STRING";

    }
}
