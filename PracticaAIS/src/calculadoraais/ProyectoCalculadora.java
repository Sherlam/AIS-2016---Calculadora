
package calculadoraais;


public class ProyectoCalculadora {
    public static void main(String [] args){
    Calculadora calcu = new Calculadora();
    calcu.setVisible(true);
        
    }

    double sumar(double a, double b) {                  //Autor Jaime Muñoz Aparicio
        return(a+b);
    }
    double restar(double a, double b) {                 //Autor David Sánchez Dueñas
        return(a-b);
    }
    double multiplicacion(double a, double b) {         //Autor Sergio Pérez Casquero
        return(a*b);
    }
    double division(double a, double b) throws ExcepcionOperacionNoPermitida {              //Autor Jaime Muñoz Aparicio
        if(a==0 || b==0){
            throw new ExcepcionOperacionNoPermitida("Operacion invalida");
        }
        else{
            return(a/b);
        }
    }
    
    double tramoParentesis(String linea) throws ExcepcionOperacionNoPermitida{              // Autores: Sergio Lázaro Matesanz y Sergio Pérez Casquero
            boolean recorridaEntera=false;
            double resultado=0;
            char simboloAntesDelParentesis='.';
            while (!recorridaEntera){
                int x=0;
                int y=-1;
                //busca los parentesis para utilizarlos posteriormente
                while((x<linea.length())&&(linea.charAt(x)!=')')){
                    if(linea.charAt(x) =='(')
                        y=x;
                    x++;
                }
                //si no quedan parentesis, realiza una operacion normal
                if ((x==linea.length())&&(y==-1)){
                    recorridaEntera=true;
                    resultado=dividirOperacionesSimples(linea);
                }
                else{
                    //System.out.println("la linea introducida es, "+linea);
                    String subCadena1 = linea.substring(y+1,x);
                    String linea1=linea.substring(0,y);
                    String linea2="";
                    if (x!=linea.length()-1){
                        if ((linea.charAt(x+1)>='0')&&(linea.charAt(x+1)<='9'))
                            linea2=linea2+"X";
                        linea2=linea2+linea.substring(x+1,linea.length());
                    }
                    if (y!=0){
                        //si hay un simbolo a la izda del parentesis lo guardo para posibles usos
                        if ((linea.charAt(y-1)>='0')&&(linea.charAt(y-1)<='9'))
                            linea1=linea1+"X";
                        else if(linea.charAt(y-1)=='+'){
                            simboloAntesDelParentesis='+';
                        }
                        else if(linea.charAt(y-1)=='-'){
                            simboloAntesDelParentesis='-';
                        }
                        
                    }
                    //System.out.println("la subcadena es, "+subCadena1);
                    double resultadoInterno = dividirOperacionesSimples(subCadena1);
                    String subCadena2 = String.valueOf(resultadoInterno);
                    //si hay un + fuera y dentro del parentesis el resultado es negativo, pongo un -
                    if (simboloAntesDelParentesis=='+' && resultadoInterno<0) {
                        linea1=linea1.substring(0, linea1.length()-1);
                    }
                    //si hay un - fuera del parentesis y un - dentro, quito los menos y pongo un +
                    if(simboloAntesDelParentesis=='-' && resultadoInterno<0){
                        linea1=linea1.substring(0, linea1.length()-1)+"+";
                        subCadena2=subCadena2.substring(1);
                    }
                    //concateno el resultado
                    linea=(linea1+subCadena2+linea2);
                    //System.out.println("******************************");
                }
            }
            return resultado;
        }

    
    
