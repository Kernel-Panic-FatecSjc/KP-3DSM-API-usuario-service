INSERT INTO usuario (id, nome, email, senha, cargo, ativo, salario, tipo_contratacao, gerente_id, data_criacao, data_atualizacao) 
VALUES 
  (1, 'Carlos Mendes', 'carlos.mendes@kernelpanic.com', '$2a$10$W3ZXrhgR5l4qiui8FVIsIOJpwpX5A59Thsr8Ugq2BAvuM5XXTde1W', 'ROLE_GESTOR', true, '8500', 'CLT', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (2, 'Ana Lima', 'ana.lima@kernelpanic.com', '$2a$10$W3ZXrhgR5l4qiui8FVIsIOJpwpX5A59Thsr8Ugq2BAvuM5XXTde1W', 'ROLE_FINANCEIRO', true, '7200', 'CLT', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (3, 'Rafael Costa', 'rafael.costa@kernelpanic.com', '$2a$10$W3ZXrhgR5l4qiui8FVIsIOJpwpX5A59Thsr8Ugq2BAvuM5XXTde1W', 'ROLE_PROFISSIONAL', true, '5500', 'PJ_HORAS_FIXAS', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (4, 'Juliana Souza', 'juliana.souza@kernelpanic.com', '$2a$10$W3ZXrhgR5l4qiui8FVIsIOJpwpX5A59Thsr8Ugq2BAvuM5XXTde1W', 'ROLE_PROFISSIONAL', true, '5800', 'PJ_HORAS_VARIAVEIS', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
AS new_data
ON DUPLICATE KEY UPDATE tipo_contratacao = new_data.tipo_contratacao;