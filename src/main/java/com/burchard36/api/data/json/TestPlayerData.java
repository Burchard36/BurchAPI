package com.burchard36.api.data.json;

import com.burchard36.api.data.annotations.PlayerDataFile;
import com.burchard36.api.data.json.enums.FileType;
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
