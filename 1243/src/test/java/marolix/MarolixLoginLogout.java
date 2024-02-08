package marolix;

public class MarolixLoginLogout 
{

	public static void main(String[] args) throws Throwable {

		login in = new login();
		logout out = new logout();

		boolean loginSuccessful = in.loginBasedOnSystemTime("login");

		if (loginSuccessful) 
		{
			out.logoutBasedOnSystemTime();
		}
		else 
		{
			System.out.println("Logout works when you have successfully logged in.");
		}

		

	}

}
