package com.nitya.billingapp.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	String greetServer(int exUnit,String exProd,String exPrice,int inUnit,String inProd,String inPrice,String name) throws IllegalArgumentException;
}
