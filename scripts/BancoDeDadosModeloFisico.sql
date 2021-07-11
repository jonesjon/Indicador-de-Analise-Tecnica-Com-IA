use indicadordeanalisetecnicacomia;
CREATE TABLE INFO_CANDLE
(
   precoMedia8 long,
   precoMedia20 long,
   precoMedia200 long,
   precoMedia20Volume long,
   nomeDoPapel VARCHAR (10),
   data TIMESTAMP,
   PRIMARY KEY
   (
      nomeDoPapel,
      data
   ),
);
CREATE TABLE CANDLE
(
   abertura long,
   fechamento long,
   maxima long,
   minima long,
   volume long,
);
CREATE TABLE OPERACAO
(
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
   nomeDoPapel VARCHAR (10),
   ID long PRIMARY KEY
);
CREATE TABLE PADROES
(
   Nome VARCHAR (20)
);
CREATE TABLE MARTELO
(
   ID long PRIMARY KEY,
   ID_Operacao long foreign key references OPERACAO (ID),
   ID_pavioSuperior long foreign key references PAVIO_SUPERIOR(ID),
   ID_pavioInferior long foreign key references PAVIO_INFERIOR(ID),
   ID_tipoCandle long foreign key references TIPO_CANDLE(ID),
   volumeAcimaMedia20 BOOLEAN
);

CREATE TABLE PAVIO_SUPERIOR
(
   ID long PRIMARY KEY,
   descricao VARCHAR (100),
);

CREATE TABLE PAVIO_INFERIOR
(
   ID long PRIMARY KEY,
   descricao VARCHAR (100),
);

CREATE TABLE TIPO_CANDLE
(
   ID long PRIMARY KEY,
   descricao VARCHAR (10),
);

ALTER TABLE InfoDiario_Candle ADD CONSTRAINT FK_InfoDiario_Candle_1 FOREIGN KEY (fk_Grafico_nomeDoPapel) REFERENCES Grafico (nomeDoPapel) ON DELETE CASCADE;
ALTER TABLE Operacao ADD CONSTRAINT FK_Operacao_2 FOREIGN KEY (fk_Grafico_nomeDoPapel) REFERENCES Grafico (nomeDoPapel) ON DELETE SET NULL;