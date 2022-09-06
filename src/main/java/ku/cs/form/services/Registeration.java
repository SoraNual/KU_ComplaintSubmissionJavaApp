package ku.cs.form.services;

public interface Registeration {

    String registrationCheck(String name, String username, String password, String confirmPassword);
    boolean usernameValidationCheck(String newUserName);
    boolean confirmationPasswordCheck(String password, String confirmationPassword);

}
