package ru.itmo.iad.assessorphotorecognize.telegram.commands.general;

import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import ru.itmo.iad.assessorphotorecognize.telegram.commands.AbsCommand;
import ru.itmo.iad.assessorphotorecognize.telegram.response.StringResponse;

@Service
@Scope("prototype")
@NoArgsConstructor
public class HelpCommand extends AbsCommand {

	@Override
	public StringResponse execute() {

		String builder = "Описание классов с примерами:" +
				System.lineSeparator() +
				System.lineSeparator() +
				"https://docs.google.com/document/d/1pG41a8pfhYZNnzaHU08J-A06_P2_QkFhOHgTluehetU";
		
		return new StringResponse(builder);
	}

}
