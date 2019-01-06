package bartarys.github.io.hogentapp

import android.app.Application
import bartarys.github.io.hogentapp.dependency.dependencyModule
import org.koin.android.ext.android.startKoin

class ApplicationEntry : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(dependencyModule))
    }
}