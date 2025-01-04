package com.wonkglorg.utilitylib.serializer;

import net.kyori.adventure.text.Component;
import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

import java.util.List;

public class ComponentUtil{
	private ComponentUtil() {
		//utility class
	}
	
	/**
	 * Creates a component from a string using miniMessage formatting
	 *
	 * @param message The message to convert
	 * @return The converted component
	 */
	public static Component deserialize(final String message) {
		return miniMessage().deserialize(message);
	}
	
	/**
	 * Creates a list of components from a list of strings using miniMessage formatting
	 *
	 * @param messages The messages to convert
	 * @return The converted list of components
	 */
	public static List<Component> deserialize(final List<String> messages) {
		return messages.stream().map(miniMessage()::deserialize).toList();
	}
	
	/**
	 * Serialize a component to a string
	 *
	 * @param message The component to serialize
	 * @return The serialized string
	 */
	public static String serialize(final Component message) {
		return miniMessage().serialize(message);
	}
	
	/**
	 * Serialize a list of components to a list of strings
	 *
	 * @param messages The list of components to serialize
	 * @return The list of serialized strings
	 */
	public static List<String> serialize(final List<Component> messages) {
		return messages.stream().map(miniMessage()::serialize).toList();
	}
	
}
