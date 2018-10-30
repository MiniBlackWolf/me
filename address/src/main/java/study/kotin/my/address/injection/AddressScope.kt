package study.kotin.my.address.injection

import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy.RUNTIME
import javax.inject.Scope

/**
 * Identifies a type that the injector only instantiates once. Not inherited.
 *
 * @see javax.inject.Scope @Scope
 */
@Suppress("DEPRECATED_JAVA_ANNOTATION")
@Scope
@Documented
@Retention(RUNTIME)
annotation class AddressScope
