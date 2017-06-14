package by.itransition.service.recovery.impl;

import by.itransition.data.model.RecoveryToken;
import by.itransition.data.model.User;
import by.itransition.data.repository.RecoveryTokenRepository;
import by.itransition.service.recovery.RecoveryService;

/**
 * Created by ilya on 6/14/17.
 */
public class DefaultRecoveryService implements RecoveryService {
    private final RecoveryTokenRepository recoveryTokenRepository;

    public DefaultRecoveryService(RecoveryTokenRepository recoveryTokenRepository) {
        this.recoveryTokenRepository = recoveryTokenRepository;
    }

    @Override
    public RecoveryToken createRecoveryToken(User user) {
        final RecoveryToken token = new RecoveryToken(user, "");
        return recoveryTokenRepository.save(token);
    }

    @Override
    public User getUser(String token) {
        return recoveryTokenRepository.findByToken(token).getUser();
    }
}
