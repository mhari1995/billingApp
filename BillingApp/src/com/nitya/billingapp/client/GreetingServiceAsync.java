package com.nitya.billingapp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(int exUnit,String exProd,String exPrice,int inUnit,String inProd,String inPrice,String input, AsyncCallback<String> callback) throws IllegalArgumentException;
}
