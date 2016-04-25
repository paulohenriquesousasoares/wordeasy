package wordeasy.br.com.wordeasy.servico;

import wordeasy.br.com.wordeasy.dao.repositorio.PalavraRepositorio;
import wordeasy.br.com.wordeasy.dao.servico.IPalavraServico;
import wordeasy.br.com.wordeasy.dominio.Palavra;

public class PalavraServico implements IPalavraServico {

    PalavraRepositorio palavraRepositorio;

    public PalavraServico(){
        palavraRepositorio = new PalavraRepositorio();
    }


    @Override
    public void create(Palavra palavra)throws Exception{
        palavraRepositorio.create(palavra);
    }


    @Override
    public String validaPalavra(Palavra palavra) {

        if(! (palavra.getPalavraEmIngles().trim().isEmpty() ||palavra.getPalavraEmPortugues().trim().isEmpty()) )  {
            return  "ok";
        }
        else if(palavra.getPalavraEmIngles().equals("") || palavra.getPalavraEmIngles().equals(null)) {
            return  "ingles";
        }
        else if(palavra.getPalavraEmPortugues().equals("") ||  palavra.getPalavraEmPortugues().equals(null) )  {
            return  "portugues";
        }
        return  null;
    }

    public String getByNome(long userId, String palavra ) {
        String result = "ok";
        try {
             boolean notExiste =  palavraRepositorio.getByName(userId,palavra);
             if(!notExiste)
                result =  "existe";

        } catch (Exception e) {
            result = ""+e;
        }
        return result;
    }
}
