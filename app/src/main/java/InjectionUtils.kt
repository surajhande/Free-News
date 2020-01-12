import android.content.Context
import com.srjhnd.opennews.data.ApplicationDatabase
import com.srjhnd.opennews.data.HeadlineRepository
import com.srjhnd.opennews.viewmodel.HeadlineViewModelFactory


object InjectionUtils {

    fun getHeadlineRepository(context: Context): HeadlineRepository {
        return HeadlineRepository.getInstance(ApplicationDatabase.getInstance(context.applicationContext).headlineDAO())
    }

    fun provideHeadlineViewModel(context: Context): HeadlineViewModelFactory {
        return HeadlineViewModelFactory(getHeadlineRepository(context))
    }
}