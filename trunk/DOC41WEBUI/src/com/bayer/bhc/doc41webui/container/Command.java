/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.container;

/**
 * Command class. Send during action request.
 * 
 * @author ezzqc
 */
public final class Command implements Actionable {

    private static final String ABORT_STR = "abort";
    
    private static final String RESET_STR = "reset";
    
    public static final Command ABORT = new Command(ABORT_STR);
    
    public static final Command RESET = new Command(RESET_STR);

    private static final String REFRESH_STR = "refresh";

    public static final Command REFRESH = new Command(REFRESH_STR);
    
    private String action;

    /**
     * Default constructor.
     */
    private Command(final String pCode) {
        this.action = pCode;
    }

    /**
     * @param action
     *            the action to set
     */
    public void setAction(String code) {
        this.action = code;
    }

    /**
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * Returns <code>true</code> if this <code>Command</code> is the same as the o argument.
     *
     * @return <code>true</code> if this <code>Command</code> is the same as the o argument.
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o.getClass() != getClass()) {
            return false;
        }
        Command castedObj = (Command) o;
        return ((this.action == null ? castedObj.action == null : this.action
            .equals(castedObj.action)));
    }
    
    @Override
    public int hashCode() {
    	if(action==null){
    		return 0;
    	}
    	return action.hashCode();
    }
    

}
