package br.net.maskit

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Specialized Maskit for Date, Time and DateTime features.
 *
 * @author Douglas Siviotti
 * @since 1.0
 */
class DateMaskit<T>(val maskit: Maskit<T>) : Maskit<T>{

    override fun mask(id: T): Masked {
        val dt = LocalDateTime.now()
        DateTimeFormatter.ISO_DATE_TIME
        TODO("Not yet implemented")
    }

    override fun unmask(masked: Masked): T {
        TODO("Not yet implemented")
    }

    override fun randomMask(id: T): Masked {
        TODO("Not yet implemented")
    }
}