package com.bayer.bhc.doc41webui.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;

import com.bayer.bhc.doc41webui.common.logging.Doc41Log;


/**
 * Collection of useful conversion operations.
 * <p>
 * Provided are <strong>encoding and decoding </strong> operations oriented at
 * RFC 2045 Base64:
 * <ul>
 * <li>{@link #base64Encode(byte[])}</li>
 * <li>{@link #base64EncodeObject(Object)}</li>
 * <li>{@link #base64Decode(String)}</li>
 * <li>{@link #base64DecodeObject(String)}</li>
 * </ul>
 * </p>
 * <p>
 * Some convenience <strong>serialization </strong> methods:
 * <ul>
 * <li>{@link #convertObjToArr(Object)}</li>
 * <li>{@link #convertArrToObj(byte[])}</li>
 * </ul>
 * </p>
 * 
 * 
 * @author imrol
 */
public final class Convert {

  // have initial buffersize increased by that value
  private static final int UNICODE_EXTRA_BUFFER = 50;


  private Convert() {
    // empty consructor to avoid creating instance
  }


  /**
   * Convert a byte array into a printable (lowercase) format containing a
   * String of hexadecimal digit characters (two per byte).
   * 
   * @param aByteArray
   *            Byte array representation
   * @return the hexdump string
   */
  public static String hexdump(byte[] aByteArray) {

    StringBuilder tmpBuffer = new StringBuilder(aByteArray.length * 2);
    for (int i = 0; i < aByteArray.length; i++) {
      tmpBuffer.append(convertDigit(aByteArray[i] >> 4));
      tmpBuffer.append(convertDigit(aByteArray[i] & 0x0f));
    }
    return tmpBuffer.toString();

  }

  /**
   * Convert a String of hexadecimal digits into the corresponding byte array by
   * encoding each two hexadecimal digits as a byte.
   * 
   * throws an IllegalArgumentException if an invalid hexadecimal digit is
   * found, or the input string contains an odd number of hexadecimal digits
   * 
   * @param aDigitString
   *            Hexadecimal digits representation
   * 
   * @return byte array as hexdecoded
   */
  public static byte[] hexdecode(String aDigitString) {

    ByteArrayOutputStream tmpOs = new ByteArrayOutputStream();
    for (int i = 0; i < aDigitString.length(); i += 2) {
      char tmpChar1 = aDigitString.charAt(i);
      if ((i + 1) >= aDigitString.length()) {
        throw new IllegalArgumentException("Odd Hexadecimal");
      }
      char tmpChar = aDigitString.charAt(i + 1);
      byte tmpByte = 0;
      if ((tmpChar1 >= '0') && (tmpChar1 <= '9')) {
        tmpByte += ((tmpChar1 - '0') * 16);
      } else if ((tmpChar1 >= 'a') && (tmpChar1 <= 'f')) {
        tmpByte += ((tmpChar1 - 'a' + 10) * 16);
      } else if ((tmpChar1 >= 'A') && (tmpChar1 <= 'F')) {
        tmpByte += ((tmpChar1 - 'A' + 10) * 16);
      } else {
        throw new IllegalArgumentException("Malformed Hexadecimal");
      }
      if ((tmpChar >= '0') && (tmpChar <= '9')) {
        tmpByte += (tmpChar - '0');
      } else if ((tmpChar >= 'a') && (tmpChar <= 'f')) {
        tmpByte += (tmpChar - 'a' + 10);
      } else if ((tmpChar >= 'A') && (tmpChar <= 'F')) {
        tmpByte += (tmpChar - 'A' + 10);
      } else {
        throw new IllegalArgumentException("Malformed Hexadecimal");
      }
      tmpOs.write(tmpByte);
    }
    return tmpOs.toByteArray();

  }

  /**
   * [Private] Convert the specified value (0 .. 15) to the corresponding
   * hexadecimal digit.
   * 
   * @param aValue
   *            Value to be converted
   */
  private static char convertDigit(int aValue) {

    aValue &= 0x0f;
    if (aValue >= 10) {
      return (char) (aValue - 10 + 'a');
    }
    return (char) (aValue + '0');

  }

