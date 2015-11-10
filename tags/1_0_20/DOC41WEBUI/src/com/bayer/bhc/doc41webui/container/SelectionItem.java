/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.container;

/**
 * Selection item. Describes an item in combo boxes.
 * 
 * @author ezzqc
 */
public class SelectionItem extends ItemObject {
	private static final long serialVersionUID = 1L;
	
    /** used for unique identification. */
    private String value="";

    /** used for display. */
    private String label;
    
    private String color; // Default "#008080"
    
    private boolean inactive = false;
    
    private boolean defaultValue = false;

    // legacy
    public String getCode() {
        return value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public boolean isInactive() {
		return inactive;
	}

	public void setInactive(boolean inactive) {
		this.inactive = inactive;
	}

	public boolean isDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(boolean defaultValue) {
		this.defaultValue = defaultValue;
	}

	public SelectionItem() {
        super();
    }

    public SelectionItem(final String value, final String label) {
        super();
        this.value = value;
        this.label = label;
    }
    
    public SelectionItem(final String value, final String label, final String color) {
        this(value, label);
        this.color = color;
    }
    
    public SelectionItem(final String value, final String label, boolean inactiveFlag) {
        super();
        this.value = value;
        this.label = label;
        inactive = inactiveFlag;
    }
    
    public SelectionItem(final String value, final String label, boolean inactiveFlag, boolean isDefault) {
        super();
        this.value = value;
        this.label = label;
        inactive = inactiveFlag;
        defaultValue = isDefault;
    }
 
}
