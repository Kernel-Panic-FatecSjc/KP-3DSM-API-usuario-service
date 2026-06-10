INSERT INTO usuario (id, nome, email, senha, cargo, ativo, salario, tipo_contratacao, gerente_id, data_criacao, data_atualizacao) 
VALUES 
  (1, 'Carlos Mendes', 'carlos.mendes@kernelpanic.com', '$2a$10$W3ZXrhgR5l4qiui8FVIsIOJpwpX5A59Thsr8Ugq2BAvuM5XXTde1W', 'ROLE_GESTOR', true, '8500', 'CLT', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (2, 'Ana Lima', 'ana.lima@kernelpanic.com', '$2a$10$W3ZXrhgR5l4qiui8FVIsIOJpwpX5A59Thsr8Ugq2BAvuM5XXTde1W', 'ROLE_FINANCEIRO', true, '7200', 'CLT', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (3, 'Rafael Costa', 'rafael.costa@kernelpanic.com', '$2a$10$W3ZXrhgR5l4qiui8FVIsIOJpwpX5A59Thsr8Ugq2BAvuM5XXTde1W', 'ROLE_PROFISSIONAL', true, '5500', 'PJ_HORAS_FIXAS', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (4, 'Juliana Souza', 'juliana.souza@kernelpanic.com', '$2a$10$W3ZXrhgR5l4qiui8FVIsIOJpwpX5A59Thsr8Ugq2BAvuM5XXTde1W', 'ROLE_PROFISSIONAL', true, '5800', 'PJ_HORAS_VARIAVEIS', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
AS new_data
ON DUPLICATE KEY UPDATE tipo_contratacao = new_data.tipo_contratacao;

INSERT INTO cliente (id, nome, cnpj, email, telefone, observacao, ativo, data_criacao)
VALUES
  (1, 'TechCorp Soluções',  '12.345.678/0001-90', 'contato@techcorp.com.br',       '(11) 91234-5678', 'Cliente focado em sistemas internos',  true, CURRENT_TIMESTAMP),
  (2, 'Inova Sistemas',     '98.765.432/0001-10', 'comercial@inovasistemas.com.br', '(21) 98765-4321', 'Demanda projetos de automação',        true, CURRENT_TIMESTAMP),
  (3, 'DataBridge LTDA',    '11.222.333/0001-44', 'ti@databridge.com.br',           '(31) 97654-3210', 'Especializada em integração de dados', true, CURRENT_TIMESTAMP)
AS new_data
ON DUPLICATE KEY UPDATE nome = new_data.nome;

INSERT INTO cliente_projeto (id, cliente_id, projeto_id)
VALUES
  (1, 1, 1),
  (2, 1, 2),
  (3, 2, 3),
  (4, 3, 2)
AS new_data
ON DUPLICATE KEY UPDATE projeto_id = new_data.projeto_id;

SET FOREIGN_KEY_CHECKS = 1;