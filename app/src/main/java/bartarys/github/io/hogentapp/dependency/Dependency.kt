package bartarys.github.io.hogentapp.dependency

import android.util.Log
import bartarys.github.io.hogentapp.persistence.*
import bartarys.github.io.hogentapp.settings.ui.settings.SelectTimeModel
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import retrofit2.Retrofit
import java.lang.Exception
import java.sql.Connection

val dependencyModule: Module = module {
    single { get<Persistence>().settings() }
    single { SelectTimeModel() }
    single {
        val db = Database.connect("jdbc:sqlite:app.db", "org.sqlite.JDBC")
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
        transaction(db) {
            SchemaUtils.create(Settings, Days, Goals)
        }
        db
    }
    single { Persistence(get(), get()) }
    single { DatabasePersistence(get()) }
    single { Retrofit.Builder().baseUrl("hogentapp.ddns.net").build() }
    single { get<Retrofit>().create(OnlinePersistence::class.java) }


}