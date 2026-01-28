package fedelesh.flowersalon.domain.service;

import fedelesh.flowersalon.domain.contract.AuthService;
import fedelesh.flowersalon.domain.dto.LoginDto;
import fedelesh.flowersalon.domain.impl.Florist;
import fedelesh.flowersalon.infrastructure.storage.impl.DataContext;
import org.mindrot.bcrypt.BCrypt;

public final class AuthServiceImpl implements AuthService {

    private final DataContext context;
    private Florist currentUser;

    public AuthServiceImpl(DataContext context) {
        this.context = context;
    }

    @Override
    public boolean authenticate(LoginDto dto) {
        return context.florists().findAll(null).stream()
              .filter(f -> f.getEmail()
                    .equals(dto.email())) // Використовуємо .equals() як ви просили раніше
              .findFirst()
              .map(f -> {
                  if (BCrypt.checkpw(dto.password(), f.getPasswordHash())) {
                      this.currentUser = f;
                      return true;
                  }
                  return false;
              })
              .orElse(false);
    }

    @Override
    public Florist getUser() {
        return currentUser;
    }

    @Override
    public void logout() {
        this.currentUser = null;
    }
}
