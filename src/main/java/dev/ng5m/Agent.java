package dev.ng5m;

import java.lang.instrument.Instrumentation;

public class Agent {
    public static Instrumentation instrumentation;

    public static void premain(String args, Instrumentation inst) {
        instrumentation = inst;
    }

    public static long getObjectSize(Object object) {
        if (instrumentation == null) {
            throw new IllegalStateException("Agent not initialized");
        }
        return instrumentation.getObjectSize(object);
    }

}
