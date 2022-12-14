package ru.itmo.iad.assessorphotorecognize.telegram.commands.general;

import org.springframework.stereotype.Service;

import ru.itmo.iad.assessorphotorecognize.telegram.commands.AbsCommand;
import ru.itmo.iad.assessorphotorecognize.telegram.response.Response;
import ru.itmo.iad.assessorphotorecognize.telegram.response.StringResponse;

@Service
public class StartCommand extends AbsCommand {

    @Override
    public Response<?> execute() {

        String builder = "Привет! Это бот, с помощью которого ты можешь помочь нам разметить фотографии.\n\n" +
                "Все просто: ты получаешь фотографию и выбираешь, какой класс больше всего подходит " +
                "главному объекту на изображении или идее изображения в целом. " +
                "Людей и животных на фотографиях можно игнорировать.\n\n"
                + "Мы написали подробное описание классов - обращайся к нему, если что-то не понятно." +
                System.lineSeparator() +
                System.lineSeparator() +
                "https://docs.google.com/document/d/1pG41a8pfhYZNnzaHU08J-A06_P2_QkFhOHgTluehetU"
                + "\n\nЧтобы начать, отправь команду /next.";

        return new StringResponse(builder);
    }

}
