/**
 * Clase principal que muestra los menús correspondientes a cada algoritmo y las opciones de ayuda
 * @autor Jairo A. Valencia
 * @date 01-may-2019
 * @version 1.0
 */









public class EncryptionAlgorithms {

    /**
     * Evalua los arguemntos recibidos y e invoca el menú correspondiente
     * @param args Argumentos recibidos por consola
     * @return Menú para imprimir
     */
    private static String evaluateInitialArguments(String[] args){
        //No argumentos
        if(Constants.NUMBER_0 == args.length){
            return Text.NO_ARGUMENTS_MENU;
        }
        //1 argumento
        else if(Constants.NUMBER_1 == args.length){
            if(Constants.HELP_ARGUMENT.equals(args[0]))
                return Text.HELP_MENU;
            else if(Constants.PLAYFAIR.equals(args[0]))
                return Text.PLAYFAIR_MENU;
            else if(Constants.ANALISIS_FRECUENCIA.equals(args[0]))
                return Text.ANALISIS_FREC_POLI_MENU;
            else
                return Util.printInvalidMenu(args[0]);
        }
        else if(Constants.PLAYFAIR.equals(args[0])){
            return evaluatePlayfair(args);
        }
        else if(Constants.ANALISIS_FRECUENCIA.equals(args[0])){
            return evaluateAnalisisFrecPoli(args);
        }
        //>1 argumento
        else{
            int lastPos = args.length - 1;
            return Util.printInvalidMenu(args[lastPos]);
        }
    }

    /**
     * Usa los parámetros de playfair e invoca cifrar o descifrar según corresponda
     * @param args
     * @return
     */
    private static String evaluatePlayfair(String[] args){
        String content = Util.readFile(args[3]);
        String key = Util.readFile(args[5]);

        String outFileName;
        long startTime;
        long endTime;

        try{
            int indexPoint = args[3].indexOf(".");
            outFileName = args[3].substring(Constants.NUMBER_0, indexPoint);
        }catch (Exception e){
            outFileName = args[3];
        }

        if ("-c".equals(args[1])) {
            outFileName += ".cif";
            startTime = System.currentTimeMillis();
            String crypto = Playfair.encode(content, key);
            Util.writeFile(outFileName, crypto);
            endTime = System.currentTimeMillis() - startTime;
        }
        else {
            outFileName += ".des";
            startTime = System.currentTimeMillis();
            String dsc = Playfair.decode(content, key);
            Util.writeFile(outFileName, dsc);
            endTime = System.currentTimeMillis() - startTime;
        }

        System.out.println("Cantidad de caracteres: " + content.length());
        System.out.println("Tamaño de la clave: " + key.length());
        System.out.println("Tiempo de ejecución: " + endTime + " milisegundos");
        System.out.println("Archivo generado: " + outFileName);
        return "\nProceso finalizado\n";
    }

    /**
     * Verifica que los parámatros para realizar el análsis por frecuencia sean correctos e invoca la clase que
     * realiza el análisis
     * @param args
     * @return
     */
    private static String evaluateAnalisisFrecPoli(String[] args){
        if(Constants.NUMBER_5 == args.length) {
            boolean correctArguments = false;
            //Clase que procesa el análisis
            AnalysisPolyalphaFreq analysisPolyalphaFreq = new AnalysisPolyalphaFreq();
            //Valida bandera "in"
            if (Constants.IN_ARGUMENT.equals(args[1])) {
                //Valida bandera "tng"
                if (Constants.TNG_ARGUMENT.equals(args[3])) {
                    //Valida que el parámetro tng sea entero
                    if(Util.isInteger(args[4])){
                        correctArguments = true;
                        String text = Util.readFile(args[2]);
                        return analysisPolyalphaFreq.executeAnalysis(text, args[4]);
                    }
                }
            }
            if(correctArguments == false){
                return Text.INVALID_ARGUMENTS_AFP;
            }
        }else{
            int lastPos = args.length - 1;
            return Util.printInvalidMenu(args[lastPos]);
        }
        return null;
    }

    /**
     * Método principal que recibe los parámetros para ejecutar el programa
     * @param args Argumentos y banderas
     */
    public static void main(String[] args) {

        try {
            Util.printMenu(EncryptionAlgorithms.evaluateInitialArguments(args));
        }catch (Exception e){
            Util.printMenu(Text.GENERAL_ERROR);
        }
    }
}
