package main;

import java.util.Random;

import admin.AdminMainForm;
import connect.Connect;
import form.LoginForm;
import form.RegisterForm;
import user.UserMainForm;

public class Main {

	Connect con = new Connect();
	
	public Main() {
//		new LoginForm();
		new RegisterForm();
//		new AdminMainForm();
//		new UserMainForm();
		
	}

	public static void main(String[] args) {
		new Main();
	}

}
