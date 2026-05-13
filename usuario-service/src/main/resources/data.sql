INSERT INTO usuario (id, nome, email, senha, cargo, ativo, salario, gerente_id, data_criacao, data_atualizacao) 
VALUES 
  (1, 'Carlos Mendes', 'carlos.mendes@kernelpanic.com', '$2a$10$...', 'ROLE_GESTOR', true, '8500', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (2, 'Ana Lima', 'ana.lima@kernelpanic.com', '$2a$10$...', 'ROLE_FINANCEIRO', true, '7200', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (3, 'Rafael Costa', 'rafael.costa@kernelpanic.com', '$2a$10$...', 'ROLE_PROFISSIONAL', true, '5500', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (4, 'Juliana Souza', 'juliana.souza@kernelpanic.com', '$2a$10$...', 'ROLE_PROFISSIONAL', true, '5800', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
AS new_data
ON DUPLICATE KEY UPDATE id = new_data.id;