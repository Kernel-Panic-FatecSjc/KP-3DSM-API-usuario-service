package com.kernelpanic.usuario_service.controles;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.kernelpanic.usuario_service.servicos.UsuarioServico;

@RestController
@RequestMapping("/dashboard")
public class ControleDashboardAuditoria {

    @Autowired
    private UsuarioServico usuarioServico;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/auditoria")
    public Map<String, Object> obterDashboard(
            @RequestParam Long usuarioId,
            @RequestParam(required = false) String dataInicio,
            @RequestParam(required = false) String dataFim) {

        Map<String, Object> resultado = new HashMap<>();

        try {
            resultado.put("usuario", usuarioServico.obterPorId(usuarioId));
        } catch (Exception e) {
            resultado.put("usuario", null);
        }

        try {
            String projetosUrl = "http://spring-api-container:8082/projeto/profissional/" + usuarioId;
            if (dataInicio != null && dataFim != null) {
                projetosUrl += "?dataInicio=" + URLEncoder.encode(dataInicio, StandardCharsets.UTF_8)
                        + "&dataFim=" + URLEncoder.encode(dataFim, StandardCharsets.UTF_8);
            }
            Object projetos = restTemplate.getForObject(projetosUrl, Object.class);
            resultado.put("projetos", projetos);
        } catch (Exception e) {
            resultado.put("projetos", List.of());
        }

        try {
            String horasUrl = "http://apontamento-service:8084/horas/profissional/" + usuarioId;
            if (dataInicio != null && dataFim != null) {
                horasUrl += "?dataInicio=" + URLEncoder.encode(dataInicio, StandardCharsets.UTF_8)
                        + "&dataFim=" + URLEncoder.encode(dataFim, StandardCharsets.UTF_8);
            }
            Object horas = restTemplate.getForObject(horasUrl, Object.class);
            resultado.put("horas", horas);
        } catch (Exception e) {
            resultado.put("horas", List.of());
        }

        try {
            String tarefasUrl = "http://task-service:8085/tarefas/funcionario/" + usuarioId;
            Object tarefas = restTemplate.getForObject(tarefasUrl, Object.class);
            resultado.put("tarefas", tarefas);
        } catch (Exception e) {
            resultado.put("tarefas", List.of());
        }

        return resultado;
    }
}
