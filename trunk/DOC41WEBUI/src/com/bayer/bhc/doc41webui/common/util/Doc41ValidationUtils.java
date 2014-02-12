package com.bayer.bhc.doc41webui.common.util;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.ecim.foundation.basic.StringTool;

public class Doc41ValidationUtils {
    
    private Doc41ValidationUtils(){
        //do not instantiate
    }

	public static void checkMaterialNumber(String value,String fieldName,Errors errors,boolean isMandatory){
		if(StringTool.isTrimmedEmptyOrNull(value)){
			if(isMandatory){
				errors.rejectValue(fieldName,"MatNoMissing");
			}
		} else {
			String trimmedValue = value.trim();
			if(trimmedValue.length()>Doc41Constants.FIELD_SIZE_MATNR){
				errors.rejectValue(fieldName,"TooLong");
			} else {
				numberCheck(trimmedValue,fieldName,errors,8);
			}
		}
	}

	private static void numberCheck(String value, String fieldName,
			Errors errors, int maxSignificant) {
		try {
			Integer intValue = Integer.valueOf(value);
			int length = String.valueOf(intValue).length();
			if(length>maxSignificant){
				errors.rejectValue(fieldName,"TooManySignificant"+maxSignificant);
			}
		} catch (NumberFormatException e) {
			errors.rejectValue(fieldName,"OnlyNumbers");
		}
	}
}
