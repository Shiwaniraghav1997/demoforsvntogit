/**
 * File:LabelTag.java
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.web.Doc41Tags;

/**
 * Tag handler class  to Translate given label to specific language.
 * 
 * @author ezzqc
 */
public class LabelTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	
    public static final String TAGS = "tags";
  
    /** the tag to display */
    private String label;
    
    private Map<String, Object> params = new HashMap<String, Object>();
    
    private String color;
    
    private String ignore;
    
    private String escape;
    
    private String nbsp;
    
    boolean noEsc = false;
    boolean jsEsc = false;
    boolean isNbsp = false;
      
    private String merge(int i, String[] tokens) {
        StringBuilder result = new StringBuilder(tokens[i]);
        for (int j = i+1; j < tokens.length; j++) {
            if (!tokens[j].equals(ignore)) {
                result.append("."+tokens[j]);
            }
        }
        return result.toString();
    }
    
    /* (non-Javadoc)
     * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
     */
    public int doEndTag() throws JspException {
        if (null != label && label.length() > 0) {
        	noEsc = "false".equals(escape);
        	jsEsc = "js".equals(escape);
        	isNbsp = Boolean.parseBoolean(nbsp);
        	
            JspWriter printer = pageContext.getOut();
            Doc41Tags translations = (Doc41Tags) pageContext.getRequest().getAttribute(TAGS);
            try {

                if (translations != null) {
                    StringBuffer out = new StringBuffer();
                    if (color != null) {
                        out.append("<div style=\"color:" + color + "\">");
                    }

                    String transText = "";
                    String[] tokens =  label.split("\\.");

                    for (int i = 0; i < tokens.length; i++) {
                        transText = getLabelTag(merge(i, tokens), translations, true,false);
                        if (!isDummyTranslation(transText)){
                            break;
                        }
                    }
                    if (isDummyTranslation(transText)) {
                        transText = getLabelTag(label, translations, false,true);
                    }
                    
                    out.append(transText);
                    if (color != null) {
                        out.append("</div>");
                    }
                    printer.print(out.toString());
                } else {
                	ServletRequest request = pageContext.getRequest();
                	String uri="";
                	if(request instanceof HttpServletRequest){
                		uri=((HttpServletRequest)request).getRequestURI();
                	}
                    Doc41Log.get().error(LabelTag.class, null,
                            "Translating the Label " + "'" + label + "'" + ", No Tags found (uri: "+uri+").");
                    printer.print("/" + label + "/");
                }
            } catch (IOException e) {
                throw new JspException("Unable to Translate given Label " + "'" + label + "' "
                        + e.getMessage(),e);
            }
        }
        return super.doEndTag();
    }

	private boolean isDummyTranslation(String transText) {
		if (noEsc || jsEsc) {
			return transText.startsWith("[");
		} else {
			return transText.startsWith("&#91;");
		}
	}
    
    /**
     * Inspects the given <code>Tags</code> to find  the translated text for the label.
     * If no text is found, a second search with upper or lower case will be tried. 
     * @return
     */
    private String getLabelTag(String label, Doc41Tags translations, boolean ignoreSensitiv,boolean monitorUntranslated) {
        String transText = "";
        
        if (null != label && label.length() > 0) {
            /* Extract keys and values from params-map to expand translation as template */
            List<String> allParamNamesList = new ArrayList<String>();
            List<Object> allParamsList = new ArrayList<Object>();

            for (String key : params.keySet()) {
                Object value = params.get(key);
                allParamNamesList.add(StringUtils.upperCase(key));
                allParamsList.add(value);
            }

            Object[] values = allParamsList.toArray(new Object[allParamsList.size()]);
            String[] keys   = allParamNamesList.toArray(new String[allParamNamesList.size()]);

        	if (noEsc) {
        		transText =  translations.getTagNoEscNoUntranslatedMonitor(label);
        	} else if(jsEsc){
                transText =  translations.getTagJSNoUntranslatedMonitor(label, values, keys);
            } else {
        		transText =  translations.getTagNoUntranslatedMonitor(label);
        	}
        	if(isNbsp){
        	    transText = transText.replace(" ", "&nbsp;");
        	}
            
            if (isDummyTranslation(transText)) {
                String labelChanged = label;
                // no translation found and the label embedded in brackets is returned
                //f.e.: &#91;nextInspection&#93;
                
                if (ignoreSensitiv) {
                    if(Character.UPPERCASE_LETTER == Character.getType(label.charAt(0))){
                        labelChanged = Character.toLowerCase(label.charAt(0)) + label.substring(1);
                    }
                    if(Character.LOWERCASE_LETTER == Character.getType(label.charAt(0))){
                        labelChanged = Character.toUpperCase(label.charAt(0)) + label.substring(1);
                    }
                }
                if(monitorUntranslated){
	                if (noEsc) {
	            		transText =  translations.getTagNoEsc(labelChanged);
	            	} else if(jsEsc){
	                    transText =  translations.getTagJS(labelChanged, values, keys);
	                } else {
	            		transText =  translations.getTag(labelChanged);
	            	}
                } else {
                	if (noEsc) {
	            		transText =  translations.getTagNoEscNoUntranslatedMonitor(labelChanged);
	            	} else if(jsEsc){
	                    transText =  translations.getTagJSNoUntranslatedMonitor(labelChanged, values, keys);
	                } else {
	            		transText =  translations.getTagNoUntranslatedMonitor(labelChanged);
	            	}
                }
                
            }
        }
        return transText;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }
    
    public void setParams(Map<String, Object> pParams) {
        params = pParams;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setIgnore(String ignore) {
        this.ignore = ignore;
    }

	public void setEscape(String escape) {
		this.escape = escape;
	}
	
	public void setNbsp(String nbsp) {
        this.nbsp = nbsp;
    }

}
