package com.servicelive.orderfulfillment.command.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class SOCommandArgHelper {
	private static final String ARG_PRFX = "soCmdArg";
	
	public static String resolveArgName(int argIdx) {
		return ARG_PRFX + argIdx;
	}
	
	public static int extractIntArg(Map<String, Object> processVariables, int argIdx) {
		return Integer.parseInt((String) processVariables.get(resolveArgName(argIdx)));
	}

	public static long extractLongArg(Map<String, Object> processVariables, int argIdx) {
		return Long.parseLong((String) processVariables.get(resolveArgName(argIdx)));
	}
	public static String extractStringArg(Map<String, Object> processVariables, int argIdx) {
		return (String) processVariables.get(resolveArgName(argIdx));
	}

    public static List<String> extractAllCommandArgs(Map<String, Object> processVariables) {
        SortedMap<String, String> sortedMap = new TreeMap<String, String>();
        for (String key : processVariables.keySet()) {
            if (key.startsWith(ARG_PRFX)) {
                sortedMap.put(key, (String)processVariables.get(key));
            }
        }
        List<String> commandArgList = new ArrayList<String>();
        for (String key : sortedMap.keySet()) {
            commandArgList.add(sortedMap.get(key));
        }

        return commandArgList;
    }
}
