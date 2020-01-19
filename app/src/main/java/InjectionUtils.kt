import android.content.Context
import com.srjhnd.opennews.api.NewsAPIService
import com.srjhnd.opennews.data.ApplicationDatabase
import com.srjhnd.opennews.data.HeadlineRepository
import com.srjhnd.opennews.viewmodel.HeadlineViewModelFactory
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