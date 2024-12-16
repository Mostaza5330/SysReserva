package DAO;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class DesEncryptionUtil {

    // Use a more secure encryption method
    private static final String ENCRYPTION_ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final int KEY_LENGTH = 256;
    private static final int ITERATION_COUNT = 65536;
    private static final int SALT_LENGTH = 16;

    /**
     * Genera una clave segura utilizando PBKDF2
     *
     * @param password Contraseña base para generación de clave
     * @param salt Sal para aumentar la seguridad
     * @return Clave secreta generada
     * @throws Exception Si hay error en la generación de la clave
     */
    private static SecretKey generateSecureKey(String password, byte[] salt) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        PBEKeySpec spec = new PBEKeySpec(
                password.toCharArray(),
                salt,
                ITERATION_COUNT,
                KEY_LENGTH
        );
        SecretKey secretKey = factory.generateSecret(spec);
        return new SecretKeySpec(secretKey.getEncoded(), ENCRYPTION_ALGORITHM);
    }

    /**
     * Cifra un número de teléfono de manera segura
     *
     * @param telefono Número de teléfono a cifrar
     * @param masterPassword Contraseña maestra
     * @return Texto cifrado en Base64
     * @throws Exception Si hay error en el cifrado
     */
    public static String cifrarTelefono(String telefono, String masterPassword) throws Exception {
        // Generar sal aleatoria
        byte[] salt = new byte[SALT_LENGTH];
        new SecureRandom().nextBytes(salt);

        // Generar clave
        SecretKey key = generateSecureKey(masterPassword, salt);

        // Inicializar cifrador
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        // Obtener vector de inicialización
        byte[] iv = cipher.getIV();

        // Cifrar datos
        byte[] encryptedData = cipher.doFinal(telefono.getBytes(StandardCharsets.UTF_8));

        // Combinar sal, IV y datos cifrados
        byte[] combinedData = new byte[salt.length + iv.length + encryptedData.length];
        System.arraycopy(salt, 0, combinedData, 0, salt.length);
        System.arraycopy(iv, 0, combinedData, salt.length, iv.length);
        System.arraycopy(encryptedData, 0, combinedData, salt.length + iv.length, encryptedData.length);

        // Convertir a Base64
        return Base64.getEncoder().encodeToString(combinedData);
    }

    /**
     * Descifra un número de teléfono cifrado
     *
     * @param telefonoCifrado Texto cifrado en Base64
     * @param masterPassword Contraseña maestra
     * @return Número de teléfono descifrado
     * @throws Exception Si hay error en el descifrado
     */
    public static String descifrarTelefono(String telefonoCifrado, String masterPassword) throws Exception {
        try {
            // Decodificar Base64
            byte[] combinedData = Base64.getDecoder().decode(telefonoCifrado);

            // Resto del método existente...
        

        // Extraer sal
        byte[] salt = new byte[SALT_LENGTH];
        System.arraycopy(combinedData, 0, salt, 0, salt.length);

        // Generar clave
        SecretKey key = generateSecureKey(masterPassword, salt);

        // Extraer IV
        byte[] iv = new byte[16]; // Tamaño estándar para AES
        System.arraycopy(combinedData, salt.length, iv, 0, iv.length);

        // Extraer datos cifrados
        byte[] encryptedData = new byte[combinedData.length - salt.length - iv.length];
        System.arraycopy(combinedData, salt.length + iv.length, encryptedData, 0, encryptedData.length);

        // Inicializar descifrador
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, key, new javax.crypto.spec.IvParameterSpec(iv));

        // Descifrar y devolver
        byte[] decryptedData = cipher.doFinal(encryptedData);
        return new String(decryptedData, StandardCharsets.UTF_8);
    } catch (IllegalArgumentException e) {
            System.err.println("Error de decodificación. Datos recibidos: " + telefonoCifrado);
            System.err.println("Longitud: " + telefonoCifrado.length());
            System.err.println("Contenido: " + Arrays.toString(telefonoCifrado.getBytes()));
            throw e;
        }
}

    // Método de prueba
    public static void main(String[] args) {
        try {
            String telefono = "1234567890";
            String clave = "sebas123";

            // Probar cifrado
            String telefonoCifrado = cifrarTelefono(telefono, clave);
            System.out.println("Teléfono original: " + telefono);
            System.out.println("Teléfono cifrado: " + telefonoCifrado);

            // Probar descifrado
            String telefonoDescifrado = descifrarTelefono(telefonoCifrado, clave);
            System.out.println("Teléfono descifrado: " + telefonoDescifrado);

            // Verificar igualdad
            if (telefono.equals(telefonoDescifrado)) {
                System.out.println("Cifrado y descifrado exitosos!");
            } else {
                System.err.println("Error: El teléfono descifrado no coincide con el original");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
