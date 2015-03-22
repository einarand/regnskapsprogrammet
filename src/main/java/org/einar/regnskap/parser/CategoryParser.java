package org.einar.regnskap.parser;

import org.einar.regnskap.Category;

import java.awt.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class CategoryParser {

	public static Category parse(String categoryString) {
		StringTokenizer tokenizer = new StringTokenizer(categoryString, ",");		
		String name = tokenizer.nextToken();			
		new Color(Integer.parseInt(tokenizer.nextToken(), 24));
		ArrayList<String> strings = new ArrayList<String>();
		while (tokenizer.hasMoreTokens()) {
			strings.add(tokenizer.nextToken().toLowerCase());
		}
		return new Category(name, strings);
	}
}
