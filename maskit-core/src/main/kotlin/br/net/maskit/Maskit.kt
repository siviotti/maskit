package br.net.maskit

/**
 * "Mask Kit" or "Mask It".
 * Façade class to use Pseudonimization of Ids (Text or Numeric).
 *
 *
 * @author Douglas Siviotti
 * @since 1.0
 * @see NumericMaskit
 * @see NumericId
 * @param T The ID Type (Numeric or Text)
 */
interface Maskit<T> {

    /**
     * Pseudonymizes an Id returning the String representation (masked Id).
     *
     * This operation is reversible through the "unmask" method.
     *
     * @param id The numeric or text ID to be pseudonymized
     * @return The reversible "pseudonym" of this ID as a String representation.
     * @see unmask
     */
    fun mask(id: T): Masked

    /**
     * Reverts the pseudonymization returning the original NumeridId
     * if the pseudonym is a valid one.
     *
     * This operation reverts the <code>pseudo<code> method.
     *
     * @param masked The masked Id (pseudonym) generated by <code>mask<code> method
     * @return The original NumericId that gave rise to the pseudonym
     * @see mask
     * @see randomMask
     */
    fun unmask(masked: Masked): T

    /**
     * Pseudonymizes an Id returning a random String representation (masked Id).
     *
     * This operation is reversible through the "unmask" method.
     *
     * @param id The numeric or text ID to be pseudonymized
     * @return The reversible "pseudonym" of this ID as a String representation.
     * @see unmask
     */
    fun randomMask(id: T): Masked

}