package com.burchard36.api;

import com.burchard36.api.command.ApiCommand;

public class TestArgument extends ApiCommand {

   public TestArgument() {
       this.subArgument("test", (subArgument) -> {
            if (!subArgument.senderIsPlayer()) return;

       });
   }
}
