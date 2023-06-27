package com.automationanywhere.botcommand.filesave;


import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.data.impl.FileValue;
import com.automationanywhere.botcommand.data.impl.SessionValue;
import com.automationanywhere.botcommand.exception.BotCommandException;
import com.automationanywhere.commandsdk.annotations.*;
import com.automationanywhere.commandsdk.annotations.rules.NotEmpty;
import com.automationanywhere.commandsdk.i18n.Messages;
import com.automationanywhere.commandsdk.i18n.MessagesFactory;
import com.automationanywhere.commandsdk.model.AttributeType;
import com.automationanywhere.commandsdk.model.ReturnSettingsType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

import static com.automationanywhere.commandsdk.model.DataType.FILE;
import static com.automationanywhere.commandsdk.model.DataType.SESSION;

@BotCommand

//CommandPks adds required information to be dispalable on GUI.
@CommandPkg(
        //Unique name inside a package and label to display.
        name = "OpenFile", label = "[[OpenFile.label]]",
        node_label = "[[OpenFile.node_label]]", description = "[[OpenFile.description]]", icon = "pkg.svg",

        //Return type information. return_type ensures only the right kind of variable is provided on the UI.
        return_label = "[[OpenFile.return_label]]",
        return_type = FILE,
        return_required = true
        )

public class OpenFile {

    //Messages read from full qualified property file name and provide i18n capability.
    private static final Messages MESSAGES = MessagesFactory
            .getMessages("com.automationanywhere.botcommand.samples.messages");
    private static final Logger logger = LogManager.getLogger(OpenFile.class);

    //Identify the entry point for the action. Returns a Value<String> because the return type is String.
    @Execute
    public FileValue action(
            //Idx 1 would be displayed first, with a text box for entering the value.
            @Idx(index = "1", type = AttributeType.FILE)
            //UI labels.
            @Pkg(label = "[[OpenFile.sourceFilePath.label]]")
            //Ensure that a validation error is thrown when the value is null.
            @NotEmpty
            String sourceFilePath) {

        //Internal validation, to disallow empty strings. No null check needed as we have NotEmpty on firstString.
        if ("".equals(sourceFilePath.trim()))
            throw new BotCommandException(MESSAGES.getString("emptyInputString", "sourceImagePath"));


        //Business logic
        return new FileValue(sourceFilePath);
    }
}
