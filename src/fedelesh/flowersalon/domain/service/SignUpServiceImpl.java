package fedelesh.flowersalon.domain.service;

import fedelesh.flowersalon.domain.contract.SignUpService;
import fedelesh.flowersalon.domain.dto.FloristAddDto;
import fedelesh.flowersalon.domain.impl.Florist;
import fedelesh.flowersalon.infrastructure.storage.impl.DataContext;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Properties;
import org.mindrot.bcrypt.BCrypt;

public class SignUpServiceImpl implements SignUpService {

    private static final int EXPIRATION_MINUTES = 5;
    private final DataContext context;
    private LocalDateTime codeCreationTime;

    public SignUpServiceImpl(DataContext context) {
        this.context = context;
    }

    @Override
    public String sendVerificationCode(String email) {
        String code = String.valueOf((int) (Math.random() * 900000 + 100000));
        sendEmail(email, code);
        this.codeCreationTime = LocalDateTime.now();
        return code;
    }

    @Override
    public void confirmAndRegister(String inputCode, String generatedCode, FloristAddDto dto) {
        LocalDateTime currentTime = LocalDateTime.now();

        if (codeCreationTime == null
              || ChronoUnit.MINUTES.between(codeCreationTime, currentTime) > EXPIRATION_MINUTES) {
            throw new RuntimeException("Час верифікації вийшов. Спробуйте ще раз.");
        }

        if (!inputCode.equals(generatedCode)) {
            throw new RuntimeException("Невірний код підтвердження.");
        }

        String hashedPassword = BCrypt.hashpw(dto.password(), BCrypt.gensalt());

        Florist florist = new Florist(
              dto.firstName(),
              dto.lastName(),
              hashedPassword,
              dto.email(),
              dto.role()
        );

        context.registerNew(florist);
        context.commit();
        this.codeCreationTime = null;
    }

    private void sendEmail(String email, String code) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("andrey.fedelesh12@gmail.com",
                      "qpcruimsipzznshg");
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("admin@flowersalon.com", "Flower Salon Admin"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Код підтвердження реєстрації");
            message.setText("Ваш код підтвердження: " + code + "\nКод дійсний 5 хвилин.");

            Transport.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Помилка відправки пошти: " + e.getMessage());
        }
    }
}
