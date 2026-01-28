package fedelesh.flowersalon.presentation.pages;

import fedelesh.flowersalon.domain.contract.AuthService;
import fedelesh.flowersalon.domain.dto.LoginDto;
import java.util.Scanner;

public class AuthView {

    private final AuthService authService;
    private final Scanner scanner = new Scanner(System.in);

    public AuthView(AuthService authService) {
        this.authService = authService;
    }

    public boolean show() {
        while (true) {
            System.out.println("=== ВХІД У СИСТЕМУ ===");

            System.out.print("Email: ");
            String email = scanner.nextLine();

            System.out.print("Пароль: ");
            String password = scanner.nextLine();

            try {
                LoginDto dto = new LoginDto(email, password);

                // Виконуємо аутентифікацію
                if (authService.authenticate(dto)) {
                    System.out.println("\nВітаємо, " + authService.getUser().getFirstName() + "!");
                    return true;
                } else {
                    System.out.println("Помилка: Неправильний Email або пароль.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Помилка: Неправильний Email або пароль.");
            }
        }
    }
}
