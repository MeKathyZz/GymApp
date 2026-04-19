package com.gymapp.demo.service;

import com.gymapp.demo.dto.TrainingRecommendationDTO;
import com.gymapp.demo.model.UserStatus;
import com.gymapp.demo.repository.UserStatusRepository;
import jakarta.annotation.Nonnull;
import lombok.Getter;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserStatusService {

    private final UserStatusRepository repository;
    @Getter
    private String lastWeeklyVeredict = "O veredito será gerado no domingo às 21h.";

    public UserStatusService(UserStatusRepository repository) {
        this.repository = repository;
    }

    public Optional<UserStatus> getLastStatus() {
        return repository.findFirstByOrderByTimestampDesc();
    }

    public UserStatus saveStatus(UserStatus status) {
        return repository.save(status);
    }

    public TrainingRecommendationDTO getSmartRecommendation() {
        return repository.findFirstByOrderByTimestampDesc()
                .map(last -> {
                    int pain = last.getPainLevel();
                    String muscles = last.getSoreMuscles();

                    if (pain >= 8) {
                        return new TrainingRecommendationDTO(
                                "RED",
                                "Nível crítico de dor detectado em: " + muscles,
                                "ATIVAR BOTÃO DE EMERGÊNCIA: Sugerido Mobilidade ou Descanso"
                        );
                    }

                    if (pain >= 5) {
                        return new TrainingRecommendationDTO(
                                "YELLOW",
                                "Recuperação em andamento no(a) " + muscles,
                                "SUGESTÃO: Reduzir volume/carga ou trocar grupamento"
                        );
                    }

                    return new TrainingRecommendationDTO(
                            "GREEN",
                            "Corpo pronto para o combate!",
                            "SUGESTÃO: Focar em progressão de carga"
                    );
                })
                .orElse(new TrainingRecommendationDTO("WHITE", "Sem dados recentes", "SUGESTÃO: Faça o check-in de dor"));
    }

    @org.springframework.scheduling.annotation.Scheduled(cron = "0 0 21 * * SUN") // Domingo, 21h
    public void generateWeeklyFeedback() {
        LocalDateTime umaSemanaAtras = LocalDateTime.now().minusDays(7);
        List<UserStatus> statusDaSemana = repository.findAllByTimestampAfter(umaSemanaAtras);

        if (statusDaSemana.isEmpty()) {
            this.lastWeeklyVeredict = "Sem dados suficientes para o veredito semanal.";
            System.out.println(lastWeeklyVeredict);
            return;
        }

        double mediaDor = statusDaSemana.stream()
                .mapToInt(UserStatus::getPainLevel)
                .average()
                .orElse(0.0);

        this.lastWeeklyVeredict = getString(mediaDor);

        System.out.println(lastWeeklyVeredict);
    }

    @Nonnull
    private static String getString(double mediaDor) {
        String mensagem;
        if (mediaDor >= 7) {
            mensagem = "Veredito Semanal: O relato de dor foi alto essa semana. Sugestão: Diminuir o peso na próxima semana para evitar lesão.";
        } else if (mediaDor <= 3) {
            mensagem = "Veredito Semanal: Relato de dor geral no VERDE! Seu corpo aguentou bem. Sugestão: Pode aumentar as cargas na próxima semana.";
        } else {
            mensagem = "Veredito Semanal: Recuperação equilibrada. Mantenha as cargas e foque na técnica.";
        }

        System.out.println(mensagem);

        return mensagem;
    }

}