  /**
   * Returns the given string in his hashed representation. Conversion is
   * provided by using <code>SHA-1</code> digest algorithm.
   * 
   * @param aString
   *            the text to hash, may be <code>null</code>
   * @return <code>null</code> if the text to hash was <code>null</code>,
   *         otherwise the hashed representation of the given text
   * 
   * @see MessageDigest
   */
  public static String hashString(String aString) {
    if (aString == null) {
      return null;
    }
    String tmpString = null;
    try {
      byte[] tmpBuff = aString.getBytes("ISO-8859-1");
      MessageDigest tmpDigest = MessageDigest.getInstance("SHA-1");
      tmpDigest.reset();
      tmpDigest.update(tmpBuff);
      byte[] tmpHash = tmpDigest.digest();
      tmpString = hexdump(tmpHash);
    } catch (Exception e) {
    	Doc41Log.get().error(Convert.class,"Convert-hashString",e);
    }
    return tmpString;

  }

  /**
   * Returns the given string in his hashed representation. Conversion is
   * provided by using <code>MD5</code> digest algorithm.
   * 
   * @param aString
   *            the text to hash, may be <code>null</code>
   * @return <code>null</code> if the text to hash was <code>null</code>,
   *         otherwise the hashed representation of the given text
   * 
   * @see MessageDigest
   */
  public static String hashStringMD5(String aString) {
    String tmpString = null;
    try {
      byte[] tmpBuff = aString.getBytes("ISO-8859-1");
      MessageDigest tmpDigest = MessageDigest.getInstance("MD5");
      tmpDigest.reset();
      tmpDigest.update(tmpBuff);
      byte[] tmpHash = tmpDigest.digest();
      tmpString = hexdump(tmpHash);
    } catch (Exception e) {
    	Doc41Log.get().error(Convert.class,"Convert-hashStringMD5",e);

    }
    return tmpString;

  }

  /**
   * Serializes the given object and returns the resulting byte representation.
   * The objects to handle must be serializable by nature or by contract.
   * 
   * @param anObject
   *            the object to serialize
   * @return the resulting byte array from successful serialization
   * 
   * @see java.io.Serializable
   */
  public static byte[] convertObjToArr(Object anObject) {
    byte[] tmpBuff = new byte[0];
    try {
      ByteArrayOutputStream tmpBAOS = new ByteArrayOutputStream();
      ObjectOutputStream tmpOs = new ObjectOutputStream(tmpBAOS);
      tmpOs.writeObject(anObject);
      tmpBAOS.close();
      tmpBuff = tmpBAOS.toByteArray();
    } catch (Exception e) {
    	Doc41Log.get().error(Convert.class,"Convert-OToA",e);
    }
    return tmpBuff;

  }

  /**
   * Interpretes the given byte array as serialized object and constructs an
   * instance by deserialization. The return instance WILL NOT be the same as
   * used for input
   * 
   * @param aByteArray
   *            the byte array representation of the serialized object
   * @return the instance restored by deserialization
   */
  public static Object convertArrToObj(byte[] aByteArray) {
    Object tmpOut = null;
    try {
      ByteArrayInputStream bi = new ByteArrayInputStream(aByteArray);
      ObjectInputStream coi = new ObjectInputStream(bi);
      tmpOut = coi.readObject();

      bi.close();
    } catch (Exception e) {
    	Doc41Log.get().error(Convert.class,"Convert-AToO",e);
    }
    return tmpOut;
  }

  /**
   * Encodes binary data using the base64 algorithm and returns the string
   * representation of the chunked output. The genuine chunk contained 76
   * character blocks, but length may differ after the provided subsequent
   * string conversion with <code>US-ASCII</code> charset.
   * 
   * @param aByteArray
   *            binary data to encode
   * @return if encoding was successful, the string representation of the Base64
   *         characters chunked in 76 character blocks, otherwise
   *         <code>null</code>
   * 
   * @see #base64EncodeObject(Object)
   */
  public static String base64Encode(byte[] aByteArray) {
    String tmpString = null;
    try {
      tmpString = new String(Base64.encodeBase64Chunked(aByteArray), "US-ASCII");
    } catch (Exception e) {
    	Doc41Log.get().error(Convert.class,"Convert-base64Encode",e);
    }
    return tmpString;
  }

