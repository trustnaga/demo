import { PublicClientApplication } from "@azure/msal-browser";

export default defineNuxtPlugin((nuxtApp) => {
    const msalConfig = {
        auth: {
            clientId: 'a8212ad6-c26e-408a-b7be-44e7c6de294e',
            authority: 'https://login.microsoftonline.com/16b3c013-d300-468d-ac64-7eda0820b6d3',
            redirectUri: '/', // Must be registered as a SPA redirectURI on your app registration
            postLogoutRedirectUri: 'http://localhost:3000/' // Must be registered as a SPA redirectURI on your app registrationregistration
        },
        cache: {
          cacheLocation: 'localStorage', // This configures where your cache will be stored
          storeAuthStateInCookie: false, // Set this to "true" if you are having issues on IE11 or Edge
        },
      }
    const msalInstance = new PublicClientApplication(msalConfig);
    return {
        provide: {
            msalInstance : msalInstance
        }
    }

})