use indicadordeanalisetecnicacomia;

CREATE TABLE Grafico (
    nomeDoPapel VARCHAR(10) PRIMARY KEY
);

CREATE TABLE InfoDiario_Candle (
    precoMedia8 long,
    precoMedia20 long,
    precoMedia200 long,
    precoMedia20Volume long,
    fk_Grafico_nomeDoPapel VARCHAR(10),
    nomeDoPapel VARCHAR(10),
    abertura long,
    fechamento long,
    maxima long,
    minima long,
    volume long,
    data TIMESTAMP
);

CREATE TABLE Operacao (
    start BOOLEAN,
    precoGainMax long,
    precoLoss long,
    precoGain long,
    porcentagemOperacaoFinal long,
    precoCancelarEntrada long,
    lucroMax BOOLEAN,
    percentualGain long,
    percentualLoss long,
    data TIMESTAMP,
    percentualGainMax long,
    lucro BOOLEAN,
    ID INTEGER PRIMARY KEY,
    precoEntrada long,
    fk_Grafico_nomeDoPapel VARCHAR(10)
);

CREATE TABLE Padroes (
    Nome VARCHAR(20)
);

CREATE TABLE Martelo (
    pavioSuperior INTEGER,
    pavioInferior INTEGER,
    tipoCandle INTEGER,
    volumeAcimaMedia20 BOOLEAN
);
 
ALTER TABLE InfoDiario_Candle ADD CONSTRAINT FK_InfoDiario_Candle_1
    FOREIGN KEY (fk_Grafico_nomeDoPapel)
    REFERENCES Grafico (nomeDoPapel)
    ON DELETE CASCADE;
 
ALTER TABLE Operacao ADD CONSTRAINT FK_Operacao_2
    FOREIGN KEY (fk_Grafico_nomeDoPapel)
    REFERENCES Grafico (nomeDoPapel)
    ON DELETE SET NULL;