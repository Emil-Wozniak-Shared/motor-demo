package pl.ejdev.plugins

import com.zaxxer.hikari.HikariDataSource
import io.higson.runtime.core.HigsonEngine
import io.higson.runtime.core.HigsonEngineFactory
import io.higson.runtime.profiler.jdbc.proxy.DataSourceProxy
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import pl.decerto.hyperon.runtime.sql.DialectRegistry
import pl.decerto.hyperon.runtime.sql.DialectTemplate
import pl.ejdev.example.Motor
import pl.ejdev.service.DictionaryService
import pl.ejdev.service.DriverService
import pl.ejdev.service.QuoteService
import pl.ejdev.service.VehicleService
import javax.sql.DataSource

fun Application.configureDependencyInjection() {
    install(Koin) {
        modules(module {
            single<DialectRegistry> { DialectRegistry().withDialect(property("higson.database.dialect")) }
            single<DialectTemplate> { get<DialectRegistry>().create() }
            single<DataSource> { dataSource(get()) }
            single<DataSourceProxy> { dataSourceProxy(get()) }
            single<HigsonEngineFactory> { higsonEngineFactory(get()) }
            single<HigsonEngine> { higsonEngine(get()) }
            single<Motor> { Motor(get()) }
            single<DictionaryService> { DictionaryService(get()) }
            single<QuoteService> { QuoteService(get()) }
            single<DriverService> { DriverService(get()) }
            single<VehicleService> { VehicleService(get()) }
        })
    }
}

private fun Application.property(name: String) = environment.config.property(name).getString()

private fun DialectRegistry.withDialect(dialect: String): DialectRegistry = apply {
    setDialect(dialect)
}

private fun Application.dataSource(template: DialectTemplate): DataSource = HikariDataSource().apply {
    username = property("higson.database.username")
    password = property("higson.database.password")
    jdbcUrl = property("higson.database.url")
    driverClassName = template.jdbcDriverClassName
}

private fun dataSourceProxy(dataSource: DataSource): DataSourceProxy = DataSourceProxy().also { it.dataSource = dataSource }

private fun Application.higsonEngineFactory(dataSource: DataSource): HigsonEngineFactory {
    val higsonEngineFactory = HigsonEngineFactory()
    higsonEngineFactory.dataSource = dataSource

    if (property("higson.dev.mode").toBoolean()) {
        higsonEngineFactory.isDeveloperMode = true
        higsonEngineFactory.username = property("higson.dev.user")
    }

    return higsonEngineFactory
}

private fun higsonEngine(higsonEngineFactory: HigsonEngineFactory): HigsonEngine {
    higsonEngineFactory.setValidateFunctionArgumentsDataTypes(true)
    return higsonEngineFactory.create()
}