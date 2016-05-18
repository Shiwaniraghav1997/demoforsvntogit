package com.bayer.bhc.doc41webui.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTag;

import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.tags.form.AbstractHtmlElementBodyTag;
import org.springframework.web.servlet.tags.form.FormTag;
import org.springframework.web.servlet.tags.form.TagWriter;

import com.bayer.ecim.foundation.business.sbeanaccess.Tags;

/**
 * Modified version of Spring <code>Errors</code> tag to do the translation of error codes before displaying to the user.
 * 
 * @author MBGHY
 *
 */
public class ErrorTag extends AbstractHtmlElementBodyTag implements BodyTag {

	private static final long serialVersionUID = 1L;
	public static final String TAGS = "tags";
	
	private static final String DEFAULT_CSS_STYLE = "color:red;";
	
	

    /**
     * The HTML '<code>span</code>' tag.
     */
    public static final String SPAN_TAG = "span";


    private String element = SPAN_TAG;

    private String delimiter = "<br/>";

    /**
     * Stores any value that existed in the 'errors messages' before the tag was started.
     */
    
    public ErrorTag() {
		super();
		setCssStyle(DEFAULT_CSS_STYLE);
	}

    /**
     * Set the HTML element must be used to render the error messages.
     * <p>Defaults to an HTML '<code>&lt;span/&gt;</code>' tag.
     */
    public void setElement(String element) {
        Assert.hasText(element, "'element' cannot be null or blank");
        this.element = element;
    }

    /**
     * Get the HTML element must be used to render the error messages.
     */
    public String getElement() {
        return this.element;
    }

    /**
     * Set the delimiter to be used between error messages.
     * <p>Defaults to an HTML '<code>&lt;br/&gt;</code>' tag.
     */
    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    /**
     * Return the delimiter to be used between error messages.
     */
    public String getDelimiter() {
        return this.delimiter;
    }


    /**
     * Get the value for the HTML '<code>id</code>' attribute.
     * <p>Appends '<code>.errors</code>' to the value returned by {@link #getPropertyPath()}
     * or to the model attribute name if the <code>&lt;form:errors/&gt;</code> tag's
     * '<code>path</code>' attribute has been omitted.
     * @return the value for the HTML '<code>id</code>' attribute
     * @see #getPropertyPath()
     */
    @Override
    protected String autogenerateId() throws JspException {
        String path = getPropertyPath();
        if ("".equals(path) || "*".equals(path)) {
            path = (String) this.pageContext.getAttribute(
                    FormTag.MODEL_ATTRIBUTE_VARIABLE_NAME, PageContext.REQUEST_SCOPE);
        }
        return StringUtils.deleteAny(path, "[]") + ".errors";
    }

    /**
     * Get the value for the HTML '<code>name</code>' attribute.
     * <p>Simply returns <code>null</code> because the '<code>name</code>' attribute
     * is not a validate attribute for the '<code>span</code>' element.
     */
    @Override
    protected String getName() throws JspException {
        return null;
    }

    /**
     * Should rendering of this tag proceed at all?
     * <p>Only renders output when there are errors for the configured {@link #setPath path}.
     * @return <code>true</code> only when there are errors for the configured {@link #setPath path}
     */
    @Override
    protected boolean shouldRender() throws JspException {
        try {
            return getBindStatus().isError();
        } catch (IllegalStateException ex) {
            // Neither BindingResult nor target object available.
            return false;
        }
    }

    @Override
    protected void renderDefaultContent(TagWriter tagWriter) throws JspException {
    	 Tags translations = (Tags) pageContext.getRequest().getAttribute(TAGS);
        tagWriter.startTag(getElement());
        writeDefaultAttributes(tagWriter);
        String delimiterDisplayString = ObjectUtils.getDisplayString(evaluate("delimiter", getDelimiter()));
        String[] errorCodes = getBindStatus().getErrorCodes();
        for (int i = 0; i < errorCodes.length; i++) {
            String errorMessage = errorCodes[i];
            if (i > 0) {
                tagWriter.appendValue(delimiterDisplayString);
            }
            //special handling for error codes=>MBGHY
            tagWriter.appendValue(translations.getTagNoEsc(getDisplayString(errorMessage)));
        }
        tagWriter.endTag();
    }

    
}
