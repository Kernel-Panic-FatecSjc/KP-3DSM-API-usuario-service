DROP PROCEDURE IF EXISTS add_usuario_financial_fields_if_missing;

CREATE PROCEDURE add_usuario_financial_fields_if_missing()
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_schema = DATABASE()
          AND table_name = 'usuario'
          AND column_name = 'tipo_contratacao'
    ) THEN
        ALTER TABLE usuario ADD COLUMN tipo_contratacao VARCHAR(30) NULL;
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_schema = DATABASE()
          AND table_name = 'usuario'
          AND column_name = 'valor_mensal'
    ) THEN
        ALTER TABLE usuario ADD COLUMN valor_mensal DECIMAL(12,2) NULL;
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_schema = DATABASE()
          AND table_name = 'usuario'
          AND column_name = 'valor_hora'
    ) THEN
        ALTER TABLE usuario ADD COLUMN valor_hora DECIMAL(12,2) NULL;
    END IF;
END;

CALL add_usuario_financial_fields_if_missing();

DROP PROCEDURE add_usuario_financial_fields_if_missing;
