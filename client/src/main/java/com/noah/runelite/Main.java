package com.noah.runelite;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class Main {
    public static void main(String[] args) throws Exception {
        ExternalPluginManager.loadBuiltin(AggroResetPlugin.class);
        RuneLite.main(args);
    }
}