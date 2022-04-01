package com.bootcamp.bankaccount.util;

public class Constants {
    public static final String API_CLIENT_URL = "http://localhost:8095/api/clients";
    public static final String API_CREDIT_URL = "http://localhost:8095/api/credit";

    public static final String TIPO_CUENTA_AHORRO = "1";
    public static final String TIPO_CUENTA_CORRIENTE = "2";
    public static final String TIPO_CUENTA_PLAZO = "3";
	public static final String TIPO_CLIENTE_PERSONA = "1";
  public static final String TIPO_CLIENTE_EMPRESA = "2";

  public static final String VIP = "VIP";
  public static final String PYME = "PYME";
  public static final String SAVING_ACCOUNT = "SAVING_ACCOUNT";

  public static final String MSJ_MONTO_MENOR_APERTURA
          = "Cliente no se registró porque el monto inicial " +
          "no es mayor a el mínimo de apertura";
  public static final String MSJ_REQUIERE_TC
          = "Cliente no se registró porque según el tipo de " +
          "producto requiere tener una tarjeta de crédito";


}