    double dividirOperacionesSimples(String linea) throws ExcepcionOperacionNoPermitida {           //Autores: David Sánchez Dueñas y Jaime Muñoz Aparicio
        int inicio = -1;
        int fin = -1;
        int contadorMenos=0;
        double valorA =0;
        double valorB =0;
        String principioLinea;
        String finalLinea;
                
                //buscamos operaciones (Multiplicaciones y divisiones)
                for (int i = 0; i < linea.length(); i++) {
                    //si tenemos una multiplicacion
                    if(linea.charAt(i)=='X' || linea.charAt(i)=='/'){
                        //busco el multiplicando o dividendo <---
                        for (int j = i-1; j >= 0; j--) {
                            switch (linea.charAt(j)) {
                            case '+': inicio = j+1;
                                     break;
                            case '-': inicio = j;
                                     break;         
                            }

                            if(j == 0 && inicio==-1)  inicio = 0;
                            else if(inicio!=-1) j=0;
                        }
                        
                        //busco el multiplicador o divisor --> 
                        for (int j = i+1; j < linea.length(); j++) {
                            switch (linea.charAt(j)) {
                            case '+': fin = j;
                                     break;
                            case '-': contadorMenos +=1; if (contadorMenos>1){fin = j;}
                            else if(j>i+1){fin = j;}
                                     break;         
                            case 'X': fin = j;
                                     break;
                            case '/': fin = j;
                                     break;  
                            }
                            if(j == linea.length()-1 && fin==-1)  fin = linea.length();
                            else if(fin!=-1) j=linea.length()-1;
                        }
                        //llamo a multiplicacion o division segun linea.charAt(i) y corto la cadena para pasar los numeros
                                valorA = Double.parseDouble(linea.substring(inicio,i));
                                valorB = Double.parseDouble(linea.substring(i+1,fin));

                                principioLinea=linea.substring(0,inicio);
                                finalLinea=linea.substring(fin, linea.length());
                                
                                if (valorA<0 && valorB<0 && !"".equals(principioLinea)) {
                                    principioLinea=principioLinea.concat("+");
                                }
                                else if(valorA>=0 && valorB<=0 && !"".equals(principioLinea)){
                                    principioLinea=principioLinea.substring(0, principioLinea.length()-1);
                                }
                            //inserto el resultado en el hueco donde estaba la multiplicacion
                            if (linea.charAt(i) == 'X'){    
                                linea="".concat(principioLinea).concat(Double.toString(multiplicacion(valorA, valorB)).concat(finalLinea));
                            }
                            //inserto el resultado en el hueco donde estaba la division
                            else if(linea.charAt(i) == '/'){
                                linea="".concat(principioLinea).concat(Double.toString(division(valorA, valorB)).concat(finalLinea));
                            } 
                            i=0;
                            inicio=fin=-1;
                            contadorMenos=0;
                    }
                }
                //buscamos operaciones (Sumas y restas)
                for (int i = 0; i < linea.length(); i++) {
                    //si tenemos una suma o resta
                    if(linea.charAt(i)=='+' || linea.charAt(i)=='-' && i!=0){
                        //busco los valores a sumar o restar <--- -5+9-6-4+3-3
                        for (int j = i-1; j >= 0; j--) {
                            switch (linea.charAt(j)) {
                            case '+': inicio = j;
                                     break;
                            case '-': inicio = j-1;
                                     break;          
                            }
                            if(j == 0 && inicio==-1)  inicio = 0;
                            else if(inicio!=-1) j=0;
                        }
                        //busco los valores a sumar o restar -->
                        for (int j = i+1; j < linea.length(); j++) {
                            switch (linea.charAt(j)) {
                            case '+': fin = j;
                                     break;
                            case '-': fin = j;
                                     break;          
                            }
                            if(j == linea.length()-1 && fin==-1)  fin = linea.length();
                            else if(fin!=-1) j=linea.length();
                        }
                        //llamo a suma o resta segun linea.charAt(i) y corto la cadena para pasar los numeros

                                valorA = Double.parseDouble(linea.substring(inicio,i));
                                valorB = Double.parseDouble(linea.substring(i+1,fin));
                                
                                principioLinea=linea.substring(0,inicio);
                                finalLinea=linea.substring(fin, linea.length());
                            //inserto el resultado en el hueco donde estaba la suma
                            if (linea.charAt(i) == '+'){    
                                linea="".concat(principioLinea).concat(Double.toString(sumar(valorA, valorB)).concat(finalLinea));
                            }
                            //inserto el resultado en el hueco donde estaba la resta
                            else if(linea.charAt(i) == '-'){
                                linea="".concat(principioLinea).concat(Double.toString(restar(valorA, valorB)).concat(finalLinea));
                            }   
                            i=0;
                            inicio=fin=-1;
                    }
                }
        return(Double.parseDouble(linea));
    }
}