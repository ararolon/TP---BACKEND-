package py.com.progweb.prueba.ejb;
public class MailEvent {
    private String to; //recipient address
    private String message;
    private String subject;

    public String getMessage() {
        return message;
    }

    public String getSubject() {
        return subject;
    }

    public String getTo() {
        return to;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTo(String to) {
        this.to = to;
    }
    //getters and setters
}