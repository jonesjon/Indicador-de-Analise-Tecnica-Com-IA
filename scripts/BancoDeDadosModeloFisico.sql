use indicadordeanalisetecnicacomia;
CREATE TABLE INFO_CANDLE
(
   precoMedia8 long,
   precoMedia20 long,
   precoMedia200 long,
   precoMedia20Volume long,
   nomeDoPapel VARCHAR (10),
   dat TIMESTAMP,
   PRIMARY KEY
   (
      nomeDoPapel,
      dat
   )
);
CREATE TABLE CANDLE
(
   abertura long,
   fechamento long,
   maxima long,
   minima long,
   volume long
);
CREATE TABLE OPERACAO
(
   ID long,
   iniciou BOOLEAN,
   precoGainMax long,
 precoLoss long,
  precoGain long,
 porcentagemOperacaoFinal long,
 precoCancelarEntrada long,
  lucroMax BOOLEAN,
 percentualGain long,
 percentualLoss long,
 dat datetime,
 percentualGainMax long,
 lucro BOOLEAN,
 precoEntrada long,
 nomeDoPapel VARCHAR(10),
 primary key (ID)
);
CREATE TABLE PADROES
(
   Nome VARCHAR (20)
);
CREATE TABLE MARTELO
(
   ID long PRIMARY KEY,
   ID_Operacao long,
   ID_pavioSuperior long ,
   ID_pavioInferior long ,
   ID_tipoCandle long ,
   volumeAcimaMedia20 BOOLEAN,
   constraint fk_ID_operacaoMartelo foreign key (ID_Operacao) references OPERACAO (ID),
   constraint fk_ID_pavioSuperiorMartelo foreign key (ID_pavioSuperior) references PAVIO_SUPERIOR (ID),
   constraint fk_ID_pavioInferior foreign key (ID_PavioInferior) references PAVIO_INFERIOR (ID),
   constraint fk_ID_tipoCandle foreign key (ID_TipoCandle) references TIPO_CANDLE (ID)
);
CREATE TABLE PAVIO_SUPERIOR
(
   ID long PRIMARY KEY,
   descricao VARCHAR (100)
);
CREATE TABLE PAVIO_INFERIOR
(
   ID long PRIMARY KEY,
   descricao VARCHAR (100)
);
CREATE TABLE TIPO_CANDLE
(
   ID long PRIMARY KEY,
   descricao VARCHAR (10)
);
