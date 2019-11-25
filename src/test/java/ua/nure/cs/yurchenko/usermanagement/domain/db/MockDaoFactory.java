package ua.nure.cs.yurchenko.usermanagement.domain.db;

import com.mockobjects.dynamic.Mock;

public class MockDaoFactory extends DaoFactory {

	private Mock mockUserDao;
	
	public MockDaoFactory() {
		mockUserDao = new Mock(UserDao.class);
	}
	
	public Mock getMockUserDao() {
		return mockUserDao;
	}
	
	@Override
	public UserDao getUserDao() {
		// TODO Auto-generated method stub
		return (UserDao) mockUserDao.proxy();
	}

}