package com.burchard36.api.command.interfaces;

import com.burchard36.api.command.actions.SubArgument;

@FunctionalInterface
public interface OnSubArgument {
    void onArgument(SubArgument argument);
}
