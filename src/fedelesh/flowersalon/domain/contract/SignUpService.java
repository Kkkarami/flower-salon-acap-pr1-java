package fedelesh.flowersalon.domain.contract;

import fedelesh.flowersalon.domain.dto.FloristAddDto;

public interface SignUpService {

    String sendVerificationCode(String email);

    void confirmAndRegister(String inputCode, String generatedCode, FloristAddDto dto);
}
