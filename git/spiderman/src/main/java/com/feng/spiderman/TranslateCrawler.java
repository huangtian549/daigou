package com.feng.spiderman;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

public class TranslateCrawler {


	    public static void main(String args[]){
	    	Translate translate = TranslateOptions.getDefaultInstance().getService();

	        // The text to translate
	        String text = "Hello, world!";

	        // Translates some text into Russian
	        Translation translation =
	            translate.translate(
	                text,
	                TranslateOption.sourceLanguage("en"),
	                TranslateOption.targetLanguage("zh-CN"));


	        System.out.printf("Text: %s%n", text);
	        System.out.printf("Translation: %s%n", translation.getTranslatedText());
	    }

	}
