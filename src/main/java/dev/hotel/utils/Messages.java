package dev.hotel.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Messages {

	private List<String> messages;

	/**
	 * Constructor
	 *
	 * @param messages
	 */
	public Messages() {
		super();
		this.messages = new ArrayList<String>();
	}

	/**
	 * Constructor
	 *
	 * @param messages
	 */
	public Messages(List<String> messages) {
		super();
		this.messages = messages;
	}

	/**
	 * Constructor
	 *
	 * @param messages
	 */
	public Messages(String message) {
		super();
		this.messages = Arrays.asList(message);
	}

	public boolean isEmpty() {
		return this.messages.isEmpty();
	}

	/**
	 * Ajoute un message à la liste
	 * 
	 * @param message
	 */
	public void addMessage(String message) {
		messages.add(message);
	}

	/**
	 * Getter
	 *
	 * @return the messages
	 */
	public List<String> getMessages() {
		return messages;
	}

	/**
	 * Setter
	 *
	 * @param messages the messages to set
	 */
	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

}
