package v2.sideproject.store.user_management_service.adapter.out.constants;

public class UsersConstants {

    private UsersConstants() {

    }

    public static final String  STATUS_201 = "201";
    public static final String  MESSAGE_201 = "User created successfully";
    public static final String  STATUS_200 = "200";
    public static final String  MESSAGE_200 = "Request processed successfully";
    public static final String  STATUS_417 = "417";
    public static final String  MESSAGE_417_UPDATE= "Update operation failed. Please try again or contact Dev team";
    public static final String  MESSAGE_417_DELETE= "Delete operation failed. Please try again or contact Dev team";
    public static final String DUPLICATED_EMAIL = "Duplicate User's email";
    public static final String PASSWORD_MISMATCH = "Password mismatch";
}
