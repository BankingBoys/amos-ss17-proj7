package de.fau.amos.virtualledger.android.dagger.component;

import dagger.Component;
import de.fau.amos.virtualledger.android.dagger.module.NetModule;
import de.fau.amos.virtualledger.android.dagger.module.OidcAuthenticationModule;


@OidcAuthenticationScope
@Component(modules = {NetModule.class, OidcAuthenticationModule.class})
public interface OidcAuthenticationComponent {
}
