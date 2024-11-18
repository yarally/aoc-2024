package com.yarally.aoc24.library;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public abstract class AbstractSolution<T> {

    protected Logger logger = Logger.getLogger(GetDay());

    public AbstractSolution() {
        // Remove default handlers
        Logger rootLogger = Logger.getLogger("");
        for (Handler handler : rootLogger.getHandlers()) {
            rootLogger.removeHandler(handler);
        }

        // Create a custom console handler
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SimpleFormatter() {
            @Override
            public String format(LogRecord record) {
                return String.format(
                    "%s (%s) | %s\n",
                    record.getLevel(),
                    record.getLoggerName(),
                    record.getMessage()
                );
            }
        });

        // Add the custom handler to the logger
        rootLogger.addHandler(consoleHandler);
    }

    protected abstract String getInput();

    protected abstract T parse(String input);

    protected abstract String solve(T input);

    private String GetDay() {
        var cleaned = getInput().split(".txt")[0];
        return cleaned.replaceAll("day(\\d+)", "Day $1");
    }

    public String SolvePuzzle() {
        long t0 = System.nanoTime();
        var parsedInput = parse(getInput());
        var solution = solve(parsedInput);
        var duration = (System.nanoTime() - t0) / Math.pow(10, 9);
        logger.info(String.format("%fs | %s", duration,solution));
        return solution;
    }

    public String SolvePuzzle(boolean skip) {
        if (skip) {
            logger.info("SKIPPED");
            return null;
        }
        return SolvePuzzle();
    }
}
