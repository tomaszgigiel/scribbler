package pl.tomaszgigiel.scribbler.cryptor

import com.typesafe.scalalogging.Logger

import java.nio.file.{Files, Paths}
import java.security.SecureRandom
import javax.crypto.spec.{IvParameterSpec, PBEKeySpec, SecretKeySpec}
import javax.crypto.{Cipher, SecretKeyFactory}

object Cryptor {
  val logger: Logger = Logger(getClass)

  val SECURE_RANDOM_ALGORITHM = "SHA1PRNG"
  val SECURE_RANDOM_PROVIDER = "SUN"
  val SECURE_RANDOM_SEED_LENGTH = 32
  val SECURE_RANDOM_SALT_LENGTH = 16

  val KEY_SPEC_ITERATION_COUNT = 65536
  val KEY_SPEC_KEY_LENGTH = 256

  val SECRET_KEY_FACTORY_ALGORITHM = "PBKDF2WithHmacSHA1"
  val SECRET_KEY_ALGORITHM = "AES"
  val IV_LENGTH = 16
  val CIPHER_TRANSFORMATION = "AES/CBC/PKCS5Padding"

  def encrypt(plain: String, encrypted: String, password: String): Unit = {
    logger.debug("encrypt")

    val plainBytes = Files.readAllBytes(Paths.get(plain))
    val encryptedBytes = doFinal(Cipher.ENCRYPT_MODE, password, plainBytes)
    Files.write(Paths.get(encrypted), encryptedBytes)
  }

  def decrypt(encrypted: String, plain: String, password: String): Unit = {
    logger.debug("decrypt")

    val encryptedBytes = Files.readAllBytes(Paths.get(encrypted))
    val plainBytes = doFinal(Cipher.DECRYPT_MODE, password, encryptedBytes)
    Files.write(Paths.get(plain), plainBytes)
  }

  private def doFinal(opmode: Integer, password: String, bytes: Array[Byte]): Array[Byte] = {
    val random = SecureRandom.getInstance(SECURE_RANDOM_ALGORITHM, SECURE_RANDOM_PROVIDER)
    val seed = new Array[Byte](SECURE_RANDOM_SEED_LENGTH)
    random.setSeed(seed)
    val salt = new Array[Byte](SECURE_RANDOM_SALT_LENGTH)
    random.nextBytes(salt)

    val spec = new PBEKeySpec(password.toCharArray, salt, KEY_SPEC_ITERATION_COUNT, KEY_SPEC_KEY_LENGTH)
    val skf = SecretKeyFactory.getInstance(SECRET_KEY_FACTORY_ALGORITHM)
    val key = skf.generateSecret(spec).getEncoded
    val secretKeySpec = new SecretKeySpec(key, SECRET_KEY_ALGORITHM)

    val ivBytes = new Array[Byte](IV_LENGTH)
    random.nextBytes(ivBytes)
    val iv = new IvParameterSpec(ivBytes)

    val cipher = Cipher.getInstance(CIPHER_TRANSFORMATION)
    cipher.init(opmode, secretKeySpec, iv)
    cipher.doFinal(bytes)
  }
}
