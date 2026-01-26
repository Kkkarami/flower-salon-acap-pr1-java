package fedelesh.flowersalon.domain.contract;

import fedelesh.flowersalon.domain.impl.Florist;

public interface AuthService {

    boolean authenticate(String email, String password);

    boolean isAuthenticated();

    Florist getUser();

    void logout();
}
