package de.fau.amos.virtualledger.android.dagger.component;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Georg on 26.06.2017.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface OidcAuthenticationScope {
}
