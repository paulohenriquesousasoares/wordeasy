package wordeasy.br.com.wordeasy.view.util;


import wordeasy.br.com.wordeasy.view.dominio.Usuario;

public class Constantes {

    public static String TAG =  "TAG";
    public static  int  CONFIGURACAO = 0;
    public static  int  ACTIVITY_CADASTRAR = 1;
    public static  int  ENCERRRAR_SESSAO = 2;
    public static int TRUE = 1;
    public static int FALSE = 0;



    public static final String PREFS_LOGIN = "PreferencesLogin";
    public static final int CADASTRANDO = 0;
    public static final int ALTERANDO = 1;
    public static final String OK = "OK";

    //Para realizar as consultas das palavras
    public static final int TAKE_ALL_CARD_PERSONALIZADO = 0;
    public static final int TAKE_ALL_NAO_ESTUDAR  = 1;
    public static final int TAKE_ALL_PALAVRAS = 3;



    //TABELA

    //database version
    public static final int DATABASE_VERSION =  3;
    public static final String DATABASE_NOME = "wordeasyDB";


    //Tabelas
    public static final String TABLE_USUARIO =  "USUARIO";
    public static final String TABLE_PALAVRA =  "PALAVRA";

    //usuario campos
    public static final String ID  = "ID";
    public static final String NOME  = "NOME";
    public static final String EMAIL  = "EMAIL";
    public static final String SENHA  = "SENHA";

    //palavra campos
    public static final String INGLES  = "INGLES";
    public static final String PORTUGUES  = "PORTUGUES";
    public static final String USUARIO  = "USUARIO";
    public static final String INDICE  = "INDICE";
    public static final String ERROS  = "ERROS";
    public static final String ACERTOS  = "ACERTOS";
    public static final String QTD_ESTUDOU  = "QTD_ESTUDOU";
    public static final String CARD_PERSONZALIZADO  = "CARD_PERSONZALIZADO";
    public static final String NAO_ESTUDAR  = "NAO_ESTUDAR";

}
