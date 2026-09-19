package com.example

import com.example.data.CatalogData
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun catalogPackages_areValid() {
    assertTrue(CatalogData.FF_PACKAGES.isNotEmpty())
    assertTrue(CatalogData.SPECIAL_OFFERS.isNotEmpty())
    assertTrue(CatalogData.TOPUP_CATEGORIES.isNotEmpty())

    // Ensure all packages have valid IDs and prices
    CatalogData.FF_PACKAGES.forEach { pkg ->
      assertTrue(pkg.id.isNotBlank())
      assertTrue(pkg.priceBdt > 0)
      assertTrue(pkg.title.isNotBlank())
    }
  }

  @Test
  fun paymentMethods_includeWallets() {
    val methods = CatalogData.PAYMENT_METHODS.map { it.id }
    assertTrue(methods.contains("wallet"))
    assertTrue(methods.contains("bkash"))
    assertTrue(methods.contains("nagad"))
    assertTrue(methods.contains("rocket"))
  }
}
