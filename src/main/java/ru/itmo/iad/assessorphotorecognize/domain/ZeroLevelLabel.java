package ru.itmo.iad.assessorphotorecognize.domain;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ZeroLevelLabel {
	INDOOR("indoor", "Внутри помещения"), 
	OUTDOOR_NATURAL("outdoor_natural", "Снаружи, нерукотворное"),
	OUTDOOR_MAN_MADE("outdoor_man_made", "Снаружи, рукотворное");

	private String buttonCode;
	private String buttonText;

	public static ZeroLevelLabel getByButtonCode(String buttonCode) throws Exception {
		return Arrays.stream(ZeroLevelLabel.values())
				.filter(label -> label.getButtonCode().equals(buttonCode))
				.findFirst()
				.get(); 
	}

}
