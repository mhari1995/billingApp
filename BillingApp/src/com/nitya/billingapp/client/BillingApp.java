package com.nitya.billingapp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class BillingApp implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network " + "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	 int total = 0;
	public void onModuleLoad() {
		final Button sendButton = Button.wrap(Document.get().getElementById("profitButton"));
		final TextBox inProd = new TextBox();
		final TextBox inUnit = new TextBox();
		final TextBox inPrice = new TextBox();
		final TextBox exProd = new TextBox();
		final TextBox exUnit = new TextBox();
		final TextBox exPrice = new TextBox();
		
		
		final TextBox nameField = new TextBox();
		nameField.setText("Invoice and Expense Saved successfully");
		final Label errorLabel = new Label();
		final Label exTotal = new Label();
		final Label inTotal = new Label();
		inPrice.addStyleName("inputField");
		inProd.addStyleName("inputField");
		inUnit.addStyleName("inputField");
		exPrice.addStyleName("inputField");
		exProd.addStyleName("inputField");
		exUnit.addStyleName("inputField");
	
		exProd.setEnabled(false);
		exUnit.setEnabled(false);


		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("invoiceProd").add(inProd);
		RootPanel.get("invoiceUnit").add(inUnit);
		RootPanel.get("invoicePrice").add(inPrice);
		RootPanel.get("expenseProd").add(exProd);
		RootPanel.get("expenseUnit").add(exUnit);
		RootPanel.get("expensePrice").add(exPrice);
		RootPanel.get("errorLabelContainer").add(errorLabel);
		RootPanel.get("expenseTotal").add(exTotal);
		RootPanel.get("invoiceTotal").add(inTotal);
		
		
	inPrice.addKeyboardListener(new KeyboardListener() {
			
			@Override
			public void onKeyUp(Widget sender, char keyCode, int modifiers) {
				// TODO Auto-generated method stub
				exProd.setText(inProd.getText());
				exUnit.setText(inUnit.getText());
			}
			
			@Override 
			public void onKeyPress(Widget sender, char keyCode, int modifiers) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onKeyDown(Widget sender, char keyCode, int modifiers) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		exPrice.addKeyboardListener(new KeyboardListener() {
			
			@Override
			public void onKeyUp(Widget sender, char keyCode, int modifiers) {
				// TODO Auto-generated method stub
				 exTotal.setText(Integer.parseInt(exUnit.getText())*Integer.parseInt(exPrice.getText())+"/-");
			        inTotal.setText(Integer.parseInt(inUnit.getText())*Integer.parseInt(inPrice.getText())+"/-");		            
			        
			}
			
			@Override 
			public void onKeyPress(Widget sender, char keyCode, int modifiers) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onKeyDown(Widget sender, char keyCode, int modifiers) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
		inUnit.addKeyPressHandler(new KeyPressHandler() {
		    @Override
		    public void onKeyPress(KeyPressEvent event) {
		        String input = inUnit.getText();
		        
				exUnit.setText(inUnit.getText());
		        if (!input.matches("[0-9]*")) {
		             errorLabel.setText("Please enter only numbers for units and price");
		             errorLabel.addStyleName("MarginUp");
		            return;
		        }
		    }
		});
		// Focus the cursor on the name field when the app loads
		inProd.setFocus(true);
		inProd.selectAll();
		
		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Saving Billing Info");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final Label invoiceProductLabel = new Label();
		final HTML totalBill = new HTML();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Details :</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Product Name:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.add(new HTML("<br><b>Profit / Loss:</b>"));
		dialogVPanel.add(totalBill);
		
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				errorLabel.setText("");
				sendButton.setEnabled(true);
				sendButton.setFocus(true);
			}
		});

		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				sendNameToServer();
				total = Integer.parseInt(inUnit.getText())*Integer.parseInt(inPrice.getText()) -Integer.parseInt(exUnit.getText())*Integer.parseInt(exPrice.getText());
				totalBill.setText(total+"");
				System.out.println(total+"is the amount of profit or loss");
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendNameToServer();
					
				}
			}

			/**
			 * Send the name from the nameField to the server and wait for a response.
			 */
			private void sendNameToServer() {
				// First, we validate the input.
				errorLabel.setText("");
				final String textToServer = nameField.getText();
				
				String invoiceProduct = inProd.getText();
				int invoiceUnit = Integer.parseInt(inUnit.getText());
				String invoicePrice = inPrice.getText();
				
				
				String expenseProduct = exProd.getText();
				int expenseUnit = Integer.parseInt(exUnit.getText());
				String expensePrice = exPrice.getText();
				
			
				// Then, we send the input to the server.
				sendButton.setEnabled(false);
				invoiceProductLabel.setText(invoiceProduct);
				textToServerLabel.setText(textToServer);
				serverResponseLabel.setText("");
				
				greetingService.greetServer(expenseUnit,expenseProduct,expensePrice,invoiceUnit,invoiceProduct,invoicePrice,textToServer, new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						// Show the RPC error message to the user
						dialogBox.setText("Saving Billing Info - Failure");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(SERVER_ERROR);
						dialogBox.center();
						closeButton.setFocus(true);
					}
					
					
					
					public void onSuccess(String result) {
										
						dialogBox.setText("Saving Billing Info");
						serverResponseLabel.removeStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(result);
						dialogBox.center();
						closeButton.setFocus(true);
					
					}
				});
			}
		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		sendButton.addClickHandler(handler);
		inProd.addKeyUpHandler(handler);
	}
}
