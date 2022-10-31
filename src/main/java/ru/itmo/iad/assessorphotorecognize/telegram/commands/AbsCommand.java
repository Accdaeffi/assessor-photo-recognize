package ru.itmo.iad.assessorphotorecognize.telegram.commands;

import ru.itmo.iad.assessorphotorecognize.telegram.response.Response;

public abstract class AbsCommand {
	
	public abstract Response<?> execute();
}
