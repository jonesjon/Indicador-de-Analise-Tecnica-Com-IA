use indicadordeanalisetecnicacomia;
CREATE TABLE OPERACAO (
    ID BIGINT,
    iniciou BOOLEAN,
    precoGainMax BIGINT,
    precoLoss BIGINT,
    precoGain BIGINT,
    porcentagemOperacaoFinal BIGINT,
    precoCancelarEntrada BIGINT,
    lucroMax BOOLEAN,
    percentualGain BIGINT,
    percentualLoss BIGINT,
    dat DATETIME,
    percentualGainMax BIGINT,
    lucro BOOLEAN,
    precoEntrada BIGINT,
    nomeDoPapel VARCHAR(10),
    PRIMARY KEY (ID)
);
CREATE TABLE PADROES (
    Nome VARCHAR(20)
);
CREATE TABLE PAVIO_SUPERIOR (
    ID BIGINT PRIMARY KEY,
    descricao VARCHAR(100)
);
CREATE TABLE PAVIO_INFERIOR (
    ID BIGINT PRIMARY KEY,
    descricao VARCHAR(100)
);
CREATE TABLE TIPO_CANDLE (
    ID BIGINT PRIMARY KEY,
    descricao VARCHAR(10)
);

CREATE TABLE INFO_CANDLE (
    precoMedia8 long,
    precoMedia20 long,
    precoMedia200 long,
    volumeMedia20 long,
    nomeDoPapel VARCHAR(10),
    abertura long,
    fechamento long,
    maxima long,
    minima long,
    volume long,
    dat TIMESTAMP not null,
    PRIMARY KEY (nomeDoPapel , dat)
);

CREATE TABLE MARTELO (
    ID BIGINT PRIMARY KEY,
    ID_Operacao BIGINT,
    ID_pavioSuperior BIGINT,
    ID_pavioInferior BIGINT,
    ID_tipoCandle BIGINT,
    volumeAcimaMedia20 BOOLEAN,
    CONSTRAINT fk_ID_operacaoMartelo FOREIGN KEY (ID_Operacao)
        REFERENCES OPERACAO (ID),
    CONSTRAINT fk_ID_pavioSuperiorMartelo FOREIGN KEY (ID_pavioSuperior)
        REFERENCES PAVIO_SUPERIOR (ID),
    CONSTRAINT fk_ID_pavioInferior FOREIGN KEY (ID_PavioInferior)
        REFERENCES PAVIO_INFERIOR (ID),
    CONSTRAINT fk_ID_tipoCandle FOREIGN KEY (ID_TipoCandle)
        REFERENCES TIPO_CANDLE (ID)
);

