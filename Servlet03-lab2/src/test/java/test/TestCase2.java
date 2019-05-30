package test;

import java.util.List;

import org.junit.Test;

import dao.UserDAO;
import entity.User;

public class TestCase2 {
	@Test
	public void test1() throws Exception {
		UserDAO dao = new UserDAO();
		List<User> users = dao.findAll();
		System.out.println(users);
	}
}