  /**
   * Converts an object to binary data, encodes this data using the base64
   * algorithm and returns the string representation of the chunked output. The
   * genuine chunk contained 76 character blocks, but length may differ after
   * the provided subsequent string conversion.
   * 
   * @param anObject
   *            the object to encode by previously converting it to binary data
   * @return if encoding was successful, the string representation of the Base64
   *         characters chunked in 76 character blocks, otherwise
   *         <code>null</code>
   * 
   * @see #base64Encode(byte[])
   * @see #convertObjToArr(Object)
   */
  public static String base64EncodeObject(final Object anObject) {
    String tmpString = null;
    try {
      byte[] tmpArray = convertObjToArr(anObject);

      tmpString = base64Encode(tmpArray);
    } catch (Exception e) {
    	Doc41Log.get().error(Convert.class,"Convert-base64EncodeObject",e);
    }
    return tmpString;
  }

  /**
   * Decodes a string represention of Base64 data bytes into octects. The string
   * is interpreted with <code>US-ASCII</code> charset.
   * 
   * @param aString
   *            a string representation of a byte array containing Base64 data
   * @return if decoding was successful, a byte Array containing decoded data,
   *         otherwise <code>null</code>
   * 
   * @see #base64DecodeObject(String)
   */
  public static byte[] base64Decode(String aString) {
    if (aString == null || aString.equals("")) {
      return null;
    }
    byte[] tmpArray = null;
    try {
      tmpArray = Base64.decodeBase64(aString.getBytes("US-ASCII"));
    } catch (Exception e) {
    	Doc41Log.get().error(Convert.class,"Convert-base64Decode",e);
    }
    return tmpArray;
  }

  /**
   * Instantiates an object from its decoded string represention of Base64 data
   * bytes. The string is interpreted with <code>US-ASCII</code> charset.
   * Deserialization is used for instantiation.
   * 
   * @param aString
   *            a string representation of a byte array containing Base64 data
   *            which result from a serialized object
   * @return if decoding was successful, a deserialized object, otherwise
   *         <code>null</code>
   * 
   * @see #base64DecodeObject(String)
   */
  public static Object base64DecodeObject(String aString) {
    if (aString == null || aString.equals("")) {
      return null;
    }
    Object tmpObject = null;
    try {
      tmpObject = convertArrToObj(base64Decode(aString));

    } catch (Exception e) {
    	Doc41Log.get().error(Convert.class,"Convert-base64DecodeObject",e);
    }
    return tmpObject;
  }

  /**
   * Replace all occurences of a String in another String. If no replacement is
   * possible (null strings provided) the same String is returned, otherwise a
   * new String object.
   * 
   * @param anInputString
   *            The String containing tokens for replacement.
   * @param aSearchforString
   *            The String to be searched for.
   * @param aReplacewithString
   *            The replacement value for the searchfor string.
   * @return String with all possible replacements.
   * 
   */
  public static String replaceAll(String anInputString, String aSearchforString, String aReplacewithString) {
    // we do not do null replacements
    if (aReplacewithString == null || aSearchforString == null) {
      return anInputString;
    }
    if (anInputString == null) {
      return null;
    }
    if (anInputString.equals("")) {
      return anInputString;
    }
    int width = aSearchforString.length();
    StringBuilder tmpin = new StringBuilder();
    int tmpPos = 0;
    int tmpLastpos = 0;
    do {
      tmpPos = anInputString.indexOf(aSearchforString, tmpPos);
      if (tmpPos > -1) {

        tmpin.append(anInputString.substring(tmpLastpos, tmpPos));
        tmpin.append(aReplacewithString);
        tmpPos += width;
        tmpLastpos = tmpPos;
      } else {
        tmpin.append(anInputString.substring(tmpLastpos));
        break;
      }
    } while (tmpPos > -1);
    return tmpin.toString();
  }

