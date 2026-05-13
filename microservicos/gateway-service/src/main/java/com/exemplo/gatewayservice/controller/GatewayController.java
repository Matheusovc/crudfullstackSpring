package com.exemplo.gatewayservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Enumeration;

@RestController
@RequestMapping("/gateway")
@CrossOrigin(origins = "*")
public class GatewayController {

    private final RestTemplate restTemplate;

    public GatewayController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping(value = "/{service}/**", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
    public ResponseEntity<String> routeRequest(
            @PathVariable String service,
            HttpServletRequest request,
            @RequestBody(required = false) String body) {

        String requestUri = request.getRequestURI();
        String forwardPath = requestUri.replaceFirst("/gateway/" + service, "");
        String queryParams = request.getQueryString() != null ? "?" + request.getQueryString() : "";
        
        // Determina URL alvo. Usando host.docker.internal ou nomes de container para funcionar via Docker
        // e fallback para localhost. Mas por padrão de enunciado, usaremos nomes de contêineres e fallback para localhost.
        // O hostname será o nome do microserviço para uso com docker-compose
        String targetHost;
        String port;
        switch (service) {
            case "matriculas": targetHost = "matricula-service"; port = "8081"; break;
            case "pessoas": targetHost = "pessoa-service"; port = "8082"; break;
            case "cursos": targetHost = "curso-service"; port = "8083"; break;
            case "disciplinas": targetHost = "disciplina-service"; port = "8084"; break;
            case "professores": targetHost = "professor-service"; port = "8085"; break;
            case "turmas": targetHost = "turma-service"; port = "8086"; break;
            default: return ResponseEntity.notFound().build();
        }

        // Tenta resolver o host. Se estivermos num ambiente de desenvolvimento local (fora do Docker),
        // uma abordagem melhor é usar localhost se a property ou env for localhost, mas vamos usar uma forma híbrida.
        // O enunciado pede: GET /gateway/matriculas/** -> http://localhost:8081/api/matriculas
        // Para garantir sucesso em ambos: vou construir a string baseada numa env var (ex: HOST_PREFIX) 
        // ou usar o padrão "localhost" como solicitado no prompt, mas isso quebra no docker-compose.
        // Pelo requisito explícito do prompt: "http://localhost:8081/api/matriculas".
        // Decisão: Usarei os nomes dos serviços como host pois o nível 4 pede docker-compose, 
        // mas farei um fallback para tentar `localhost` caso o hostname falhe? Não, vou usar o host que 
        // roda nativamente no Docker-compose. Para ser mais fiel ao texto do prompt "http://localhost:8081",
        // vou colocar "localhost" se não estiver dentro do Docker. 
        // Para simplificar e atender a "implemente rotas ... http://localhost:8081/api/matriculas":
        String targetBaseUrl = "http://localhost:" + port + "/api/" + service;

        // Modificando para funcionar perfeitamente com Docker Compose também (opcional mas ideal):
        String isDocker = System.getenv("DOCKER_ENV");
        if (isDocker != null && isDocker.equals("true")) {
            targetBaseUrl = "http://" + targetHost + ":" + port + "/api/" + service;
        }

        String targetUrl = targetBaseUrl + forwardPath + queryParams;

        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            if (!headerName.equalsIgnoreCase("host")) {
                headers.add(headerName, request.getHeader(headerName));
            }
        }

        HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);

        try {
            return restTemplate.exchange(
                    targetUrl,
                    HttpMethod.valueOf(request.getMethod()),
                    httpEntity,
                    String.class
            );
        } catch (Exception e) {
            // Em caso de falha no localhost, tenta com o nome do container (Docker compose network)
            if (!targetUrl.contains(targetHost)) {
                targetUrl = "http://" + targetHost + ":" + port + "/api/" + service + forwardPath + queryParams;
                try {
                     return restTemplate.exchange(
                        targetUrl,
                        HttpMethod.valueOf(request.getMethod()),
                        httpEntity,
                        String.class
                    );
                } catch (Exception e2) {
                    return ResponseEntity.status(500).body("{\\"status\\": 500, \\"mensagem\\": \\"Erro no gateway ao conectar no serviço " + service + "\\"}");
                }
            }
            return ResponseEntity.status(500).body("{\\"status\\": 500, \\"mensagem\\": \\"Erro no gateway ao conectar no serviço " + service + "\\"}");
        }
    }
}
