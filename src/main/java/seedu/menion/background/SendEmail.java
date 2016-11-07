package seedu.menion.background;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.*;
import javax.mail.internet.*;

import seedu.menion.commons.core.Messages;
import seedu.menion.commons.core.MotivationalQuotes;
import seedu.menion.logic.commands.RemindCommand;
import seedu.menion.logic.commands.UnremindCommand;
import seedu.menion.model.activity.ReadOnlyActivity;

//@@author A0139164A
public class SendEmail {

    public static final String MESSAGE_BODY = "Hey you missed a task deadline! Here is the info: ";
    public static final String EMAIL_SUBJECT = "Menion! You missed a deadline!";
    
    final String senderEmailID = "menioncena@gmail.com";
    final String senderPassword = "johncena2103";
    final String emailSMTPserver = "smtp.gmail.com";
    final String emailServerPort = "465";
    
    /**
     * Variables that will be set in send method.
     */
    String userEmail = null;
    String emailBody = null;
    String motivationalQuote = null;
    String remind = UnremindCommand.REMINDER_OFF;

    // public void send(ReadOnlyActivity dub) {

    public void send(ReadOnlyActivity outdated) throws FileNotFoundException, MessagingException {

        // Retrieve the email of the user from the txt file.
        Scanner fromFile = new Scanner(new File(Messages.MESSAGE_FILE));
        remind = fromFile.next();
        fromFile.nextLine(); // Skips a line.
        userEmail = fromFile.nextLine();
        fromFile.close(); // close input file stream

        if (remind.equals(RemindCommand.REMINDER_ON)) {

            Properties props = new Properties();
            props.put("mail.smtp.user", senderEmailID);
            props.put("mail.smtp.host", emailSMTPserver);
            props.put("mail.smtp.port", emailServerPort);
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.auth", "true");
            // props.put("mail.smtp.debug", "true");
            props.put("mail.smtp.socketFactory.port", emailServerPort);
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.socketFactory.fallback", "false");

            SecurityManager security = System.getSecurityManager();

            Authenticator auth = new SMTPAuthenticator();
            Session session = Session.getInstance(props, auth);
            // session.setDebug(true);

            MimeMessage msg = new MimeMessage(session);
            emailBody = getMessageBody(outdated);
            msg.setText(emailBody);
            msg.setSubject(EMAIL_SUBJECT);
            msg.setFrom(new InternetAddress(senderEmailID));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
            Transport.send(msg);
        }
        else {
            throw new MessagingException();
        }
    }

    public String getMessageBody(ReadOnlyActivity outdated) {
        StringBuilder build = new StringBuilder();

        build.append(MESSAGE_BODY);
        build.append("\n\n");
        build.append("Task name: " + outdated.getActivityName().toString());
        build.append("\n");
        build.append("Task note: " + outdated.getNote().toString());
        build.append("\n");
        build.append("Task date: " + outdated.getActivityStartDate().toString());
        build.append("\n");
        build.append("Task time: " + outdated.getActivityStartTime().toString());
        build.append("\n\n");
        build.append("'");
        build.append(MotivationalQuotes.getRandomQuote());
        build.append("'");

        return build.toString();
    }

    public class SMTPAuthenticator extends javax.mail.Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(senderEmailID, senderPassword);
        }
    }
}