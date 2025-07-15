package Estagios;

import Pipeline.MEMWB;
import UnidadeFuncionais.BancoRegistradores;

public class WriteBack {
    
    // Método para escrever um valor em um registrador
    public static void execute() {

        int registerAddress = MEMWB.getRd();

        if(MEMWB.isRegWrite()){
            if(MEMWB.isMemToReg()){
            int memoryData = MEMWB.getMemoryReadData();
            BancoRegistradores.setRegisterValue(registerAddress, memoryData);

            //Escreve no registerAddress
            }

            else{
                int aluData = MEMWB.getAluResult();
                BancoRegistradores.setRegisterValue(registerAddress, aluData);
            }

        }
        
        
    }
    
}
