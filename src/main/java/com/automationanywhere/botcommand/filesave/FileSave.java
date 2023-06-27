package com.automationanywhere.botcommand.filesave;


import com.automationanywhere.botcommand.exception.BotCommandException;
import com.automationanywhere.commandsdk.annotations.*;
import com.automationanywhere.commandsdk.annotations.rules.NotEmpty;
import com.automationanywhere.commandsdk.i18n.Messages;
import com.automationanywhere.commandsdk.i18n.MessagesFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static com.automationanywhere.commandsdk.model.AttributeType.*;

@BotCommand

//CommandPks adds required information to be dispalable on GUI.
@CommandPkg(
        //Unique name inside a package and label to display.
        name = "FileSave", label = "[[FileSave.label]]",
        node_label = "[[FileSave.node_label]]", description = "[[FileSave.description]]", icon = "pkg.svg")


public class FileSave {

    //Messages read from full qualified property file name and provide i18n capability.
    private static final Messages MESSAGES = MessagesFactory
            .getMessages("com.automationanywhere.botcommand.samples.messages");

    private static final Logger logger = LogManager.getLogger(FileSave.class);

    //Identify the entry point for the action. Returns a Value<String> because the return type is String.
    @Execute
    public void action(
            @Idx(index = "1", type = FILE)
            @Pkg(label = "[[FileSave.sourceFile.label]]", description = "")
            @NotEmpty
            String sourceFile,

            @Idx(index = "2", type = TEXT)
            @Pkg(label = "[[FileSave.destinationPath.label]]")
            @NotEmpty
            String destinationPath) {

        //Internal validation, to disallow empty strings. No null check needed as we have NotEmpty on firstString.
        if ("".equals(destinationPath.trim()))
            throw new BotCommandException(MESSAGES.getString("emptyInputString", "destination"));

        if (destinationPath.charAt(destinationPath.length() - 1) != '/')
            destinationPath += '/';


        File file = new File(sourceFile);
        destinationPath = destinationPath + file.getName();
        
        //Business logic
        try {
            Path sourcePath = file.toPath();
            Path destination = Path.of(destinationPath);
            Files.copy(sourcePath, destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
