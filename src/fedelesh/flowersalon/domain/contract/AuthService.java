package fedelesh.flowersalon.domain.contract;

import fedelesh.flowersalon.domain.dto.LoginDto;
import fedelesh.flowersalon.domain.impl.Florist;

public interface AuthService {

    boolean authenticate(LoginDto dto);

    Florist getUser();

    void logout();
}
