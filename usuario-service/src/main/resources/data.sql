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
AS new_data
ON DUPLICATE KEY UPDATE
    cpf = new_data.cpf,
    tipo_contratacao = new_data.tipo_contratacao;