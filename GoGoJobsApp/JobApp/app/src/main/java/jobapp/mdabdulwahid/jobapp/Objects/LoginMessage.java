package jobapp.mdabdulwahid.jobapp.Objects;

/**
 *
 */
public class LoginMessage {

    private String message;
    private int access;
    private String account_type;

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }


    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }


    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }







}
