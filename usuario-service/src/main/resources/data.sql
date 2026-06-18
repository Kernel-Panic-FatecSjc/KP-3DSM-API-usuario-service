-- Insert de Usuários
INSERT INTO usuario (
    id,
    nome,
    cpf,
    email,
    senha,
    cargo,
    ativo,
    salario,
    tipo_contratacao,
    gerente_id,
    data_criacao,
    data_atualizacao
)
VALUES
(
    1,
    'Carlos Mendes',
    '52998224725',
    'carlos.mendes@kernelpanic.com',
    '$2a$10$W3ZXrhgR5l4qiui8FVIsIOJpwpX5A59Thsr8Ugq2BAvuM5XXTde1W',
    'ROLE_GESTOR',
    true,
    '8500',
    'CLT',
    NULL,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    2,
    'Ana Lima',
    '11144477735',
    'ana.lima@kernelpanic.com',
    '$2a$10$W3ZXrhgR5l4qiui8FVIsIOJpwpX5A59Thsr8Ugq2BAvuM5XXTde1W',
    'ROLE_FINANCEIRO',
    true,
    '7200',
    'CLT',
    NULL,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    3,
    'Rafael Costa',
    '12345678909',
    'rafael.costa@kernelpanic.com',
    '$2a$10$W3ZXrhgR5l4qiui8FVIsIOJpwpX5A59Thsr8Ugq2BAvuM5XXTde1W',
    'ROLE_PROFISSIONAL',
    true,
    '5500',
    'PJ_HORAS_FIXAS',
    1,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    4,
    'Juliana Souza',
    '98765432100',
    'juliana.souza@kernelpanic.com',
    '$2a$10$W3ZXrhgR5l4qiui8FVIsIOJpwpX5A59Thsr8Ugq2BAvuM5XXTde1W',
    'ROLE_PROFISSIONAL',
    true,
    '5800',
    'PJ_HORAS_VARIAVEIS',
    1,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
)
ON DUPLICATE KEY UPDATE
    cpf = VALUES(cpf),
    tipo_contratacao = VALUES(tipo_contratacao);

-- Insert de Clientes
INSERT INTO cliente (
    id,
    nome,
    cnpj,
    email,
    telefone,
    observacao,
    ativo,
    data_criacao
)
VALUES
(
    1,
    'Acme Corporation',
    '12345678000195',
    'contato@acme.com',
    '1133334444',
    'Cliente principal de projetos internos',
    true,
    CURRENT_TIMESTAMP
),
(
    2,
    'TechVision Solutions',
    '98765432000160',
    'vendas@techvision.com.br',
    '1144445555',
    'Desenvolvedor de software especializado em cloud',
    true,
    CURRENT_TIMESTAMP
),
(
    3,
    'Global Consultoria Ltda',
    '11222333000181',
    'info@globalconsultoria.com.br',
    '1155556666',
    'Empresa de consultoria empresarial',
    true,
    CURRENT_TIMESTAMP
),
(
    4,
    'DataFlow Analytics',
    '45678901000123',
    'suporte@dataflow.com.br',
    '1166667777',
    'Especialista em análise de dados e BI',
    true,
    CURRENT_TIMESTAMP
)
ON DUPLICATE KEY UPDATE
    nome = VALUES(nome),
    email = VALUES(email),
    telefone = VALUES(telefone);