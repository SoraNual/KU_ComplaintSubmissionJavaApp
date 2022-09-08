package ku.cs.form.services;

public interface Registration {

    String registrationCheck(String name, String username, String password, String confirmPassword);
    boolean usernameValidationCheck(String newUserName);
    boolean confirmationPasswordCheck(String password, String confirmationPassword);

}
