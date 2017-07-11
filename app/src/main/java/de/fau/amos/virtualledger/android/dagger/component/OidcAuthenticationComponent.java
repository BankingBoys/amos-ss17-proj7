package de.fau.amos.virtualledger.android.dagger.component;

import dagger.Component;
import de.fau.amos.virtualledger.android.api.auth.AuthenticationProvider;
import de.fau.amos.virtualledger.android.api.shared.CallWithToken;
import de.fau.amos.virtualledger.android.authentication.oidc.OidcAuthenticationActivity;
import de.fau.amos.virtualledger.android.dagger.module.AppModule;
import de.fau.amos.virtualledger.android.dagger.module.NetModule;
import de.fau.amos.virtualledger.android.dagger.module.OidcAuthenticationModule;


@OidcAuthenticationScope
@Component(modules = {AppModule.class, NetModule.class, OidcAuthenticationModule.class})
public interface OidcAuthenticationComponent {
    AuthenticationProvider authenticationProvider();

    CallWithToken callWithToken();

    void inject(OidcAuthenticationActivity activity);
}
