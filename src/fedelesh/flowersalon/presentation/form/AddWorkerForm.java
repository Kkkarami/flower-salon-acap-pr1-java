package fedelesh.flowersalon.presentation.form;

import fedelesh.flowersalon.domain.contract.SignUpService;
import fedelesh.flowersalon.domain.dto.FloristAddDto;
import fedelesh.flowersalon.domain.enums.WorkerRole;
import java.util.Scanner;

public class AddWorkerForm {

    private final Scanner scanner = new Scanner(System.in);
    private final SignUpService signUpService;

    public AddWorkerForm(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    public void fill() {
        try {
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Пароль: ");
            String pass = scanner.nextLine();
            System.out.print("Ім'я: ");
            String name = scanner.nextLine();
            System.out.print("Прізвище: ");
            String lName = scanner.nextLine();

            FloristAddDto dto = new FloristAddDto(name, lName, email, pass, WorkerRole.WORKER);

            String code = signUpService.sendVerificationCode(email);
            System.out.print("Введіть код надісланий на пошту: ");
            String inputCode = scanner.nextLine();

            signUpService.confirmAndRegister(inputCode, code, dto);
            System.out.println("Працівника успішно додано.");
        } catch (Exception e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }
}
