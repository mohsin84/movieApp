package mohsin.reza.movieapp.home

import android.content.Intent
import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasSibling
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import mohsin.reza.movieapp.MainActivity
import mohsin.reza.movieapp.R.id
import mohsin.reza.movieapp.TestApp
import mohsin.reza.movieapp.TestAppComponent
import mohsin.reza.movieapp.network.NetworkSettings
import mohsin.reza.movieapp.ui.ShelveViewHolder
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import timber.log.Timber
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, true, false)

    @Inject
    lateinit var networkSettings: NetworkSettings

    @Before
    fun setUp() {
        (TestApp.app.appComponent as TestAppComponent).inject(this)
    }

    private fun launchActivity() {
        val intent = Intent(
            InstrumentationRegistry.getInstrumentation().targetContext,
            MainActivity::class.java
        )
        activityRule.launchActivity(intent)
    }

    @Test
    fun verifyHomePageContent() {
        launchActivity()
        // verify recyclerView is displayed
        waitFor(withId(id.home_page_recycler_view), matches(isDisplayed()))
        // verify first shelve
        waitFor(
            withId(id.movie_item_image_view) and withContentDescription("Mulan") and isDescendantOfA(
                withId(id.item_shelve_recycler_view) and hasSibling(withText("Action"))
            )
        )
        // scroll to Sixth shelve
        Espresso.onView(withId(id.home_page_recycler_view)).perform(
            RecyclerViewActions.scrollToPosition<ShelveViewHolder>(6)
        )
        // verify we are in Sixth shelve
        waitFor(
            withId(id.movie_item_image_view) and withContentDescription("Enola Holmes") and isDescendantOfA(
                withId(id.item_shelve_recycler_view) and hasSibling(withText("Crime"))
            )
        )
        // verify third item of Sixth shelve
        Espresso.onView(
            withId(id.item_shelve_recycler_view) and hasSibling(withText("Drama"))
        ).perform(RecyclerViewActions.scrollToPosition<ShelveViewHolder>(3))

        waitFor(
            withId(id.movie_item_image_view) and withContentDescription("Ava") and isDescendantOfA(
                withId(id.item_shelve_recycler_view) and hasSibling(withText("Drama"))
            )
        )
    }

    @Test
    fun verifyMovieContentDetails() {
        launchActivity()
        waitFor(withId(id.home_page_recycler_view), matches(isDisplayed()))
        // scroll to last item of first row
        waitFor(
            withId(id.item_shelve_recycler_view) and hasSibling(withText("Action"))
        ).perform(RecyclerViewActions.scrollToPosition<ShelveViewHolder>(7))
        // verify we are at the last item
        waitFor(
            withId(id.movie_item_image_view) and withContentDescription("Santana") and isDescendantOfA(
                withId(id.item_shelve_recycler_view) and hasSibling(withText("Action"))
            )
        )
        // click to last item of first row
        waitFor(withId(id.item_shelve_recycler_view) and hasSibling(withText("Action")))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<ShelveViewHolder>(
                    7,
                    ViewActions.click()
                )
            )
        // verify content details for movie "Santana"
        waitFor(withId(id.item_movie_poster_image), matches(isDisplayed()))
        waitFor(withId(id.movie_title_text) and withText("Santana"), matches(isDisplayed()))
        waitFor(
            withId(id.movie_details_text) and withText(
                Matchers.containsString("Action\nRelease 28 Aug 2020")
            ),
            matches(isDisplayed())
        )
    }

    @Test
    fun verifyAppRememberSelectionAndRestoreToSelectedState() {
        launchActivity()
        waitFor(
            withId(id.item_shelve_recycler_view) and hasSibling(withText("Action"))
        ).perform(RecyclerViewActions.scrollToPosition<ShelveViewHolder>(6))
        waitFor(
            withId(id.movie_item_image_view) and withContentDescription("Rogue") and isDescendantOfA(
                withId(id.item_shelve_recycler_view) and hasSibling(withText("Action"))
            )
        )
        waitFor(withId(id.item_shelve_recycler_view) and hasSibling(withText("Action")))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<ShelveViewHolder>(
                    6,
                    ViewActions.click()
                )
            )
        Espresso.pressBack()
        waitFor(
            withId(id.movie_item_image_view) and withContentDescription("Rogue") and isDescendantOfA(
                withId(id.item_shelve_recycler_view) and hasSibling(withText("Action"))
            )
        )
    }

    @Test
    fun verifyErrorIsShownWhenNoNetwork() {
        // simulate network is disconnected
        networkSettings.isConnectedToInternet = false
        launchActivity()
        // verify error error and retry button is shown
        waitFor(withId(id.content_error_message), matches(isDisplayed()))
        waitFor(withId(id.retry_button), matches(isDisplayed()))

        // simulate network is connected
        networkSettings.isConnectedToInternet = true
        Espresso.onView(withId(id.retry_button)).perform(ViewActions.click())
        waitFor(
            withId(id.content_error_message),
            matches(Matchers.not(isDisplayed()))
        )
        // verify first tile
    }

    private fun waitFor(
        matcher: Matcher<View>,
        viewAssertion: ViewAssertion = matches(
            isDisplayed()
        ),
        seconds: Int = 10
    ): ViewInteraction {
        var counter = 0
        while (true) {
            try {
                return Espresso.onView(matcher).check(viewAssertion)
            } catch (e: Throwable) {
                counter++
                if (counter > seconds * 10) {
                    Timber.e(e, "Waited for $seconds seconds")
                    throw e
                }
                Thread.sleep(100)
            }
        }
    }

    private infix fun <T> Matcher<T>.and(another: Matcher<T>): Matcher<T> =
        Matchers.allOf(this, another)
}