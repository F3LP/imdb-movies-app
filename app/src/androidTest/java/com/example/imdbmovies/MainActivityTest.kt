package com.example.imdbmovies

import com.example.imdbmovies.data.ImdbApi
import com.example.imdbmovies.data.model.PopularMoviesResult
import com.google.gson.Gson
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import java.io.*
import javax.inject.Inject

@HiltAndroidTest
class MainActivityTest {

    private val mockWebServer = MockWebServer()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var service: ImdbApi

    @Before
    fun setUp() {
        mockWebServer.start(8080)
        hiltRule.inject()
    }

    @Test
    fun `testRequestSuccess`() = runTest {
        val expected = Gson()
            .fromJson(FileUtil.kotlinReadFileWithNewLineFromResources("MoviesReturn.json"), PopularMoviesResult::class.java)
        mockWebServer.dispatcher = successDispatcher()
        val actual = service.getPopularMovies()

        assertEquals(expected, actual)
    }

    @Test(expected = Exception::class)
    fun `testRequestError`() = runTest {
        val expected = Gson()
            .fromJson(FileUtil.kotlinReadFileWithNewLineFromResources("MoviesReturn.json"), PopularMoviesResult::class.java)
        mockWebServer.dispatcher = errorDispatcher()
        service.getPopularMovies()
    }


    private fun successDispatcher(): Dispatcher {
        return object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when(request.path) {
                    else -> MockResponse().setResponseCode(200)
                        .setBody(
                            FileUtil.kotlinReadFileWithNewLineFromResources("MoviesReturn.json")
                        )
                }
            }
        }
    }

    private fun errorDispatcher(): Dispatcher {
        return object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when(request.path) {
                    else -> MockResponse().setResponseCode(404).setBody("")
                }
            }
        }
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    object FileUtil {
        @Throws(IOException::class)
        fun readFileWithoutNewLineFromResources(fileName: String): String {
            var inputStream: InputStream? = null
            try {
                inputStream = getInputStreamFromResource(fileName)
                val builder = StringBuilder()
                val reader = BufferedReader(InputStreamReader(inputStream))

                var str: String? = reader.readLine()
                while (str != null) {
                    builder.append(str)
                    str = reader.readLine()
                }
                return builder.toString()
            } finally {
                inputStream?.close()
            }
        }

        @Throws(IOException::class)
        fun readFileWithNewLineFromResources(fileName: String): String {
            var inputStream: InputStream? = null
            try {
                inputStream = getInputStreamFromResource(fileName)
                val builder = StringBuilder()
                val reader = BufferedReader(InputStreamReader(inputStream))

                var theCharNum = reader.read()
                while (theCharNum != -1) {
                    builder.append(theCharNum.toChar())
                    theCharNum = reader.read()
                }

                return builder.toString()
            } finally {
                inputStream?.close()
            }
        }

        fun kotlinReadFileWithNewLineFromResources(fileName: String): String {
            return getInputStreamFromResource(fileName)?.bufferedReader()
                .use { bufferReader -> bufferReader?.readText() } ?: ""
        }

        @Throws(IOException::class)
        fun readBinaryFileFromResources(fileName: String): ByteArray {
            var inputStream: InputStream? = null
            val byteStream = ByteArrayOutputStream()
            try {
                inputStream = getInputStreamFromResource(fileName)

                var nextValue = inputStream?.read() ?: -1

                while (nextValue != -1) {
                    byteStream.write(nextValue)
                    nextValue = inputStream?.read() ?: -1
                }
                return byteStream.toByteArray()

            } finally {
                inputStream?.close()
                byteStream.close()
            }
        }

        fun kotlinReadBinaryFileFromResources(fileName: String): ByteArray {
            ByteArrayOutputStream().use { byteStream ->
                getInputStreamFromResource(fileName)?.copyTo(byteStream)
                return byteStream.toByteArray()
            }
        }

        private fun getInputStreamFromResource(fileName: String)
                = javaClass.classLoader?.getResourceAsStream(fileName)
    }
}


//@Suppress("BlockingMethodInNonBlockingContext")
//@HiltAndroidTest
//class MainActivityTest {
//
//    private lateinit var recipeListViewModel: RecipeListViewModel
//    private val hiltRule = HiltAndroidRule(this)
//    private val instantTaskExecutorRule = InstantTaskExecutorRule()
//    private val coroutineRule = MainCoroutineRule()
//
//    @get:Rule
//    val rule = RuleChain
//        .outerRule(hiltRule)
//        .around(instantTaskExecutorRule)
//        .around(coroutineRule)
//
//    @Inject
//    lateinit var useCase: GetAllRecipesUseCase
//
//    @Inject
//    lateinit var categoryDAO: CategoryDAO
//
//    @Inject
//    lateinit var recipeDAO: RecipeDAO
//
//    @Before
//    fun setUp() {
//        hiltRule.inject()
//        recipeListViewModel = RecipeListViewModel(useCase)
//    }
//
//    @Test
//    fun getRecipes() = coroutineRule.runBlockingTest {
//        saveRecipe()
//        Assert.assertTrue(getValue(recipeListViewModel.recipes).size == 5)
//    }
//
//    private fun createFakeRecipe() = Recipe(
//        name = "chocolate cake",
//        pictureUrl = "test",
//        utensils = "blend",
//        serves = 2,
//        cookTime = 2,
//        directions = "test",
//        categoryId = 1
//    )
//
//    private suspend fun saveRecipe() {
//        categoryDAO.saveCategory(Category(name = "name", namePt = "nome"))
//        for (recipe in 1..5) {
//            recipeDAO.saveRecipe(createFakeRecipe())
//        }
//    }
//}
