package fedelesh.flowersalon.domain.service;

import fedelesh.flowersalon.domain.contract.AuthService;
import fedelesh.flowersalon.domain.impl.Florist;
import fedelesh.flowersalon.infrastructure.storage.impl.DataContext;
import java.util.Optional;

public final class AuthServiceImpl implements AuthService {

    private final DataContext context;
    private Florist currentUser;

    public AuthServiceImpl(DataContext context) {
        this.context = context;
    }

    @Override
    public boolean authenticate(String phoneNumber, String password) {
        Optional<Florist> florist = context.florists().findAll(null).stream()
              .filter(f -> f.getPhoneNumber().equals(phoneNumber) && f.getPasswordHash()
                    .equals(password))
              .findFirst();

        if (florist.isPresent()) {
            this.currentUser = florist.get();
            return true;
        }
        return false;
    }

    @Override
    public boolean isAuthenticated() {
        return currentUser != null;
    }

    @Override
    public Florist getUser() {
        if (!isAuthenticated()) {
            throw new SecurityException("Користувач не авторизований");
        }
        return currentUser;
    }

    @Override
    public void logout() {
        this.currentUser = null;
    }
}
