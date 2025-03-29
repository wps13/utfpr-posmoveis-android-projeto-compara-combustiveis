package br.edu.utfpr.comparacombustiveis

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.anything
import org.hamcrest.Matchers.containsString
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InstrumentedTest {

    @get:Rule()
    val activity = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun checkAlcoolCheaper() {
        onView(withId(R.id.opcao1Bt)).perform(click())

        onData(anything()).inAdapterView(withId(R.id.listaCombustiveis)).atPosition(0)
            .perform(click())

        onView(withId(R.id.opcao2Bt)).perform(click())

        onData(anything()).inAdapterView(withId(R.id.listaCombustiveis)).atPosition(1)
            .perform(click())

        onView(withId(R.id.precoCombustivel1)).perform((typeText("6.02")))

        onView(withId(R.id.precoCombustivel2)).perform((typeText("3.99")))

        onView(withId(R.id.resultadoBt)).perform(click())

        onView(withId(R.id.resultadoDescricao)).check(
            matches(

                withText("O Combustível mais barato é Álcool")
            )
        )

        onView(withId(R.id.resultadoDescricao2)).check(
            matches(
                withText(
                    containsString("R$ 4.25")
                )
            )
        )
    }

    @Test
    fun checkGasolinaCheaper() {
        onView(withId(R.id.opcao1Bt)).perform(click())

        onData(anything()).inAdapterView(withId(R.id.listaCombustiveis)).atPosition(0)
            .perform(click())

        onView(withId(R.id.opcao2Bt)).perform(click())

        onData(anything()).inAdapterView(withId(R.id.listaCombustiveis)).atPosition(1)
            .perform(click())

        onView(withId(R.id.precoCombustivel1)).perform((typeText("6.45")))

        onView(withId(R.id.precoCombustivel2)).perform((typeText("4.99")))

        onView(withId(R.id.resultadoBt)).perform(click())

        onView(withId(R.id.resultadoDescricao)).check(
            matches(

                withText("O Combustível mais barato é Gasolina")
            )
        )

        onView(withId(R.id.resultadoDescricao2)).check(
            matches(
                withText(
                    containsString("R$ 4.55")
                )
            )
        )
    }

    @Test
    fun checkEqualsOptions() {
        onView(withId(R.id.opcao1Bt)).perform(click())

        onData(anything()).inAdapterView(withId(R.id.listaCombustiveis)).atPosition(0)
            .perform(click())

        onView(withId(R.id.opcao2Bt)).perform(click())

        onData(anything()).inAdapterView(withId(R.id.listaCombustiveis)).atPosition(0)
            .perform(click())

        onView(withId(R.id.precoCombustivel1)).perform((typeText("6.45")))

        onView(withId(R.id.precoCombustivel2)).perform((typeText("4.99")))

        onView(withId(R.id.resultadoBt)).perform(click())

        onView(withId(R.id.resultadoDescricao)).check(
            matches(

                withText("O Combustível mais barato é nenhum, os combustíveis escolhidos são iguais.")
            )
        )
    }
}