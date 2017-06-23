package tk.appstart.start;

/**
 * Created by Adjailson on 31/05/2017.
 */

public class ValidaVariavel {
    private final String numeros = "1234567890";
    private final String caracteres_especiais = "áàãâçéèêíìîóòõôúùû";
    private final String simbolos = ",.;'/+-*&%$#@=[]{}|\\";

    public boolean isVariavel(String var){
        boolean valida = true;
        try {
            var = var.toLowerCase();
            for(int i=0; i<numeros.length(); i++){
                if(var.charAt(0) == numeros.charAt(i)){
                    valida = false;
                }
            }
            for(int i=0; i<var.length(); i++){
                for(int j=0; j<caracteres_especiais.length(); j++){
                    if(var.charAt(i) == caracteres_especiais.charAt(j)){
                        valida = false;
                    }
                }
            }
            for(int i=0; i<var.length(); i++){
                for(int j=0; j<simbolos.length(); j++){
                    if(var.charAt(i) == simbolos.charAt(j)){
                        valida = false;
                    }
                }
            }
        } catch (Exception e) {
            valida = false;
        }
        return valida;
    }

    public boolean isNumero(String var){
        boolean valida = false;
        for(int i=0; i<numeros.length(); i++){
            if(var.charAt(0) == numeros.charAt(i)){
                valida = true;
                break;
            }
        }
        return valida;
    }
}
