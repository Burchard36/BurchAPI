package com.burchard36.api.command;

import com.burchard36.api.command.interfaces.OnSubArgument;

import javax.annotation.Nullable;

record ApiCommandArgument(@Nullable String argument,
                          @Nullable String requiredPermission,
                          OnSubArgument argumentFunction) {
}
