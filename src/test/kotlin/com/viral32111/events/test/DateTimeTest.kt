package com.viral32111.events.test

import com.viral32111.events.Server
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class DateTimeTest {
	@Test
	fun dateTimeTest() {
		val dateTime = Server.getDateTime()
		assertNotEquals( "", dateTime )
	}
}