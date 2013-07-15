/**
 * File:LabelTag.java
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.tags;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.ecim.foundation.business.sbeanaccess.Tags;

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
    
    private String color;
    
    private String ignore;
    
    private String escape;
    
    boolean noEsc = false;
      
    private String merge(int i, String[] tokens) {
        String result = tokens[i];
        for (int j = i+1; j < tokens.length; j++) {
            if (!tokens[j].equals(ignore)) {
                result += "."+tokens[j];
            }
        }
        return result;
    }
    
//    private String[] split(String str) {
//        
//        ArrayList tokens = new ArrayList();
//        StringTokenizer tok = new StringTokenizer(str, ".");
//        
//        while (tok.hasMoreTokens()) {
//            tokens.add(tok.nextToken());
//        }       
//        
//        return (String[])tokens.toArray(new String[]{});
//    }
    
    /* (non-Javadoc)
     * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
     */
    public int doEndTag() throws JspException {
        if (null != label && label.length() > 0) {
        	noEsc = "false".equals(escape);
        	
            JspWriter printer = pageContext.getOut();
            Tags translations = (Tags) pageContext.getRequest().getAttribute(TAGS);
            try {

                if (translations != null) {
                    StringBuffer out = new StringBuffer();
                    if (color != null) {
                        out.append("<div style=\"color:" + color + "\">");
                    }

                    String transText = "";
                    String[] tokens =  label.split("\\."); //split(label);

                    for (int i = 0; i < tokens.length; i++) {
                        transText = getLabelTag(merge(i, tokens), translations, true);
                        if (!transText.startsWith("&#91;"))
                            break;
                    }
                    if (transText.startsWith("&#91;")) {
                        transText = getLabelTag(label, translations, false);
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
                        + e.getMessage());
            }
        }
        return super.doEndTag();
    }
    
    /**
     * Inspects the given <code>Tags</code> to find  the translated text for the label.
     * If no text is found, a second search with upper or lower case will be tried. 
     * @return
     */
    private String getLabelTag(String lab, Tags translations, boolean ignoreSensitiv) {
        String transText = "";
        boolean startsWithBrake = false;
        
        if (null != lab && lab.length() > 0) {
        	if (noEsc) {
        		transText =  translations.getTagNoEsc(lab);
        		startsWithBrake = transText.startsWith("[");
        	} else {
        		transText =  translations.getTag(lab);
        		startsWithBrake = transText.startsWith("&#91;");
        	}
            
            if (startsWithBrake) {
                String labelChanged = lab;
                // no translation found and the label embedded in brackets is returned
                //f.e.: &#91;nextInspection&#93;
                
                if (ignoreSensitiv) {
                    if(Character.UPPERCASE_LETTER == Character.getType(lab.charAt(0))){
                        labelChanged = Character.toLowerCase(lab.charAt(0)) + lab.substring(1);
                    }
                    if(Character.LOWERCASE_LETTER == Character.getType(lab.charAt(0))){
                        labelChanged = Character.toUpperCase(lab.charAt(0)) + lab.substring(1);
                    }
                }
                if (noEsc) {
            		transText =  translations.getTagNoEsc(labelChanged);
            	} else {
            		transText =  translations.getTag(labelChanged);
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
    
    public void setColor(String color) {
        this.color = color;
    }

    public void setIgnore(String ignore) {
        this.ignore = ignore;
    }

	public void setEscape(String escape) {
		this.escape = escape;
	}

}
