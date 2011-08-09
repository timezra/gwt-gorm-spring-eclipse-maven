package timezra.blog.gwt.client;

import timezra.blog.gwt.shared.GreetingServiceAsync;
import timezra.blog.gwt.shared.FieldVerifier;
import timezra.blog.gwt.shared.GreetingService;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * GWT JUnit <b>integration</b> tests must extend GWTTestCase. Using <code>"GwtTest*"</code> naming pattern exclude them
 * from running with surefire during the test phase.
 */
public class GwtTestClient extends GWTTestCase {

	/**
	 * Must refer to a valid module that sources this class.
	 */
	@Override
	public String getModuleName() {
		return "timezra.blog.gwt.ClientJUnit";
	}

	/**
	 * Tests the FieldVerifier.
	 */
	public void testFieldVerifier() {
		assertFalse(FieldVerifier.isValidName(null));
		assertFalse(FieldVerifier.isValidName(""));
		assertFalse(FieldVerifier.isValidName("a"));
		assertFalse(FieldVerifier.isValidName("ab"));
		assertFalse(FieldVerifier.isValidName("abc"));
		assertTrue(FieldVerifier.isValidName("abcd"));
	}

	/**
	 * This test will send a request to the server using the greetServer method in GreetingService and verify the
	 * response.
	 */
	public void testGreetingService() {
		// Create the service that we will test.
		final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
		final ServiceDefTarget target = (ServiceDefTarget) greetingService;
		target.setServiceEntryPoint(GWT.getModuleBaseURL() + "Client/greet");

		// Since RPC calls are asynchronous, we will need to wait for a response
		// after this test method returns. This line tells the test runner to wait
		// up to 10 seconds before timing out.
		delayTestFinish(10000);

		// Send a request to the server.
		greetingService.greetServer("GWT User", new AsyncCallback<String>() {
			@Override
			public void onFailure(final Throwable caught) {
				// The request resulted in an unexpected error.
				fail("Request failure: " + caught.getMessage());
			}

			@Override
			public void onSuccess(final String result) {
				// Verify that the response is correct.
				assertTrue(result.startsWith("Hello, GWT User!"));

				// Now that we have received a response, we need to tell the test runner
				// that the test is complete. You must call finishTest() after an
				// asynchronous test finishes successfully, or the test will time out.
				finishTest();
			}
		});
	}

}
