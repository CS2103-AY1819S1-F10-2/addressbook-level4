package loanbook.logic;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import loanbook.model.Model;
import loanbook.model.loan.Loan;

/**
 * The class is used for create reminder email and send the email.
 */
public class SendReminder {
    private String myEmailAccount;
    private String myEmailPassword;
    private String myEmailSmtpHost = "smtp.gmail.com";
    private Loan targetLoan;

    public SendReminder(Model model, String myEmailPassword, Loan targetLoan) {
        this.myEmailAccount = model.getMyEmail();
        this.myEmailPassword = myEmailPassword;
        this.targetLoan = targetLoan;
    }

    /**
     * Connect to your email and send a reminder email.
     *
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    public void send() throws MessagingException, UnsupportedEncodingException {
        //@@author Kelly9373-reused
        //Reused from https://github.com/clk528/maven-spring/blob/master/src/main/java/com/clk/library/sendmail.java
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", myEmailSmtpHost);
        props.setProperty("mail.smtp.auth", "true");

        final String smtpPort = "465";
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);

        Session session = Session.getInstance(props);
        session.setDebug(true);

        MimeMessage message = createReminderEmail(session, myEmailAccount);

        Transport transport = session.getTransport();
        transport.connect(myEmailAccount, myEmailPassword);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
        //@@author
    }

    /**
     * Create a reminder email.
     *
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    public MimeMessage createReminderEmail(Session session, String sendMail)
            throws MessagingException, UnsupportedEncodingException {

        StringBuffer content = new StringBuffer();
        content.append("Dear " + targetLoan.getName().value + ",<br>");
        content.append("<br>This is a gentle reminder for your loan. You rented "
                + targetLoan.getBike().getName().value);
        content.append(" on " + targetLoan.getLoanStartTime().toString() + " with rate $");
        content.append(targetLoan.getLoanRate().toString());
        content.append("/hr. Please remember to return your bike after use as soon as possible.<br>");
        content.append("<br>Thank you for using Loan Book! Enjoy your trip!<br>");
        content.append("<br>Best Regards,<br>");
        content.append("LoanBook Team");

        //@@author Kelly9373-reused
        //Reused from https://github.com/clk528/maven-spring/blob/master/src/main/java/com/clk/library/sendmail.java
        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress(sendMail, "LoanBook", "UTF-8"));
        message.setRecipient(MimeMessage.RecipientType.TO,
                new InternetAddress(targetLoan.getEmail().value, targetLoan.getName().value, "UTF-8"));
        message.setSubject("Your trip with Loan Book", "UTF-8");
        message.setContent(content.toString(), "text/html");
        message.setSentDate(new Date());
        message.saveChanges();

        return message;
        //@@author
    }
}
