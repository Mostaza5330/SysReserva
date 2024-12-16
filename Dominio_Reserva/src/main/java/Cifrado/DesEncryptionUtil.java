/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Cifrado;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Sebastian Murrieta Verduzco - 233463
 */
public class DesEncryptionUtil {

    
    // Método para cifrar el número de teléfono
    public static String cifrarTelefono(String telefono, String clave) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(clave.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] telefonoBytes = telefono.getBytes();
        byte[] telefonoCifrado = cipher.doFinal(telefonoBytes);
        return Base64.getEncoder().encodeToString(telefonoCifrado); // Convertir a Base64 para almacenamiento
    }

    // Método para descifrar el número de teléfono
    public static String descifrarTelefono(String telefonoCifrado, String clave) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(clave.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] telefonoCifradoBytes = Base64.getDecoder().decode(telefonoCifrado);
        byte[] telefonoDescifrado = cipher.doFinal(telefonoCifradoBytes);
        return new String(telefonoDescifrado);
    }
    
}
