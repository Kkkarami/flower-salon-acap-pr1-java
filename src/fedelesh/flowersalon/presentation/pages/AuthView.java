package fedelesh.flowersalon.presentation.pages;

import fedelesh.flowersalon.domain.contract.AuthService;
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

            if (authService.authenticate(email, password)) {
                System.out.println("\nВітаємо, " + authService.getUser().getFirstName() + "!");
                return true;
            } else {
                System.out.println(
                      "\n[!] Помилка: неправильний Email або пароль. Спробуйте ще раз.");
            }
        }
    }
}
