package krisapps.omnitracker.managers;

import krisapps.omnitracker.OmniTracker;
import krisapps.omnitracker.enums.LogRecordType;
import krisapps.omnitracker.enums.OperationType;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ActivityLogManager {

    private boolean isEnabled = true;

    OmniTracker main;
    File messages;
    File commands;
    File operations;
    File general;
    public ActivityLogManager(OmniTracker main){
        this.main = main;
    }

    public void logMessage(String from, String fromUUID,  String message){
        messages = main.messageLogFile;

        String logMessage = String.format("Message sent by %s (%s) > '" + message + "'", from, fromUUID);

        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        FileWriter fw = null;
        try {
            fw = new FileWriter(messages, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PrintWriter pw = new PrintWriter(fw);

        pw.println(String.format("(%s)-[MessageLogger]: " + logMessage, format.format(now)));
        appendToGeneralLogFile(logMessage, LogRecordType.MESSAGE);
        pw.flush();
        pw.close();

    }

    public void logCommand(String from, String fromUUID,  String command){
        commands = main.commandsLogFile;

        String logMessage = String.format("Command executed by %s (%s) > '" + command + "'", from, fromUUID);

        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        FileWriter fw = null;
        try {
            fw = new FileWriter(commands, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PrintWriter pw = new PrintWriter(fw);

        pw.println(String.format("(%s)-[CommandLogger]: " + logMessage, format.format(now)));
        appendToGeneralLogFile(logMessage, LogRecordType.COMMAND);
        pw.flush();
        pw.close();

    }

    public void logOperation(String player, String playerUUID, OperationType operation, @Nullable HashMap<String, String> args){
        operations = main.operationsLogFile;

        String logMessage = "";

        switch (operation){

            case GAMEMODE_CHANGE -> {
                logMessage = String.format("%s (%s) changed their gamemode from %s to %s", player, playerUUID, args.get("arg1"), args.get("arg2"));
                break;
            }
            case TELEPORT -> {
                logMessage = String.format("%s (%s) teleported from %s to %s", player, playerUUID, args.get("arg1"), args.get("arg2"));
            }
            case JOIN_SERVER -> {
                logMessage = String.format("%s (%s) joined the server from the following IP: %s", player, playerUUID, args.get("arg2"));
            }
            case LEAVE_SERVER -> {
                logMessage = String.format("%s (%s) left the server with exit message <%s>", player, playerUUID, args.get("arg1"));
            }
        }

        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        FileWriter fw;
        try {
            fw = new FileWriter(operations, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PrintWriter pw = new PrintWriter(fw);

        pw.println(String.format("(%s)-[OperationsLogger]: " + logMessage, format.format(now)));
        appendToGeneralLogFile(logMessage, LogRecordType.ACTION);
        pw.flush();
        pw.close();

    }

    void appendToGeneralLogFile(String message, LogRecordType type){
        general = main.generalLogFile;

        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        FileWriter fw;
        try {
            fw = new FileWriter(general, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PrintWriter pw = new PrintWriter(fw);

        pw.println(String.format("(%s)-[General/%s]: " + message, format.format(now), type));
        pw.flush();
        pw.close();
    }



    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
