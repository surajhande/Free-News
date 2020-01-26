import android.content.Context
import com.srjhnd.freenews.api.NewsAPIService
import com.srjhnd.freenews.data.ApplicationDatabase
import com.srjhnd.freenews.data.HeadlineRepository
import com.srjhnd.freenews.viewmodel.HeadlineViewModelFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object InjectionUtils {

    private fun getHeadlineRepository(context: Context): HeadlineRepository {
        val retrofit = Retrofit.Builder().baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create()).build()

        val newsAPIService = retrofit.create(NewsAPIService::class.java)
        val headlineRepository =
            ApplicationDatabase.getInstance(context.applicationContext).headlineDAO()
        return HeadlineRepository.getInstance(
            headlineRepository,
            newsAPIService
        )
    }

    fun provideHeadlineViewModel(context: Context): HeadlineViewModelFactory {
        return HeadlineViewModelFactory(getHeadlineRepository(context))
    }
}