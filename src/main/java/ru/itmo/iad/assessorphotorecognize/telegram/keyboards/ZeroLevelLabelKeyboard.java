package ru.itmo.iad.assessorphotorecognize.telegram.keyboards;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import ru.itmo.iad.assessorphotorecognize.domain.ZeroLevelLabel;

@Service
public class ZeroLevelLabelKeyboard {

	public InlineKeyboardMarkup getKeyboard(String photoId, boolean isHoneypot) {
		InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

		List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

		for (ZeroLevelLabel label : ZeroLevelLabel.values()) {
			InlineKeyboardButton button = new InlineKeyboardButton();
			button.setText(label.getButtonText());
			button.setCallbackData(String.format("zero_level_label %s %s %s", 
					photoId, 
					label.getButtonCode(), 
					isHoneypot ? "true" : "false"));

			List<InlineKeyboardButton> row = new ArrayList<>();
			row.add(button);

			rowList.add(row);
		}

		InlineKeyboardButton button = new InlineKeyboardButton();
		button.setText("Пропустить");
		button.setCallbackData("skip");
		
		rowList.add(List.of(button));
		
		markup.setKeyboard(rowList);

		return markup;
	}

}