  /**
   * Replace all occurences of tokens in a String with corresponding values
   * found in a parameter map. A token is a key surrounded by delimiter chars
   * 
   * e.g. <code>&#064;key&#064;</code>, where <code>'&#064;'</code> is the
   * delimiter char. For replacement the <code>toString()</code> method of an
   * object contained in the parameter map is called to retrieve the replacement
   * value. Only items with matching keys for the tokens will be used. Example:
   * the String <code>"@value@XXXX@value2@YYYY"</code> will be converted to
   * <code>"AXXXXBYYYY"</code> if delimiter equals <code>'&#064;'</code> and
   * the provided parameter map contains (at least) the following entries:
   * <code>value="A"</code>, <code>value2="B"</code>. Tokens, for which no
   * valid replacement can be found in the parameter map will not be replaced.
   * As a simple consistency check, only the pairwise existence of delimiter
   * chars is checked.<br>
   * If the template contains placeholder delimiter chars, delimeter chars have
   * to be escaped by a backslash!
   * 
   * @param anInputString
   *            The String containing tokens for replacement.
   * @param aParameterMap
   *            A Map with the replacement objects.
   * @param aDelimiter
   *            The char which denotes a token to be replaced.
   * @param anEliminateFlag
   *            If the replacement vlaue is not contained in the paremeter map,
   *            the placeholder should be eliminated
   * @return A String with all possible replacements.
   * 
   * @throws Exception
   *             if a token in the input String is not correctly surrounded by
   *             delimiters
   * 
   */
  public static String replaceAll(String anInputString, Map<String, Object> aParameterMap, char aDelimiter,
      boolean anEliminateFlag) throws Exception {
    if (anInputString == null) {
      return null;
    }
    if (anInputString.equals("")) {
      return anInputString;
    }
    if (aParameterMap == null) {
      aParameterMap = new HashMap<String, Object>();
    }

    Set<String> tmpKeys = new HashSet<String>();

    // replace escaped delimiters to hide them from the
    // token-replace-algorithm
    anInputString = replaceAll(anInputString, "\\" + aDelimiter, "&6789;&9876;");

    int tmpPos = 0;
    int tmpPos2 = 0;
    String tmpKey;
    tmpPos = anInputString.indexOf(aDelimiter);
    // no replacements
    if (tmpPos == -1) {
      return anInputString;
    }
    // determine needed replacements
    do {
      tmpPos2 = anInputString.indexOf(aDelimiter, tmpPos + 1);
      if (tmpPos2 == -1) {
        throw new Exception("Convert-replaceAll error: key not terminated by second delimiter. Source:" + anInputString);
      }
      tmpKey = anInputString.substring(tmpPos + 1, tmpPos2);
      tmpKeys.add(tmpKey);
      tmpPos = anInputString.indexOf(aDelimiter, tmpPos2 + 1);
    } while (tmpPos != -1);

    String tmpString = anInputString;
    Iterator<String> it = tmpKeys.iterator();
    while (it.hasNext()) {
      String tmpSearchfor = it.next();
      String tmpParmkey = aDelimiter + tmpSearchfor + aDelimiter;
      Object replaceobj = aParameterMap.get(tmpSearchfor);
      boolean tmpKeyFound = aParameterMap.containsKey(tmpSearchfor);
      if (!anEliminateFlag && !tmpKeyFound) {
        // if no elimination was requested and we have no replacement
        // value
        // we do not need to bother anymore - next round
        continue;
      }
      String tmpReplacewith = null;
      if (replaceobj != null) {
        tmpReplacewith = replaceobj.toString();
      } else if (anEliminateFlag) {
        tmpReplacewith = "";
      }
      tmpString = replaceAll(tmpString, tmpParmkey, tmpReplacewith);
    }

    // finally replace the masked delimiters again
    tmpString = replaceAll(tmpString, "&6789;&9876;", "" + aDelimiter);
    return tmpString;
  }


  /**
   * Delivers a String with XML ready escaped "Unicode" characters in their
   * hex-code representation
   * 
   * @param anArray
   *            the character array to convert
   * @return the escaped String
   */
  public static String escapeUnicode(char[] anArray) {
    final int arrayLength = anArray.length + UNICODE_EXTRA_BUFFER;
    StringBuilder tmpOut = new StringBuilder(arrayLength);
    for (int i = 0; i < anArray.length; i++) {
      char tmpChar = anArray[i];
      if (tmpChar > 255) {
        tmpOut.append("&#x");
        tmpOut.append(Integer.toHexString(tmpChar));
        tmpOut.append(';');
      } else {
        tmpOut.append(tmpChar);
      }
    }
    return tmpOut.toString();
  }


}