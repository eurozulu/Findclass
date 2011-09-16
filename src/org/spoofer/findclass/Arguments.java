package org.spoofer.findclass;

import java.util.HashMap;

import java.util.Map;
import java.util.Set;

/**
 * Arguments class manages the command line arguments into named arguments.
 * Each named argument is preceeded by a '-' dash character, followed by the argument name.
 * Any text following the space after the name will be taken as that arguments value.
 * Any text found before the first named argument is captured as an unnamed argument
 * and refered to as a 'null' name in all the methods.
 * 
 * @author robgilham
 **/
public class Arguments {

	public static final String SWITCH = "-";
	private static final String UNNAMED_ARG = "__UNNAMED";
	
	
	private final Map<String, String> arguments;
	public Arguments(String[] args)
	{
		super();
		
		arguments = parseArgs(args);

	}
	
	public String getArgument(String name)
	{
		if (null == name || name.trim().length() < 1)
			name = UNNAMED_ARG;
		
		return arguments.get(name);
	}
	
	public boolean containsArgument(String name)
	{
		if (null == name || name.trim().length() < 1)
			name = UNNAMED_ARG;
		return arguments.containsKey(name);
	}
	
	public int getCount()
	{
		return arguments.size();
	}
	
	public Set<String> getArgumentNames()
	{
		Set<String> result = arguments.keySet();
		if (result.contains(UNNAMED_ARG))
			result.remove(UNNAMED_ARG);
		
		return arguments.keySet();
	}
	
	private Map<String, String> parseArgs(String[] args)
	{
		StringBuffer oneArg = new StringBuffer();
		for (String arg : args)  // combine all args into one.
		{
			if (oneArg.length() > 0)
				oneArg.append(' ');
			oneArg.append(arg);
		}
		
		String[] splitArgs = oneArg.toString().split(SWITCH);
		Map<String, String> newArgs = new HashMap<String, String>();
	
		if (splitArgs.length > 0)
		{
			if (splitArgs[0].trim().length() > 0)
				newArgs.put(UNNAMED_ARG, splitArgs[0].trim());
			
			for (int i=1; i < splitArgs.length; i++)
			{
				int iPos = splitArgs[i].indexOf(' ');
				String name = iPos > 0 ? splitArgs[i].substring(0, iPos).trim() : splitArgs[i].trim();
				String value = iPos > 0 ? splitArgs[i].substring(iPos + 1).trim() : "";
				newArgs.put(name, value);
			}
		}
		return newArgs;
	}
}
