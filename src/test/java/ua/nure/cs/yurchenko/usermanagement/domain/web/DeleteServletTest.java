package ua.nure.cs.yurchenko.usermanagement.domain.web;

import java.util.Date;

import ua.nure.cs.yurchenko.usermanagement.domain.User;

public class DeleteServletTest extends MockServletTestCase {

	private static final String OK_BUTTON = "Ok";
	private static final String OK_BUTTON_PARAMETER = "ok";
	private static final String USER_SESSION_ATTRIBUTE_NAME = "user";
	private static final String DELETE_QUERY = "delete";
	private static final String LAST_NAME = "Doe";
	private static final String FIRST_NAME = "John";

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testDelete() {
		User user = new User(new Long(1000), FIRST_NAME, LAST_NAME, new Date());
        getMockUserDao().expect(DELETE_QUERY, user);
        setSessionAttribute(USER_SESSION_ATTRIBUTE_NAME, user);
        addRequestParameter(OK_BUTTON_PARAMETER, OK_BUTTON);
        doPost();
	}
}
